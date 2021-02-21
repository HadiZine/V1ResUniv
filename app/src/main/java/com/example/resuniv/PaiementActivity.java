package com.example.resuniv;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.resuniv.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PaiementActivity extends AppCompatActivity {

    public FirebaseAuth mAuth;
    public FirebaseFirestore Root_fb;

    private    static  final  String TAG ="RestauUniv";
    private    static  final  String LOG_TAG ="RestauUniv";

    public String ID_tst;

    private ProgressBar progressBar_reg_etud;
    private TextView    textBar_reg_etud;
    private LinearLayout linearLayout;






    Button paiement;
    TextView dateExp;
    Spinner mm;
    Spinner yy;
    EditText carte_num,code_validation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paiement);

        progressBar_reg_etud = findViewById(R.id.progressBar_reg_etud);
        textBar_reg_etud = findViewById(R.id.textBar_reg_etud);
        linearLayout = findViewById(R.id.linearLayoutP);

        mAuth = FirebaseAuth.getInstance();
        Root_fb = FirebaseFirestore.getInstance();

        paiement = findViewById(R.id.validerPaim);
        dateExp = findViewById(R.id.date_expiratio);
        //mm = findViewById(R.id.MM);
        //yy = findViewById(R.id.YY);



        //**********
        Activity_login_etudiant.shP2 = getSharedPreferences("ID_File",MODE_PRIVATE);
        ID_tst = Activity_login_etudiant.shP2.getString(Activity_login_etudiant.Id_log,"master.stri2020@gmail.com");
        //*********


            //********************************
            if(HomeFragment.typeChoix==1) {
                paiement.setText(ActivityPaiementComplet.PrixApayerC);
            }

            if(HomeFragment.typeChoix==2) {
                paiement.setText(ActivityPaiementPersonnalise.PrixApayer);
            }
            //*************************
            paiement.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {



                    carte_num=findViewById(R.id.N_carte);
                    code_validation=findViewById(R.id.code_paiement);

                    String mcart=carte_num.getText().toString();
                    String mcode_valide=code_validation.getText().toString();

                if(mcart.isEmpty()||mcode_valide.isEmpty())
                {
                    if(mcart.isEmpty()){
                        carte_num.setError("SVP entrez vos coordonnées banquaires");
                        carte_num.requestFocus();
                        //return;
                    }
                    if(mcode_valide.isEmpty()){
                        code_validation.setError("SVP entrez votre code de validation");
                        code_validation.requestFocus();
                        //return;
                    }
                }

                else{
                    progressBar_reg_etud.setVisibility(View.VISIBLE);
                    textBar_reg_etud.setVisibility(View.VISIBLE);


                        Root_fb.collection("Etudiant").document(ID_tst).collection("Reservationn").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {
                                progressBar_reg_etud.setVisibility(View.GONE);
                                textBar_reg_etud.setVisibility(View.GONE);
                                List<String> list = new ArrayList<>();

                                for (QueryDocumentSnapshot document : task.getResult()) {



                                    Log.d(TAG, document.getId() + " => " + document.getData());

                                    String tstS = document.getId();
                                    list.add(tstS);

                                }

                                for (String idDate : list) {
                                    Root_fb.collection("Etudiant").document(ID_tst).collection("Reservationn").document(idDate).update(ActivityReservationPersonnalise.Onclique,"Invalid").addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "Username updated!");

                                        }
                                    });
                                }




                            } else {


                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }



                    });

                    //***


                    AlertDialog.Builder builder = new AlertDialog.Builder(PaiementActivity.this);
                    // builder.setMessage(result.getContents());
                    builder.setTitle("Operation validée.");
                    builder.setMessage("Un code QR est disponible dans votre Menu.");
                    builder.setIcon( R.drawable.ic_baseline_check_box_24);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                        //linearLayout.setVisibility(View.VISIBLE);
                        builder.setPositiveButton("Retour au Menus.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                progressBar_reg_etud.setVisibility(View.GONE);
                                textBar_reg_etud.setVisibility(View.GONE);
                                // scanCode();
                                Intent intent = new Intent(PaiementActivity.this, ActivityMenu.class);
                                startActivity(intent);
                                finish();

                            }
                        }).setPositiveButtonIcon(getDrawable(R.drawable.ic_baseline_home_24)).setNegativeButton("Consulter vos réservations.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                progressBar_reg_etud.setVisibility(View.GONE);
                                textBar_reg_etud.setVisibility(View.GONE);
                                Intent intent = new Intent(PaiementActivity.this, ActivityReservations.class);
                                startActivity(intent);
                                finish();
                            }
                        }).setNegativeButtonIcon(getDrawable(R.drawable.ic_mes_reservations));
                    }
                    AlertDialog dialog = builder.create();
                    //dialog.setIconAttribute(R.drawable.ic_baseline_check_circle);
                    dialog.show();
                    //progressBar_reg_etud.setVisibility(View.INVISIBLE);
                    //textBar_reg_etud.setVisibility(View.INVISIBLE);
                   //
                    // linearLayout.setVisibility(View.INVISIBLE);





                }



                                    }
            });



        /*
        mm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dateExp.setText(" "+mm.getSelectedItem()+" / "+yy.getSelectedItem()+" ");

            }
        });

        yy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dateExp.setText(" "+mm.getSelectedItem()+" / "+yy.getSelectedItem()+" ");

            }
        });
        **************************************


        ************************************

*/

         //dateExp.setText(mm.getSelectedItem().toString());







    }

}