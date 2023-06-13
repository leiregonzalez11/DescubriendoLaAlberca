package com.example.tfg.mapOptions.otrosLugares.lashurdes;

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

public class hurdesElector extends DialogFragment implements View.OnClickListener {

    View hurView;
    String lugar;
    DialogFragment fragment;
    private final Bundle args = new Bundle();

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        hurView = inflater.inflate(R.layout.fragment_hurdeselector, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(hurView);

        assert getArguments() != null;
        String idioma = getArguments().getString("idioma");
        args.putString("idioma", idioma);

        setListeners(hurView);

        Button volver = hurView.findViewById(R.id.buttonvolverHurdes);
        volver.setOnClickListener(view -> dismiss());

        return builder.create();
    }

    private void setListeners(View hurView) {

        Button elgasco = hurView.findViewById(R.id.btngasco);
        Button chorro = hurView.findViewById(R.id.btnchorromeancera);
        Button riomalo = hurView.findViewById(R.id.btnriomalo);
        Button mestas = hurView.findViewById(R.id.btnmestas);
        Button meandro = hurView.findViewById(R.id.btnmeandro);
        elgasco.setOnClickListener(this);
        chorro.setOnClickListener(this);
        riomalo.setOnClickListener(this);
        mestas.setOnClickListener(this);
        meandro.setOnClickListener(this);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        Button btn = (Button) view;

        switch (btn.getId()) {
            case R.id.btngasco:
                lugar = "elgasco";
                args.putString("sitio", lugar);
                args.putString("titulo", "El Gasco");
                break;
            case R.id.btnchorromeancera:
                lugar = "chorrodelameancera";
                args.putString("sitio", lugar);
                args.putString("titulo", "Chorro de la Meancera");
                break;
            case R.id.btnmestas:
                lugar = "lasmestas";
                args.putString("sitio", lugar);
                args.putString("titulo", "Las Mestas");
                break;
            case R.id.btnriomalo:
                lugar = "riomalodearriba";
                args.putString("sitio", lugar);
                args.putString("titulo", "Riomalo de Arriba");
                break;
            case R.id.btnmeandro:
                lugar = "meandroelmelero";
                args.putString("sitio", lugar);
                args.putString("titulo", "Meandro del Melero");
                break;

        }

        fragment = new hurdes3mon();
        fragment.setArguments(args);
        fragment.setCancelable(false);
        fragment.show(getChildFragmentManager(),"fragment");

    }

}