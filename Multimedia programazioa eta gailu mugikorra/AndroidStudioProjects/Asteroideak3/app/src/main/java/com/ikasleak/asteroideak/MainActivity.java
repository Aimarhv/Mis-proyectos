package com.ikasleak.asteroideak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // "Acerca de" botoiaren erreferentzia gordetzen duen aldagaia
    private Button bAcercaDe;
    // "Irten" botoiaren erreferentzia gordetzen duen aldagaia
    private Button bIrten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Klase gurasoaren onCreate metodoari deitzen dio (derrigorrezkoa)
        super.onCreate(savedInstanceState);

        // Jarduera hau main.xml layoutarekin lotzen du
        setContentView(R.layout.main);

        // Botoia bere id-aren bidez bilatzen du eta aldagaian gordetzen du
        bAcercaDe = (Button) findViewById(R.id.AcercaDe);

        // Botoiaren klik gertaerarako entzulea (listener) erregistratzen du
        bAcercaDe.setOnClickListener(new OnClickListener() {
            // Botoia sakatzen den bakoitzean exekutatzen da
            public void onClick(View view) {
                // "Acerca de" jarduera abiarazten du
                lanzarAcercaDe(null);
            }
        });

        // "Irten" botoia bere id-aren bidez bilatzen du eta aldagaian gordetzen du
        bIrten = (Button) findViewById(R.id.Salir);

        // Botoiaren klik gertaerarako entzulea erregistratzen du
        bIrten.setOnClickListener(new OnClickListener() {
            // Botoia sakatzen den bakoitzean exekutatzen da
            public void onClick(View view) {
                // Jarduera ixten du (atzera tekla sakatzearen berdina)
                finish();
            }
        });
    }

    // "Acerca de" jarduera abiarazten duen metodoa
    public void lanzarAcercaDe(View view) {
        // Intent bat sortzen du, jarduera honetatik AcercaDeActivity-ra joateko
        Intent i = new Intent(this, AcercaDeActivity.class);

        startActivity(i);
    }
}