package com.ikasle.hipotenusarenkalkuluarenaplikazioaigo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

/**
 * Triangelu angeluzuzen baten hipotenusa kalkulatzen duen aplikazioa.
 */
public class MainActivity extends AppCompatActivity {

    // Bi katetoak idazteko laukiak
    private EditText etKateto_a, etKateto_b;
    // Botoiak: bata dena garbitzeko eta bestea kalkulatzeko
    private Button btnGarbitu, btnKalkulatu;
    // Emaitza idazteko testua
    private TextView tvEmaitza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Layout-ko elementuak aldagaiekin lotzen ditut
        etKateto_a = findViewById(R.id.etKateto_a);
        etKateto_b = findViewById(R.id.etKateto_b);
        btnGarbitu = findViewById(R.id.btnGarbitu);
        btnKalkulatu = findViewById(R.id.btnKalkulatu);
        tvEmaitza = findViewById(R.id.tvEmaitza);

        // Kalkulatu botoia sakatzean kalkulatu() metodoa deitzen da
        btnKalkulatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kalkulatu();
            }
        });

        // Garbitu botoia sakatzean eremuak_garbitu() metodoa deitzen da
        btnGarbitu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eremuak_garbitu();
            }
        });
    }

    /**
     * Hipotenusa kalkulatzen du Pitagorasen teoremarekin.
     *
     * @param a lehenengo katetoa
     * @param b bigarren katetoa
     * @return hipotenusa, edo -1 errorea badago
     */
    private double hipotenusa_kalkulatu(double a, double b) {
        try {
            // Pitagoras: h = erro karratua (a2 + b2)
            double h = Math.sqrt(a * a + b * b);
            // Emaitza ez bada zenbaki baliozkoa (NaN edo infinitua), errorea botatzen dut
            if (Double.isNaN(h) || Double.isInfinite(h)) {
                throw new ArithmeticException();
            }
            return h;
        } catch (Exception e) {
            // Errorea badago -1 itzultzen dut
            return -1;
        }
    }

    /**
     * Laukiak eta emaitza garbitzen ditu.
     */
    private void eremuak_garbitu() {
        etKateto_a.setText("");
        etKateto_b.setText("");
        tvEmaitza.setText("");
        // Kurtsorea a katetoaren laukira bueltatzen da
        etKateto_a.requestFocus();
    }

    /**
     * Eremuak begiratzen ditu, hipotenusa kalkulatzen du eta pantailan jartzen du.
     */
    private void kalkulatu() {
        // Begiratu eremuren bat hutsik dagoen
        // Hutsik badago errorea ateratzen da eta return-ekin irteten da
        if (etKateto_a.getText().toString().trim().isEmpty()) {
            errorea(R.string.hutsik_a, etKateto_a);
            return;
        }
        if (etKateto_b.getText().toString().trim().isEmpty()) {
            errorea(R.string.hutsik_b, etKateto_b);
            return;
        }

        // Testua zenbaki bihurtzen dut (double)
        // Bakoitzak bere try-catch-a dauka, ze eremutan dagoen errorea jakiteko
        double a, b;
        try {
            a = Double.parseDouble(etKateto_a.getText().toString());
        } catch (NumberFormatException e) {
            errorea(R.string.zenbaki_baliogabea, etKateto_a);
            return;
        }
        try {
            b = Double.parseDouble(etKateto_b.getText().toString());
        } catch (NumberFormatException e) {
            errorea(R.string.zenbaki_baliogabea, etKateto_b);
            return;
        }

        // Katetoak positiboak izan behar dira (0 edo negatiboa ezin da)
        if (a <= 0) {
            errorea(R.string.positiboa, etKateto_a);
            return;
        }
        if (b <= 0) {
            errorea(R.string.positiboa, etKateto_b);
            return;
        }

        // Hipotenusa kalkulatu
        double h = hipotenusa_kalkulatu(a, b);

        // h negatiboa bada, kalkuluan errorea egon da (-1 itzuli du)
        if (h < 0) {
            errorea(R.string.kalkulu_errorea, etKateto_a);
            return;
        }

        // Osoa bada dezimalik gabe, bestela 2 dezimal
        String testua;
        if (h % 1 == 0) {
            testua = String.valueOf((long) h);
        } else {
            testua = String.format(Locale.getDefault(), "%.2f", h);
        }
        // Emaitza pantailan jarri
        tvEmaitza.setText(getString(R.string.hipotenusa_da, testua));
    }

    /**
     * Errore-mezua duen leihoa erakusten du.
     *
     * @param mezuaId mezuaren id-a (strings.xml-n dago)
     * @param et      errorea eman duen lauki hori
     */
    private void errorea(int mezuaId, final EditText et) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle(R.string.errorea);
        dialog.setMessage(mezuaId);
        // false jartzen dut, botoia sakatu behar delako itxi ahal izateko
        dialog.setCancelable(false);
        dialog.setPositiveButton(R.string.itxi, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface d, int id) {
                // Leihoa itxi, lauki hori garbitu eta kurtsorea bertan jarri
                d.cancel();
                et.setText("");
                et.requestFocus();
            }
        });
        // Leihoa erakutsi
        dialog.show();
    }
}