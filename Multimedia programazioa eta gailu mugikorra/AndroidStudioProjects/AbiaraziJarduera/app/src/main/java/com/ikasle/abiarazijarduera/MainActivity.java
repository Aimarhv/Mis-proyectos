package com.ikasle.abiarazijarduera;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

// Lehen jarduera: produktuaren izena eta zenbatekoa (BEZ gabe) jasotzen ditu
public class MainActivity extends AppCompatActivity {

    // Interfazeko elementuak: artikuluaren izena, zenbatekoa eta +BEZ botoia
    private EditText etArtikulua, etInportea;
    private Button btnIva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Layout-a kargatu (activity_main.xml)
        setContentView(R.layout.activity_main);

        // XML-ko elementuak Java aldagaiekin lotu
        etArtikulua = findViewById(R.id.etArtikulua);
        etInportea = findViewById(R.id.etInportea);
        btnIva = findViewById(R.id.IVA);

        // +BEZ botoia sakatzean exekutatuko da
        btnIva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Testuak irakurri, hutsuneak kendu eta koma puntuarekin ordezkatu
                String artikulua = etArtikulua.getText().toString().trim();
                String inporteaTxt = etInportea.getText().toString().trim().replace(',', '.');

                // Balidazioak
                // 1. Artikuluaren izena ez dago hutsik
                if (artikulua.isEmpty()) {
                    etArtikulua.setError(getString(R.string.errorArtikulua));
                    etArtikulua.requestFocus();
                    return;
                }
                // 2. Zenbatekoa ez dago hutsik
                if (inporteaTxt.isEmpty()) {
                    etInportea.setError(getString(R.string.errorInporteaHutsik));
                    etInportea.requestFocus();
                    return;
                }

                // 3. Zenbatekoa zenbaki baliozkoa da
                double inportea;
                try {
                    inportea = Double.parseDouble(inporteaTxt);
                } catch (NumberFormatException e) {
                    etInportea.setError(getString(R.string.errorInporteaOkerra));
                    etInportea.requestFocus();
                    return;
                }
                // 4. Zenbatekoa zero baino handiagoa da
                if (inportea <= 0) {
                    etInportea.setError(getString(R.string.errorInporteaOkerra));
                    etInportea.requestFocus();
                    return;
                }

                // Balidazio guztiak ondo: bigarren jarduerara joan datuak bidaliz
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("artikulua", artikulua);
                intent.putExtra("inportea", inportea);
                startActivity(intent);
            }
        });
    }
}