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
import android.widget.ListView;
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

public class delegue_compteRendu extends AppCompatActivity {

    Button b1, b2;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.delegue_consulter);

        b1 = (Button) findViewById(R.id.bouton3);
        b2 = (Button) findViewById(R.id.bouton2);
        listView = (ListView) findViewById(R.id.listView);

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

        getJSON("http://10.60.20.146/GSB_doctors/secure_API/getConsulterVisiteur.php");
    }

    public void openActivity1(){
        Intent delegue_compteRendu = new Intent(this, delegue_compteRendu.class);
        startActivity(delegue_compteRendu);
        finish();
    }

    public void openActivity2(){
        Intent delegue_statistique = new Intent(this, delegue_statistique.class);
        startActivity(delegue_statistique);
        finish();
    }

    public void openActivity3(int id){ // Afficher les détails d'un compte rendu
        String id2 = Integer.toString(id);
        Intent detail = new Intent(this, delegue_detail.class);
        detail.putExtra("id2", id2);
        startActivity(detail);
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
        String[] heroes = new String[jsonArray.length()];
        String[] get_id = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            heroes[i] = obj.getString("titre") + " " + System.getProperty("line.separator")
                    + "Date : " + obj.getString("rdv") + System.getProperty("line.separator")
                    + "Région " + obj.getString("region");

            get_id[i] = obj.getString("id");
            //System.out.println(get_id[i]);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // Création d'un lien unique par ligne
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) { // Variable n : ligne de la listeview
                    int get_id2 = Integer.parseInt(get_id[n]);
                    openActivity3(get_id2);
                }
            });
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, heroes);
        listView.setAdapter(arrayAdapter);
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