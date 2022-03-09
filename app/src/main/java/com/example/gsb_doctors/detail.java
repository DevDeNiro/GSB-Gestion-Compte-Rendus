package com.example.gsb_doctors;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import java.time.Year;

public class detail extends AppCompatActivity {

    TextView titre, age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        titre = (TextView) findViewById(R.id.titre);
        age = (TextView) findViewById(R.id.age);

        Intent intent = getIntent();
        String text = intent.getStringExtra("id1");

        System.out.println(text);

        String lien = "http://192.168.1.136/GSB_doctors/secure_API/getDataDetail.php" + "?id=" + text;
        getJSON(lien);
    };

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
        String getNomPrenom, getAge;
        int i = 0;
        int year = Year.now().getValue();
        JSONObject obj = jsonArray.getJSONObject(i);
        getNomPrenom = obj.getString("prenom") + " " + obj.getString("nom");
        getAge = obj.getString("rdv");

        //getAge = year - 5;
        
        titre.setText(getNomPrenom);
        age.setText(getAge);

    }

    @Override
    public void onBackPressed() {
        Intent intentBack = new Intent(this, consulter.class);
        startActivity(intentBack);

    }
}
