package com.example.resuniv.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.resuniv.ActivityReservationComplete;
import com.example.resuniv.ActivityReservationPersonnalise;
import com.example.resuniv.Activity_login_etudiant;
import com.example.resuniv.ChoixTypeReservation;
import com.example.resuniv.R;
import com.example.resuniv.activity_creer_compte;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    public TextView txt_NameRecuReservation;
    //public TextView txt_PrenomRecuReservation;

    public FirebaseAuth mAuth;
    public FirebaseFirestore Root_fb;//+" "+documentSnapshot.getString(activity_creer_compte.Prenom_Etud

    public String ID_tst;
    public static int typeChoix =0;

    private    static  final  String LOG_TAG ="RestauUniv";

    private CheckBox ch_Complet, ch_person;
    private Button button_ChoixType;
    private View mView;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        //View root = inflater.inflate(R.layout.fragment_home, container, false);
        View mView = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
       // homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
           /// @Override
           // public void onChanged(@Nullable String s) {
               // textView.setText(s);
           // }
       /// });
        //****
        Root_fb = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Activity_login_etudiant.shP2 =  getActivity().getSharedPreferences("ID_File",MODE_PRIVATE);
        ID_tst = Activity_login_etudiant.shP2.getString(Activity_login_etudiant.Id_log,"master.stri2020@gmail.com");


        txt_NameRecuReservation = mView.findViewById(R.id.Nom_Bienvenu);

        DocumentReference Mydocument = Root_fb.collection("Etudiant").document(ID_tst);
        Mydocument.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if(documentSnapshot.exists()){
                    txt_NameRecuReservation.setText(documentSnapshot.getString(activity_creer_compte.Name_Etud)+" "+documentSnapshot.getString(activity_creer_compte.Prenom_Etud));

                }
                else{
                    Toast.makeText(getActivity().getApplicationContext()," User not found ",Toast.LENGTH_LONG).show();
                }
            }
        });
        //***ChoixType****

        ch_Complet= mView.findViewById(R.id.checkBox_complete);
        ch_person= mView.findViewById(R.id.checkBox_Personalisé);
        button_ChoixType= mView.findViewById(R.id.buttonTypeReservation);


        ch_Complet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (checked){
                    ch_person.setEnabled(false);
                    button_ChoixType.setEnabled(true);
                    typeChoix =1;

                    button_ChoixType.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent_goComplet = new Intent(getActivity(), ActivityReservationComplete.class);
                            startActivity(intent_goComplet);


                        }
                    });

                }
                else{ch_person.setEnabled(true);
                    button_ChoixType.setEnabled(false);}
            }
        });

        ch_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (checked){
                    ch_Complet.setEnabled(false);
                    button_ChoixType.setEnabled(true);
                    typeChoix =2;
                    button_ChoixType.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent_goPersonn = new Intent(getActivity(), ActivityReservationPersonnalise.class);
                            startActivity(intent_goPersonn);

                        }
                    });

                }
                else{ch_Complet.setEnabled(true);
                    button_ChoixType.setEnabled(false);}
            }
        });




















        //****

        //onCheckboxClicked();



        return mView;
    }


/*
    public void onCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();
        switch(view.getId()) {
            case R.id.checkBox_complete:
                if (checked){
                    ch_person.setEnabled(false);
                    button_ChoixType.setEnabled(true);

                    button_ChoixType.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent_goComplet = new Intent(getActivity(), ActivityReservationComplete.class);
                            startActivity(intent_goComplet);


                        }
                    });

                }
                else{ch_person.setEnabled(true);
                    button_ChoixType.setEnabled(false);}
                break;

            case R.id.checkBox_Personalisé:
                if (checked){
                    ch_Complet.setEnabled(false);
                    button_ChoixType.setEnabled(true);
                    button_ChoixType.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent_goPersonn = new Intent(getActivity(), ActivityReservationPersonnalise.class);
                            startActivity(intent_goPersonn);


                        }
                    });

                }
                else{ch_Complet.setEnabled(true);
                    button_ChoixType.setEnabled(false);}
                break;
            // TODO: Veggie sandwich
        }
    }

 */
}