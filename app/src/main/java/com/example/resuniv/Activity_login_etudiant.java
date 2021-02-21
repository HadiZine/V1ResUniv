package com.example.resuniv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Activity_login_etudiant extends AppCompatActivity {

    private Button Sign_In;

    private ProgressBar progressBar_reg_etud;
    private TextView    textBar_reg_etud;



    private TextView passwReset;
    private EditText YourEmail, YourPassword;
    private FirebaseAuth MyAuth;

    public static SharedPreferences shP2;
    public static String Id_log = "IDLog";
    public static String My_Id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_etudiant);

        TextView passwReset = findViewById(R.id.MotPasseWord);
        passwReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToreset = new Intent(Activity_login_etudiant.this,ActivityResetPasseword.class);
                startActivity(intentToreset);


            }
        });

        TextView text_inscrir = findViewById(R.id.clique_inscrir_etudiant);
        text_inscrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentInscription = new Intent(Activity_login_etudiant.this,activity_creer_compte.class);
                startActivity(intentInscription);

            }
        });


        shP2 = getSharedPreferences("ID_File",MODE_PRIVATE);



        Sign_In = findViewById(R.id.Connexion_button_etudiant);
        progressBar_reg_etud = findViewById(R.id.progressBar_reg_etud);
        textBar_reg_etud = findViewById(R.id.textBar_reg_etud);


        Sign_In.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                My_Id = shP2.getString(Id_log,"master.stri2020@gmail.com");
                YourEmail = (EditText) findViewById(R.id.username_etudiant);
                YourPassword = (EditText) findViewById(R.id.password_etudiant);
                String Email = YourEmail.getText().toString();
                shP2.edit().putString(Id_log,Email).apply();
                String Password = YourPassword.getText().toString();
                if(Email.isEmpty()||Password.isEmpty())
                {
                    if(Email.isEmpty()){
                        YourEmail.setError("S'il vous plait entrez voter Email");
                        YourEmail.requestFocus();
                        return;
                    }
                    if(Password.isEmpty()){
                        YourPassword.setError("S'il vous plait entrez voter mot de passe");
                        YourPassword.requestFocus();
                        return;
                    }
                }

                else{



                    MyAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressBar_reg_etud.setVisibility(View.VISIBLE);
                                textBar_reg_etud.setVisibility(View.VISIBLE);
                                Toast.makeText(getApplicationContext(),"Log in succefully",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Activity_login_etudiant.this, ActivityMenu.class);
                                startActivity(intent);

                            }
                            else
                            {
                                progressBar_reg_etud.setVisibility(View.GONE);
                                textBar_reg_etud.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),task.getException().toString(),Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }

            }
        });

    }

}