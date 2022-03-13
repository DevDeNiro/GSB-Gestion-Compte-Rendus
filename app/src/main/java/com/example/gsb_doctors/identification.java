package com.example.gsb_doctors;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class identification extends AppCompatActivity {

    android.widget.EditText pseudo, mdp;
    Button btn;
    ProgressBar progressBar;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

          super.onCreate(savedInstanceState);
          setContentView(R.layout.login_main);

          pseudo = findViewById(R.id.connexionPseudo);
          mdp = findViewById(R.id.connexionPassword);
          btn = findViewById(R.id.btnLogin);
          progressBar = findViewById(R.id.progress);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String  Pseudo, password;
                    Pseudo = String.valueOf(pseudo.getText());
                    password = String.valueOf(mdp.getText());

                    // Verification of the type of the value entered in the EditText
                    if (!Pseudo.equals("") && !password.equals("")) {
                        //Start ProgressBar first (Set visibility VISIBLE)
                        progressBar.setVisibility(View.VISIBLE);
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //Starting Write and Read data with URL
                                //Creating array for parameters
                                String[] field = new String[2];
                                field[0] = "pseudo";
                                field[1] = "mdp";

                                //Creating array for data
                                String[] data = new String[2];
                                data[0] = Pseudo;
                                data[1] = password;

                                PutData putData = new PutData("http://192.168.1.136/GSB_doctors/secure_API/login.php", "POST", field, data);  // Mettre son adrr ip
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        progressBar.setVisibility(View.GONE);

                                        String result = putData.getResult();

                                        JSONArray jsonArray = null;
                                        try {
                                            jsonArray = new JSONArray(result);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        String[] get_login = new String[jsonArray.length()];
                                        String[] get_region = new String[jsonArray.length()];

                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject obj = null;
                                            try {
                                                obj = jsonArray.getJSONObject(i);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            try {
                                                get_login[i] = obj.getString("login");
                                                get_region[i] = obj.getString("region");
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        if (get_login[0].equals("Login Success")) {
                                            Toast.makeText(getApplicationContext(), "Bienvenue", Toast.LENGTH_SHORT).show();
                                            Intent home = new Intent(getApplicationContext(), accueil.class);
                                            startActivity(home);
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
    }
