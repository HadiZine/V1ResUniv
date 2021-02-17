package com.example.resuniv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ActivityPaiementPersonnalise extends AppCompatActivity {

    public FirebaseAuth mAuth;
    public FirebaseFirestore Root_fb;

    private    static  final  String TAG ="RestauUniv";
    private    static  final  String LOG_TAG ="RestauUniv";

    public String ID_tst;

    private final float prix_repas = (float) 1.5;
    private TextView Prix_reservation_Personnaliset;
    private static int cmpt_repas;

    public static ListView Lv_Personnalise;
    public static String[] List_reservation_personnalise = new String[]{"","","","","","",""};
    private int indice_list=0;
    public static String day1, dej1, dinn1;

    private Button Paiement_butt_personnalise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paiement_personnalise);

        Prix_reservation_Personnaliset = findViewById(R.id.prix_reservation_Personnalise);
        Lv_Personnalise = findViewById(R.id.ListViewPersonnalise);

        mAuth = FirebaseAuth.getInstance();
        Root_fb = FirebaseFirestore.getInstance();

        //**********
        Activity_login_etudiant.shP2 = getSharedPreferences("ID_File",MODE_PRIVATE);
        ID_tst = Activity_login_etudiant.shP2.getString(Activity_login_etudiant.Id_log,"master.stri2020@gmail.com");
        //*********

        Root_fb.collection("Etudiant").document(ID_tst).collection("Reservationn").whereEqualTo(ActivityReservationPersonnalise.Onclique,"Active").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                cmpt_repas=0;

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Log.d(TAG, document.getId() + " => " + document.getData());

                        day1 = document.getString(ActivityReservationPersonnalise.date_Reservation);
                        //dejeuner1.setVisibility(View.VISIBLE);
                        //dejeuner1.setText(documentSnapshot.getBoolean(ProfilEtudiant.Dej).toString());
                        dej1 = document.getString(ActivityReservationPersonnalise.Dej);
                        if(dej1.equals("Déjeuner")){
                            cmpt_repas++;
                        }

                        dinn1=document.getString(ActivityReservationPersonnalise.Dinner);
                        if(dinn1.equals("Diner")){
                            cmpt_repas++;
                        }

                        List_reservation_personnalise[indice_list]= "Le : "+day1+"          "+dej1+"         "+dinn1;
                        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ActivityPaiementPersonnalise.this, android.R.layout.simple_list_item_1,ActivityPaiementPersonnalise.List_reservation_personnalise);
                        Lv_Personnalise.setAdapter(adapter);
                        indice_list++;

                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                       }

                Prix_reservation_Personnaliset.setText("Prix Total: "+String.valueOf(cmpt_repas*prix_repas)+" DH");
            }



        });















        //****************









        Paiement_butt_personnalise=findViewById(R.id.Paiment_personnalise);
        Paiement_butt_personnalise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGo_PaimentP = new Intent(ActivityPaiementPersonnalise.this, ActivityMethodePaiement.class);
                startActivity(intentGo_PaimentP);
            }
        });

    }
}