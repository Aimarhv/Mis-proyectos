package com.ikasle.masvistas;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MasVistasActivity extends AppCompatActivity {

    private EditText entrada;
    private TextView salida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        entrada = (EditText) findViewById(R.id.entrada);
        salida = (TextView) findViewById(R.id.salida);
    }

    public void sePulsa0(View view) {
        entrada.setText(entrada.getText() + (String) view.getTag());
    }

    public void sePulsa(View view) {
        salida.setText(String.valueOf(Float.parseFloat(entrada.getText().toString()) * 2.0));
    }
}