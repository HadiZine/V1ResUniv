package com.example.resuniv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ActivityMethodePaiement extends AppCompatActivity {

    public FirebaseAuth mAuth;
    public FirebaseFirestore Root_fb;

    private    static  final  String TAG ="RestauUniv";
    private    static  final  String LOG_TAG ="RestauUniv";

    public String ID_tst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_methode_paiement);

        mAuth = FirebaseAuth.getInstance();
        Root_fb = FirebaseFirestore.getInstance();

        //**********
        Activity_login_etudiant.shP2 = getSharedPreferences("ID_File",MODE_PRIVATE);
        ID_tst = Activity_login_etudiant.shP2.getString(Activity_login_etudiant.Id_log,"master.stri2020@gmail.com");
        //*********

        //***Mis-à-jour base donnee****
        Root_fb.collection("Etudiant").document(ID_tst).collection("Reservationn").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Log.d(TAG, document.getId() + " => " + document.getData());

                        String tstS = document.getId();
                        list.add(tstS);

                    }

                    for (String idDate : list) {
                        Root_fb.collection("Etudiant").document(ID_tst).collection("Reservationn").document(idDate).update(ActivityReservationPersonnalise.Onclique,"Invalid").addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "Username updated!");

                            }
                        });
                    }




                } else {


                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }



        });

        //*****************



    }
}