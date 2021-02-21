package com.example.resuniv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity2choixProfil extends AppCompatActivity {

    private ImageView imageProfil1;
    private TextView textProfil1;
    private TextView textProfil2;
    private ImageView imageProfil2;
    private Button buttValider;
    private int indice_choix_profil=0;

    public static SharedPreferences shP;
    public static final String user_type="Profil";
    public static int indiceProfil;


    public static int getIndiceProfil() {
        return indiceProfil;
    }

    public static void setIndiceProfil(int indiceProfil) {
        Activity2choixProfil.indiceProfil = indiceProfil;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity2choix_profil);

        imageProfil1 = (ImageView) findViewById(R.id.imageView9);
        buttValider = (Button)findViewById(R.id.button2);
        imageProfil2 = (ImageView) findViewById(R.id.imageView10);
        textProfil2 = (TextView) findViewById(R.id.textView2);
        textProfil1 = (TextView) findViewById(R.id.textView1);


        shP = getSharedPreferences("typeProfil",MODE_PRIVATE);

        indiceProfil = shP.getInt(user_type, 0);




        imageProfil1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    indice_choix_profil = 1;

                    //buttValider.setEnabled(view.isEnabled());
                    buttValider.setEnabled(true);
                    imageProfil2.animate().alpha((float) 0.40);
                    imageProfil1.animate().alpha((float) 1);
                    //imageProfil1.setEnabled(view.isEnabled());
                    //imageProfil2.setVisibility(view.INVISIBLE);
                    //imageProfil2.setSelected(false);
                    shP.edit().putInt(user_type,1).apply();

                    //textProfil2.setVisibility(view.INVISIBLE);
                    //textProfil2.animate().alpha((float) 0.40);
                    //textProfil2.setVisibility(View.INVISIBLE);
                    //textProfil1.setVisibility(View.VISIBLE);

                    textProfil2.animate().alpha((float) 0.40);
                    textProfil1.animate().alpha((float) 1);
                    //textProfil2.animate().alpha((float) 0.40);
                    //textProfil2.setSelected(false);
                    //imageProfil1.animate().alpha((float) 0.40);
                }
        });





        imageProfil2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                indice_choix_profil = 2;

                //buttValider.setEnabled(view.isEnabled());
                buttValider.setEnabled(true);
                imageProfil1.animate().alpha((float) 0.40);
                imageProfil2.animate().alpha((float) 1);

                //imageProfil2.setEnabled(view.isEnabled());
                //imageProfil1.setSelected(false);
                shP.edit().putInt(user_type,2).apply();

                //textProfil1.setVisibility(view.INVISIBLE);
                //textProfil1.setVisibility(View.INVISIBLE);
                textProfil1.animate().alpha((float) 0.40);
                textProfil2.animate().alpha((float) 1);
                //textProfil2.setVisibility(View.VISIBLE);
                //textProfil1.setSelected(false);
                //imageProfil2.animate().alpha((float) 0.40);
            }
        });

    }

    public void buttValider(View view) {

        if(indice_choix_profil==1) {
            Intent intent3 = new Intent(this, Activity_login_staff.class);
            startActivity(intent3);
        }
        if(indice_choix_profil==2) {
            Intent intent4 = new Intent(this, Activity_login_etudiant.class);
            startActivity(intent4);
        }
    }
}

   /* public void buttChoix1(View view) {
        imageProfil1 = (ImageView) findViewById(R.id.imageView9);
        imageProfil1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttValider = (Button)findViewById(R.id.button2);
                buttValider.setEnabled(view.isEnabled());
                imageProfil2 = (ImageView) findViewById(R.id.imageView10);
                imageProfil2.animate().alpha((float) 0.40);
                //imageProfil1.setEnabled(view.isEnabled());
                //imageProfil2.setVisibility(view.INVISIBLE);
                //imageProfil2.setSelected(false);
                textProfil2 = (TextView) findViewById(R.id.textView2);
                textProfil2.setVisibility(view.INVISIBLE);
                //textProfil2.setSelected(false);
                //imageProfil1.animate().alpha((float) 0.40);
            }
        });
    }*/

   /* public void buttChoix2(View view) {
        imageProfil2 = (ImageView) findViewById(R.id.imageView10);
        imageProfil2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttValider = (Button)findViewById(R.id.button2);
                buttValider.setEnabled(view.isEnabled());
                imageProfil1 = (ImageView) findViewById(R.id.imageView9);
                imageProfil1.animate().alpha((float) 0.40);
                //imageProfil2.setEnabled(view.isEnabled());
                //imageProfil1.setSelected(false);
                textProfil1 = (TextView) findViewById(R.id.textView1);
                textProfil1.setVisibility(view.INVISIBLE);
                //textProfil1.setSelected(false);
                //imageProfil2.animate().alpha((float) 0.40);
            }
        });

    }
}*/