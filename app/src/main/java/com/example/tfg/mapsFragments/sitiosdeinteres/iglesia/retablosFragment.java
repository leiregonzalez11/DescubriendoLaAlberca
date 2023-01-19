package com.example.tfg.mapsFragments.sitiosdeinteres.iglesia;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Typeface;
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

public class retablosFragment extends DialogFragment {

    private StorageReference storageRef;

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View infomView = inflater.inflate(R.layout.fragment_retablos, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(infomView);

        assert getArguments()!=null;
        String retablo = getArguments().getString("retablo");
        String idioma = getArguments().getString("idioma");

        setTitulo(retablo, infomView);

        TextView text1 = infomView.findViewById(R.id.retabloinfotext1);
        TextView text2 = infomView.findViewById(R.id.retabloinfotext2);

        GestorDB dbHelper = new GestorDB(getContext());

        String [] datos =dbHelper.obtenerInfoMonumentos(idioma, "iglesia", retablo, 2);

        text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        ImageView img1 = infomView.findViewById(R.id.retabloimg1);

        //Imagen
        storageRef = FirebaseStorage.getInstance().getReference();
        obtenerImagenFirebase("mapas/monumentos/" + retablo + ".png", img1);

        Button volver = infomView.findViewById(R.id.buttonVolverRetablos);
        volver.setOnClickListener(view -> dismiss());

        return builder.create();
    }

    private void setTitulo(String retablo, View view) {

        TextView titulo = view.findViewById(R.id.tituloretablo);

        if (retablo.contains("ana")){
            titulo.setText("Retablo de Santa Ana");
        } else if (retablo.contains("rosario")){
            titulo.setText("Retablo de la Virgen del Rosario");
        }
    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }

}