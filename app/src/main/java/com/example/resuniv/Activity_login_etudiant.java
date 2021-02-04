package com.example.resuniv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Activity_login_etudiant extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_etudiant);
    }

    public void buttInsciption(View view) {

        Intent intentInscription = new Intent(this,activity_creer_compte.class);
        startActivity(intentInscription);

    }
}