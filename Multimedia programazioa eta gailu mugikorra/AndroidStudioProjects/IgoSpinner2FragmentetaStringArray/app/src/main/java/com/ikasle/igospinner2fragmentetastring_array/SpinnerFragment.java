package com.ikasle.igospinner2fragmentetastring_array;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SpinnerFragment extends Fragment {
    private Spinner gureSpinner;
    private TextView testua;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_spinner_fragment, container, false);

        gureSpinner = view.findViewById(R.id.spinner);
        testua = view.findViewById(R.id.textView);

        // String-Array-tik adapter-a sortu (strings.xml -> planets_array)
        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.planets_array,
                android.R.layout.simple_spinner_item      // itxita dagoenean
        );
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // zabalik

        gureSpinner.setAdapter(adaptador);
        gureSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                testua.setText("Aukeratua: " + selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                testua.setText("");
            }
        });

        return view;
    }
}