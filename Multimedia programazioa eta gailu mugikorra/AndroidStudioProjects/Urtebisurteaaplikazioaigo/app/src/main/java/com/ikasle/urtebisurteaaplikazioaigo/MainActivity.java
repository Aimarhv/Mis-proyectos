package com.ikasle.urtebisurteaaplikazioaigo;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ikasle.urtebisurteaaplikazioaigo.R;

public class MainActivity extends AppCompatActivity {

    private EditText Urtea1;
    private TextView Emaitza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Urtea1 = findViewById(R.id.Urtea1);
        Emaitza = findViewById(R.id.Emaitza);
    }

    // EGIAZTATU botoiaren onClick
    public void egiaztatu(View view) {
        String testua = Urtea1.getText().toString().trim();

        if (testua.isEmpty()) {
            Emaitza.setText(R.string.errorea_hutsik);
            return;
        }

        int urtea = Integer.parseInt(testua);
        boolean bisurtea = (urtea % 4 == 0 && urtea % 100 != 0) || (urtea % 400 == 0);

        if (bisurtea) {
            Emaitza.setText(getString(R.string.da_bisurtea, urtea));
        } else {
            Emaitza.setText(getString(R.string.ez_da_bisurtea, urtea));
        }
    }

    // Urtea bisurtea da 4ren multiploa bada eta 100ena ez bada, edo 400ena bada
    public boolean bisurtea_da(int urtea) {
        return (urtea % 4 == 0 && urtea % 100 != 0) || (urtea % 400 == 0);
    }
}