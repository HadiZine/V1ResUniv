package com.example.resuniv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class ActivityRemplisaageMenu extends AppCompatActivity {

    private ImageView caledn ;
    static TextView text ;
    private Button btn_enregistrer ;


    public static  String Ordre_reservation = "ordre: ";
    private long i= 0;

    public static String mtext  ;
    private Button btn_consultation;

    public   static Spinner spinner1 ;
    public   static Spinner spinner2 ;
    public   static Spinner spinner3 ;
    public   static Spinner spinner4 ;
    public   static Spinner spinner5 ;
    public   static Spinner spinner6 ;

    public static String dej_plat = "plat dej ";
    public static String dej_dessert = "dessert dej ";
    public static String dej_boisson = "boissons dej ";
    //// dinner
    public static String din_plat = "plat din ";
    public static String din_dessert = "dessert din ";
    public static String din_boisson = "boissons  din";


    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remplisaage_menu);



        text = findViewById(R.id.txt);
        btn_enregistrer = findViewById(R.id.btn_enregistrer);
        caledn = findViewById(R.id.calendrier);

        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setCalendarConstraints(limitRange(2).build());
        builder.setTitleText("Selectionnez une date");
        MaterialDatePicker<Long> materialDatePicker = builder.build();



        caledn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "*******");

            }
        });


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
            }
        });

        btn_enregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remplissage_map(mtext);
                Toast.makeText(getApplicationContext(), "Le Menus pour la date : " + mtext + " a été bien enregistrer", Toast.LENGTH_LONG).show();
            }
        });


        btn_consultation = findViewById(R.id.btn_econsulter_menus);
        btn_consultation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityRemplisaageMenu.this , ActivityConsultationMenu.class);
                startActivity(intent);
            }
        });


        spinner1 = findViewById(R.id.dej_princ_spinner);
        spinner2 = findViewById(R.id.dej_dessert_spinner);
        spinner3 = findViewById(R.id.dej_boissons_spinner);
        //// dinner
        spinner4 = findViewById(R.id.din_princ_spinner);
        spinner5 = findViewById(R.id.din_dessert_spinner);
        spinner6 = findViewById(R.id.din_boissons_spinner);



    }


    public  void  remplissage_map (String date) {

        String dej_plat1 = spinner1.getSelectedItem().toString();
        String dej_dessert1 = spinner2.getSelectedItem().toString();
        String dej_boisson1 = spinner3.getSelectedItem().toString();
        //// dinner
        String din_plat1 = spinner4.getSelectedItem().toString();
        String  din_dessert1 = spinner5.getSelectedItem().toString();
        String din_boisson1 = spinner6.getSelectedItem().toString();
        //String date = text.toString();
        Map<String, Object > DataBase = new HashMap<>();
        DataBase.put(dej_plat,dej_plat1 );
        DataBase.put(dej_dessert,dej_dessert1 );
        DataBase.put(dej_boisson,dej_boisson1 );
        DataBase.put(din_plat,din_plat1 );
        DataBase.put(din_dessert ,din_dessert1 );
        DataBase.put(din_boisson ,din_boisson1 );
        DataBase.put(Ordre_reservation, i);
        i=i+1;
        db.collection("MENUS").document(date).set(DataBase).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("","C'est bon la Base Donée est bon");
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("","Attetion ELLE EST PAS CREER");
            }
        });
    }


    public static CalendarConstraints.Builder limitRange(int i) { // 1 pour l'etudiant  2 pour Staff

        CalendarConstraints.Builder constraintsBuilderRange = new CalendarConstraints.Builder();

        Calendar calendarStart = Calendar.getInstance();
        Calendar calendarEnd = Calendar.getInstance();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int startMonth = Calendar.getInstance().get(Calendar.MONTH);
        int startDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        if (i==1)
        {
            calendarStart.set(year, startMonth, startDate);
            calendarEnd.set(year, startMonth, startDate + 7);
            long minDate = calendarStart.getTimeInMillis();
            long maxDate = calendarEnd.getTimeInMillis();
            constraintsBuilderRange.setStart(minDate);
            constraintsBuilderRange.setEnd(maxDate);
            constraintsBuilderRange.setValidator(new RangeValidator(minDate, maxDate));
            return constraintsBuilderRange;
        }
        else
        {
            calendarStart.set(year, startMonth, startDate+7);
            calendarEnd.set(year, startMonth, startDate + 14);
            long minDate = calendarStart.getTimeInMillis();
            long maxDate = calendarEnd.getTimeInMillis();
            constraintsBuilderRange.setStart(minDate);
            constraintsBuilderRange.setEnd(maxDate);
            constraintsBuilderRange.setValidator(new RangeValidator(minDate, maxDate));
            return constraintsBuilderRange;
        }
    }

    static class RangeValidator implements CalendarConstraints.DateValidator {
        long minDate, maxDate;
        RangeValidator(long minDate, long maxDate) {
            this.minDate = minDate;
            this.maxDate = maxDate;
        }

        RangeValidator(Parcel parcel) {
            minDate = parcel.readLong();
            maxDate = parcel.readLong();
        }

        @Override
        public boolean isValid(long date) {
            return (minDate <= date && maxDate >= date );
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(minDate);
            dest.writeLong(maxDate);
        }

        public static final Parcelable.Creator<RangeValidator> CREATOR = new Parcelable.Creator<RangeValidator>() {

            @Override
            public RangeValidator createFromParcel(Parcel parcel) {
                return new RangeValidator(parcel);
            }

            @Override
            public RangeValidator[] newArray(int size) {
                return new RangeValidator[size];
            }
        };
    }


    public static String getMtext() {
        return mtext;
    }

    public static void setMtext(String mtext) {
        ActivityRemplisaageMenu.mtext = mtext;
    }

    public static String getOrdre_reservation() {
        return Ordre_reservation;
    }

    public static void setOrdre_reservation(String ordre_reservation) {
        Ordre_reservation = ordre_reservation;
    }

}
