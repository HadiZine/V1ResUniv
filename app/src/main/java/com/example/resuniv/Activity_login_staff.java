package com.example.resuniv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Activity_login_staff extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_staff);

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