package com.example.tfg.mapsFragments.otrosLugares.penaFrancia;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class lacasabajaFragment extends DialogFragment {

    private StorageReference storageRef;

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View infoView = inflater.inflate(R.layout.fragment_lacasabaja, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(infoView);

        assert getArguments() != null;
        String idioma = getArguments().getString("idioma");
        String categoria = getArguments().getString("categoria");

        Button back = infoView.findViewById(R.id.buttonVolverCasa);
        TextView info = infoView.findViewById(R.id.casainfotext1);
        TextView info2 = infoView.findViewById(R.id.casainfotext2);
        TextView info3 = infoView.findViewById(R.id.casainfotext3);
        TextView info4 = infoView.findViewById(R.id.casainfotext4);
        TextView info5 = infoView.findViewById(R.id.casainfotext5);
        TextView info6 = infoView.findViewById(R.id.casainfotext6);
        TextView info7 = infoView.findViewById(R.id.casainfotext7);
        ImageView img1 = infoView.findViewById(R.id.casaimg1);
        ImageView img2 = infoView.findViewById(R.id.casaimg2);
        ImageView img3 = infoView.findViewById(R.id.casaimg3);
        ImageView img4 = infoView.findViewById(R.id.casaimg4);

        GestorDB dbHelper = new GestorDB(getContext());

        String [] datos = dbHelper.obtenerInfoPena(idioma, "lacasabaja", categoria, "peñadefrancia", 7);

        info.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        info2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        info3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        info4.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        info5.setText(datos[4] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        info6.setText(datos[5] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        info7.setText(datos[6] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        storageRef = FirebaseStorage.getInstance().getReference();

        obtenerImagenFirebase("mapas/otros/penafrancia/casabaja1.png", img1);
        obtenerImagenFirebase("mapas/otros/penafrancia/casabaja2.png", img2);
        obtenerImagenFirebase("mapas/otros/penafrancia/casabaja3.png", img3);
        obtenerImagenFirebase("mapas/otros/penafrancia/casabaja4.png", img4);

        back.setOnClickListener(view -> dismiss());

        return builder.create();
    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }
}