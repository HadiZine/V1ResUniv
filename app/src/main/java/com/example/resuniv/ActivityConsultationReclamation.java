package com.example.resuniv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ActivityConsultationReclamation extends AppCompatActivity {

    public ListView reclamationListView;
    public static String[] rec = new String[] {"","","","","","",""};
    public static ArrayList<String> arrayList = new ArrayList<>();


    public static String date_Reclamation= "Le  : ";
    public static String Email= "resto@gmail.com: ";
    public static String Tel= "00000";
    //public static  String Ordre_reservation = "ordre: ";
    public static String Anomalie= "Rien";
    public static String Description= "Decription";

    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_reclamation);
        reclamationListView = findViewById(R.id.listreclamation);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
       /* db.collection("Reclamations").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Fragment1.arrayListl.add(documentSnapshot.getId());
                       // Fragment1.arrayListl.add("bonjour");
                    }
                }
            }
        });*/
       /* db.collection("Reclamations").document("marouane@gmail.com").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Fragment1.arrayListl.add(task.getResult().getId());

            }
        });*/

/*
        FragmentManager manager= getSupportFragmentManager();
        FragmentTransaction t = manager.beginTransaction();
        Fragment1 m1= new Fragment1();
        t.add(R.id.frame1,m1);
        t.commit();
*/

        //////

        //FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Reclamations").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        //long indice = documentSnapshot.getLong(ActivityReclamationsEtudiant.getOrdre_reclamation());
                        //int indic_int = ((int) indice);
                       // int j = indic_int+1;
                        //String str = "";
                        Map<Integer, Object> list = new HashMap<>();
                        // Fragment1.arrayListl.add(documentSnapshot.getId());
                        // Fragment1.arrayListl.add("bonjour");

                        arrayList.add(" Reclamation du : "+documentSnapshot.getString(ActivityReclamationsEtudiant.date)+"\n"+"_____________________________\n   - Email : "+documentSnapshot.getId()+"\n   - Tel :"+ documentSnapshot.getString(ActivityReclamationsEtudiant.Tel)+
                                "\n   - Anomalie :"+ documentSnapshot.getString(ActivityReclamationsEtudiant.Anomalie)+"\n   - Description : "+documentSnapshot.getString(ActivityReclamationsEtudiant.description));
                    /*   list.put(0, "Reclamation \n"+"____________________");
                        list.put(1, "_______________\n"+"Reclamation "+j+" :\n"+"_______________\n   - Email : "+documentSnapshot.getId() );
                        list.put(2, "   - Date : " +documentSnapshot.getString(ActivityReclamationsEtudiant.date));
                        list.put(3, "   - Tel : " + documentSnapshot.getString(ActivityReclamationsEtudiant.Tel));
                        list.put(4, "   - Anomalie : " + documentSnapshot.getString(ActivityReclamationsEtudiant.Anomalie));
                        list.put(5, "   - Description : " );
                        list.put(6,"    "+documentSnapshot.getString(ActivityReclamationsEtudiant.description));

                     */
                      /*  Set<Map.Entry<Integer, Object>> setEntry = list.entrySet();
                        Iterator<Map.Entry<Integer, Object>> itEntry = setEntry.iterator();
                        while (itEntry.hasNext()) {
                            Map.Entry<Integer, Object> entry = itEntry.next();

                            for (Iterator<Map.Entry<Integer, Object>> it = itEntry; it.hasNext(); )
                            {
                                Object o = it.next().getValue();
                                str += o + "\n";
                            }
                            str += "";
                        }


                        rec[indic_int] = str;


                        // i++;
                        //rec[i]= documentSnapshot.getId();

                       */
                        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ActivityConsultationReclamation.this, android.R.layout.simple_list_item_1, arrayList);
                        reclamationListView.setAdapter(adapter);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Aucune reclamation a afficher !", Toast.LENGTH_LONG).show();
                }
            }
        });

        //////






    }
}