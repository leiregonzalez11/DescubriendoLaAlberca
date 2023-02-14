package com.example.tfg.ajustesFragments.alojamiento;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.tfg.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class alojamientoFragment extends DialogFragment {

    String categoria, alojamiento, telefono;
    private StorageReference storageRef;

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View infoView = inflater.inflate(R.layout.fragmentdialog_alojamiento, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(infoView);

        assert getArguments() != null;
        Alojamiento alojamiento = getArguments().getParcelable("aloj");


        Button back = infoView.findViewById(R.id.buttonVolverAloj);
        TextView nombre = infoView.findViewById(R.id.nombreAloj);
        TextView tel = infoView.findViewById(R.id.telaloj2);
        TextView ubi = infoView.findViewById(R.id.ubi2);
        RatingBar ratingBar = infoView.findViewById(R.id.ratingBarAloj);


        //Titulo
        nombre.setText(alojamiento.getNombreAloj());

        //Imagen
        ImageView img = infoView.findViewById(R.id.imgaloj);
        storageRef = FirebaseStorage.getInstance().getReference();
        obtenerImagenFirebase("ajustes/alojamientos/" + alojamiento.getNombreAloj().toLowerCase().replace(" ", "") + ".png", img);

        //Datos informativos y ubicación
        ratingBar.setRating((float) alojamiento.getPuntAloj());

        ubi.setText(alojamiento.getLocationAloj() + "  ");
        telefono = alojamiento.getTelAloj();

        if (!telefono.equals("No Disponible")) {
            SpannableString telsubrayado = new SpannableString(telefono);
            telsubrayado.setSpan(new UnderlineSpan(), 0, telsubrayado.length(), 0);
            tel.setText(telsubrayado  + "  ");
            tel.setOnClickListener(v -> {
                Uri number = Uri.parse("tel:" + telefono); // Creamos una uri con el número de telefono
                Intent dial = new Intent(Intent.ACTION_DIAL, number); // Creamos una llamada al Intent de llamadas
                startActivity(dial); // Ejecutamos el Intent
            });
        } else{
            tel.setText(telefono);
        }

        back.setOnClickListener(view -> dismiss());

        return builder.create();
    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }

}