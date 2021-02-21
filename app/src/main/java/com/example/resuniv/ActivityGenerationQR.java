package com.example.resuniv;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ActivityGenerationQR extends AppCompatActivity {

    Button buttonGenerateQRDejeunner;
    Button buttonGenerateQRDinner;
    ImageView imageViewQR;



    public static String message ;
    ImageView imageViewQR_dinner;
    Button btn_enregistrerQr ;
    public static  int ind = 0;

    static TextView text ;
    Button scanbtn;
    Long today = MaterialDatePicker.todayInUtcMilliseconds();

    public   static Spinner spinner1 ;
    // public static String dej_din = "dej/din";

    public static String dejeuner_comparabel = "Déjeuner";
    public static String dinner_comparabel = "Diner";

    public FirebaseAuth mAuth;
    public FirebaseFirestore Root_fb;
    public String ID_tst;
    public static String date_Selectionee;
    public int indice = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generation_q_r);

        mAuth = FirebaseAuth.getInstance();
        Root_fb = FirebaseFirestore.getInstance();

        //**********
        Activity_login_etudiant.shP2 = getSharedPreferences("ID_File",MODE_PRIVATE);
        ID_tst = Activity_login_etudiant.shP2.getString(Activity_login_etudiant.Id_log,"master.stri2020@gmail.com");
        //*********

        text = findViewById(R.id.date);
        SimpleDateFormat simpleFormat = new SimpleDateFormat("dd-M-yyyy", Locale.FRANCE);
        date_Selectionee = simpleFormat.format(today);
        //date_Selectionee = "18-2-2021";
        text.setText(date_Selectionee);
        Toast.makeText(getApplicationContext(), "Vous ne pouvez pas modifier la date ! ",Toast.LENGTH_LONG).show();


        // buttonGenerateQR.setEnabled(true);

        imageViewQR = findViewById(R.id.View_QR);

        // Note.setText("N.B : Veuillez gardez le code QR ci-dessous chez vous, et le montrer aux responsables du restaurant pour que vous puissiez prendre vos repas !");
        buttonGenerateQRDejeunner =  findViewById(R.id.GenerateQrDejeunner);
        buttonGenerateQRDejeunner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewQR.setVisibility(View.INVISIBLE);
                indice= 1;
                Root_fb.collection("Etudiant").document(ID_tst).collection("Reservationn").document(date_Selectionee).addSnapshotListener(ActivityGenerationQR.this, new EventListener<DocumentSnapshot>() {

                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                        if (documentSnapshot.exists()) {

                            String dejeunner = documentSnapshot.getString(ActivityReservationPersonnalise.Dej);

                            if(dejeunner.equals("Déjeuner")) {
                                imageViewQR.setVisibility(View.VISIBLE);
                                generatQR("Déjeuner");
                            } else
                            {
                                Toast.makeText(ActivityGenerationQR.this, "Vous n'avez pas réserver le déjeuner pour Aujourd'hui !", Toast.LENGTH_LONG).show();

                            }

                        }else
                        {Toast.makeText(ActivityGenerationQR.this, "Aucune réservation Aujourd'hui !", Toast.LENGTH_LONG).show();}
                    }
                });


                // appelBase(dejeuner_comparabel);
                //appelBase();
                //generatQR(dejeuner_comparabel);

            }
        });
        buttonGenerateQRDinner =  findViewById(R.id.GenerateQrDinner);

        buttonGenerateQRDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewQR.setVisibility(View.INVISIBLE);// setEnabled(false);
                indice= 2;
                Root_fb.collection("Etudiant").document(ID_tst).collection("Reservationn").document(date_Selectionee).addSnapshotListener(ActivityGenerationQR.this, new EventListener<DocumentSnapshot>() {

                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                        if (documentSnapshot.exists()) {
                            String dinner  = documentSnapshot.getString(ActivityReservationPersonnalise.Dinner) ;
                            //Toast.makeText(GenerationQr.this, dinner, Toast.LENGTH_LONG).show();
                            if(dinner.equals("Diner")) {
                                imageViewQR.setVisibility(View.VISIBLE);// setEnabled(false);
                                generatQR("Diner");
                            }else
                            {
                                Toast.makeText(ActivityGenerationQR.this, "Vous n'avez pas réserver le dinner pour Aujourd'hui !", Toast.LENGTH_LONG).show();

                            }

                        }else
                        {Toast.makeText(ActivityGenerationQR.this, "Aucune réservation Aujourd'hui !", Toast.LENGTH_LONG).show();}

                    }
                });
                // appelBase(dinner_comparabel);
                //appelBase();


            }
        });




        // spin(spinner1);
       /* if(dej_din1==dejeuner_comparabel) {
            //dej_din=dejeuner_comparabel;
            // buttonGenerateQRDejeunner.setEnabled(true);
            buttonGenerateQRDejeunner.setEnabled(false);
        } else if (dej_din1==dinner_comparabel){
            buttonGenerateQRDinner.setEnabled(false);
        }

        */





        // Note.setText("N.B : Veuillez gardez le code QR ci-dessous chez vous, et le montrer aux responsables du restaurant pour que vous puissiez prendre vos repas !");
        //Root_fb.collection("Etudiant").document(ID_tst).collection("Reservationn").document(date_Selectionee).addSnapshotListener(GenerationQr.this, new EventListener<DocumentSnapshot>() {




    }


   /* public void appelBase() {

        Root_fb.collection("Etudiant").document(ID_tst).collection("Reservationn").document(date_Selectionee).addSnapshotListener(GenerationQr.this, new EventListener<DocumentSnapshot>() {

            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot.exists()) {
                    //String dinner  = documentSnapshot.getString(ProfilEtudiant.Dinner) ;

                    String dejeunner = documentSnapshot.getString(ProfilEtudiant.Dej);

                    if(dejeunner.equals("Déjeuner")) {
                        generatQR("Déjeuner");
                    }

                    String dinner  = documentSnapshot.getString(ProfilEtudiant.Dinner) ;
                    Toast.makeText(GenerationQr.this, dinner, Toast.LENGTH_LONG).show();
                    if(dinner.equals("Diner")) {

                        generatQR("Diner");
                    }



                   /* if (dinner.equals(dej_din11)) {
                        //generatQR();
                        generatQR(dej_din11);
                    } else {
                        Toast.makeText(GenerationQr.this, "Vous n'avait pas reserver le Diner ! ", Toast.LENGTH_LONG).show();
                    }

                    if (dejeunner == dej_din11) {
                        generatQR(dej_din11);
                    } else {
                        Toast.makeText(GenerationQr.this, "Vous n'avait pas reserver le Déjeuner ! ", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(GenerationQr.this, "Aucune reservation pour aujourd'hui !", Toast.LENGTH_LONG).show();
                }


                }
                else {
                Toast.makeText(GenerationQr.this, "Aucune réservation pour Aujourd'hui ", Toast.LENGTH_LONG).show(); }
            }

        });

    }

    */


    public void generatQR(String rep){
        // buttonGenerateQR =  findViewById(R.id.GenerateQr);
        //:imageViewQR = findViewById(R.id.View_QR);
        //buttonGenerateQR.setOnClickListener(new View.OnClickListener() {
        //@Override
        //public void onClick(View view) {

// fct_comparaison(rep);
        // message =("***Application RestoUniv*** \n - Date : "+date_Selectionee+" - Repas : "+rep);
        message ="*Application RestoUniv : "+date_Selectionee+""+rep;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();


        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(message, BarcodeFormat.QR_CODE,500,500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageViewQR.setImageBitmap(bitmap);
           /* if (indice == 1) {
                imageViewQR_dejeunner.setImageBitmap(bitmap);
            }
            else {
            if(indice==2) {
                imageViewQR_dinner.setImageBitmap(bitmap);
            }}*/

        }catch (Exception e){
            e.printStackTrace();
        }

        //}
        // });
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }





}
