package com.example.tfg.mapOptions.servicios;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import com.example.tfg.R;

public class infoServFragment extends DialogFragment {

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View infomView = inflater.inflate(R.layout.fragment_infoserv, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(infomView);

        Button volver = infomView.findViewById(R.id.buttonVolverInfoServ);
        volver.setOnClickListener(view -> dismiss());

        return builder.create();
    }

}