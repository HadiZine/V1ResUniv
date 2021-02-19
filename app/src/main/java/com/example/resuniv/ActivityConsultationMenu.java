package com.example.resuniv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ActivityConsultationMenu extends AppCompatActivity {
    public ListView menusList;
    public static String[] menus = new String[] {"","","","","","",""};

    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_menu);
        menusList = findViewById(R.id.listmenus);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("MENUS").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        long indice = documentSnapshot.getLong(ActivityRemplisaageMenu.getOrdre_reservation());
                        int indic_int = ((int) indice);
                        String str = "";
                        Map<Integer, Object> list = new HashMap<>();
                        list.put(0, "*" + ActivityRemplisaageMenu.mtext + "");
                        list.put(1, documentSnapshot.getId() + "\n");
                        list.put(2, "    Dejeunner :");
                        list.put(3, "            Plat Principale :   " + documentSnapshot.getString(ActivityRemplisaageMenu.dej_plat));
                        list.put(4, "            Dessert         :   " + documentSnapshot.getString(ActivityRemplisaageMenu.dej_dessert));
                        list.put(5, "            Boissons        :   " + documentSnapshot.getString(ActivityRemplisaageMenu.dej_boisson));
                        list.put(6, " ");
                        list.put(7, "    Dinner :");
                        list.put(8, "        Plat Principale     :   " + documentSnapshot.getString(ActivityRemplisaageMenu.din_plat));
                        list.put(9, "        Dessert             :   " + documentSnapshot.getString(ActivityRemplisaageMenu.din_dessert));
                        list.put(10, "        Boissons           :   " + documentSnapshot.getString(ActivityRemplisaageMenu.din_boisson));
                        Set<Map.Entry<Integer, Object>> setEntry = list.entrySet();
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

                        menus[indic_int] = str;

                        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ActivityConsultationMenu.this, android.R.layout.simple_list_item_1, menus);
                        menusList.setAdapter(adapter);
                    }
                }
            }
        });
    }

}