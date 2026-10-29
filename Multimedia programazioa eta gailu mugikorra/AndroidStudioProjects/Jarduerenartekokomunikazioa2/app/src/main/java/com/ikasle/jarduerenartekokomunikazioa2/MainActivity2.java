package com.ikasle.jarduerenartekokomunikazioa2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Bigarren pantailaren diseinua kargatu
        setContentView(R.layout.activity_main2);

        // Elementuak lortu
        TextView agurra = findViewById(R.id.agurra);
        Button onartu = findViewById(R.id.onartu);
        Button baztertu = findViewById(R.id.baztertua);

        // Lehen pantailatik bidalitako izena jaso eta agurra erakutsi
        String izena = getIntent().getStringExtra("izena");
        agurra.setText(getString(R.string.agurra, izena));

        // Onartu sakatzean, "Onartua" itzuli
        onartu.setOnClickListener(v -> itzuliEmaitza(getString(R.string.onartua)));

        // Baztertu sakatzean, "Baztertua" itzuli
        baztertu.setOnClickListener(v -> itzuliEmaitza(getString(R.string.baztertua)));
    }

    // Emaitza lehen pantailara bidali eta pantaila hau itxi
    private void itzuliEmaitza(String emaitza) {
        Intent data = new Intent();
        data.putExtra("emaitza", emaitza);
        setResult(RESULT_OK, data);
        finish();
    }
}