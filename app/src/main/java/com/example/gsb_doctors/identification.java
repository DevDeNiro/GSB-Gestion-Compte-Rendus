package com.example.gsb_doctors;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vishnusivadas.advanced_httpurlconnection.PutData;


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

                                PutData putData = new PutData("http://192.168.1.136/Chat_Android/login.php", "POST", field, data);  // Mettre son adrr ip
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        progressBar.setVisibility(View.GONE);

                                        String result = putData.getResult();

                                        /*
                                        if (result.equals("Login Success")) {
                                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                            Intent conversations = new Intent(getApplicationContext(), conversations.class);
                                            startActivity(conversations);
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        }
                                         */
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        }
    }
