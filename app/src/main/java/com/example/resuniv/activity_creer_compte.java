package com.example.resuniv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class activity_creer_compte extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private ProgressBar progressBar_reg_etud;
    private TextView    textBar_reg_etud;

    private Button BoutValide;
    private FirebaseFirestore db;
    private    static  final  String LOG_TAG ="RestauUniv";
    public static  final String Name_Etud="nom";
    public static  final String Prenom_Etud="Prenom";
    public static  final String Email_Etud="EMAIL";
    public static  final String ID_Etud="IDHEBER";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_compte);
        TextView textLink = findViewById(R.id.Telcharger_CGU);
        textLink.setMovementMethod(LinkMovementMethod.getInstance());
        db=FirebaseFirestore.getInstance();



    }


    public void buttAjouterEtudiant(View view){

        mAuth = FirebaseAuth.getInstance();


        progressBar_reg_etud = findViewById(R.id.progressBar_reg_etud);
        textBar_reg_etud = findViewById(R.id.textBar_reg_etud);


        EditText idHebergement = findViewById(R.id.user_ID_etudiant);
        String idHeb = idHebergement.getText().toString();


        EditText Email = (EditText) findViewById(R.id.user_mail_etudiant);
        String email = Email.getText().toString();


        EditText Password=findViewById(R.id.user_Mdp_etudiant);
        String mdp=Password.getText().toString();

        EditText Name = (EditText) findViewById(R.id.nom_etudiant);
        String Nom = Name.getText().toString();

        EditText Prénom = (EditText) findViewById(R.id.Prenom_etudiant);
        String pr=Prénom.getText().toString();


        //****
        if (email.isEmpty() || mdp.isEmpty() || idHeb.isEmpty() || Nom.isEmpty() || pr.isEmpty())
        {


            if(Nom.isEmpty())
            {
                Name.setError("S'il vous plait entrez voter nom");
                Name.requestFocus();
                return;
            }
            else if(pr.isEmpty())
            {
                Prénom.setError("S'il vous entrez voter prénom");
                Prénom.requestFocus();
                return;
            }
            else if(idHeb.isEmpty())
            {
                idHebergement.setError("S'il vous entrez voter Numero d'Hebergement");
                idHebergement.requestFocus();
                return;
            }
           else if(email.isEmpty()){
            Email.setError("S'il vous plait entrez voter Email");
            Email.requestFocus();
            return;
        }
            else if(mdp.isEmpty())
            {
                Password.setError("S'il vous entrez voter mot de passe");
                Password.requestFocus();
                return;
            }


        }
        else {

            progressBar_reg_etud.setVisibility(View.VISIBLE);

            textBar_reg_etud.setVisibility(View.VISIBLE);

            //progressBar_reg_etud.;
            mAuth.createUserWithEmailAndPassword(email,mdp).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override

                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(getApplicationContext(),"Registred Succeffuly",Toast.LENGTH_LONG).show();
                        progressBar_reg_etud.setVisibility(View.GONE);
                        textBar_reg_etud.setVisibility(View.GONE);

                        Verification();

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),task.getException().toString(),Toast.LENGTH_LONG).show();

                    }
                }
            });


            Map<String, Object> DataBase = new HashMap<>();
            DataBase.put(Name_Etud, Nom);
            DataBase.put(Email_Etud, email);
            DataBase.put(ID_Etud, idHeb);
            DataBase.put(Prenom_Etud, pr);


            db.collection("Etudiant").document(email).set(DataBase).addOnSuccessListener(new OnSuccessListener<Void>() {


                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(LOG_TAG,"C'est bon la Base Donée est bon");
                }


            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(LOG_TAG,"Attetion ELLE EST PAS CREER");
                }
            });


            Intent intent = new Intent(activity_creer_compte.this, Activity_login_etudiant.class);
            startActivity(intent);

        }


    }

    private void Verification(){
        FirebaseUser currentUser =mAuth.getCurrentUser();
        currentUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Intent intent = new Intent(activity_creer_compte.this, Activity_login_etudiant.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Registred Succeffuly",Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}