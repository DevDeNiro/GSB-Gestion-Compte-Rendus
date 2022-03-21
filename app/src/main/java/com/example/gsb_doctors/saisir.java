package com.example.gsb_doctors;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.os.Handler;
import android.os.Looper;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class saisir extends AppCompatActivity {

    android.widget.EditText medicament, duree, rdv, prix, titre;
    Button b0, b1, b2, b3;
    ProgressBar progressBar;
    private Spinner spinner;
    String[] get_id1 = new String[10], get_medecin1 = new String[10];
    List<String> categories;

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

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("region", MODE_PRIVATE);
        String get_region = prefs.getString("region1", "Aucun");
        String get_id = prefs.getString("id1", "0");

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

        categories = new ArrayList<String>();
        categories.add("Médecin");

        //Définition du tableau pour le spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

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
                openActivity1();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
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

                String Medic, Duree, Rdv, Prix, Titre, Region, Id, Medecin;
                int position_item;

                position_item = spinner.getSelectedItemPosition();

                Prix = String.valueOf(prix.getText());
                Medic = String.valueOf(medicament.getText());
                Duree = String.valueOf(duree.getText());
                Rdv = String.valueOf(rdv.getText());
                Region = get_region;
                Id = get_id;
                Titre = String.valueOf(titre.getText());

                if (position_item == 0)
                    Medecin = "Médecin";
                else
                    Medecin = get_id1[position_item];

                if (!Medic.equals("") && !Duree.equals("") && !Rdv.equals("") && !Prix.equals("") && !Region.equals("") && !Titre.equals("")) { // Vérifie qu'aucun champ n'est null
                    //Start ProgressBar first (Set visibility VISIBLE)

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[8];
                            field[0] = "prix";
                            field[1] = "medic";
                            field[2] = "duree";
                            field[3] = "rdv";
                            field[4] = "titre";
                            field[5] = "region";
                            field[6] = "id";
                            field[7] = "medecin";

                            //Creating array for data
                            String[] data = new String[8];
                            data[0] = Prix;
                            data[1] = Medic;
                            data[2] = Duree;
                            data[3] = Rdv;
                            data[4] = Titre;
                            data[5] = Region;
                            data[6] = Id;
                            data[7] = Medecin;

                            PutData putData = new PutData("http://10.60.20.146/GSB_doctors/secure_API/visiteurInsertCompteRendu.php", "POST", field, data);  // Mettre son ip
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Sign Up Success") && data[7] != "Médecin") { // Vérifie que le spinner a bien été sélectionné sur un médecin
                                        Toast.makeText(getApplicationContext(), "Enregistrement effectué", Toast.LENGTH_SHORT).show();
                                        Intent accueil = new Intent(getApplicationContext(), consulter.class);
                                        startActivity(accueil);
                                        finish();
                                    } else if (data[7] == "Médecin") {
                                        Toast.makeText(getApplicationContext(), "Veuillez sélectionner un médecin", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "Tous les champs sont requis", Toast.LENGTH_SHORT).show();
                }
            }
        });

        String lien = "http://10.60.20.146/GSB_doctors/secure_API/getListMedecin.php?region=" + get_region;
        getJSON(lien);
    }


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = get_id1[position];

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    private void getJSON(final String urlWebService) { // Lecture du tableau Json

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException { // Affichage des différentes colonnes dans les différents blocs (titre, date...)
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            get_medecin1[i] = obj.getString("prenom") + " " + obj.getString("nom");
            get_id1[i+1] = obj.getString("id");
            categories.add(get_medecin1[i]); // Ajout dans le spinner un médecin
        }
    }

    public void openActivity1(){
        Intent saisir = new Intent(this, saisir.class);
        startActivity(saisir);
        finish();
    }

    public void openActivity2(){
        Intent accueil = new Intent(this, visiteur_medecin.class);
        startActivity(accueil);
        finish();
    }

    public void openActivity3(){
        Intent consulter = new Intent(this, consulter.class);
        startActivity(consulter);
        finish();
    }

    // ********** Bouton déconnexion **********

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.visiteur_menu_saisir, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.deco) {
            Intent accueil = new Intent(this, identification.class);
            startActivity(accueil);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}