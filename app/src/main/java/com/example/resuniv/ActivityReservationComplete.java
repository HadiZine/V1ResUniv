package com.example.resuniv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

public class ActivityReservationComplete extends AppCompatActivity {

    //public TextView txt_DateValide;
    public static CalendarView Cv;
    private String DateText;
    private Long date1;
    private Long date2;
    private final long jour = 86400000;
    private final long semaine = 604800000;
    private Button Button_ConfirmerComplet;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_complete);

        //txt_DateValide = findViewById(R.id.textReservationCompl);

        Cv = findViewById(R.id.calendarViewComp);

        date1= Cv.getDate() + jour;
        date2 = Cv.getDate() + semaine;
        Cv.setMinDate(date1);
        Cv.setMaxDate(date2);

        Button_ConfirmerComplet= findViewById(R.id.button_ConfirmerComplet);
        Button_ConfirmerComplet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_goPaiementComplet = new Intent(ActivityReservationComplete.this, ActivityPaiementComplet.class);
                startActivity(intent_goPaiementComplet);


            }
        });



    }
}