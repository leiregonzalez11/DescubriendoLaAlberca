package com.example.tfg.mapOptions.sitiosdeinteres;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.DialogFragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class monumentos4 extends DialogFragment {

    private StorageReference storageRef;
    private View viewR;

    public monumentos4() {
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        viewR = inflater.inflate(R.layout.fragment_monumentos4, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(viewR);

        assert getArguments()!=null;
        String monumento = getArguments().getString("monumento");
        String idioma = getArguments().getString("idioma");
        String title = getArguments().getString("titulo");

        TextView titulo = viewR.findViewById(R.id.titulomon4);
        titulo.setText(title);

        TextView text1 = viewR.findViewById(R.id.monu4infotext1);
        TextView text2 = viewR.findViewById(R.id.monu4infotext2);
        TextView text3 = viewR.findViewById(R.id.monu4infotext3);
        TextView text4 = viewR.findViewById(R.id.monu4infotext4);

        String[] datos;
        try (GestorDB dbHelper = GestorDB.getInstance(getContext())) {

            datos = dbHelper.obtenerInfoMonumentos(idioma, monumento, 4);
        }

        text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text4.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));


        ImageView img1 = viewR.findViewById(R.id.monu4img1);
        ImageView img2 = viewR.findViewById(R.id.monu4img2);
        ImageView img3 = viewR.findViewById(R.id.monu4img3);

        //Imagen
        storageRef = FirebaseStorage.getInstance().getReference();
        obtenerImagenFirebase("mapas/monumentos/" + monumento + "1.png", img1);
        obtenerImagenFirebase("mapas/monumentos/" + monumento + "2.png", img2);
        obtenerImagenFirebase("mapas/monumentos/" + monumento + "3.png", img3);

        Button volver = viewR.findViewById(R.id.buttonVolverM4);
        volver.setOnClickListener(view -> zoomOut());

        return builder.create();
    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }

    public void zoomOut (){
        Animation slideAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_out);
        viewR.startAnimation(slideAnimation);

        new Handler().postDelayed(this::dismiss,900);
    }


}