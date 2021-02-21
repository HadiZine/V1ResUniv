package com.example.resuniv;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


public class ActivityAccueil extends AppCompatActivity {
    private FirebaseAuth MyAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public int tst_ind;
   // public static SharedPreferences shP;
    public static String txt="5000";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        MyAuth = FirebaseAuth.getInstance();


        Animation topanim, buttanim;
        ImageView logo;
        ImageView mTxt;
        topanim = AnimationUtils.loadAnimation(this, R.anim.button);
        buttanim = AnimationUtils.loadAnimation(this, R.anim.top_animate);


        logo = findViewById(R.id.imagechef);
        mTxt = findViewById(R.id.logo);
        logo.setAnimation(topanim);
        mTxt.setAnimation(buttanim);

        Activity2choixProfil.shP = getSharedPreferences("typeProfil",MODE_PRIVATE);
        tst_ind = Activity2choixProfil.shP.getInt("Profil",0);




    }


    public void onStart()
    {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = MyAuth.getCurrentUser();
        if (currentUser == null) {


            /*if(db.collection("Etudiant").getPath().equals("Etudiant")){

                Intent Intent_tologin_etud = new Intent(Accueil.this,Activity_login_etudiant.class);
                startActivity(Intent_tologin_etud);
            }

            else if(db.collection("Staff").getPath().equals("Staff")){

                Intent Intent_tologin_staff = new Intent(Accueil.this,Activity_login_staff.class);
                startActivity(Intent_tologin_staff);
            }*/

            if(tst_ind==0){

                Intent Intent_tologin = new Intent(this,MainActivity.class);
                startActivity(Intent_tologin);

            }
            else{
                if(tst_ind==2){

                    Intent Intent_tologin = new Intent(this,Activity_login_etudiant.class);
                    startActivity(Intent_tologin);

                }

                if(tst_ind==1){

                    Intent Intent_tologin = new Intent(this,Activity_login_staff.class);
                    startActivity(Intent_tologin);

                }

            }




        }

        else
        {

            // Intent Intent_choix_profil = new Intent(Accueil.this,Activity2choixProfil.class);
            //startActivity(Intent_choix_profil);
            /*
            indice = Activity2choixProfil.getIndice_choix_profil();
            if(indice==1){
                Intent Intent_tologin_staff = new Intent(Accueil.this,ProfilStaff.class);
                startActivity(Intent_tologin_staff);
            }
            else if(indice==2){
                Intent Intent_tologin_etud = new Intent(Accueil.this,ProfilEtudiant.class);
                startActivity(Intent_tologin_etud);
            }*/

            //CollectionReference dbref = db.collection("Etudiant");

            //**********


            //**********
            // shP = getSharedPreferences("typeProfil",MODE_PRIVATE);

           // Toast.makeText(getApplicationContext(),"test_indice="+tst_ind,Toast.LENGTH_LONG).show();
            if(tst_ind==2)
            {

                Intent Intent_tologin_etud = new Intent(this,ActivityMenu.class);
                startActivity(Intent_tologin_etud);

            }

            if(tst_ind==1)
            {
                Intent Intent_tologin_staff = new Intent(this,ActivityProfilStaff.class);
                startActivity(Intent_tologin_staff);
            }


        }

    }



}