package com.example.tfg.tour;

import androidx.fragment.app.DialogFragment;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.tfg.inicio.MainActivity;
import com.example.tfg.R;

public class tourSiFragment extends DialogFragment{

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        Intent inicio = new Intent(getActivity(), MainActivity.class);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setTitle(R.string.dialog_tour_titulo);
        //builder.setMessage(R.string.dialog_tour);

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.fragment_tour_si, null));

        builder.setPositiveButton(R.string.siguiente, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int id) {
                dismiss();
                inicio.putExtra("tour", "siguiente");
                startActivity(inicio);
            }
        });

        return builder.create();
    }
}