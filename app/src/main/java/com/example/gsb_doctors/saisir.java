package com.example.gsb_doctors;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class saisir extends AppCompatActivity {

    android.widget.EditText prenom, nom, naiss, adresse, telephone, email, ante, medicament, duree, rdv;
    Button b0, b1, b2, b3;
    TextView enregistrer;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.saisir);

        b0 = findViewById(R.id.enregistrer);
        b1 = (Button) findViewById(R.id.bouton1);
        b2 = (Button) findViewById(R.id.bouton2);
        b3 = (Button) findViewById(R.id.bouton3);
        progressBar = findViewById(R.id.progress);

        prenom = findViewById(R.id.prenom);
        nom = findViewById(R.id.nom);
        naiss = findViewById(R.id.naissance);
        adresse = findViewById(R.id.adresse);
        telephone = findViewById(R.id.telephone);
        email = findViewById(R.id.email);
        ante = findViewById(R.id.ante);
        medicament = findViewById(R.id.medicament);
        duree = findViewById(R.id.duree);
        rdv = findViewById(R.id.rdv);

        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), identification.class);
                startActivity(intent);
                finish();
            }
        });

        // ***************** Changement de page au clic *****************

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                // Appel à la fonction openActivity pour changer de page (activity)
                openActivity1();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                // Appel à la fonction openActivity pour changer de page (activity)
                openActivity2();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                // Appel à la fonction openActivity pour changer de page (activity)
                openActivity3();
            }
        });

        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Prenom, Nom, Naiss, Adresse, Tel, Email, Ante, Medic, Duree, Rdv;
                Prenom = String.valueOf(prenom.getText());
                Nom = String.valueOf(nom.getText());
                Naiss = String.valueOf(naiss.getText());
                Adresse = String.valueOf(adresse.getText());
                Tel = String.valueOf(telephone.getText());
                Email = String.valueOf(email.getText());
                Ante = String.valueOf(ante.getText());
                Medic = String.valueOf(medicament.getText());
                Duree = String.valueOf(duree.getText());
                Rdv = String.valueOf(rdv.getText());

                // Verification of the type of the value entered in the EditText
                if (!Prenom.equals("") && !Nom.equals("") && !Naiss.equals("") && !Adresse.equals("") && !Tel.equals("") && !Email.equals("") && !Ante.equals("") && !Medic.equals("") && !Duree.equals("") && !Rdv.equals("")) {
                    //Start ProgressBar first (Set visibility VISIBLE)
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[10];
                            field[0] = "prenom";
                            field[1] = "nom";
                            field[2] = "naiss";
                            field[3] = "adresse";
                            field[4] = "tel";
                            field[5] = "email";
                            field[6] = "ante";
                            field[7] = "medic";
                            field[8] = "duree";
                            field[9] = "rdv";

                            //Creating array for data
                            String[] data = new String[10];
                            data[0] = Prenom;
                            data[1] = Nom;
                            data[2] = Naiss;
                            data[3] = Adresse;
                            data[4] = Tel;
                            data[5] = Email;
                            data[6] = Ante;
                            data[7] = Medic;
                            data[8] = Duree;
                            data[9] = Rdv;

                            PutData putData = new PutData("http://192.168.1.136/GSB_doctors/secure_API/insertCompteRendu.php", "POST", field, data);  // Mettre son ip
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);

                                    String result = putData.getResult();

                                    if (result.equals("Sign Up Success")) {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent accueil = new Intent(getApplicationContext(), accueil.class);
                                        startActivity(accueil);
                                        finish();
                                    } else {

                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }
                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openActivity1(){
        Intent saisir = new Intent(this, saisir.class);
        startActivity(saisir);
        finish();
    }

    public void openActivity2(){
        Intent accueil = new Intent(this, accueil.class);
        startActivity(accueil);
        finish();
    }

    public void openActivity3(){
        Intent consulter = new Intent(this, consulter.class);
        startActivity(consulter);
        finish();
    }
}