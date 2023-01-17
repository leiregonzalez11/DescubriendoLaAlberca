package com.example.tfg.mapsFragments.sitiosdeinteres;

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
import android.widget.Toast;

import com.example.tfg.R;

public class iglesiaFragment extends DialogFragment implements View.OnClickListener {

    private String monumento;

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View plazaView = inflater.inflate(R.layout.fragmentdialog_iglesia, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(plazaView);

        //assert getArguments()!=null;
        //String idioma = getArguments().getString("idioma");

        setListeners(plazaView);

        Button volver = plazaView.findViewById(R.id.buttonVolverIglesia);
        volver.setOnClickListener(view -> dismiss());

        return builder.create();
    }

    private void setListeners(View plazaView) {

        Button torre = plazaView.findViewById(R.id.torre);
        torre.setOnClickListener(this);
        Button carmen = plazaView.findViewById(R.id.retablovirgencarmen);
        carmen.setOnClickListener(this);
        Button pedro = plazaView.findViewById(R.id.retablosanpedro);
        pedro.setOnClickListener(this);
        Button sudor = plazaView.findViewById(R.id.retablocristosudor);
        sudor.setOnClickListener(this);
        Button rosario = plazaView.findViewById(R.id.virgenrosario);
        rosario.setOnClickListener(this);
        Button capillacentral = plazaView.findViewById(R.id.capillacentral);
        capillacentral.setOnClickListener(this);
        Button salida1 = plazaView.findViewById(R.id.portico1);
        salida1.setOnClickListener(this);
        Button salida2 = plazaView.findViewById(R.id.portico2);
        salida2.setOnClickListener(this);
        Button pilab = plazaView.findViewById(R.id.pilabautismal);
        pilab.setOnClickListener(this);
        Button pila1 = plazaView.findViewById(R.id.pilaagua1);
        pila1.setOnClickListener(this);
        Button pila2 = plazaView.findViewById(R.id.pilaagua2);
        pila2.setOnClickListener(this);
        Button retablomayor = plazaView.findViewById(R.id.retablomayor);
        retablomayor.setOnClickListener(this);
        Button dolores = plazaView.findViewById(R.id.capilladolores);
        dolores.setOnClickListener(this);
        Button pulpito = plazaView.findViewById(R.id.pulpito);
        pulpito.setOnClickListener(this);
        Button ana = plazaView.findViewById(R.id.retablosantaana);
        ana.setOnClickListener(this);
        Button santoc = plazaView.findViewById(R.id.santocristo);
        santoc.setOnClickListener(this);


    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        Button btn = (Button) view;

        switch (btn.getId()) {


        }

    }
}