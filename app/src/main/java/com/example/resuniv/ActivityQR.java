package com.example.resuniv;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ActivityQR extends AppCompatActivity {//implements View.OnClickListener {
    static TextView text ;
    Button scanbtn_dej;
    Button scanbtn_din;
    Long today = MaterialDatePicker.todayInUtcMilliseconds();
    static String repdej ="Déjeuner";
    static String repdin ="Diner";
    public static String date_Selectionee;
    static  int indice = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r);

        text = findViewById(R.id.date);
        SimpleDateFormat simpleFormat = new SimpleDateFormat("dd-M-yyyy", Locale.FRANCE);
        date_Selectionee = simpleFormat.format(today);
        text.setText(date_Selectionee);
        Toast.makeText(getApplicationContext(), "Modification de la date est impossible !",Toast.LENGTH_LONG).show();
        scanbtn_dej = findViewById(R.id.QrDejeunner);
        scanbtn_dej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indice = 1;
                scanCode();
            }
        });
        scanbtn_din = findViewById(R.id.QrDinner);
        scanbtn_din.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indice = 2;
                scanCode();
            }
        });

    }


    private void scanCode () {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(true);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Code Scanner");
        integrator.initiateScan();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected  void onActivityResult(int requestCode , int resultCode , Intent data) {


        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null) {
            if (result.getContents() != null) {


                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                // builder.setMessage(result.getContents());
                builder.setPositiveButton("Scanner une autre fois ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        scanCode();
                    }
                }).setPositiveButtonIcon(getDrawable(R.drawable.ic_baseline_cached_24)).setNegativeButton("Terminer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                        recreate();
                    }
                });
                AlertDialog dialog = builder.create();
                if(indice==1) /// pour scanner les déjeunner
                {
                    if(result.getContents().equals("*Application RestoUniv : "+date_Selectionee+""+repdej) )
                    {
                        dialog.setIcon(R.drawable.ic_baseline_check_circle_24);
                        dialog.setTitle("Code QR est VALIDE ");

                    }
                    else
                    {
                        dialog.setIcon(R.drawable.ic_baseline_cancel_24);
                        dialog.setTitle("Code QR est INVALIDE !");
                    }
                }
                if(indice==2) /// pour scanner les dinner
                {
                    if( result.getContents().equals("*Application RestoUniv : "+date_Selectionee+""+repdin) )
                    {
                        dialog.setIcon(R.drawable.ic_baseline_check_circle_24);
                        dialog.setTitle("Code QR est VALIDE ");
                    }
                    else
                    {
                        dialog.setIcon(R.drawable.ic_baseline_cancel_24);dialog.setTitle("Code QR est INVALIDE !");
                    }
                }
                dialog.show();
            }
            else
            {
                Toast.makeText(this, "pas de resultat ",Toast.LENGTH_LONG).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void showImage(int iv) {
        Dialog builder = new Dialog(this);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

            }
        });

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(iv);
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }


}