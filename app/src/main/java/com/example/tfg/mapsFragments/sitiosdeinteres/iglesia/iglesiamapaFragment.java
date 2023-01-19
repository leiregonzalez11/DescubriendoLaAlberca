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
import com.example.tfg.mapsFragments.sitiosdeinteres.iglesia.iglesiaFragment;
import com.example.tfg.mapsFragments.sitiosdeinteres.iglesia.info.infomonu1Fragment;


public class iglesiamapaFragment extends DialogFragment implements View.OnClickListener {

    private Bundle args = new Bundle();

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View plazaView = inflater.inflate(R.layout.fragmentdialog_iglesiamapa, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(plazaView);

        assert getArguments()!=null;
        String idioma = getArguments().getString("idioma");
        args.putString("idioma", idioma);

        setListeners(plazaView);

        ImageButton info = plazaView.findViewById(R.id.moninfo1);
        info.setOnClickListener(view -> {
            DialogFragment fragment = new infomonu1Fragment();
            fragment.setCancelable(false);
            fragment.show(getChildFragmentManager(),"plaza_fragment");
        });

        Button volver = plazaView.findViewById(R.id.buttonVolverPlazaIglesia);
        volver.setOnClickListener(view -> dismiss());

        return builder.create();
    }

    private void setListeners(View plazaView) {

        Button marrano = plazaView.findViewById(R.id.buttonMarrano);
        marrano.setOnClickListener(this);
        Button iglesia = plazaView.findViewById(R.id.buttonIglesia);
        iglesia.setOnClickListener(this);
        Button casaSS = plazaView.findViewById(R.id.buttonCasaSS);
        casaSS.setOnClickListener(this);
        Button hornacina = plazaView.findViewById(R.id.buttonHornacina);
        hornacina.setOnClickListener(this);

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        Button btn = (Button) view;

        switch (btn.getId()) {

            case R.id.buttonHornacina:
                String monumento = "hornacina de la moza de ánimas";
                Toast.makeText(getContext(), "Has pulsado:" + monumento, Toast.LENGTH_LONG).show();
                break;
            case R.id.buttonMarrano:
                monumento = "escultura del marrano de San Antón";
                Toast.makeText(getContext(), "Has pulsado:" + monumento, Toast.LENGTH_LONG).show();
                break;
            case R.id.buttonIglesia:
                DialogFragment iglesia = new iglesiaFragment();
                iglesia.setArguments(args);
                iglesia.setCancelable(false);
                iglesia.show(getChildFragmentManager(),"iglesia_fragment");
                break;
            case R.id.buttonCasaSS:
                monumento = "antigua casa de la Santa Inquisición";
                Toast.makeText(getContext(), "Has pulsado:" + monumento, Toast.LENGTH_LONG).show();
                break;
        }

    }

}