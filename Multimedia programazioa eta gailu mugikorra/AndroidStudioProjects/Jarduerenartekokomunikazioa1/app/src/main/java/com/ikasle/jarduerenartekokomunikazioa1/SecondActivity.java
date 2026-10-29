package com.ikasle.jarduerenartekokomunikazioa1;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Bigarren pantailaren diseinua kargatu
        setContentView(R.layout.activity_main2);

        // Agurra erakusteko TextView-a lortu
        TextView agurra = findViewById(R.id.agurra);
        // Lehen pantailatik bidalitako izena jaso ("izena" gakoa)
        String izena = getIntent().getStringExtra("izena");
        // Izena string-aren %1$s hutsunean sartu eta testua erakutsi
        agurra.setText(getString(R.string.agurra, izena));
    }
}