package com.ikasle.digitu_batura;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etZenbakia;
    private TextView tvMezua;
    private Button btnEgiaztatu, btnHustu, btnIrten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Osagaiak lotu
        etZenbakia = findViewById(R.id.etZenbakia);
        tvMezua = findViewById(R.id.tvMezua);
        btnEgiaztatu = findViewById(R.id.btnEgiaztatu);
        btnHustu = findViewById(R.id.btnHustu);
        btnIrten = findViewById(R.id.btnIrten);

        // Exekutatu botoia
        btnEgiaztatu.setOnClickListener(v -> {
            String testua = etZenbakia.getText().toString().trim();

            // Hutsik badago, errorea erakutsi
            if (testua.isEmpty()) {
                tvMezua.setText(R.string.errorBuhoa);
                return;
            }

            try {
                int zenbakia = Integer.parseInt(testua);
                int emaitza = batu_digitu(zenbakia);
                tvMezua.setText(sumaTestua(zenbakia) + "=" + emaitza);
            } catch (NumberFormatException e) {
                tvMezua.setText(R.string.errorZenbakia);
            }
        });

        // Garbitu botoia
        btnHustu.setOnClickListener(v -> {
            etZenbakia.setText("");
            tvMezua.setText("");
            etZenbakia.requestFocus();
        });

        // Irten botoia
        btnIrten.setOnClickListener(v -> finish());
    }

    // Zenbaki oso baten digituen batura itzultzen du
    public int batu_digitu(int zenbakia) {
        zenbakia = Math.abs(zenbakia);
        int batura = 0;
        while (zenbakia > 0) {
            batura += zenbakia % 10;
            zenbakia /= 10;
        }
        return batura;
    }

    // "2+6+7" bezalako testua sortzen du eragiketa erakusteko
    private String sumaTestua(int zenbakia) {
        String digitoak = String.valueOf(Math.abs(zenbakia));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digitoak.length(); i++) {
            if (i > 0) sb.append("+");
            sb.append(digitoak.charAt(i));
        }
        return sb.toString();
    }
}