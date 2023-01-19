package com.example.tfg.mapsFragments.sitiosdeinteres.iglesia;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tfg.R;
import com.example.tfg.mapsFragments.sitiosdeinteres.iglesia.info.infomonu3Fragment;

public class iglesiaFragment extends DialogFragment implements View.OnClickListener {

    private String monumento;
    private Bundle args = new Bundle();

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

        assert getArguments()!=null;
        String idioma = getArguments().getString("idioma");
        args.putString("idioma", idioma);

        setListeners(plazaView);

        ImageButton info = plazaView.findViewById(R.id.moninfo3);
        info.setOnClickListener(view -> {
            DialogFragment fragment = new infomonu3Fragment();
            fragment.setCancelable(false);
            fragment.show(getChildFragmentManager(),"plaza_fragment");
        });

        Button volver = plazaView.findViewById(R.id.buttonVolverIglesia);
        volver.setOnClickListener(view -> dismiss());

        return builder.create();
    }

    private void setListeners(View plazaView) {

        Button torre = plazaView.findViewById(R.id.torre);
        Button carmen = plazaView.findViewById(R.id.retablovirgencarmen);
        Button pedro = plazaView.findViewById(R.id.retablosanpedro);
        Button sudor = plazaView.findViewById(R.id.retablocristosudor);
        Button rosario = plazaView.findViewById(R.id.virgenrosario);
        Button capillacentral = plazaView.findViewById(R.id.capillacentral);
        Button salida1 = plazaView.findViewById(R.id.portico1);
        Button salida2 = plazaView.findViewById(R.id.portico2);
        Button pilab = plazaView.findViewById(R.id.pilabautismal);
        Button pila1 = plazaView.findViewById(R.id.pilaagua1);
        Button pila2 = plazaView.findViewById(R.id.pilaagua2);
        Button retablomayor = plazaView.findViewById(R.id.retablomayor);
        Button dolores = plazaView.findViewById(R.id.capilladolores);
        Button pulpito = plazaView.findViewById(R.id.pulpito);
        Button ana = plazaView.findViewById(R.id.retablosantaana);
        Button santoc = plazaView.findViewById(R.id.santocristo);
        torre.setOnClickListener(this);
        carmen.setOnClickListener(this);
        pedro.setOnClickListener(this);
        sudor.setOnClickListener(this);
        rosario.setOnClickListener(this);
        capillacentral.setOnClickListener(this);
        salida1.setOnClickListener(this);
        salida2.setOnClickListener(this);
        pilab.setOnClickListener(this);
        pila1.setOnClickListener(this);
        pila2.setOnClickListener(this);
        retablomayor.setOnClickListener(this);
        dolores.setOnClickListener(this);
        pulpito.setOnClickListener(this);
        ana.setOnClickListener(this);
        santoc.setOnClickListener(this);

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        Button btn = (Button) view;
        DialogFragment fragment;

        switch (btn.getId()) {

            case R.id.santocristo:
                monumento = "santocristo";
                break;
            case R.id.retablosanpedro:
                monumento = "sanpedro";
                break;
            case R.id.retablosantaana:
                monumento = "retablosantaana";
                args.putString("retablo", monumento);
                fragment = new retablosFragment();
                fragment.setArguments(args);
                fragment.setCancelable(false);
                fragment.show(getChildFragmentManager(), "retablo_fragment");
                break;
            case R.id.pulpito:
                monumento = "pulpito";
                break;
            case R.id.pilaagua1:
            case R.id.pilaagua2:
                monumento = "pilaagua";
                break;
            case R.id.pilabautismal:
                monumento = "bautismal";
                break;
            case R.id.retablocristosudor:
                monumento = "cristosudor";
                break;
            case R.id.capillacentral:
                monumento = "capillacentral";
                break;
            case R.id.retablomayor:
                monumento = "retablomayor";
                break;
            case R.id.portico1:
            case R.id.portico2:
                monumento = "portico";
                break;
            case R.id.capilladolores:
                monumento = "dolores";
                break;
            case R.id.torre:
                monumento = "torre";
                break;
            case R.id.retablovirgencarmen:
                monumento = "carmen";
                break;
            case R.id.virgenrosario:
                monumento = "retablorosario";
                args.putString("retablo", monumento);
                fragment = new retablosFragment();
                fragment.setArguments(args);
                fragment.setCancelable(false);
                fragment.show(getChildFragmentManager(), "retablo_fragment");
                break;
        }

        if (!monumento.contains("rosario") && !monumento.contains("ana")){
            Toast.makeText(getContext(), "Has pulsado: " + monumento, Toast.LENGTH_SHORT).show();
        }

    }
}