package com.example.resuniv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class activity_creer_compte extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_compte);
        TextView textLink = findViewById(R.id.Telcharger_CGU);
        textLink.setMovementMethod(LinkMovementMethod.getInstance());
    }
}