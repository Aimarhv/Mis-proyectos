package com.example.erabiltzaile_interfazeasortujavakodearekin;

import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NireJava2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        TextView titulo = new TextView(this);
        titulo.setText("Auzoko Jaiak");

        layout.addView(titulo);

        EditText izena = new EditText(this);
        izena.setHint("Auzoko izena sartu");

        layout.addView(izena);

        EditText helbidea = new EditText (this);
        helbidea.setHint("Helbidea sartu");

        layout.addView(helbidea);

        EditText email = new EditText(this);
        email.setHint("Emaila sartu");
        email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        layout.addView(email);

        EditText pasahitza = new EditText(this);
        pasahitza.setHint("Pasahitza sartu");
        pasahitza.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

        layout.addView(pasahitza);


        Button bidali = new Button(this);
        bidali.setText("BIDALI");

        layout.addView(bidali);

        Button irten = new Button(this);
        irten.setText("IRTEN");

        layout.addView(irten);

        setContentView(layout);

    }

}