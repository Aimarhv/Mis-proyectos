package com.ikasle.zenbakiaasmatu;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_SAIAKERAK = 10;

    private EditText etZenbakia;
    private CheckBox cbSaiakerak, cbLaguntza;
    private TextView tvMezua;
    private Button btnIrten, btnHustu;

    private final Random random = new Random();
    private int zenbakiSekretua;
    private int saiakerak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etZenbakia = findViewById(R.id.etZenbakia);
        cbSaiakerak = findViewById(R.id.cbSaiakerak);
        cbLaguntza = findViewById(R.id.cbLaguntza);
        tvMezua = findViewById(R.id.tvMezua);
        btnIrten = findViewById(R.id.btnIrten);
        btnHustu = findViewById(R.id.btnHustu);
        Button btnEgiaztatu = findViewById(R.id.btnEgiaztatu);

        jokoBerria();

        btnEgiaztatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                egiaztatu();
            }
        });

        btnHustu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hustu();
            }
        });


        btnIrten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
    }

    private void hustu() {
        cbSaiakerak.setChecked(false);
        cbLaguntza.setChecked(false);
        jokoBerria();
        tvMezua.setText("");
    }

    private void jokoBerria() {
        zenbakiSekretua = random.nextInt(101); // 0 - 100
        saiakerak = 0;
        etZenbakia.setText("");
    }

    private void egiaztatu() {
        String testua = etZenbakia.getText().toString().trim();
        if (testua.isEmpty()) {
            Toast.makeText(this, "Sartu zenbaki bat", Toast.LENGTH_SHORT).show();
            return;
        }

        int sartutakoa = Integer.parseInt(testua);
        saiakerak++;

        if (sartutakoa == zenbakiSekretua) {
            tvMezua.setText("Asmatu duzu! " + saiakerak + " saiakera behar izan dituzu.");
            jokoBerria();
            return;
        }

        if (cbSaiakerak.isChecked() && saiakerak >= MAX_SAIAKERAK) {
            tvMezua.setText("Saiakeren muga gainditu da. Zenbakia " + zenbakiSekretua + " zen.");
            jokoBerria();
            return;
        }

        String mezua = "Ez duzu asmatu.";
        if (cbLaguntza.isChecked()) {
            if (zenbakiSekretua > sartutakoa) {
                mezua += " Asmatu beharreko zenbakia handiagoa da.";
            } else {
                mezua += " Asmatu beharreko zenbakia txikiagoa da.";
            }
        }
        tvMezua.setText(mezua);
        etZenbakia.setText("");
    }
}