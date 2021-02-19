package com.example.resuniv;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ActivityProfilStaff extends AppCompatActivity {

    Button btn ;
    Button btn_reclamation ;
    Button btn_reservation;
    Button btn_verification_QR;


    TextView nameRecuStaff;

    public FirebaseAuth mAuth;
    public FirebaseFirestore Root_fb;

    public String ID_tst;

    private    static  final  String LOG_TAG ="RestauUniv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_staff);


        Root_fb = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Activity_login_staff.shP3 =  getSharedPreferences("ID_File",MODE_PRIVATE);
        ID_tst = Activity_login_staff.shP3.getString(Activity_login_staff.Id_log,"Staff.RestoUniv@gmail.com");

        nameRecuStaff = findViewById(R.id.nom_prenom);

        DocumentReference Mydocument = Root_fb.collection("Staff").document(ID_tst);
        Mydocument.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if(documentSnapshot.exists()){
                    nameRecuStaff.setText(documentSnapshot.getString(activity_creer_compte_staff.Name_Staff)+" "+documentSnapshot.getString(activity_creer_compte_staff.Prenom_Staff));

                }
                else{
                    Toast.makeText(getApplicationContext()," User not found ",Toast.LENGTH_LONG).show();
                }
            }
        });











        btn = findViewById(R.id.btn_remlissage);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityProfilStaff.this , ActivityRemplisaageMenu.class);
                startActivity(intent);
            }
        });

        btn_reclamation = findViewById(R.id.btn_consultation_reclamation);
        btn_reclamation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(ProfilStaff.this , ConsultationReclamation.class);
                Intent intent = new Intent(ActivityProfilStaff.this , ActivityConsultationReclamation.class);
                startActivity(intent);
            }
        });

        btn_reservation = findViewById(R.id.btn_consultation_reservation);
        btn_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(ProfilStaff.this , ConsultationReclamation.class);
                Intent intent = new Intent(ActivityProfilStaff.this , ActivitySuiviReservation.class);
                startActivity(intent);
            }
        });

        btn_verification_QR = findViewById(R.id.Verification_QR);
        btn_verification_QR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(ProfilStaff.this , ConsultationReclamation.class);
                Intent intent = new Intent(ActivityProfilStaff.this , ActivityQR.class);
                startActivity(intent);
            }
        });


    }
}