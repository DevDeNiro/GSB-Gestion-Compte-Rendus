package com.example.gsb_doctors;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.os.Handler;
import android.os.Looper;
import android.widget.ProgressBar;
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

public class delegue_statistique extends AppCompatActivity {

    Button b1, b2;
    int Ile = 0, Paca = 0, Auver = 0, Bourg = 0, Occi = 0, Aqui = 0, Bret = 0, Pays = 0, Cent = 0, Haut = 0, Grand = 0;
    TextView ile, paca, auver, bourg, occi, aqui, bret, pays, cent, haut, grand;
    String getIle, getpaca, getAuver, getBourg, getOcci, getAqui, getBret, getPays, getCent, getHaut, getGrand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.delegue_statistique);

        b1 = (Button) findViewById(R.id.bouton1);
        b2 = (Button) findViewById(R.id.bouton2);

        ile = (TextView) findViewById(R.id.ile);
        paca = (TextView) findViewById(R.id.paca);
        auver = (TextView) findViewById(R.id.auver);
        bourg = (TextView) findViewById(R.id.bourg);
        occi = (TextView) findViewById(R.id.occi);
        aqui = (TextView) findViewById(R.id.aqui);
        bret = (TextView) findViewById(R.id.bret);
        pays = (TextView) findViewById(R.id.pays);
        cent = (TextView) findViewById(R.id.cent);
        haut = (TextView) findViewById(R.id.haut);
        grand = (TextView) findViewById(R.id.grand);

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
        System.out.println(Ile);
        getJSON("http://10.60.22.78/GSB_doctors/secure_API/getCompterenduRegion.php");
        SharedPreferences ile2 = getApplicationContext().getSharedPreferences("regionIle", MODE_PRIVATE);
        String getIle = ile2.getString("ile", "0");
        SharedPreferences paca2 = getApplicationContext().getSharedPreferences("regionPaca", MODE_PRIVATE);
        String getPaca = paca2.getString("paca", "0");
        SharedPreferences auver2 = getApplicationContext().getSharedPreferences("regionAuver", MODE_PRIVATE);
        String getAuver = auver2.getString("auver", "0");
        SharedPreferences occi2 = getApplicationContext().getSharedPreferences("regionOcci", MODE_PRIVATE);
        String getOcci = occi2.getString("occi", "0");
        SharedPreferences aqui2 = getApplicationContext().getSharedPreferences("regionAqui", MODE_PRIVATE);
        String getAqui = aqui2.getString("aqui", "0");
        SharedPreferences bret2 = getApplicationContext().getSharedPreferences("regionBret", MODE_PRIVATE);
        String getBret = bret2.getString("bret", "0");
        SharedPreferences pays2 = getApplicationContext().getSharedPreferences("regionPays", MODE_PRIVATE);
        String getPays = pays2.getString("pays", "0");
        SharedPreferences cent2 = getApplicationContext().getSharedPreferences("regionCent", MODE_PRIVATE);
        String getCent = cent2.getString("cent", "0");
        SharedPreferences haut2 = getApplicationContext().getSharedPreferences("regionHaut", MODE_PRIVATE);
        String getHaut = haut2.getString("haut", "0");
        SharedPreferences grand2 = getApplicationContext().getSharedPreferences("regionGrand", MODE_PRIVATE);
        String getGrand = grand2.getString("grand", "0");
        SharedPreferences bourg2 = getApplicationContext().getSharedPreferences("regionBourg", MODE_PRIVATE);
        String getBourg = bourg2.getString("bourg", "0");

        ile.setText(getIle);
        paca.setText(getPaca);
        auver.setText(getAuver);
        bourg.setText(getBourg);
        occi.setText(getOcci);
        aqui.setText(getAqui);
        bret.setText(getBret);
        pays.setText(getPays);
        cent.setText(getCent);
        haut.setText(getHaut);
        grand.setText(getGrand);
    }

    public void openActivity1(){
        Intent saisir = new Intent(this, delegue_compteRendu.class);
        startActivity(saisir);
        finish();
    }

    public void openActivity2(){
        Intent accueil = new Intent(this, delegue_statistique.class);
        startActivity(accueil);
        finish();
    }

    private void getJSON(final String urlWebService) {

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
        SharedPreferences ile2 = getSharedPreferences("regionIle", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = ile2.edit();
        ile2.edit().clear().apply();
        SharedPreferences paca2 = getSharedPreferences("regionPaca", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = paca2.edit();
        ile2.edit().clear().apply();
        SharedPreferences auver2 = getSharedPreferences("regionAuver", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor3 = auver2.edit();
        ile2.edit().clear().apply();
        SharedPreferences occi2 = getSharedPreferences("regionOcci", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor4 = occi2.edit();
        ile2.edit().clear().apply();
        SharedPreferences aqui2 = getSharedPreferences("regionAqui", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor5 = aqui2.edit();
        ile2.edit().clear().apply();
        SharedPreferences bret2 = getSharedPreferences("regionBret", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor6 = bret2.edit();
        ile2.edit().clear().apply();
        SharedPreferences pays2 = getSharedPreferences("regionPays", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor7 = pays2.edit();
        ile2.edit().clear().apply();
        SharedPreferences cent2 = getSharedPreferences("regionCent", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor8 = cent2.edit();
        ile2.edit().clear().apply();
        SharedPreferences haut2 = getSharedPreferences("regionHaut", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor9 = haut2.edit();
        ile2.edit().clear().apply();
        SharedPreferences grand2 = getSharedPreferences("regionGrand", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor10 = grand2.edit();
        ile2.edit().clear().apply();
        SharedPreferences bourg2 = getSharedPreferences("regionBourg", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor11 = bourg2.edit();
        ile2.edit().clear().apply();


        JSONArray jsonArray = new JSONArray(json);
        String[] get_region = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

            get_region[i] = obj.getString("region");

            switch (get_region[i]) {
                case "Ile-De-France":
                    Ile++;
                    editor.putString("ile", String.valueOf(Ile));
                    editor.commit();
                    break;
                case ("PACA"):
                    Paca++;
                    editor2.putString("paca", String.valueOf(Ile));
                    editor2.commit();
                    break;
                case "Auvergne-Rhône-Alpes":
                    Auver++;
                    editor3.putString("auver", String.valueOf(Ile));
                    editor3.commit();
                    break;
                case "Bourgogne-Franche-Comté":
                    Bourg++;
                    editor4.putString("bourg", String.valueOf(Ile));
                    editor4.commit();
                    break;
                case "Occitanie":
                    Occi++;
                    editor5.putString("occi", String.valueOf(Ile));
                    editor5.commit();
                    break;
                case "Nouvelle Aquitaine":
                    Aqui++;
                    editor6.putString("aqui", String.valueOf(Ile));
                    editor6.commit();
                    break;
                case "Bretagne":
                    Bret++;
                    editor7.putString("bret", String.valueOf(Ile));
                    editor7.commit();
                    break;
                case "Pays de la Loire":
                    Pays++;
                    editor8.putString("pays", String.valueOf(Ile));
                    editor8.commit();
                    break;
                case "Centre-Val de Loire":
                    Cent++;
                    editor9.putString("cent", String.valueOf(Ile));
                    editor9.commit();
                    break;
                case "Hauts-de-France":
                    Haut++;
                    editor10.putString("haut", String.valueOf(Ile));
                    editor10.commit();
                    break;
                case "Grand Est":
                    Grand++;
                    editor11.putString("grand", String.valueOf(Ile));
                    editor11.commit();
                    break;
            }
        }
    }
}