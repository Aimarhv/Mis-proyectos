package com.ikasle.androidjakinarazpenakiiidialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAlerta = (Button) findViewById(R.id.btnAlerta);
        Button btnBaiezkoa = (Button) findViewById(R.id.btnBaieztapena);
        Button btnSimple = (Button) findViewById(R.id.btnSimple);
        Button btnMultiple = (Button) findViewById(R.id.btnMultiple);

        btnAlerta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                DialogoAlerta dialogo = new DialogoAlerta();
                dialogo.show(fragmentManager, "Alerta");
            }
        });

        btnBaiezkoa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                DialogoBaieztapena dialogo = new DialogoBaieztapena();
                dialogo.show(fragmentManager, "Baiezkoa");
            }
        });

        btnSimple.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                DialogoSelekzioa dialogo = new DialogoSelekzioa();
                dialogo.show(fragmentManager, "Selekzioa");
            }
        });

        btnMultiple.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                DialogoSelekzioMultiplea dialogo = new DialogoSelekzioMultiplea();
                dialogo.show(fragmentManager, "Multiple");
            }
        });
    }
}
