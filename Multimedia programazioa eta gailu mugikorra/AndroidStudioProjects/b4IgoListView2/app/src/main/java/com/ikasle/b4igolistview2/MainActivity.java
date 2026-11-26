package com.ikasle.b4igolistview2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView lblEtiqueta;
    private ListView lstOpciones;

    // Zerrendan erakutsiko diren proba-datuak
    private Titular[] datos =
            new Titular[]{
                    new Titular("Izenburua 1", "Azpititulu luzea 1"),
                    new Titular("Izenburua 2", "Azpititulu luzea 2"),
                    new Titular("Izenburua 3", "Azpititulu luzea 3"),
                    new Titular("Izenburua 4", "Azpititulu luzea 4"),
                    new Titular("Izenburua 5", "Azpititulu luzea 5")};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Egokigailua (adaptadorea) sortu
        AdaptadorTitulares adaptador =
                new AdaptadorTitulares(this);

        // XMLko kontrolen erreferentziak lortu
        lblEtiqueta = (TextView)findViewById(R.id.LblEtiqueta);
        lstOpciones = (ListView)findViewById(R.id.LstOpciones);

        // Egokigailua zerrendari esleitu
        lstOpciones.setAdapter(adaptador);

        // Zerrendako elementu bat sakatzean exekutatzen da
        lstOpciones.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                //Alternativa 1: egokigailutik Titular objektua lortu
                String opcionSeleccionada =
                        ((Titular)a.getAdapter().getItem(position)).getTitulo();

                //Alternativa 2: sakatutako bistatik izenburua irakurri
                //String opcionSeleccionada =
                //        ((TextView)v.findViewById(R.id.LblTitulo)).getText().toString();

                // Hautatutako aukera goiko etiketan erakutsi
                lblEtiqueta.setText("Hautatutako aukera: " + opcionSeleccionada);

            }
        });
    }

    // Egokigailu pertsonalizatua: Titular bakoitza errenkada bihurtzen du
    class AdaptadorTitulares extends ArrayAdapter<Titular> {

        Activity context;

        AdaptadorTitulares(Activity context) {
            super(context, R.layout.listitem_titular, datos);
            this.context = context;
        }

        // Zerrendako elementu bat erakutsi behar den bakoitzean deitzen da
        public View getView(int position, View convertView, ViewGroup parent) {
            // listitem_titular.xml diseinua puztu (inflate)
            LayoutInflater inflater = context.getLayoutInflater();
            View item = inflater.inflate(R.layout.listitem_titular, null);

            // Izenburua jarri
            TextView lblTitulo = (TextView)item.findViewById(R.id.LblTitulo);
            lblTitulo.setText(datos[position].getTitulo());

            // Azpititulua jarri
            TextView lblSubtitulo = (TextView)item.findViewById(R.id.LblSubTitulo);
            lblSubtitulo.setText(datos[position].getSubtitulo());

            return(item);
        }
    }
}