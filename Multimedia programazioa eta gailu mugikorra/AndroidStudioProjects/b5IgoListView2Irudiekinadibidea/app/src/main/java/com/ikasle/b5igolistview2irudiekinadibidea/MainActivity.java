package com.ikasle.b5igolistview2irudiekinadibidea;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends Activity {

    ListViewAdapter adapter;

    // Zerrendako izenburuak
    String[] titulo = new String[]{
            "izenburua1",
            "izenburua2",
            "izenburua3",
            "izenburua4",
    };

    // Zerrendako irudiak (res/drawable karpetakoak)
    int[] imagenes = {
            R.drawable.icon01,
            R.drawable.icon02,
            R.drawable.icon01,
            R.drawable.icon02
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ListView-a lortu, egokigailua sortu eta esleitu
        final ListView lista = (ListView) findViewById(R.id.listView1);
        adapter = new ListViewAdapter(this, titulo, imagenes);
        lista.setAdapter(adapter);

        // Klik laburra
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "sakatu duzu " + i, Toast.LENGTH_SHORT).show();
            }
        });

        // Klik luzea
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "sakatu duzu LUZEA " + i, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }
}