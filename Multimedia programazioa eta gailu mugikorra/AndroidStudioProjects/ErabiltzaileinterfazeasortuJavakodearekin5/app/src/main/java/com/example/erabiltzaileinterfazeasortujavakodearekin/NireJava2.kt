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

        // 4. Dagoen layout-a LinearLayout bertikala bihurtu kode bidez
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // 5.a. “Auzoko Jaiak” adierazten duen TextView bat sortu eta gehitu
        TextView titulo = new TextView(this);
        titulo.setText("Auzoko Jaiak");
        layout.addView(titulo);

        // 5.b. Auzoko izena adierazteko EditText (Plain Text) + 5.f. Hint-a
        EditText izena = new EditText(this);
        izena.setHint("Auzoko izena sartu");
        layout.addView(izena);

        // 5.c. Helbide arrunterako EditText + 5.f. Hint-a
        EditText helbidea = new EditText(this);
        helbidea.setHint("Helbidea sartu");
        layout.addView(helbidea);

        // 5.d. E-mail-etarako EditText + 5.f. Hint-a eta mota egokia
        EditText email = new EditText(this);
        email.setHint("Emaila sartu");
        email.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        layout.addView(email);

        // 5.e. Hitz sekretu moduko bat (Password) + 5.f. Hint-a
        EditText pasahitza = new EditText(this);
        pasahitza.setHint("Pasahitza sartu");
        pasahitza.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        layout.addView(pasahitza);

        // 5.g. Bi botoi gehitu: BIDALI eta IRTEN
        Button bidali = new Button(this);
        bidali.setText("BIDALI");
        layout.addView(bidali);

        Button irten = new Button(this);
        irten.setText("IRTEN");
        layout.addView(irten);

        // setContentView deia egin baino lehen elementu guztiak gehitu dira
        setContentView(layout);
    }
}