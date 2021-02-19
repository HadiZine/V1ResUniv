package com.example.resuniv;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ActivitySuiviReservation extends AppCompatActivity {
    private ImageView caledn ;
    public static TextView text ;
    public static String mtext  ;

   public FirebaseAuth mAuth;
   public FirebaseFirestore Root_fb;

   private    int nbrRepasDej=0;
   private    int nbrRepasdiner=0;

   private TextView nbrDéjeuner;
   private TextView nbrDinner;


    private    static  final  String TAG ="RestauUniv";
   private    static  final  String LOG_TAG ="RestauUniv";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suivi_reservation);

        mAuth = FirebaseAuth.getInstance();
        Root_fb = FirebaseFirestore.getInstance();



        nbrDéjeuner = findViewById(R.id.Nbr_DEJ);
        nbrDinner = findViewById(R.id.Nbr_DIN);

        //mAuth = FirebaseAuth.getInstance();
        //Root_fb = FirebaseFirestore.getInstance();



        //nbrDéjeuner = findViewById(R.id.Nbr_DEJ);
        //nbrDinner = findViewById(R.id.Nbr_DIN);


        caledn = findViewById(R.id.calendrier);

        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        //builder.setCalendarConstraints();
        builder.setTitleText("Selectionnez une date");
        MaterialDatePicker<Long> materialDatePicker = builder.build();



        caledn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "*******");

            }
        });

        text = findViewById(R.id.txt);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "*******");

            }
        });


        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                TimeZone timeZoneUTC = TimeZone.getDefault();
                int offsetFromUTC = timeZoneUTC.getOffset(new Date().getTime()) * -1;
                SimpleDateFormat simpleFormat = new SimpleDateFormat("dd-M-yyyy", Locale.FRANCE);
                Date date = new Date(selection + offsetFromUTC);
                text.setText(simpleFormat.format(date));
                mtext = simpleFormat.format(date);
                Consulter_reservations(mtext);
            }
        });

        //Toast.makeText(getApplicationContext(),"  mtext  "+ mtext,Toast.LENGTH_LONG).show();


    }

    public void Consulter_reservations(String par){



        nbrRepasDej=0;

        nbrRepasdiner=0;
        nbrDéjeuner.setText("  "+String.valueOf(nbrRepasDej)+" Repas. ");
        nbrDinner.setText("  "+String.valueOf(nbrRepasdiner)+" Repas. ");



        Root_fb.collection("Etudiant").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    List<String> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Toast.makeText(getApplicationContext(),"  voila  "+document.getId() ,Toast.LENGTH_LONG).show();

                        list.add(document.getId());
                    }
                    for (String email : list) {
                        Root_fb.collection("Etudiant").document(email).collection("Reservationn").document(mtext).addSnapshotListener(ActivitySuiviReservation.this, new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                                if(documentSnapshot.exists()){
                                    Toast.makeText(getApplicationContext(),"  voila dkheeelt  ",Toast.LENGTH_LONG).show();

                                    String var1= documentSnapshot.getString(ActivityReservationPersonnalise.Dej);
                                    String var2= documentSnapshot.getString(ActivityReservationPersonnalise.Dinner);



                                    if(var1.equals("Déjeuner")) {

                                        nbrRepasDej++;
                                        Toast.makeText(getApplicationContext(),""+var1+""+nbrRepasDej,Toast.LENGTH_LONG).show();
                                        nbrDéjeuner.setText("  "+String.valueOf(nbrRepasDej)+" Repas. ");
                                    }
                                    if(var2.equals("Diner")) {
                                        nbrRepasdiner++;
                                        Toast.makeText(getApplicationContext(),""+var2+""+nbrRepasdiner,Toast.LENGTH_LONG).show();
                                        nbrDinner.setText("  "+String.valueOf(nbrRepasdiner)+" Repas. ");
                                    }


                                }
                                else{
                                    Toast.makeText(getApplicationContext()," Reservattion not found ",Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }

                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }

            }

        });





    }

}