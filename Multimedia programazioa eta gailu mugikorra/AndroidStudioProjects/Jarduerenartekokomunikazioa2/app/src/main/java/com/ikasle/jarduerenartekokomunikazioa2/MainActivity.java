package com.ikasle.jarduerenartekokomunikazioa2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvEmaitza;

    // Bigarren pantailatik emaitza jasotzeko launcher-a
    private final ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                // Emaitza zuzena bada, "emaitza" gakoko testua erakutsi
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String emaitza = result.getData().getStringExtra("emaitza");
                    tvEmaitza.setText(emaitza);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Lehen pantailaren diseinua kargatu
        setContentView(R.layout.activity_main);

        // Diseinuko elementuak lortu
        Button btn = findViewById(R.id.egiaztatu);
        EditText etIzena = findViewById(R.id.etIzena);
        tvEmaitza = findViewById(R.id.tvEmaitza);

        // Botoia sakatzean, bigarren pantaila ireki izenarekin
        btn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            intent.putExtra("izena", etIzena.getText().toString());
            // startActivity ordez launcher-a erabili, emaitza itzuliko delako
            launcher.launch(intent);
        });
    }
}