package com.example.resuniv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ActivityReservations extends AppCompatActivity {
    public static ListView Lv_reservations;
    //public static String[] List_reservations = new String[]{} ;
    public static ArrayList<String> arrayList = new ArrayList<>();

    public FirebaseAuth mAuth;
    public FirebaseFirestore Root_fb;

    private    static  final  String TAG ="RestauUniv";
    private    static  final  String LOG_TAG ="RestauUniv";

    public String ID_tst;

    private int indice_list=0;
    public static String day1, dej1, dinn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);
        Lv_reservations=findViewById(R.id.ListView_Reservations);
        mAuth = FirebaseAuth.getInstance();
        Root_fb = FirebaseFirestore.getInstance();

        //**********
        Activity_login_etudiant.shP2 = getSharedPreferences("ID_File",MODE_PRIVATE);
        ID_tst = Activity_login_etudiant.shP2.getString(Activity_login_etudiant.Id_log,"master.stri2020@gmail.com");
        //*********
        Root_fb.collection("Etudiant").document(ID_tst).collection("Reservationn").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {


                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Log.d(TAG, document.getId() + " => " + document.getData());

                        day1 = document.getString(ActivityReservationPersonnalise.date_Reservation);

                        dej1 = document.getString(ActivityReservationPersonnalise.Dej);

                        dinn1=document.getString(ActivityReservationPersonnalise.Dinner);

                          arrayList.add("Le : "+day1+"          "+dej1+"         "+dinn1);

                       // List_reservations[indice_list]= "Le : "+day1+"          "+dej1+"         "+dinn1;
                        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ActivityReservations.this, android.R.layout.simple_list_item_1,ActivityReservations.arrayList);
                        Lv_reservations.setAdapter(adapter);
                       // indice_list++;

                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }


            }



        });








    }
}