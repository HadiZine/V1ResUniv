package com.example.resuniv;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ActivityMenu extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private View mView;
    private TextView email,Id,Nom,Prenom ,mEmail;
    public String ID_tst;
    FirebaseUser user;
    FirebaseFirestore Root_fb;

    private ImageView imgTool;

    private DrawerLayout drawer;




    //FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);



        setSupportActionBar(toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = findViewById(R.id.drawer_layout);

                drawer.openDrawer(GravityCompat.START);

            }
        });





        /*

        imgTool = findViewById(R.id.NvToolbar);

        imgTool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.openDrawer(GravityCompat.START);
            }
        });

         */



        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        ///****************************************************


        FirebaseFirestore Root_fb=FirebaseFirestore.getInstance();


        mView=navigationView.getHeaderView(0);
        Nom=mView.findViewById(R.id.Nom_prenom);

       // Prenom=mView.findViewById(R.id.Prenom_etudiant);
        Id=mView.findViewById(R.id.IdHeadr);
        mEmail=mView.findViewById(R.id.EmailHeader);


        //:  ******************************************************

        Activity_login_etudiant.shP2 = getSharedPreferences("ID_File",MODE_PRIVATE);
        ID_tst = Activity_login_etudiant.shP2.getString(Activity_login_etudiant.Id_log,"master.stri2020@gmail.com");

        ///   ******************************************

        DocumentReference Mydocument = Root_fb.collection("Etudiant").document(ID_tst);
        Mydocument.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if(documentSnapshot.exists()){
                    Nom.setText(documentSnapshot.getString(activity_creer_compte.Name_Etud));
                    mEmail.setText(documentSnapshot.getString(activity_creer_compte.Email_Etud));
                    Id.setText(documentSnapshot.getString(activity_creer_compte.ID_Etud));

                }

            }
        });




        ///////****************************************************
        navigationView.setNavigationItemSelectedListener(item -> {
            int i=item.getItemId();

            if(i==R.id.menu_repas){
                Intent intent_to_menu=new Intent(this,ActivityConsultationMenu.class);
                startActivity(intent_to_menu);
                Toast.makeText(getApplicationContext(), "Le menu de cette semain", Toast.LENGTH_SHORT).show();

            }

            else if (i==R.id.rec_test)
            {
                Intent intent_to_rec=new Intent(this,ActivityReclamationsEtudiant.class);
                startActivity(intent_to_rec);
                Toast.makeText(getApplicationContext(), "Reclamer-Vous", Toast.LENGTH_SHORT).show();


            }
            else if(i==R.id.Apropos) {
                Intent intent_to_info = new Intent(this,ActivityApropos.class);
                startActivity(intent_to_info);
                Toast.makeText(getApplicationContext(), "Explorer notre application", Toast.LENGTH_SHORT).show();

            }
            else if (i==R.id.Les_reservations)
            {
                Intent intent_to_reservations= new Intent(this,ActivityReservations.class);
                startActivity(intent_to_reservations);
                Toast.makeText(getApplicationContext(), "Activity reservations", Toast.LENGTH_SHORT).show();


            }
            else if (i==R.id.QR)
            {
                Intent intent_to_reservations= new Intent(this,ActivityGenerationQR.class);
                startActivity(intent_to_reservations);
                Toast.makeText(getApplicationContext(), "Activity Generation QR", Toast.LENGTH_SHORT).show();


            }

            else if (i==R.id.Log_out)
            {
                Intent intent_to_login= new Intent(this,Activity_login_etudiant.class);
                startActivity(intent_to_login);
                finish();
                Toast.makeText(getApplicationContext(), "Deconnecter", Toast.LENGTH_SHORT).show();


            }
            return true;
        });





    }








    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(R.id.action_deconnexion==item.getItemId()){

            FirebaseAuth.getInstance().signOut();
            Intent Intent_deconnexion = new Intent(this,Activity_login_etudiant.class);
            startActivity(Intent_deconnexion);

        }
        return true;
    }




}