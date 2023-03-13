package com.example.tfg.mapsFragments.otrosLugares.penaFrancia;

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
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.ajustesFragments.alojamiento.Alojamiento;
import com.example.tfg.ajustesFragments.alojamiento.ListaAlojamientos;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class hospederiaFragment extends DialogFragment {

    private StorageReference storageRef;

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View infoView = inflater.inflate(R.layout.fragmentdialog_hospederia, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(infoView);

        assert getArguments() != null;
        Alojamiento alojamiento = ListaAlojamientos.getMiListaAlojamientos().buscarAloj("Hospedería Peña de Francia");

        Button back = infoView.findViewById(R.id.buttonVolverHosp);
        TextView text1 = infoView.findViewById(R.id.nombreHosp);
        TextView tel = infoView.findViewById(R.id.telHosp2);
        TextView ubi = infoView.findViewById(R.id.ubiHosp2);
        RatingBar ratingBar = infoView.findViewById(R.id.ratingBarHosp);
        ImageView img = infoView.findViewById(R.id.imgHosp);

        //Titulo
        text1.setText(alojamiento.getNombreAloj());

        //Imagen
        storageRef = FirebaseStorage.getInstance().getReference();
        obtenerImagenFirebase(img);

        //Datos informativos y ubicación
        ratingBar.setRating((float) alojamiento.getPuntAloj());

        String telefono = alojamiento.getTelAloj();

        SpannableString websubrayado = new SpannableString(alojamiento.getLocationAloj());
        websubrayado.setSpan(new UnderlineSpan(), 0, websubrayado.length(), 0);

        ubi.setText(websubrayado);
        ubi.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://" + alojamiento.getLocationAloj()); // Creamos una uri con el numero de telefono
            Intent dial = new Intent(Intent.ACTION_VIEW, uri); // Creamos una llamada al Intent de llamadas
            startActivity(dial); // Ejecutamos el Intent
        });

        if (!telefono.equals("No Disponible")) {
            SpannableString telsubrayado = new SpannableString(telefono);
            telsubrayado.setSpan(new UnderlineSpan(), 0, telsubrayado.length(), 0);
            tel.setText(telsubrayado);
            tel.setOnClickListener(v -> {
                Uri number = Uri.parse("tel:" + tel); // Creamos una uri con el número de telefono
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
    private void obtenerImagenFirebase(ImageView img){
        StorageReference pathReference = storageRef.child("mapas/otros/penafrancia/hospederia.png");
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }
}