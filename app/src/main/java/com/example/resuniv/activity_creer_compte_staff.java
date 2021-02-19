package com.example.resuniv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class activity_creer_compte_staff extends AppCompatActivity {

    private FirebaseAuth myAuth;
    private ProgressBar progressBar_reg_Staff;
    private TextView textBar_reg_Staff;

    public     static  final  String LOG_TAG ="RestauUniv";
    public static  final String Name_Staff="nom";
    public static  final String Prenom_Staff="Prenom";
    public static  final String Email_Staff="EMAIL";
    public static  final String CIN_Staff="mCIN";
    public static final String user_type_S="Staff";
    FirebaseFirestore db1 = FirebaseFirestore.getInstance();

    public CollectionReference c_r_S = db1.collection("Staff");
    public CollectionReference getC_r_S() {
        return c_r_S;
    }

    public void setC_r_S(CollectionReference c_r_S) {
        this.c_r_S = c_r_S;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_compte_staff);
        TextView textLink = findViewById(R.id.Telcharger_CGU);
        textLink.setMovementMethod(LinkMovementMethod.getInstance());

        myAuth=FirebaseAuth.getInstance();


    }


    public void buttAjouterStaff(View view) {
        progressBar_reg_Staff = findViewById(R.id.progressBar_staff);
        textBar_reg_Staff = findViewById(R.id.textBar_reg_staff);


        EditText iCIN = findViewById(R.id.staff_Cin);
        String C_I_N = iCIN.getText().toString();

        EditText Email = (EditText) findViewById(R.id.staff_mail);
        String email = Email.getText().toString();

        EditText Password=findViewById(R.id.staff_Mdp);
        String mdp=Password.getText().toString();

        EditText Name = (EditText) findViewById(R.id.nstaff_name);
        String Nom = Name.getText().toString();

        EditText Prénom = (EditText) findViewById(R.id.Pstaff_Prenom);
        String pr=Prénom.getText().toString();

        EditText SCIN = findViewById(R.id.staff_Cin);
        String Mcin = SCIN.getText().toString();

        //****************************************
        if (email.isEmpty() || mdp.isEmpty() || Mcin.isEmpty() || Nom.isEmpty() || pr.isEmpty()) {

            //Toast.makeText(getApplicationContext(),"Remplissez les champs",Toast.LENGTH_LONG).show();
            if (email.isEmpty()) {
                Email.setError("S'il vous plait entrez voter Email");
                Email.requestFocus();
                return;
            } else if (Nom.isEmpty()) {
                Name.setError("S'il vous plait entrez voter nom");
                Name.requestFocus();
                return;
            } else if (pr.isEmpty()) {
                Prénom.setError("S'il vous entrez voter prénom");
                Prénom.requestFocus();
                return;
            } else if (Mcin.isEmpty()) {
                SCIN.setError("S'il vous entrez voter Numero d'Hebergement");
                SCIN.requestFocus();
                return;
            } else if (mdp.isEmpty()) {
                Password.setError("S'il vous entrez voter mot de passe");
                Password.requestFocus();
                return;
            }
        }

        else {

            progressBar_reg_Staff.setVisibility(View.VISIBLE);

            textBar_reg_Staff.setVisibility(View.VISIBLE);

            //progressBar_reg_etud.;
            myAuth.createUserWithEmailAndPassword(email,mdp).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override

                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(getApplicationContext(),"Registred Succeffuly",Toast.LENGTH_LONG).show();
                        progressBar_reg_Staff.setVisibility(View.GONE);
                        textBar_reg_Staff.setVisibility(View.GONE);

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),task.getException().toString(),Toast.LENGTH_LONG).show();

                    }
                }
            });

            //**************Map
            Map<String, Object> DataBase_Staff = new HashMap<>();
            DataBase_Staff.put(Name_Staff, Nom);
            DataBase_Staff.put(Prenom_Staff, pr);
            DataBase_Staff.put(Email_Staff, email);
            DataBase_Staff.put(CIN_Staff,Mcin);
            // String champ= idHeb.concat(pr).toString();

            this.setC_r_S(c_r_S);
            db1.collection("Staff").document(email).set(DataBase_Staff).addOnSuccessListener(new OnSuccessListener<Void>() {
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


            Intent intent = new Intent(activity_creer_compte_staff.this, Activity_login_staff.class);
            startActivity(intent);

            //***

        }

    }
}