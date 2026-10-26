package com.example.erabiltzaileinterfazeasortujavakodearekin;

import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NireJava2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Lyout bertikala
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Text view jarri auzoak jaiak agertzeko
        TextView titulo = new TextView(this);
        titulo.setText("Auzoko Jaiak");
        layout.addView(titulo);

        // Edit text bat jarri, hintekin, auzoko izena agertzeko
        EditText izena = new EditText(this);
        izena.setHint("Auzoko izena sartu");
        layout.addView(izena);

        // Edit text bat jarri, hintekin, helbidea sartu
        EditText helbidea = new EditText(this);
        helbidea.setHint("Helbidea sartu");
        layout.addView(helbidea);

        // Edit text bat jarri, hintekin, emaila sartzeko
        EditText email = new EditText(this);
        email.setHint("Emaila sartu");
        email.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        layout.addView(email);

        // Edit text bat jarri, hintekin, pasahitza jartzeko
        EditText pasahitza = new EditText(this);
        pasahitza.setHint("Pasahitza sartu");
        pasahitza.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        layout.addView(pasahitza);

        // Bi botoi gehitu: BIDALI ETA IRTEN
        Button bidali = new Button(this);
        bidali.setText("BIDALI");
        layout.addView(bidali);

        Button irten = new Button(this);
        irten.setText("IRTEN");
        layout.addView(irten);


        setContentView(layout);

    }
}