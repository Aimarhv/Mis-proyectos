package com.ikasle.androidjakinarazpenakiiidialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.DialogFragment;

public class DialogoSelekzioa extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String[] items = {"Español", "Inglés", "Francés"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Holo_Light_Dialog);


        builder.setTitle("Selekzioa")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        Log.i("Dialogok", "Aukeratitako Aukerak: " + items[item]);
                    }
                });

        return builder.create();
    }
}