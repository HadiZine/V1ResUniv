package com.example.resuniv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ActivityPaiementComplet extends AppCompatActivity {

    private final float prix_repas = (float) 1.5;
    private TextView Prix_reservation_complet;

    private final long jour = 86400000;

    public static ListView Lv;
    public static String[] List_reservation = new String[]{"","","","","","",""};


    private Button Paiement_butt_Complet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paiement_complet);
        Prix_reservation_complet = findViewById(R.id.prix_reservation_complet);
        Lv = findViewById(R.id.ListViewComplet);
        

        Prix_reservation_complet.setText("Prix Total : "+String.valueOf(prix_repas*14)+" DH");


        long date = ActivityReservationComplete.Cv.getDate();

        for(int i=0;i<=6;i++){
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(date);
            int Year = calendar.get(Calendar.YEAR);
            int Month = calendar.get(Calendar.MONTH);
            int Day = calendar.get(Calendar.DAY_OF_MONTH);
            String finalDate= String.valueOf(Day)+"-"+String.valueOf(Month+1)+"-"+String.valueOf(Year);
            List_reservation[i]= "Le  "+finalDate+" : "+"          Déjeuner           Diner";
            date=date+jour;
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ActivityPaiementComplet.this, android.R.layout.simple_list_item_1,List_reservation);
        Lv.setAdapter(adapter);

        Paiement_butt_Complet=findViewById(R.id.button_paiement_Complet);

        Paiement_butt_Complet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentGo_PaimentC = new Intent(ActivityPaiementComplet.this, ActivityMethodePaiement.class);
                startActivity(intentGo_PaimentC);

            }
        });


    }
}