package com.example.resuniv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ActivityReservationPersonnalise extends AppCompatActivity {

    public FirebaseAuth mAuth;
    public FirebaseFirestore Root_fb;

    public String ID_tst;
    private CalendarView Cv;
    private    static  final  String TAG ="RestauUniv";
    private    static  final  String LOG_TAG ="RestauUniv";

    private CheckBox checkBox_dej;
    private CheckBox checkBox_denner;
    private Button valider_repas;
    private LinearLayout Ly;
    private TextView dateReservation;

    public static String date_Selectionee;
    private Long date1;
    private Long date2;
    private final long jour = 86400000;
    private final long semaine = 604800000;

    public static String date_Reservation = "Le  : ";
    public static String Dej= "Déjeuner: ";
    public static String Dinner= "Dinner: ";
    //public static  String Ordre_reservation = "ordre: ";
    public static String Onclique= "Statut";

    private Button Button_ContinuerPerson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_personnalise);

        mAuth = FirebaseAuth.getInstance();
        Root_fb = FirebaseFirestore.getInstance();

        //**********
        Activity_login_etudiant.shP2 = getSharedPreferences("ID_File",MODE_PRIVATE);
        ID_tst = Activity_login_etudiant.shP2.getString(Activity_login_etudiant.Id_log,"master.stri2020@gmail.com");
        //*********

        Cv = findViewById(R.id.calendarViewPerso);
        checkBox_dej = findViewById(R.id.checkBox_dej);
        checkBox_denner = findViewById(R.id.checkBox_dinner);
        valider_repas = findViewById(R.id.ok_repas);
        Ly = findViewById(R.id.Ly);
        dateReservation = findViewById(R.id.date_reservation);

        date1= Cv.getDate() + jour;
        date2 = Cv.getDate() + semaine;
        //Toast.makeText(getApplicationContext(),"Day_test = "+date1.intValue(),Toast.LENGTH_LONG).show();
        Cv.setMinDate(date1);
        Cv.setMaxDate(date2);


        Cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                checkBox_dej.setVisibility(View.VISIBLE);
                checkBox_denner.setVisibility(View.VISIBLE);
                valider_repas.setVisibility(View.VISIBLE);
                Ly.setVisibility(View.VISIBLE);

                dateReservation.setText(String.valueOf(dayOfMonth)+"-"+String.valueOf(month+1)+"-"+String.valueOf(year));

                dateReservation.setVisibility(View.VISIBLE);

                date_Selectionee = dateReservation.getText().toString();


                valider_repas.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Map<String, Object> DataBaseReservation_repas = new HashMap<>();
                        DataBaseReservation_repas.put(date_Reservation, date_Selectionee);
                        DataBaseReservation_repas.put(Onclique,"Active");
                        if(checkBox_dej.isChecked()){
                            String repsChoisit= "Déjeuner";
                            DataBaseReservation_repas.put(Dej, repsChoisit);
                        }else {
                            String repsChoisit= "--------";
                            DataBaseReservation_repas.put(Dej, repsChoisit);
                        }

                        if(checkBox_denner.isChecked()){
                            String repsChoisit= "Diner";
                            DataBaseReservation_repas.put(Dinner, repsChoisit);
                        }else {
                            String repsChoisit= "-----";
                            DataBaseReservation_repas.put(Dinner, repsChoisit);
                        }

                        Root_fb.collection("Etudiant").document(ID_tst).collection("Reservationn").document(date_Selectionee).set(DataBaseReservation_repas);

                        //*****
                        checkBox_dej.setVisibility(View.INVISIBLE);
                        checkBox_denner.setVisibility(View.INVISIBLE);
                        valider_repas.setVisibility(View.INVISIBLE);
                        Ly.setVisibility(View.INVISIBLE);
                        dateReservation.setVisibility(View.INVISIBLE);
                        checkBox_dej.setChecked(false);
                        checkBox_denner.setChecked(false);

                    }
                });

            }
        });

        Button_ContinuerPerson=findViewById(R.id.button_ContinuerPerson);
        Button_ContinuerPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGo_ContinuerP = new Intent(ActivityReservationPersonnalise.this, ActivityPaiementPersonnalise.class);
                startActivity(intentGo_ContinuerP);
            }
        });


    }
}