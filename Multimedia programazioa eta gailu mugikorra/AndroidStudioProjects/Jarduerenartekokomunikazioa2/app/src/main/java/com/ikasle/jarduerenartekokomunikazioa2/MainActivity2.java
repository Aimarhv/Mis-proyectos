package com.ikasle.jarduerenartekokomunikazioa2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    // Interfazeko elementuak
    TextView mezua;     // Agurra eta galdera
    Button Onartua;     // Onartzeko botoia
    Button Baztertua;   // Baztertzeko botoia
    String esaldia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // XMLko elementuekin lotu
        mezua = findViewById(R.id.agurra);
        Onartua = findViewById(R.id.onartu);
        Baztertua = findViewById(R.id.baztertua);

        // MainActivity-tik jasotako izenarekin esaldia osatu
        Bundle extras = getIntent().getExtras();
        esaldia = "Kaixo, " + extras.getString("dato") + ",  Baldintzak onartzen dituzu?";
        mezua.setText(esaldia);

        // Botoi bakoitzak bere erantzuna itzultzen du
        Onartua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                devolverEmaitza("Onartua");
            }
        });
        Baztertua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                devolverEmaitza("Baztertua");
            }
        });
    }

    // Erantzuna MainActivity-ra bidali eta pantaila hau itxi
    private void devolverEmaitza(String emaitza) {
        Intent intent = new Intent();
        intent.putExtra("emaitza", emaitza);
        setResult(RESULT_OK, intent);
        finish();
    }
}