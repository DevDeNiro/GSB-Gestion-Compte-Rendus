package com.example.gsb_doctors;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.os.Handler;
import android.os.Looper;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class saisir extends AppCompatActivity {

    android.widget.EditText medicament, duree, rdv, prix, titre;
    Button b0, b1, b2, b3;
    TextView enregistrer;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.visiteur_saisir);

        b0 = findViewById(R.id.enregistrer);
        b1 = (Button) findViewById(R.id.bouton1);
        b2 = (Button) findViewById(R.id.bouton2);
        b3 = (Button) findViewById(R.id.bouton3);
        progressBar = findViewById(R.id.progress);

        prix = findViewById(R.id.prix);
        medicament = findViewById(R.id.medicament);
        duree = findViewById(R.id.duree);
        rdv = findViewById(R.id.rdv);
        titre = findViewById(R.id.titre);

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

                String Medic, Duree, Rdv, Prix, Titre, Region;

                Prix = String.valueOf(prix.getText());
                Medic = String.valueOf(medicament.getText());
                Duree = String.valueOf(duree.getText());
                Rdv = String.valueOf(rdv.getText());
                Region = "Région";
                Titre = String.valueOf(titre.getText());

                // Verification of the type of the value entered in the EditText
                if (!Medic.equals("") && !Duree.equals("") && !Rdv.equals("") && !Prix.equals("") && !Region.equals("") && !Titre.equals("")) {
                    //Start ProgressBar first (Set visibility VISIBLE)

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[6];
                            field[0] = "prix";
                            field[1] = "medic";
                            field[2] = "duree";
                            field[3] = "rdv";
                            field[4] = "titre";
                            field[5] = "region";

                            //Creating array for data
                            String[] data = new String[6];
                            data[0] = Prix;
                            data[1] = Medic;
                            data[2] = Duree;
                            data[3] = Rdv;
                            data[4] = Titre;
                            data[5] = Region;

                            PutData putData = new PutData("http://192.168.1.136/GSB_doctors/secure_API/visiteurInsertCompteRendu.php", "POST", field, data);  // Mettre son ip
                            if (putData.startPut()) {
                                if (putData.onComplete()) {

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