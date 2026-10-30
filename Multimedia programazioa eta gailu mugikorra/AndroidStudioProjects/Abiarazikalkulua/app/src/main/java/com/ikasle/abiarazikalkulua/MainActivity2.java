package com.ikasle.abiarazikalkulua;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Bigarren jarduera: lehenengo jarduerako izena erakusten du,
 * zenbaki bat sartu eta haren bikoitza kalkulatzen du.
 */
public class MainActivity2 extends AppCompatActivity {

    // Layoutaren bistak (onCreate-n lotzen dira)
    private TextView tvIzena;                 // Erabiltzailearen izena erakusteko
    private EditText etZenbakia, etEmaitza;   // Zenbakiaren eta emaitzaren eremuak
    private Button biderkatu, atera;          // "Biderkatu x 2" eta "Atera" botoiak

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Aplikazioak pantaila osoa erabiltzea ahalbidetzen du
        EdgeToEdge.enable(this);

        // Jarduera honen XML diseinua kargatzen du
        setContentView(R.layout.activity_main2);

        // Padding-a doitzen du, edukia sistemaren barrek estal ez dezaten
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Java aldagaiak XMLko elementuekin lotzen ditu id-aren bidez
        tvIzena = findViewById(R.id.tvizena);
        etZenbakia = findViewById(R.id.etzenbakia);
        etEmaitza = findViewById(R.id.etemaitza);
        biderkatu = findViewById(R.id.biderkatu);
        atera = findViewById(R.id.atera);

        // Lehenengo jarduerak bidalitako izena jaso eta erakutsi (4. puntua)
        String izena = getIntent().getStringExtra("izena");
        tvIzena.setText(getString(R.string.kaixo, izena));

        // "Biderkatu x 2" botoiaren ekintza: zenbakia baliozkotu eta bikoitza kalkulatu
        biderkatu.setOnClickListener(v -> {
            // Idatzitako testua irakurri eta zuriuneak kendu
            String testua = etZenbakia.getText().toString().trim();

            // Baliozkotzea: eremua hutsik badago, mezua erakutsi eta ez jarraitu
            if (testua.isEmpty()) {
                Toast.makeText(this, R.string.zenbakia_derrigorrezkoa, Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                // Testua zenbaki bihurtu eta 2rekin biderkatu
                double emaitza = Double.parseDouble(testua) * 2;

                // Emaitza osoa bada, ".0" gabe erakutsi (adib. 50 eta ez 50.0)
                if (emaitza == (long) emaitza) {
                    etEmaitza.setText(String.valueOf((long) emaitza));
                } else {
                    etEmaitza.setText(String.valueOf(emaitza));
                }
            } catch (NumberFormatException e) {
                // Testua ez bada zenbaki baliozkoa (adib. "-" edo "."), errore-mezua erakutsi
                Toast.makeText(this, R.string.zenbakia_derrigorrezkoa, Toast.LENGTH_SHORT).show();
            }
        });

        // "Atera" botoiaren ekintza: jarduera hau ixten du eta aurrekora itzultzen da
        atera.setOnClickListener(v -> finish());
    }
}