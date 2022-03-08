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

public class detail extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        Intent intent = getIntent();
        String text = intent.getStringExtra("id1");

        System.out.println(text);
        };

    @Override
    public void onBackPressed() {
        Intent intentBack = new Intent(this, consulter.class);
        startActivity(intentBack);

    }
    }
