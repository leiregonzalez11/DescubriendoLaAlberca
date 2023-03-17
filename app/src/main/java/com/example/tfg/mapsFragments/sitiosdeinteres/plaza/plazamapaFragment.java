package com.example.tfg.mapsFragments.sitiosdeinteres.plaza;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tfg.R;
import com.example.tfg.mapsFragments.sitiosdeinteres.iglesia.info.infomonu2Fragment;


public class plazamapaFragment extends DialogFragment implements View.OnClickListener {

    Animation slideAnimation;
    View plazaMView;

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        plazaMView = inflater.inflate(R.layout.fragmentdialog_plazamapa, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(plazaMView);

        assert getArguments() != null;
        String idioma = getArguments().getString("idioma");

        setListeners(plazaMView);

        ImageButton info = plazaMView.findViewById(R.id.moninfo2);
        info.setOnClickListener(view -> {
            DialogFragment fragment = new infomonu2Fragment();
            fragment.setCancelable(false);
            fragment.show(getChildFragmentManager(),"plaza_fragment");
        });

        Button volver = plazaMView.findViewById(R.id.buttonVolverPlaza);
        volver.setOnClickListener(view -> zoomOut());

        return builder.create();
    }

    private void setListeners(View plazaMView) {
        Button ayunt = plazaMView.findViewById(R.id.buttonAyuntamiento);
        ayunt.setOnClickListener(this);
        Button teatro = plazaMView.findViewById(R.id.buttonTeatro);
        teatro.setOnClickListener(this);
        Button biblio = plazaMView.findViewById(R.id.buttonBiblioteca);
        biblio.setOnClickListener(this);
        Button crucero = plazaMView.findViewById(R.id.buttonCrucero);
        crucero.setOnClickListener(this);
        Button unamuno = plazaMView.findViewById(R.id.buttonUnamuno);
        unamuno.setOnClickListener(this);
        Button escuelas = plazaMView.findViewById(R.id.buttonEscuelas);
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

    /*public void zoomIn (DialogFragment fragment){
        slideAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_in3);
        backgroundImage.startAnimation(slideAnimation);

        new Handler().postDelayed(() -> {
            fragment.setArguments(args);
            fragment.setCancelable(false);
            fragment.show(getChildFragmentManager(),"fragment");
        },900);
    }*/

    public void zoomOut (){
        slideAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_out);
        plazaMView.startAnimation(slideAnimation);

        new Handler().postDelayed(this::dismiss,900);
    }

}