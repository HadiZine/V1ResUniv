package com.example.resuniv;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ChoixTypeReservation extends AppCompatActivity {

    public TextView txt_NameRecuReservation;
    //public TextView txt_PrenomRecuReservation;

    public FirebaseAuth mAuth;
    public FirebaseFirestore Root_fb;//+" "+documentSnapshot.getString(activity_creer_compte.Prenom_Etud

    public String ID_tst;

    private    static  final  String LOG_TAG ="RestauUniv";

    private CheckBox ch_Complet, ch_person;
    private Button button_ChoixType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_type_reservation);

        Root_fb = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Activity_login_etudiant.shP2 = getSharedPreferences("ID_File",MODE_PRIVATE);
        ID_tst = Activity_login_etudiant.shP2.getString(Activity_login_etudiant.Id_log,"master.stri2020@gmail.com");


        txt_NameRecuReservation = findViewById(R.id.Nom_Bienvenu);

        DocumentReference Mydocument = Root_fb.collection("Etudiant").document(ID_tst);
        Mydocument.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if(documentSnapshot.exists()){
                    txt_NameRecuReservation.setText(documentSnapshot.getString(activity_creer_compte.Name_Etud)+" "+documentSnapshot.getString(activity_creer_compte.Prenom_Etud));

                }
                else{
                    Toast.makeText(getApplicationContext()," User not found ",Toast.LENGTH_LONG).show();
                }
            }
        });
        //***ChoixType****

        ch_Complet= findViewById(R.id.checkBox_complete);
        ch_person= findViewById(R.id.checkBox_Personalisé);
        button_ChoixType= findViewById(R.id.buttonTypeReservation);

    }

    public void onCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();
        switch(view.getId()) {
            case R.id.checkBox_complete:
                if (checked){
                    ch_person.setEnabled(false);
                    button_ChoixType.setEnabled(true);

                    button_ChoixType.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent_goCompl = new Intent(ChoixTypeReservation.this, ActivityReservationComplete.class);
                            startActivity(intent_goCompl);


                        }
                    });

                }
             else{ch_person.setEnabled(true);
                    button_ChoixType.setEnabled(false);}
                break;

            case R.id.checkBox_Personalisé:
                if (checked){
                    ch_Complet.setEnabled(false);
                    button_ChoixType.setEnabled(true);
                    button_ChoixType.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent_goPersonn = new Intent(ChoixTypeReservation.this, ActivityReservationPersonnalise.class);
                            startActivity(intent_goPersonn);


                        }
                    });

                }
               else{ch_Complet.setEnabled(true);
                    button_ChoixType.setEnabled(false);}
                break;
            // TODO: Veggie sandwich
        }
    }
}