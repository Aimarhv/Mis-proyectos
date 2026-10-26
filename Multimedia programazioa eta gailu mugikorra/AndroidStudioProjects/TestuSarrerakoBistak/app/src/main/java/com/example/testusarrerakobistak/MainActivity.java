package com.example.testusarrerakobistak;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formularioa);

        Button btnBidali = findViewById(R.id.button);
        Button btnIrten = findViewById(R.id.button2);

        btnBidali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Datuak ondo bidali dira", Toast.LENGTH_SHORT).show();
            }
        });

        btnIrten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}