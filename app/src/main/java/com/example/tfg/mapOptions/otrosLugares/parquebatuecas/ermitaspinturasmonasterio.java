package com.example.tfg.mapOptions.otrosLugares.parquebatuecas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ermitaspinturasmonasterio extends DialogFragment {

    private StorageReference storageRef;

    public ermitaspinturasmonasterio() {
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View viewR = inflater.inflate(R.layout.fragment_ermitasbatuecas, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(viewR);

        assert getArguments()!=null;
        String idioma = getArguments().getString("idioma");
        String button = getArguments().getString("button");

        TextView titulo = viewR.findViewById(R.id.tituloerm);
        TextView text1 = viewR.findViewById(R.id.erminfotext1);
        TextView text2 = viewR.findViewById(R.id.erminfotext2);
        TextView text3 = viewR.findViewById(R.id.erminfotext3);
        TextView text4 = viewR.findViewById(R.id.erminfotext4);
        TextView text5 = viewR.findViewById(R.id.erminfotext5);
        ImageView img1 = viewR.findViewById(R.id.ermimg1);
        ImageView img2 = viewR.findViewById(R.id.ermimg2);
        ImageView img3 = viewR.findViewById(R.id.ermimg3);

        storageRef = FirebaseStorage.getInstance().getReference();
        String[] datos;
        try (GestorDB dbHelper = GestorDB.getInstance(getContext())) {

            if (button.equalsIgnoreCase("ermitas")) {
                titulo.setText("Las ermitas de Batuecas");
                datos = dbHelper.obtenerInfoLugares(idioma, "ermitas", "batuecas", 5);
                obtenerImagenFirebase("mapas/otros/batuecas/ermitas1.png", img1);
                obtenerImagenFirebase("mapas/otros/batuecas/ermitas2.png", img2);
                obtenerImagenFirebase("mapas/otros/batuecas/ermitas3.png", img3);
            } else if (button.equalsIgnoreCase("pinturas")) {
                titulo.setText("Las Pinturas Rupestres");
                datos = dbHelper.obtenerInfoLugares(idioma, "pinturas", "batuecas", 5);
                obtenerImagenFirebase("mapas/otros/batuecas/pinturas1.png", img1);
                obtenerImagenFirebase("mapas/otros/batuecas/pinturas2.png", img2);
                obtenerImagenFirebase("mapas/otros/batuecas/pinturas3.png", img3);
            } else {
                titulo.setText("Historia del Monasterio");
                datos = dbHelper.obtenerInfoLugares(idioma, "monasterio", "batuecas", 5);
                obtenerImagenFirebase("mapas/otros/batuecas/monasterio1.png", img1);
                obtenerImagenFirebase("mapas/otros/batuecas/monasterio2.png", img2);
                obtenerImagenFirebase("mapas/otros/batuecas/monasterio3.png", img3);
            }
        }

        text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text4.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text5.setText(datos[4] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        Button volver = viewR.findViewById(R.id.buttonVolverErmitas);
        volver.setOnClickListener(view -> dismiss());

        return builder.create();
    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }

   
}