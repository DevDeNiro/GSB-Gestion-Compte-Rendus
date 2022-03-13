package com.example.gsb_doctors;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.os.Handler;
import android.os.Looper;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class delegue_compteRendu extends AppCompatActivity {

    Button b1, b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.delegue_consulter);

        b1 = (Button) findViewById(R.id.bouton3);
        b2 = (Button) findViewById(R.id.bouton2);

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
}