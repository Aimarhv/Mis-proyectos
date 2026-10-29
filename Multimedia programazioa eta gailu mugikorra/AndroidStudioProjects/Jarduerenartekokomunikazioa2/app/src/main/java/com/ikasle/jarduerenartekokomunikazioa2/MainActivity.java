package com.ikasle.jarduerenartekokomunikazioa2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    // Interfazeko elementuak
    EditText izena;     // Izena idazteko eremua
    Button botoia;      // Bigarren pantaila irekitzen duen botoia
    TextView emaitza;   // Erantzuna erakusten duen testua

    // MainActivity2 ireki eta emaitza jasotzeko abiarazlea
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // XMLko elementuekin lotu
        izena = findViewById(R.id.etIzena);
        botoia = findViewById(R.id.egiaztatu);
        emaitza = findViewById(R.id.tvEmaitza);

        // MainActivity2 itxitakoan bueltatutako emaitza jasotzen du
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            // Gako berarekin irakurri ("emaitza") eta pantailan erakutsi
                            String erantzuna = result.getData().getStringExtra("emaitza");
                            emaitza.setText(erantzuna);
                        }
                    }
                });

        // Botoia sakatzean
        botoia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Izenik ez badago, abisua eman
                if (izena.length() == 0) {
                    Toast.makeText(MainActivity.this, "Izen bat jarri behar duzu", Toast.LENGTH_SHORT).show();
                }
                else {
                    // Izena bigarren pantailara bidali eta hura ireki
                    String name = izena.getText().toString();
                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                    intent.putExtra("dato", name);
                    activityResultLauncher.launch(intent);
                }
            }
        });
    }
}