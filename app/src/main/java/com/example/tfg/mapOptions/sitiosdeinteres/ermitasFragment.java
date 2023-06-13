package com.example.tfg.mapOptions.sitiosdeinteres;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ermitasFragment extends DialogFragment {

    private StorageReference storageRef;

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View ermitaView = inflater.inflate(R.layout.fragment_ermitas, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(ermitaView);

        assert getArguments()!=null;
        String ermita = getArguments().getString("ermita");
        String idioma = getArguments().getString("idioma");
        setTitulo(ermita, ermitaView);

        TextView text1 = ermitaView.findViewById(R.id.ermita1text);
        TextView text2 = ermitaView.findViewById(R.id.ermita2text);
        TextView text3 = ermitaView.findViewById(R.id.ermita3text);

        String[] datos;
        try (GestorDB dbHelper = new GestorDB(getContext())) {

            datos = dbHelper.obtenerInfoMonumentos(idioma, ermita, 3);
        }

        text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        if (ermita.contains("ant")){
            text2.setTypeface(Typeface.create(text2.getTypeface(), Typeface.ITALIC));
        }else{
            text2.setTypeface(Typeface.create(text2.getTypeface(), Typeface.NORMAL));
        }

        ImageView img1 = ermitaView.findViewById(R.id.ermitasfoto1);
        ImageView img2 = ermitaView.findViewById(R.id.ermitasfoto2);

        //Imagen
        storageRef = FirebaseStorage.getInstance().getReference();
        obtenerImagenFirebase("mapas/monumentos/" + ermita + "1.png", img1);
        obtenerImagenFirebase("mapas/monumentos/" + ermita + "2.png", img2);

        Button volver = ermitaView.findViewById(R.id.buttonVolverErmita);
        volver.setOnClickListener(view -> dismiss());

        return builder.create();
    }

    private void setTitulo(String ermita, View ermitaView) {

        TextView titulo = ermitaView.findViewById(R.id.tituloermita);

        if (ermita.contains("humi")){
            titulo.setText(R.string.humilladero);
        } else if (ermita.contains("blas")){
            titulo.setText(R.string.sanblas);
        }else{
            titulo.setText(R.string.sanantonio);
        }
    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }
}