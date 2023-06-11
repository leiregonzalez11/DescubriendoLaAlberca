package com.example.tfg.OtherFiles.DialogFragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.tfg.R;

public class tourFragment extends DialogFragment{

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        Intent inicio = new Intent(getActivity(), null);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        //builder.setTitle(R.string.dialog_tour_titulo);
        //builder.setMessage(R.string.dialog_tour);

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.fragment_tour, null));

        builder.setNegativeButton(R.string.button_notour, (dialogInterface, id) -> {
            dismiss();
            inicio.putExtra("tour", "notour");
            startActivity(inicio);


        });

        builder.setPositiveButton(R.string.button_tour, (dialogInterface, id) -> {
            dismiss();
            inicio.putExtra("tour", "tour");
            startActivity(inicio);
        });

        return builder.create();
    }
}