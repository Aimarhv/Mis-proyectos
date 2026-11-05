package com.ikasle.zenbakiaasmatub;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_SAIAKERAK = 10;

    private EditText etZenbakia;

    private RadioGroup rgZenbakiak;
    private RadioButton rbSaiakera, rbLaguntza;
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
        rgZenbakiak = findViewById(R.id.rgZenbakiak);
        rbSaiakera = findViewById(R.id.rbSaiakera);
        rbLaguntza = findViewById(R.id.rbLaguntza);
        tvMezua = findViewById(R.id.tvMezua);
        btnIrten = findViewById(R.id.btnIrten);
        btnHustu = findViewById(R.id.btnHustu);
        Button btnEgiaztatu = findViewById(R.id.btnEgiaztatu);

        jokoBerria();

        rgZenbakiak.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                jokoBerria();
            }
        });

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
        rgZenbakiak.check(R.id.rbSaiakera);
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

        int sartutakoa;
        try {
            sartutakoa = Integer.parseInt(testua);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Zenbaki baliogabea", Toast.LENGTH_SHORT).show();
            return;
        }

        if (sartutakoa < 0 || sartutakoa > 100) {
            Toast.makeText(this, "0 eta 100 artean", Toast.LENGTH_SHORT).show();
            return;
        }

        saiakerak++;

        if (sartutakoa == zenbakiSekretua) {
            tvMezua.setText("Asmatu duzu! " + saiakerak + " saiakera behar izan dituzu.");
            jokoBerria();
            return;
        }

        if (rbSaiakera.isChecked() && saiakerak >= MAX_SAIAKERAK) {
            tvMezua.setText("Saiakeren muga gainditu da. Zenbakia " + zenbakiSekretua + " zen.");
            jokoBerria();
            return;
        }

        String mezua = "Ez duzu asmatu.";
        if (rbLaguntza.isChecked()) {
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