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


public class plazamapaFragment extends DialogFragment implements View.OnClickListener {

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

        setListeners(plazaView);

        ImageButton info = plazaView.findViewById(R.id.moninfo2);
        info.setOnClickListener(view -> {
            DialogFragment fragment = new infomonu2Fragment();
            fragment.setCancelable(false);
            fragment.show(getChildFragmentManager(),"plaza_fragment");
        });

        Button volver = plazaView.findViewById(R.id.buttonVolverPlaza);
        volver.setOnClickListener(view -> dismiss());

        return builder.create();
    }

    private void setListeners(View plazaView) {
        Button ayunt = plazaView.findViewById(R.id.buttonAyuntamiento);
        ayunt.setOnClickListener(this);
        Button teatro = plazaView.findViewById(R.id.buttonTeatro);
        teatro.setOnClickListener(this);
        Button biblio = plazaView.findViewById(R.id.buttonBiblioteca);
        biblio.setOnClickListener(this);
        Button crucero = plazaView.findViewById(R.id.buttonCrucero);
        crucero.setOnClickListener(this);
        Button unamuno = plazaView.findViewById(R.id.buttonUnamuno);
        unamuno.setOnClickListener(this);
        Button escuelas = plazaView.findViewById(R.id.buttonEscuelas);
        escuelas.setOnClickListener(this);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        Button btn = (Button) view;

        switch (btn.getId()) {
            case R.id.buttonEscuelas:
                String monumento = "antiguas escuelas";
                Toast.makeText(getContext(), "Has pulsado: " + monumento, Toast.LENGTH_LONG).show();
                break;
            case R.id.buttonBiblioteca:
                monumento = "biblioteca";
                Toast.makeText(getContext(), "Has pulsado: " + monumento, Toast.LENGTH_LONG).show();
                break;
            case R.id.buttonUnamuno:
                monumento = "retrato de unamuno";
                Toast.makeText(getContext(), "Has pulsado: " + monumento, Toast.LENGTH_LONG).show();
                break;
            case R.id.buttonCrucero:
                monumento = "crucero";
                Toast.makeText(getContext(), "Has pulsado: " + monumento, Toast.LENGTH_LONG).show();
                break;
            case R.id.buttonAyuntamiento:
                monumento = "ayuntamiento";
                Toast.makeText(getContext(), "Has pulsado: " + monumento, Toast.LENGTH_LONG).show();
                break;
            case R.id.buttonTeatro:
                monumento = "teatro";
                Toast.makeText(getContext(), "Has pulsado: " + monumento, Toast.LENGTH_LONG).show();
                break;
        }
    }

}