package com.ikasle.igospinner1activityetatokikoarraylokala;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Spinner cmbSpinner;
    private TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cmbSpinner = findViewById(R.id.spinner);
        texto = findViewById(R.id.textView);

        // Array local con los datos del Spinner
        final String[] datos = {"Sistemas de Gestión Empresarial", "Empresa",
                "Servicios y Procesos", "Acceso a datos", "Móviles", "Interfaces"};

        // Adaptador: simple_spinner_item para el elemento cerrado,
        // simple_spinner_dropdown_item para la lista desplegada
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, datos);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        cmbSpinner.setAdapter(adaptador);

        cmbSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                // También valdría: parent.getItemAtPosition(position).toString()
                texto.setText("Seleccionado: " + datos[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                texto.setText("");
            }
        });
    }
}