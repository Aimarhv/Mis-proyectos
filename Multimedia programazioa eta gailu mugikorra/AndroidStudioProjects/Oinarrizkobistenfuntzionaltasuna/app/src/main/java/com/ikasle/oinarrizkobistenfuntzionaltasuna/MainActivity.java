package com.ikasle.oinarrizkobistenfuntzionaltasuna;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bistak);

        // TextView
        TextView tv = (TextView) findViewById(R.id.textView1);
        tv.setText("Testua kodetik editatzen dugu");

        // Button
        Button b = (Button) findViewById(R.id.button1);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.e("Info", "Boton click");
            }
        });

        // CheckBox
        CheckBox cb = (CheckBox) findViewById(R.id.checkBox1);
        cb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                if (cb.isChecked()) {
                    Log.e("Info", "CheckBox hautatuta");
                } else {
                    Log.e("Info", "CheckBox ez hautatuta");
                }
            }
        });

        //RadioGroup
        RadioGroup tRadiog = (RadioGroup) findViewById(R.id.radioGroup1);
        tRadiog.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio0) {
                    Log.e("Info", "RadioButton0 aukeratu");
                } else if (checkedId == R.id.radio1) {
                    Log.e("Info", "RadioButton1 aukeratu");
                }
            }
        });

        //EditText
        EditText et = (EditText) findViewById(R.id.editText1);
        et.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    EditText et = (EditText) v;
                    Log.e("Info", et.getText().toString());
                }
                return false;
            }
        });

        // Spinner
        Spinner s = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.SpinnerArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Info", "Se ha seleccionado " + parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        // SeekBar
        SeekBar sb = (SeekBar) findViewById(R.id.seekBar1);
        sb.setMax(10);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
                Integer i = seekBar.getProgress();
                Log.e("Info", i.toString());
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }
        });
    }
}
