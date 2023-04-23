package com.example.tfg.Maps.sitiosdeinteres;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class monumentos1 extends DialogFragment {

    private StorageReference storageRef;
    ImageView img1;
    private View viewR;
    Animation slideAnimation;

    public monumentos1() {
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        viewR = inflater.inflate(R.layout.fragment_monumentos1, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(viewR);

        assert getArguments()!=null;
        String monumento = getArguments().getString("monumento");
        String idioma = getArguments().getString("idioma");
        String title = getArguments().getString("titulo");

        TextView titulo = viewR.findViewById(R.id.titulomon);
        titulo.setText(title);

        TextView text1 = viewR.findViewById(R.id.monuinfotext1);

        GestorDB dbHelper = new GestorDB(getContext());

        String [] datos =dbHelper.obtenerInfoMonumentos(idioma, monumento, 1);

        text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        img1 = viewR.findViewById(R.id.monuimg1);

        //Imagen
        storageRef = FirebaseStorage.getInstance().getReference();
        obtenerImagenFirebase("mapas/monumentos/" + monumento + ".png", img1);

        Button volver = viewR.findViewById(R.id.buttonVolverM);
        volver.setOnClickListener(view -> zoomOut());

        return builder.create();
    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }

    public void zoomOut (){
        slideAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_out);
        ScrollView scr = viewR.findViewById(R.id.scrollviewM);
        scr.startAnimation(slideAnimation);
        img1.startAnimation(slideAnimation);

        new Handler().postDelayed(this::dismiss,900);
    }
}