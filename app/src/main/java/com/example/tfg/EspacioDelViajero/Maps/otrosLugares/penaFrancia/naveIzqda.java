package com.example.tfg.EspacioDelViajero.Maps.otrosLugares.penaFrancia;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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


public class naveIzqda extends DialogFragment {

    private StorageReference storageRef;

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View infoView = inflater.inflate(R.layout.fragment_nave_izqda, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(infoView);

        assert getArguments() != null;
        String idioma = getArguments().getString("idioma");

        Button back = infoView.findViewById(R.id.buttonVolverNave);
        TextView text1 = infoView.findViewById(R.id.naveizqtext1);
        TextView text2 = infoView.findViewById(R.id.naveizqtext2);
        TextView text3 = infoView.findViewById(R.id.naveizqtext3);
        TextView text4 = infoView.findViewById(R.id.naveizqtext4);
        TextView text5 = infoView.findViewById(R.id.naveizqtext5);
        ImageView img1 = infoView.findViewById(R.id.naveizqimg1);
        ImageView img2 = infoView.findViewById(R.id.naveizqimg2);
        ImageView img3 = infoView.findViewById(R.id.naveizqimg3);
        ImageView img4 = infoView.findViewById(R.id.naveizqimg4);

        GestorDB dbHelper = new GestorDB(getContext());

        storageRef = FirebaseStorage.getInstance().getReference();

        String [] datos2 = dbHelper.obtenerInfoLugares(idioma, "naveizquierda", "peñadefrancia", 5);
        if (idioma.equalsIgnoreCase("es")){
            img1.setImageResource(R.drawable.planoiglesiaizqdaes);
        } else if (idioma.equalsIgnoreCase("en")){
            img1.setImageResource(R.drawable.planoiglesiaizqdaen);
        } else{
            img1.setImageResource(R.drawable.planoiglesiaizqdaeu);
        }
        text1.setText(datos2[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        img1.requestFocus();
        text2.setText(datos2[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text3.setText(datos2[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text4.setText(datos2[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text5.setText(datos2[4] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        obtenerImagenFirebase("mapas/otros/penafrancia/naveizqda2.png", img2);
        obtenerImagenFirebase("mapas/otros/penafrancia/naveizqda3.png", img3);
        obtenerImagenFirebase("mapas/otros/penafrancia/naveizqda4.png", img4);

        back.setOnClickListener(view -> dismiss());

        return builder.create();
    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }
    
}