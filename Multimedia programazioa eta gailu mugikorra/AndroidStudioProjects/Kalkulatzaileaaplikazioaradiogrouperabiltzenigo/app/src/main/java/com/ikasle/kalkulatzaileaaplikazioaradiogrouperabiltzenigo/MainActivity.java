package com.ikasle.kalkulatzaileaaplikazioaradiogrouperabiltzenigo;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Interfazeako osagaien aldagaiak deklaratzen dira
    private RadioButton batuketa;
    private RadioButton kenketa;
    private RadioButton biderketa;
    private RadioButton zatiketa;
    private Button garbitu;
    private Button kalkulatu;
    private EditText zenb1;
    private EditText zenb2;
    private TextView tvEmaitza; // Emaitza bistaratzeko TextView-a

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // XML fitxategiko elementuak Java-ko aldagaiekin lotzen dira IDen bidez
        garbitu = (Button) findViewById(R.id.Garbitu);
        garbitu.setEnabled(false); // Hasieran garbitu botoia desaktibatuta egongo da

        batuketa = (RadioButton) findViewById(R.id.rbsuma);
        kenketa = (RadioButton) findViewById(R.id.rbresta);
        biderketa = (RadioButton) findViewById(R.id.rbmultiplicacion);
        zatiketa = (RadioButton) findViewById(R.id.rbdivision);

        kalkulatu = (Button) findViewById(R.id.Erakutsi);
        tvEmaitza = (TextView) findViewById(R.id.Emaitza);

        // Kalkulatu ("Erakutsi") botoia sakatzean exekutatuko den ekintza
        kalkulatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pantailako teklatua ezkutatzeko metodoari deitzen zaio
                helaraziTeklatua();

                zenb1 = (EditText) findViewById(R.id.etzenbakia1);
                zenb2 = (EditText) findViewById(R.id.etzenbakia2);

                // Eremuak hutsik ez daudela ziurtatzen da
                if(!zenb1.getText().toString().isEmpty() && !zenb2.getText().toString().isEmpty()) {
                    garbitu.setEnabled(true); // Eremuak beteta daudenez, garbitu botoia aktibatzen da

                    // Eragiketetako bat hautatuta dagoela egiaztatzen da
                    if(batuketa.isChecked() || kenketa.isChecked() || biderketa.isChecked() || zatiketa.isChecked()) {

                        final double numero1, numero2;

                        // EditText-etako testua zenbaki hamartar (double) bihurtzen da
                        numero1 = Double.parseDouble(zenb1.getText().toString());
                        numero2 = Double.parseDouble(zenb2.getText().toString());

                        double emaitza = 0;
                        boolean eginda = true;

                        // Hautatutako aukeraren arabera dagokion eragiketa-metodoa exekutatzen da
                        if(batuketa.isChecked()) {
                            emaitza = batu(numero1, numero2);
                        } else if(kenketa.isChecked()) {
                            emaitza = kendu(numero1, numero2);
                        } else if(biderketa.isChecked()) {
                            emaitza = biderkatu(numero1, numero2);
                        } else if(zatiketa.isChecked()) {
                            // Salbuespenen kontrola: zeroz zatitzea eragozteko
                            if(numero2 == 0) {
                                Toast.makeText(MainActivity.this, "Ezin da zeroz zatitu", Toast.LENGTH_SHORT).show();
                                tvEmaitza.setText("Errorea: Ezin da zeroz zatitu");
                                eginda = false;
                            } else {
                                emaitza = zatitu(numero1, numero2);
                            }
                        }

                        // Eragiketa zuzena izan bada, emaitza TextView-an agertzen da (bi hamartarrekin)
                        if(eginda) {
                            tvEmaitza.setText(String.format("Emaitza: %.2f", emaitza));
                        }

                    } else {
                        Toast.makeText(MainActivity.this, "Hautatu eragiketa bat egiteko", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Bi zenbakiek balioa izan behar dute", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Garbitu botoia sakatzean datu guztiak eta hautapenak garbitzen dira
        garbitu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zenb1.setText("");
                zenb2.setText("");
                batuketa.setChecked(false);
                kenketa.setChecked(false);
                biderketa.setChecked(false);
                zatiketa.setChecked(false);
                tvEmaitza.setText(""); // Emaitza eremua ere garbitzen da
                garbitu.setEnabled(false); // Garbitu botoia berriro desaktibatzen da
            }
        });
    }

    // --- TEKLATUA EZKUTATZEko METODOA ---
    private void helaraziTeklatua() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    // --- ERAGIKETA BAKOITZEKO METODO INDEPENDENTEAK PARAMETROEKIN ---


    private double batu(double a, double b) {
        return a + b;
    }


    private double kendu(double a, double b) {
        return a - b;
    }


    private double biderkatu(double a, double b) {
        return a * b;
    }


    private double zatitu(double a, double b) {
        return a / b;
    }
}