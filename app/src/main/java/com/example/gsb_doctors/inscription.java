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


public class inscription extends AppCompatActivity {

  android.widget.EditText Pseudo1, mdp1;
  Button b3;
  TextView titles;
  ProgressBar progressBar;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.inscription);

    Pseudo1 = findViewById(R.id.InscriptionPseudo);
    mdp1 = findViewById(R.id.MotDePasse);
    b3 = findViewById(R.id.button3bis);
    progressBar = findViewById(R.id.progress);

    b3.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        String pseudo, password;
        pseudo = String.valueOf(Pseudo1.getText());
        password = String.valueOf(mdp1.getText());

        // Verification of the type of the value entered in the EditText
        if (!pseudo.equals("") && !password.equals("")) {
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
              data[0] = pseudo;
              data[1] = password;

              PutData putData = new PutData("http://10.60.20.146/GSB_doctors/secure_API/signup.php", "POST", field, data);  // Mettre son ip
              if (putData.startPut()) {
                if (putData.onComplete()) {
                  progressBar.setVisibility(View.GONE);

                  String result = putData.getResult();

                  if (result.equals("Sign Up Success")) {
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                    Intent identification = new Intent(getApplicationContext(), visiteur_medecin.class);
                    startActivity(identification);
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
