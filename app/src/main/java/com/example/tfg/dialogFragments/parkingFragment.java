package com.example.tfg.dialogFragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tfg.R;

public class parkingFragment extends DialogFragment {

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View parkingView = inflater.inflate(R.layout.fragment_parking, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(parkingView);

        assert getArguments() != null;
        String idioma = getArguments().getString("idioma");
        String parking = getArguments().getString("parking");
        int aparcar = getArguments().getInt("aparcar");


        TextView precio = parkingView.findViewById(R.id.euro);
        ProgressBar progressBar = parkingView.findViewById(R.id.progressBar);
        ImageView img = parkingView.findViewById(R.id.parkingfoto);

        if (parking.equalsIgnoreCase("sanantonio")){
            precio.setText(R.string.de_pago);
        } else{
            precio.setText(R.string.gratis);
        }

        progressBar.setProgress(aparcar);
        if (aparcar == 33){
            progressBar.getProgressDrawable().setTint(Color.RED);
        } else if (aparcar == 100){
            progressBar.getProgressDrawable().setTint(Color.rgb(0, 143, 57));
        } else{
            progressBar.getProgressDrawable().setTint(Color.rgb(255,200,30));
        }

        Button no = parkingView.findViewById(R.id.buttonVolverP);

        no.setOnClickListener(view -> dismiss());

        ImageButton info = parkingView.findViewById(R.id.infoparking);

        info.setOnClickListener(view -> {
            DialogFragment parkinginfoFragment = new infoparkingFragment();
            parkinginfoFragment.setCancelable(false);
            parkinginfoFragment.show(getChildFragmentManager(),"parkinginfo_fragment");
        });

        return builder.create();
    }

}