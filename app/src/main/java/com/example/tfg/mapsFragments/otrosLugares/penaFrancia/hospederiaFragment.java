package com.example.tfg.mapsFragments.otrosLugares.penaFrancia;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
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
        String idioma = getArguments().getString("idioma");
        String categoria = getArguments().getString("categoria");
        String alojamiento = "Hospedería Peña de Francia";

        Button back = infoView.findViewById(R.id.buttonVolverHosp);
        TextView text1 = infoView.findViewById(R.id.nombreHosp);
        TextView tel = infoView.findViewById(R.id.telHosp2);
        TextView ubi = infoView.findViewById(R.id.ubiHosp2);
        RatingBar ratingBar = infoView.findViewById(R.id.ratingBarHosp);
        ImageView img = infoView.findViewById(R.id.imgHosp);

        //Datos de la interfaz
        GestorDB dbHelper = new GestorDB(getContext());

        //Titulo
        text1.setText(alojamiento);

        //Imagen
        storageRef = FirebaseStorage.getInstance().getReference();
        obtenerImagenFirebase("mapas/otros/penafrancia/hospederia.png", img);

        //Datos informativos y ubicación
        double punt = dbHelper.obtenerPuntAloj("alojamiento", alojamiento);
        ratingBar.setRating((float) punt);

        String [] datos = dbHelper.obtenerDatosAloj("alojamiento", alojamiento);

        String telefono = datos[0];

        SpannableString websubrayado = new SpannableString(datos[1]);
        websubrayado.setSpan(new UnderlineSpan(), 0, websubrayado.length(), 0);

        ubi.setText(websubrayado);
        ubi.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://" + datos[1]); // Creamos una uri con el numero de telefono
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
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }
}