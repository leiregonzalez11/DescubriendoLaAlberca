package com.example.tfg.mapsFragments.sitiosdeinteres;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.categoriasFragments.principal.historiaInicio;
import com.google.firebase.storage.FirebaseStorage;


public class plazamapaFragment extends DialogFragment {

    private String monumento;

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View plazaView = inflater.inflate(R.layout.fragmentdialog_plazamapa, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(plazaView);

        assert getArguments() != null;
        String idioma = getArguments().getString("idioma");

        Button ayunt = plazaView.findViewById(R.id.buttonAyuntamiento);
        ayunt.setOnClickListener(view -> {
            monumento = "ayuntamiento";
            Toast.makeText(getContext(), "Has pulsado: " + monumento, Toast.LENGTH_LONG).show();
        });

        Button teatro = plazaView.findViewById(R.id.buttonTeatro);
        teatro.setOnClickListener(view -> {
            monumento = "teatro";
            Toast.makeText(getContext(), "Has pulsado: " + monumento, Toast.LENGTH_LONG).show();
        });

        Button biblio = plazaView.findViewById(R.id.buttonBiblioteca);
        biblio.setOnClickListener(view -> {
            monumento = "biblioteca";
            Toast.makeText(getContext(), "Has pulsado: " + monumento, Toast.LENGTH_LONG).show();
        });

        Button crucero = plazaView.findViewById(R.id.buttonCrucero);
        crucero.setOnClickListener(view -> {
            monumento = "crucero";
            Toast.makeText(getContext(), "Has pulsado: " + monumento, Toast.LENGTH_LONG).show();
        });

        Button unamuno = plazaView.findViewById(R.id.buttonUnamuno);
        unamuno.setOnClickListener(view -> {
            monumento = "imagen de unamuno";
            Toast.makeText(getContext(), "Has pulsado: " + monumento, Toast.LENGTH_LONG).show();
        });

        Button escuelas = plazaView.findViewById(R.id.buttonEscuelas);
        escuelas.setOnClickListener(view -> {
            monumento = "antiguas escuelas";
            Toast.makeText(getContext(), "Has pulsado :" + monumento, Toast.LENGTH_LONG).show();
        });

        Button volver = plazaView.findViewById(R.id.buttonVolverPlaza);
        volver.setOnClickListener(view -> dismiss());

        return builder.create();
    }

}