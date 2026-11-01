package com.ikasle.abiarazijarduera;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

// Bigarren jarduera: BEZ ehunekoa aukeratu eta zenbateko osoa kalkulatzen du
public class MainActivity2 extends AppCompatActivity {

    // Interfazeko elementuak: erradio taldea, emaitzaren testua eta irten botoia
    private RadioGroup rgBez;
    private TextView tvTotala;
    private Button btnIrten;
    // Lehen jardueratik jasotako hasierako zenbatekoa (BEZ gabe)
    private double inportea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Layout-a kargatu (activity_main2.xml)
        setContentView(R.layout.activity_main2);

        // XML-ko elementuak Java aldagaiekin lotu
        rgBez = findViewById(R.id.rgBez);
        tvTotala = findViewById(R.id.tvTotala);
        btnIrten = findViewById(R.id.btnIrten);

        // Lehen jardueratik bidalitako datuak jaso
        Bundle extras = getIntent().getExtras();
        // Balidazioa: datuak ez badira iritsi, errore mezua erakutsi eta jarduera itxi
        if (extras == null || !extras.containsKey("inportea")) {
            Toast.makeText(this, R.string.errorDatuak, Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        inportea = extras.getDouble("inportea");

        // Erradio botoi bat aukeratzean exekutatuko da
        rgBez.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Aukeratutako erradioaren arabera BEZ ehunekoa zehaztu
                double bez;
                if (checkedId == R.id.rb4) {
                    bez = 4;
                } else if (checkedId == R.id.rb10) {
                    bez = 10;
                } else if (checkedId == R.id.rb21) {
                    bez = 21;
                } else {
                    // Ezer aukeratu gabe: ez egin ezer
                    return;
                }

                // Zenbateko osoa = hasierako zenbatekoa + BEZa
                double totala = inportea + (inportea * bez / 100);
                // Emaitza TextView-an erakutsi, bi hamartarrekin
                tvTotala.setText(getString(R.string.zenbatekoTotala)
                        + " " + String.format("%.2f", totala) + " €");
            }
        });

        // Irten botoia sakatzean aplikazio osoa itxi
        btnIrten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
    }
}