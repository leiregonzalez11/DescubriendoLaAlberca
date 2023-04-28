package com.example.tfg.Maps.otrosLugares.penaFrancia;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tfg.GestorDB;
import com.example.tfg.R;


public class rolloinfofragment extends DialogFragment {

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View infoView = inflater.inflate(R.layout.fragment_rolloinfofragment, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(infoView);

        assert getArguments() != null;
        String idioma = getArguments().getString("idioma");

        Button back = infoView.findViewById(R.id.buttonVolver);
        TextView info = infoView.findViewById(R.id.rolloinfotext);

        GestorDB dbHelper = new GestorDB(getContext());

        String [] datos = dbHelper.obtenerInfoLugares(idioma, "elrollo-info", "peñadefrancia", 1);

        info.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        back.setOnClickListener(view -> dismiss());

        return builder.create();
    }

}