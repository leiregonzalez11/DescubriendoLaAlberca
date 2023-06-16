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

public class casaParqueymirador extends DialogFragment {

    private StorageReference storageRef;

    public casaParqueymirador() {
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View viewR = inflater.inflate(R.layout.fragment_casaparque, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(viewR);

        assert getArguments()!=null;
        String idioma = getArguments().getString("idioma");
        String button = getArguments().getString("button");

        TextView titulo = viewR.findViewById(R.id.titulocasa);
        TextView text1 = viewR.findViewById(R.id.casainfotext1);
        TextView text2 = viewR.findViewById(R.id.casainfotext2);
        ImageView img1 = viewR.findViewById(R.id.casaimg1);
        ImageView img2 = viewR.findViewById(R.id.casaimg2);

        storageRef = FirebaseStorage.getInstance().getReference();
        String[] datos;
        try (GestorDB dbHelper = GestorDB.getInstance(getContext())) {

            if (button.equalsIgnoreCase("casaparque")) {
                titulo.setText("Casa del Parque Natural de Las Batuecas-Sierra de Francia");
                datos = dbHelper.obtenerInfoLugares(idioma, "casadelparque", "batuecas", 2);
                obtenerImagenFirebase("mapas/otros/batuecas/casaparque1.png", img1);
                obtenerImagenFirebase("mapas/otros/batuecas/casaparque2.png", img2);
            } else {
                titulo.setText("Mirador de San José");
                datos = dbHelper.obtenerInfoLugares(idioma, "mirador", "batuecas", 5);
                obtenerImagenFirebase("mapas/otros/batuecas/mirador1.png", img1);
                obtenerImagenFirebase("mapas/otros/batuecas/mirador2.png", img2);
            }
        }

        text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        Button volver = viewR.findViewById(R.id.buttonVolverCasaParque);
        volver.setOnClickListener(view -> dismiss());

        return builder.create();
    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }



}