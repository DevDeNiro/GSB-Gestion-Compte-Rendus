package com.example.gsb_doctors;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class detail extends AppCompatActivity {

    TextView prenom, nom, prix, titre, ante, medicament, duree, rdv, medecin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visiteur_detail);
        
        Button b1, b2, b3, b4;
        prenom = (TextView) findViewById(R.id.prenom);
        nom = (TextView) findViewById(R.id.nom);
        ante = (TextView) findViewById(R.id.ante);
        medicament = (TextView) findViewById(R.id.medicament);
        duree = (TextView) findViewById(R.id.duree);
        rdv = (TextView) findViewById(R.id.rdv);
        titre = (TextView) findViewById(R.id.titre);
        prix = (TextView) findViewById(R.id.prix);
        medecin = (TextView) findViewById(R.id.medecin);

        Intent intent = getIntent();
        String text = intent.getStringExtra("id2");

        System.out.println(text);

        String lien = "http://10.60.22.78/GSB_doctors/secure_API/getDataDetailVisiteur.php" + "?id=" + text;
        getJSON(lien);

        b1 = (Button) findViewById(R.id.bouton1);
        b2 = (Button) findViewById(R.id.bouton2);
        b3 = (Button) findViewById(R.id.bouton3);
        b4 = (Button) findViewById(R.id.bouton4);

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

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String suppr_id = suppr();
                String id_compte = suppr_id;

                String[] field = new String[1];
                field[0] = "id_compte";

                //Creating array for data
                String[] data = new String[1];
                data[0] = id_compte;


                PutData putData = new PutData("http://10.60.22.78/GSB_doctors/secure_API/delete.php", "POST", field, data);  // Mettre son ip
                if (putData.startPut()) {
                    if (putData.onComplete()) {

                        String result = putData.getResult();
                        System.out.println(result);

                        if (result.equals("Sign Up Success")) {
                            Toast.makeText(getApplicationContext(), "Suppression effectuée", Toast.LENGTH_SHORT).show();
                            Intent consulter = new Intent(getApplicationContext(), consulter.class);
                            startActivity(consulter);
                            finish();
                        } else {

                            Toast.makeText(getApplicationContext(), "Echec de la suppression", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }
        });
    };

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

    private void getJSON(final String urlWebService) {

        System.out.println(urlWebService);

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

    private void loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String getPrenom, getNom, getAnte, getMedic, getDuree, getRdv, getPrix, getTitre, getId, Medecin;
        int i = 0;
        JSONObject obj = jsonArray.getJSONObject(i);
        getPrenom = obj.getString("prenom");
        getNom = obj.getString("nom");
        getAnte = obj.getString("ante");
        getMedic = obj.getString("medic");
        getDuree = obj.getString("duree");
        getRdv = obj.getString("rdv");
        getPrix = obj.getString("prix");
        getTitre = obj.getString("titre");
        getId = obj.getString("id");
        Medecin = obj.getString("prenom_medecin") + " " + obj.getString("nom_medecin");

        prenom.setText(getPrenom);
        nom.setText(getNom);
        ante.setText(getAnte);
        medicament.setText(getMedic);
        duree.setText(getDuree);
        rdv.setText(getRdv);
        prix.setText(getPrix);
        titre.setText(getTitre);
        medecin.setText(Medecin);

        SharedPreferences sharedpreferences = getSharedPreferences("suppr", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        sharedpreferences.edit().clear().apply();
        editor.putString("suppr_id", getId);
        editor.commit();
    }

    public String suppr(){
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("suppr", MODE_PRIVATE);
        return prefs.getString("suppr_id", "Aucun");
    }

    @Override
    public void onBackPressed() {
        Intent intentBack = new Intent(this, consulter.class);
        startActivity(intentBack);

    }
}
