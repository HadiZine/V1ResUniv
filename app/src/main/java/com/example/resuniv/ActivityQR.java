package com.example.resuniv;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ActivityQR extends AppCompatActivity implements View.OnClickListener {
    static TextView text ;
    Button scanbtn;
    Long today = MaterialDatePicker.todayInUtcMilliseconds();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r);

        text = findViewById(R.id.txtdate);
        SimpleDateFormat simpleFormat = new SimpleDateFormat("dd-M-yyyy", Locale.FRANCE);
        text.setText(simpleFormat.format(today));
        Toast.makeText(getApplicationContext(), "Vous ne pouvez pas modifier la date ! ", Toast.LENGTH_LONG).show();
        scanbtn = findViewById(R.id.btn_scan);
        scanbtn.setOnClickListener(this);
        Toast.makeText(getApplicationContext(), "Veuillez choisit le type de repas  ",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View v) {
        scanCode();
    }

    private void scanCode () {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Code Scanner");
        integrator.initiateScan();

    }

    @Override
    protected  void onActivityResult(int requestCode , int resultCode , Intent data) {


        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null) {
            if (result.getContents() != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(result.getContents());
                builder.setTitle("le resultat du scan : ");
                builder.setPositiveButton("Scanner une autre fois ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        scanCode();
                    }
                }).setNegativeButton("Terminer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
            else {
                Toast.makeText(this, "pas de resultat ",Toast.LENGTH_LONG).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}