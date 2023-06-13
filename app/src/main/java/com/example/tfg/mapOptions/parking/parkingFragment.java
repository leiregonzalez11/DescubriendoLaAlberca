package com.example.tfg.mapOptions.parking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tfg.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class parkingFragment extends DialogFragment {

    private StorageReference storageRef;

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
        String parking = getArguments().getString("parking");
        int aparcar = getArguments().getInt("aparcar");

        TextView precio = parkingView.findViewById(R.id.euro);
        ProgressBar progressBar = parkingView.findViewById(R.id.progressBar);
        ImageView img = parkingView.findViewById(R.id.parkingfoto);

        //Imagen
        storageRef = FirebaseStorage.getInstance().getReference();
        obtenerImagenFirebase("mapas/parking/" + parking + ".png", img);

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

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }

}