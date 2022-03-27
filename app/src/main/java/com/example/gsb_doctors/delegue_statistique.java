package com.example.gsb_doctors;

import android.content.Context;
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
    int Ile = 0, Paca = 0, Auver = 0, Bourg = 0, Occi = 0, Aqui = 0, Bret = 0, Pays = 0, Cent = 0, Haut = 0, Grand = 0, Norm = 0, Cors = 0,
            calc_Ile = 0, calc_Paca = 0, calc_Auver = 0, calc_Bourg = 0, calc_Occi = 0, calc_Aqui = 0, calc_Bret = 0, calc_Pays = 0, calc_Cent = 0, calc_Haut = 0, calc_Grand = 0, calc_Norm = 0, calc_Cors = 0;
    TextView ile, paca, auver, bourg, occi, aqui, bret, pays, cent, haut, grand, norm, cors;
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
        norm = (TextView) findViewById(R.id.norm);
        cors = (TextView) findViewById(R.id.cors);

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
        System.out.println(Ile);

        getJSON("https://apicompterendu.fr/getCompterenduRegion.php");

        // Récupération des valeurs de région dans des variables
        SharedPreferences ile2 = getApplicationContext().getSharedPreferences("regionIle", MODE_PRIVATE);
        String getIle = ile2.getString("ile", "0");
        SharedPreferences paca2 = getApplicationContext().getSharedPreferences("regionPaca", MODE_PRIVATE);
        String getPaca = paca2.getString("paca", "0");
        SharedPreferences auver2 = getApplicationContext().getSharedPreferences("regionAuver", MODE_PRIVATE);
        String getAuver = auver2.getString("auver", "0");
        SharedPreferences bourg2 = getApplicationContext().getSharedPreferences("regionBourg", MODE_PRIVATE);
        String getBourg = bourg2.getString("bourg", "0");
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
        SharedPreferences norm2 = getApplicationContext().getSharedPreferences("regionNorm", MODE_PRIVATE);
        String getNorm = norm2.getString("norm", "0");
        SharedPreferences cors2 = getApplicationContext().getSharedPreferences("regionCors", MODE_PRIVATE);
        String getCors = cors2.getString("cors", "0");

        // Affichage des valeur dans les champs
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
        norm.setText(getNorm);
        cors.setText(getCors);
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

    private void getJSON(final String urlWebService) { // Lecture du tableau Json

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
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
        //Création des variables globales et nettoyage de ces dernières
        SharedPreferences ile2 = getSharedPreferences("regionIle", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = ile2.edit();
        ile2.edit().clear().apply();
        SharedPreferences paca2 = getSharedPreferences("regionPaca", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = paca2.edit();
        paca2.edit().clear().apply();
        SharedPreferences auver2 = getSharedPreferences("regionAuver", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor3 = auver2.edit();
        auver2.edit().clear().apply();
        SharedPreferences occi2 = getSharedPreferences("regionOcci", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor4 = occi2.edit();
        occi2.edit().clear().apply();
        SharedPreferences aqui2 = getSharedPreferences("regionAqui", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor5 = aqui2.edit();
        aqui2.edit().clear().apply();
        SharedPreferences bret2 = getSharedPreferences("regionBret", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor6 = bret2.edit();
        bret2.edit().clear().apply();
        SharedPreferences pays2 = getSharedPreferences("regionPays", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor7 = pays2.edit();
        pays2.edit().clear().apply();
        SharedPreferences cent2 = getSharedPreferences("regionCent", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor8 = cent2.edit();
        cent2.edit().clear().apply();
        SharedPreferences haut2 = getSharedPreferences("regionHaut", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor9 = haut2.edit();
        haut2.edit().clear().apply();
        SharedPreferences grand2 = getSharedPreferences("regionGrand", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor10 = grand2.edit();
        grand2.edit().clear().apply();
        SharedPreferences bourg2 = getSharedPreferences("regionBourg", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor11 = bourg2.edit();
        bourg2.edit().clear().apply();
        SharedPreferences norm2 = getSharedPreferences("regionNorm", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor12 = norm2.edit();
        norm2.edit().clear().apply();
        SharedPreferences cors2 = getSharedPreferences("regionCors", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor13 = cors2.edit();
        cors2.edit().clear().apply();


        JSONArray jsonArray = new JSONArray(json);
        String[] get_region = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            get_region[i] = obj.getString("region");

            // Récupération du nombre de région
            switch (get_region[i]) {
                case "Ile-De-France":
                    Ile++;
                    break;
                case ("PACA"):
                    Paca++;
                    break;
                case "Auvergne-Rhône-Alpes":
                    Auver++;
                    break;
                case "Bourgogne-Franche-Comté":
                    Bourg++;
                    break;
                case "Occitanie":
                    Occi++;
                    break;
                case "Nouvelle Aquitaine":
                    Aqui++;
                    break;
                case "Bretagne":
                    Bret++;
                    break;
                case "Pays de la Loire":
                    Pays++;
                    break;
                case "Centre-Val de Loire":
                    Cent++;
                    break;
                case "Hauts-de-France":
                    Haut++;
                    break;
                case "Grand Est":
                    Grand++;
                    break;
                case "Normandie":
                    Norm++;
                    break;
                case "Corse":
                    Cors++;
                    break;
            }
        }

        // Calculs pour les pourcentages, et ajout dans les variables globales
        if (Paca + Auver + Bourg + Occi + Aqui + Bret + Pays + Cent + Haut + Grand + Norm + Cors != 0)
            calc_Ile = (100 * Ile) / (Paca + Auver + Bourg + Occi + Aqui + Bret + Pays + Cent + Haut + Grand + Ile + Norm + Cors);
        else
            calc_Ile = 100;
        String string_Ile = Integer.toString(Ile) + " - " + Integer.toString(calc_Ile) + "%";
        editor.putString("ile", String.valueOf(string_Ile));
        editor.commit();

        if (Ile + Auver + Bourg + Occi + Aqui + Bret + Pays + Cent + Haut + Grand + Norm + Cors != 0)
            calc_Paca = (100 * Paca) / (Paca + Auver + Bourg + Occi + Aqui + Bret + Pays + Cent + Haut + Grand + Ile + Norm + Cors);
        else
            calc_Paca = 100;
        String string_Paca = Integer.toString(Paca) + " - " + Integer.toString(calc_Paca) + "%";
        editor2.putString("paca", String.valueOf(string_Paca));
        editor2.commit();

        if (Paca + Ile + Bourg + Occi + Aqui + Bret + Pays + Cent + Haut + Grand + Norm + Cors != 0)
            calc_Auver = (100 * Auver) / (Paca + Auver + Bourg + Occi + Aqui + Bret + Pays + Cent + Haut + Grand + Ile + Norm + Cors);
        else
            calc_Auver = 100;
        String string_Auver = Integer.toString(Auver) + " - " + Integer.toString(calc_Auver) + "%";
        editor3.putString("auver", String.valueOf(string_Auver));
        editor3.commit();

        if (Paca + Auver + Ile + Occi + Aqui + Bret + Pays + Cent + Haut + Grand + Norm + Cors != 0)
            calc_Bourg = (100 * Bourg) / (Paca + Auver + Bourg + Occi + Aqui + Bret + Pays + Cent + Haut + Grand + Ile + Norm + Cors);
        else
            calc_Bourg = 100;
        String string_Bourg = Integer.toString(Bourg) + " - " + Integer.toString(calc_Bourg) + "%";
        editor11.putString("bourg", String.valueOf(string_Bourg));
        editor11.commit();

        if (Paca + Auver + Bourg + Ile + Aqui + Bret + Pays + Cent + Haut + Grand + Norm + Cors != 0)
            calc_Occi = (100 * Occi) / (Paca + Auver + Bourg + Occi + Aqui + Bret + Pays + Cent + Haut + Grand + Ile + Norm + Cors);
        else
            calc_Occi = 100;
        String string_Occi = Integer.toString(Occi) + " - " + Integer.toString(calc_Occi) + "%";
        editor4.putString("occi", String.valueOf(string_Occi));
        editor4.commit();

        if (Paca + Auver + Bourg + Occi + Ile + Bret + Pays + Cent + Haut + Grand + Norm + Cors != 0)
            calc_Aqui = (100 * Aqui) / (Paca + Auver + Bourg + Occi + Aqui + Bret + Pays + Cent + Haut + Grand + Ile + Norm + Cors);
        else
            calc_Aqui = 100;
        String string_Aqui = Integer.toString(Aqui) + " - " + Integer.toString(calc_Aqui) + "%";
        editor5.putString("aqui", String.valueOf(string_Aqui));
        editor5.commit();

        if (Paca + Auver + Bourg + Occi + Aqui + Ile + Pays + Cent + Haut + Grand + Norm + Cors != 0)
            calc_Bret = (100 * Bret) / (Paca + Auver + Bourg + Occi + Aqui + Bret + Pays + Cent + Haut + Grand + Ile + Norm + Cors);
        else
            calc_Bret = 100;
        String string_Bret = Integer.toString(Bret) + " - " + Integer.toString(calc_Bret) + "%";
        editor6.putString("bret", String.valueOf(string_Bret));
        editor6.commit();

        if (Paca + Auver + Bourg + Occi + Aqui + Bret + Ile + Cent + Haut + Grand + Norm + Cors != 0)
            calc_Pays = (100 * Pays) / (Paca + Auver + Bourg + Occi + Aqui + Bret + Pays + Cent + Haut + Grand + Ile + Norm + Cors);
        else
            calc_Pays = 100;
        String string_Pays = Integer.toString(Pays) + " - " + Integer.toString(calc_Pays) + "%";
        editor7.putString("pays", String.valueOf(string_Pays));
        editor7.commit();

        if (Paca + Auver + Bourg + Occi + Aqui + Bret + Pays + Ile + Haut + Grand + Norm + Cors != 0)
            calc_Cent = (100 * Cent) / (Paca + Auver + Bourg + Occi + Aqui + Bret + Pays + Cent + Haut + Grand + Ile + Norm + Cors);
        else
            calc_Cent = 100;
        String string_Cent = Integer.toString(Cent) + " - " + Integer.toString(calc_Cent) + "%";
        editor8.putString("cent", String.valueOf(string_Cent));
        editor8.commit();

        if (Paca + Auver + Bourg + Occi + Aqui + Bret + Pays + Cent + Ile + Grand + Norm + Cors != 0)
            calc_Haut = (100 * Haut) / (Paca + Auver + Bourg + Occi + Aqui + Bret + Pays + Cent + Haut + Grand + Ile + Norm + Cors);
        else
            calc_Haut = 100;
        String string_Haut = Integer.toString(Haut) + " - " + Integer.toString(calc_Haut) + "%";
        editor9.putString("haut", String.valueOf(string_Haut));
        editor9.commit();

        if (Paca + Auver + Bourg + Occi + Aqui + Bret + Pays + Cent + Haut + Ile + Norm + Cors != 0)
            calc_Grand = (100 * Grand) / (Paca + Auver + Bourg + Occi + Aqui + Bret + Pays + Cent + Haut + Grand + Ile + Norm + Cors);
        else
            calc_Grand = 100;
        String string_Grand = Integer.toString(Grand) + " - " + Integer.toString(calc_Grand) + "%";
        editor10.putString("grand", String.valueOf(string_Grand));
        editor10.commit();

        if (Paca + Auver + Bourg + Occi + Aqui + Bret + Pays + Cent + Haut + Ile + Cors != 0)
            calc_Norm = (100 * Norm) / (Paca + Auver + Bourg + Occi + Aqui + Bret + Pays + Cent + Haut + Grand + Ile + Norm + Cors);
        else
            calc_Norm = 100;
        String string_Norm = Integer.toString(Norm) + " - " + Integer.toString(calc_Norm) + "%";
        editor12.putString("norm", String.valueOf(string_Norm));
        editor12.commit();

        if (Paca + Auver + Bourg + Occi + Aqui + Bret + Pays + Cent + Haut + Ile != 0)
            calc_Cors = (100 * Cors) / (Paca + Auver + Bourg + Occi + Aqui + Bret + Pays + Cent + Haut + Grand + Ile + Norm + Cors);
        else
            calc_Cors = 100;
        String string_Cors = Integer.toString(Cors) + " - " + Integer.toString(calc_Cors) + "%";
        editor13.putString("cors", String.valueOf(string_Cors));
        editor13.commit();
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