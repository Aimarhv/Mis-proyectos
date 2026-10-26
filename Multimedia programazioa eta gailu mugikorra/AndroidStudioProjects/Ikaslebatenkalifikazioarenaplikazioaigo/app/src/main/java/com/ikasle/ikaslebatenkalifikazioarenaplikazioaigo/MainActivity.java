package com.ikasle.ikaslebatenkalifikazioarenaplikazioaigo;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Onartutako nota tartea (0 eta 20 artekoa)
    private static final double NOTA_MINIMA = 0;
    private static final double NOTA_MAXIMA = 20;

    // Interfazeko osagaiak
    private EditText alumno;
    private EditText nota;
    private TextView resultado;
    private Button limpiar;
    private Button mostrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sistemaren barrak errespetatu eta 16dp-ko padding-a mantendu
        final int margen = (int) (16 * getResources().getDisplayMetrics().density);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            v.setPadding(bars.left + margen, bars.top + margen,
                    bars.right + margen, bars.bottom + margen);
            return insets;
        });

        // Osagaiak layout-ekoekin lotu
        alumno = findViewById(R.id.alumno);
        nota = findViewById(R.id.nota);
        resultado = findViewById(R.id.resultado);
        limpiar = findViewById(R.id.limpiar);
        mostrar = findViewById(R.id.mostrar);

        // GARBITU botoia desgaituta hasten da
        limpiar.setEnabled(false);

        // GARBITU gaitu eremuren batean testua dagoenean; idaztean aurreko emaitza ezabatu
        TextWatcher vigilante = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                limpiar.setEnabled(!alumno.getText().toString().isEmpty()
                        || !nota.getText().toString().isEmpty());
                resultado.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) { }
        };
        alumno.addTextChangedListener(vigilante);
        nota.addTextChangedListener(vigilante);

        limpiar.setOnClickListener(v -> limpiarCampos());
        mostrar.setOnClickListener(v -> mostrarResultado());
    }

    // GARBITU botoia: eremuak, erroreak eta emaitza hustu
    private void limpiarCampos() {
        alumno.setText("");        // izena hustu
        nota.setText("");          // nota hustu
        alumno.setError(null);
        nota.setError(null);
        resultado.setText("");
        alumno.requestFocus();     // kurtsorea izenaren eremura eraman
        limpiar.setEnabled(false); // botoia desgaitu
    }

    // ERAKUTSI botoia: balidatu eta kategoria erakutsi
    private void mostrarResultado() {
        String nombre = alumno.getText().toString().trim();
        String notaIdatzia = nota.getText().toString().trim();
        // Koma hamartarra onartu: 10,5 -> 10.5
        String textoNota = notaIdatzia.replace(',', '.');

        alumno.setError(null);
        nota.setError(null);

        // 1) Izena hutsik
        if (nombre.isEmpty()) {
            alumno.setError(getString(R.string.izena_hutsik));
            alumno.requestFocus();
            return;
        }

        // 2) Nota hutsik
        if (textoNota.isEmpty()) {
            nota.setError(getString(R.string.nota_hutsik));
            nota.requestFocus();
            return;
        }

        // 3) Nota zenbaki baliozkoa den edo ez
        double valor;
        try {
            valor = Double.parseDouble(textoNota);
        } catch (NumberFormatException e) {
            nota.setError(getString(R.string.nota_baliogabea));
            nota.requestFocus();
            return;
        }

        // 4) Nota 0 eta 20 artekoa den edo ez
        if (valor < NOTA_MINIMA || valor > NOTA_MAXIMA) {
            nota.setError(getString(R.string.nota_tartea));
            nota.requestFocus();
            return;
        }

        // Dena zuzena: teklatua ezkutatu eta emaitza erakutsi
        ocultarTeclado();
        String categoria = getString(obtenerCategoria(valor));
        resultado.setText(getString(R.string.emaitza, nombre, notaIdatzia, categoria));
    }

    // 0-10,5 Txarra | 10,5-14 Erdipurdikoa | 14-18 Ona | 18tik gora Bikaina
    private int obtenerCategoria(double valor) {
        if (valor <= 10.5) return R.string.txarra;
        if (valor <= 14) return R.string.erdipurdikoa;
        if (valor <= 18) return R.string.ona;
        return R.string.bikaina;
    }

    //T eklatua izkutatzen du
    private void ocultarTeclado() {
        View foco = getCurrentFocus();
        if (foco != null) {
            InputMethodManager imm =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(foco.getWindowToken(), 0);
            }
        }
    }
}