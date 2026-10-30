package com.ikasle.abiarazikalkulua;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Lehenengo jarduera: erabiltzailearen izena eskatzen du.
 * Izena hutsik ez badago, MainActivity2 irekitzen du eta izena bidaltzen dio.
 */
public class MainActivity extends AppCompatActivity {

    // Layoutaren bistak (onCreate-n lotzen dira)
    private EditText etIzena;          // Izenaren testu-eremua
    private Button kalkulatu, atera;   // "Kalkulatu" eta "Atera" botoiak

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Aplikazioak pantaila osoa erabiltzea ahalbidetzen du (sistemaren barren atzean)
        EdgeToEdge.enable(this);

        // Jarduera honen XML diseinua kargatzen du
        setContentView(R.layout.activity_main);

        // Padding-a doitzen du, edukia egoera-barrak eta nabigazio-barrak
        // estal ez dezaten
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Java aldagaiak XMLko elementuekin lotzen ditu id-aren bidez
        etIzena = findViewById(R.id.etIzena);
        kalkulatu = findViewById(R.id.kalkulatu);
        atera = findViewById(R.id.atera);

        // "Kalkulatu" botoiaren ekintza: izena baliozkotu eta 2. jarduera ireki
        kalkulatu.setOnClickListener(v -> {
            // Idatzitako testua irakurri eta hasierako eta amaierako zuriuneak kendu
            String izena = etIzena.getText().toString().trim();

            // Baliozkotzea: eremua hutsik badago, mezua erakutsi eta ez jarraitu
            if (izena.isEmpty()) {
                Toast.makeText(this, R.string.izena_derrigorrezkoa, Toast.LENGTH_SHORT).show();
                return; // Listenertik irteten da, bigarren jarduera ireki gabe
            }

            // MainActivity2-ra joateko Intent-a sortu
            Intent intent = new Intent(this, MainActivity2.class);

            // Izena datu gehigarri gisa erantsi ("izena" gakoarekin)
            intent.putExtra("izena", izena);

            // Bigarren jarduera ireki
            startActivity(intent);
        });

        // "Atera" botoiaren ekintza: jarduera hau (eta horrekin aplikazioa) ixten du
        atera.setOnClickListener(v -> finish());
    }
}