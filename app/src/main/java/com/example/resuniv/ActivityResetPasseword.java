package com.example.resuniv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityResetPasseword extends AppCompatActivity {
    private EditText Email;
    private Button ResetPassword;
    FirebaseAuth MYAUTH;
    private ProgressBar progressBar_reg_etud;
    private TextView textBar_reg_etud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_passeword);
        Email = findViewById(R.id.editTextTextEmailAddress);
        String mEmail = Email.getText().toString().trim();

        MYAUTH = FirebaseAuth.getInstance();
        ResetPassword = findViewById(R.id.button_reset_mdp);
        progressBar_reg_etud = findViewById(R.id.progressBar_reg_etud);
        textBar_reg_etud = findViewById(R.id.textBar_reg_etud);

        ResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPass();
            }
        });
    }

    private void  resetPass()
    {
        progressBar_reg_etud.setVisibility(View.VISIBLE);
        textBar_reg_etud.setVisibility(View.VISIBLE);

        String mEmail = Email.getText().toString().trim();
        MYAUTH.sendPasswordResetEmail(mEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"Reset the password successfuly",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ActivityResetPasseword.this, Activity_login_etudiant.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Try again please",Toast.LENGTH_LONG).show();

                }
            }
        });

    }
}