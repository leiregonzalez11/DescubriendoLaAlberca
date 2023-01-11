package com.example.tfg.dialogFragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tfg.R;

public class parkingFragment extends DialogFragment {

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View parkingView = inflater.inflate(R.layout.fragment_parking, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(parkingView);

        if (getArguments() != null) {
            String idioma = getArguments().getString("idioma");
            String parking = getArguments().getString("parking");
        }

        TextView precio = parkingView.findViewById(R.id.euro);
        TextView aparcar = parkingView.findViewById(R.id.aparcar);
        ImageView img = parkingView.findViewById(R.id.parkingfoto);



        Button no = parkingView.findViewById(R.id.buttonVolverP);

        no.setOnClickListener(view -> dismiss());

        return builder.create();
    }

}