package com.example.gsb_doctors;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class detail extends AppCompatActivity {

    TextView prenom, nom, naiss, adresse, tel, email, ante, medicament, duree, rdv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visiteur_detail);

        Button b0, b1, b2, b3;
        prenom = (TextView) findViewById(R.id.prenom);
        nom = (TextView) findViewById(R.id.nom);
        ante = (TextView) findViewById(R.id.ante);
        medicament = (TextView) findViewById(R.id.medicament);
        duree = (TextView) findViewById(R.id.duree);
        rdv = (TextView) findViewById(R.id.rdv);

        Intent intent = getIntent();
        String text = intent.getStringExtra("id1");

        System.out.println(text);

        String lien = "http://192.168.1.16/GSB_doctors/secure_API/getDataDetail.php" + "?id=" + text;
        getJSON(lien);

        b1 = (Button) findViewById(R.id.bouton1);
        b2 = (Button) findViewById(R.id.bouton2);
        b3 = (Button) findViewById(R.id.bouton3);

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
        String getPrenom, getNom, getNaiss, getAdresse, getTel, getEmail, getAnte, getMedoc, getDuree, getRdv;
        int i = 0;
        JSONObject obj = jsonArray.getJSONObject(i);
        getPrenom = obj.getString("prenom");
        getNom = obj.getString("nom");
        getNaiss = obj.getString("naiss");
        getAdresse = obj.getString("adresse");
        getTel = obj.getString("tel");
        getEmail = obj.getString("email");
        getAnte = obj.getString("ante");
        getMedoc = obj.getString("medic");
        getDuree = obj.getString("duree");
        getRdv = obj.getString("rdv");

        prenom.setText(getPrenom);
        nom.setText(getNom);
        naiss.setText(getNaiss);
        adresse.setText(getAdresse);
        tel.setText(getTel);
        email.setText(getEmail);
        ante.setText(getAnte);
        medicament.setText(getMedoc);
        duree.setText(getDuree);
        rdv.setText(getRdv);

        System.out.println(getAdresse);
    }

    @Override
    public void onBackPressed() {
        Intent intentBack = new Intent(this, consulter.class);
        startActivity(intentBack);

    }
}
