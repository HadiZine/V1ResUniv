package com.example.resuniv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    //1c3d3802e24d5d6cec0ac6af645c014a75ff97e1
    //test add
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttBienvenue(View view) {
        Intent intent2 = new Intent(this,Activity2choixProfil.class);
        startActivity(intent2);

    }
}