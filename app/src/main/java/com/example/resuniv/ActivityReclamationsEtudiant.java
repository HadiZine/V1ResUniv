package com.example.resuniv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class ActivityReclamationsEtudiant extends AppCompatActivity {
    private static EditText Tele, Date, Description;
    private Button Envoyer;
    //private Spinner Anomalie_Rec;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    public String ID_User;
    public   static Spinner anomalie ;

    public static String Tel = "Numero du Tel";
    public static String date = "Date";
    public static String description = "Description";
    public static String Anomalie = "anomalie";


    private long i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclamations_etudiant);

        Tele = (EditText) findViewById(R.id.set_tel);
        Date = (EditText) findViewById(R.id.set_date);
        Description = (EditText) findViewById(R.id.set_description);
        Envoyer = (Button) findViewById(R.id.envoyer_button);
        anomalie = findViewById(R.id.spinner_anomalies);

        //  Anomalie = (Spinner) findViewById(R.id. );

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // pour que la reclamation soit enregistrer par l'emailde l'utilisateur
        //
        //

        Activity_login_etudiant.shP2 = getSharedPreferences("ID_File",MODE_PRIVATE);
        ID_User = Activity_login_etudiant.shP2.getString(Activity_login_etudiant.Id_log,"");



        //


        Envoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final HashMap<String, Object> reclamation_Map = new HashMap<>();

                final String setTel = Tele.getText().toString();
                final String setDate = Date.getText().toString();
                final String setDescription = Description.getText().toString();
                final String setAnomalie = anomalie.getSelectedItem().toString();

                reclamation_Map.put(Tel, setTel);
                reclamation_Map.put(date, setDate);
                reclamation_Map.put(description, setDescription);
                reclamation_Map.put(Anomalie, setAnomalie);
                reclamation_Map.put(Ordre_reclamation, i);
                i=i+1;


                if ((TextUtils.isEmpty(setTel)) || (TextUtils.isEmpty(setDate)) || (TextUtils.isEmpty(setDescription))) {
                    Toast.makeText(getApplicationContext(), "Complete all fields", Toast.LENGTH_LONG).show();

                }
                else
                {
                    db.collection("Reclamations").document(ID_User).set(reclamation_Map);
                    Toast.makeText(getApplicationContext(), "Votre reclamation a été bien enregistrer. ", Toast.LENGTH_LONG).show();

                }


            }
        });

    }

    public static String getOrdre_reclamation() {
        return Ordre_reclamation;
    }

    public static void setOrdre_reclamation(String ordre_reclamation) {
        Ordre_reclamation = ordre_reclamation;
    }

    public static  String Ordre_reclamation = "ordre: ";
    }
