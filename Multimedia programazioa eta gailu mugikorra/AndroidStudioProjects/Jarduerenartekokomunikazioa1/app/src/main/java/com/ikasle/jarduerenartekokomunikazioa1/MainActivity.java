package com.ikasle.jarduerenartekokomunikazioa1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Lehen pantailaren diseinua kargatu
        setContentView(R.layout.activity_main);

        // Diseinuko elementuak lortu (botoia eta izenaren testu-eremua)
        Button btn = findViewById(R.id.egiaztatu);
        EditText etIzena = findViewById(R.id.etIzena);

        // Botoia sakatzean, bigarren pantaila ireki
        btn.setOnClickListener(v -> {
            // Bigarren Activity-ra joateko Intent-a sortu
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            // Idatzitako izena bidali "izena" gakoarekin
            intent.putExtra("izena", etIzena.getText().toString());
            // Bigarren pantaila abiarazi
            startActivity(intent);
        });
    }
}