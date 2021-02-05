package com.example.resuniv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Activity_login_etudiant extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_etudiant);

        TextView text_inscrir = findViewById(R.id.clique_inscrir_etudiant);
        text_inscrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentInscription = new Intent(Activity_login_etudiant.this,activity_creer_compte.class);
                startActivity(intentInscription);

            }
        });

    }


    /*
    public void buttInsciption(View view) {

        Intent intentInscription = new Intent(this,activity_creer_compte.class);
        startActivity(intentInscription);

    }*/
}