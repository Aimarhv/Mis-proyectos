package com.ikasle.androidjakinarazpenakiiidialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.DialogFragment;
public class DialogoBaieztapena extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity(), android.R.style.Theme_Holo_Light_Dialog);
        builder.setMessage("Hautatutako ekintza berresten du honk?")
                .setTitle("Baieztapena")
                .setPositiveButton("Azeptatu", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("Dialogok", "Azeptatua.");
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Kantzelatu", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("Dialogok", "Kantzelatua.");
                        dialog.cancel();
                    }
                });
        return builder.create();
    }
}