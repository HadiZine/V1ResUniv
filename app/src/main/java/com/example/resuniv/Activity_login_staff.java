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

public class Activity_login_staff extends AppCompatActivity {

    public static SharedPreferences shP3;
    public static String Id_log = "IDLog";
    public static String My_Id;

    private TextView passwReset_staf;

    private Button cnx_staff;
    private EditText YourEmail_staff;
    private EditText YourPassword_staff;

    private FirebaseAuth MyAuth;

    private ProgressBar progressBar_reg_Staff;
    private TextView    textBar_reg_Staff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_staff);
        MyAuth = FirebaseAuth.getInstance();

        TextView passwReset_staf = findViewById(R.id.MotPasseforgetStaf);
        passwReset_staf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToreset = new Intent(Activity_login_staff.this,ActivityResetPasseword.class);
                startActivity(intentToreset);


            }
        });



        shP3 = getSharedPreferences("ID_File",MODE_PRIVATE);


        cnx_staff = findViewById(R.id.Connexion_button_staff);
        progressBar_reg_Staff = findViewById(R.id.progressBar_reg_staff);
        textBar_reg_Staff = findViewById(R.id.textBar_reg_staff);

        cnx_staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                My_Id = shP3.getString(Id_log,"Staff.RestoUniv@gmail.com");
                YourEmail_staff = (EditText) findViewById(R.id.username_Staff);
                YourPassword_staff = (EditText) findViewById(R.id.password_Staff);
                String Email = YourEmail_staff.getText().toString();
                shP3.edit().putString(Id_log,Email).apply();
                String Password = YourPassword_staff.getText().toString();
                if(Email.isEmpty()||Password.isEmpty())
                {
                    if(Email.isEmpty()){
                        YourEmail_staff.setError("S'il vous plait entrez voter Email");
                        YourEmail_staff.requestFocus();
                        return;
                    }
                    if(Password.isEmpty()){
                        YourPassword_staff.setError("S'il vous plait entrez voter mot de passe");
                        YourPassword_staff.requestFocus();
                        return;
                    }
                }

                else{



                    MyAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressBar_reg_Staff.setVisibility(View.VISIBLE);
                                textBar_reg_Staff.setVisibility(View.VISIBLE);
                                Toast.makeText(getApplicationContext(),"Log in succefully",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Activity_login_staff.this, ActivityProfilStaff.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),task.getException().toString(),Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }

            }

        });


        TextView text_inscrir = findViewById(R.id.clique_inscrir_staff);
        text_inscrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentInscription_staff = new Intent(Activity_login_staff.this,activity_creer_compte_staff.class);
                startActivity(intentInscription_staff);
            }
        });
    }
}