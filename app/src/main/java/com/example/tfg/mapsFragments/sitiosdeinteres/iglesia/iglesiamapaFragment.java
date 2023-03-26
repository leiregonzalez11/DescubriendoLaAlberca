package com.example.tfg.mapsFragments.sitiosdeinteres.iglesia;

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
import com.example.tfg.mapsFragments.sitiosdeinteres.info.infomonu1Fragment;
import com.example.tfg.mapsFragments.sitiosdeinteres.monumentos3;


public class iglesiamapaFragment extends DialogFragment implements View.OnClickListener {

    private final Bundle args = new Bundle();
    ImageButton iglesia, predio1, predio2, predio3, predio4, predio5, hornacina, marrano;
    Animation slideAnimation;
    View iglesiaView;

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        iglesiaView = inflater.inflate(R.layout.fragmentdialog_iglesiamapa, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(iglesiaView);

        assert getArguments()!=null;
        String idioma = getArguments().getString("idioma");
        args.putString("idioma", idioma);

        // HERE WE ARE TAKING THE REFERENCE OF OUR IMAGE
        // SO THAT WE CAN PERFORM ANIMATION USING THAT IMAGE

        setListeners();

        ImageButton info = iglesiaView.findViewById(R.id.moninfo1);
        info.setOnClickListener(view -> {
            DialogFragment fragment = new infomonu1Fragment();
            fragment.setCancelable(false);
            fragment.show(getChildFragmentManager(),"plaza_fragment");
        });

        Button volver = iglesiaView.findViewById(R.id.buttonVolverPlazaIglesia);
        volver.setOnClickListener(view -> zoomOut());

        return builder.create();
    }

    private void setListeners() {

        marrano = iglesiaView.findViewById(R.id.buttonMarrano);
        marrano.setOnClickListener(this);
        iglesia = iglesiaView.findViewById(R.id.buttonIglesia);
        iglesia.setOnClickListener(this);
        hornacina = iglesiaView.findViewById(R.id.buttonHornacina);
        hornacina.setOnClickListener(this);
        predio1 = iglesiaView.findViewById(R.id.buttonpredio1);
        predio1.setOnClickListener(this);
        predio2 = iglesiaView.findViewById(R.id.buttonpredio2);
        predio2.setOnClickListener(this);
        predio3 = iglesiaView.findViewById(R.id.buttonpredio3);
        predio3.setOnClickListener(this);
        predio4 = iglesiaView.findViewById(R.id.buttonpredio4);
        predio4.setOnClickListener(this);
        predio5 = iglesiaView.findViewById(R.id.buttonpredio5);
        predio5.setOnClickListener(this);

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        ImageButton btn = (ImageButton) view;

        switch (btn.getId()) {

            case R.id.buttonHornacina:
                String monumento = "hornacinaanimas";
                Toast.makeText(getContext(), "Has pulsado:" + monumento, Toast.LENGTH_LONG).show();
                break;
            case R.id.buttonMarrano:
                monumento = "esculturamarrano";
                Toast.makeText(getContext(), "Has pulsado:" + monumento, Toast.LENGTH_LONG).show();
                break;
            case R.id.buttonIglesia:
                DialogFragment iglesiaF = new iglesiaFragment();
                zoomIn(iglesiaF, btn);
                break;
            case R.id.buttonpredio1:
            case R.id.buttonpredio2:
            case R.id.buttonpredio3:
            case R.id.buttonpredio4:
            case R.id.buttonpredio5:
                monumento = "predios";
                DialogFragment fragment = new monumentos3();
                args.putString("monumento", monumento);
                args.putString("titulo", "Predios");
                zoomIn(fragment, btn);
                break;

        }

    }

    public void zoomIn (DialogFragment fragment, View view){

        slideAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_in2);
        view.startAnimation(slideAnimation);

        new Handler().postDelayed(() -> {
            fragment.setArguments(args);
            fragment.setCancelable(false);
            fragment.show(getChildFragmentManager(),"fragment");
        },1000);
    }


    public void zoomOut (){
        slideAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_out);
       iglesiaView.startAnimation(slideAnimation);

        new Handler().postDelayed(this::dismiss,900);
    }

}