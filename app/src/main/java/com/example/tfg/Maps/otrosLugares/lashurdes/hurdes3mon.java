package com.example.tfg.Maps.otrosLugares.lashurdes;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class hurdes3mon extends DialogFragment {

    private StorageReference storageRef;

    public hurdes3mon() {
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View viewR = inflater.inflate(R.layout.fragment_hurdes3mon, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(viewR);

        assert getArguments()!=null;
        String sitio = getArguments().getString("sitio");
        String idioma = getArguments().getString("idioma");
        String title = getArguments().getString("titulo");

        TextView titulo = viewR.findViewById(R.id.titulomonhurdes3);
        titulo.setText(title);

        TextView textPrueba = viewR.findViewById(R.id.pruebatextohurdes);
        TextView text1 = viewR.findViewById(R.id.hurdes3infotext1);
        TextView text2 = viewR.findViewById(R.id.hurdes3infotext2);
        TextView text3 = viewR.findViewById(R.id.hurdes3infotext3);

        ImageButton ant = viewR.findViewById(R.id.hurdesbtn1);
        ImageButton sig = viewR.findViewById(R.id.hurdesbtn2);

        ImageView img1 = viewR.findViewById(R.id.hurdes3img1);
        ImageView img2 = viewR.findViewById(R.id.hurdes3img2);

        GestorDB dbHelper = new GestorDB(getContext());
        storageRef = FirebaseStorage.getInstance().getReference();

        if (!sitio.equalsIgnoreCase("lasmestas")){
            String [] datos = dbHelper.obtenerInfoLugares(idioma, sitio, "hurdes", 3);

            text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

            obtenerImagenFirebase("mapas/otros/hurdes/" + sitio + "1.png", img1);
            obtenerImagenFirebase("mapas/otros/hurdes/" + sitio + "2.png", img2);
        } else{

            if (textPrueba.getText().toString().equalsIgnoreCase("1")){
                ant.setVisibility(View.GONE);
                sig.setVisibility(View.VISIBLE);
                String [] datos = dbHelper.obtenerInfoLugares(idioma, "lasmestas-cat1", "hurdes", 3);
                text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                obtenerImagenFirebase("mapas/otros/hurdes/" + sitio + "1.png", img1);
                obtenerImagenFirebase("mapas/otros/hurdes/" + sitio + "2.png", img2);
            }


            ant.setOnClickListener(view1 -> {
                textPrueba.clearFocus();
                if (textPrueba.getText().toString().equalsIgnoreCase("2")){
                    ant.setVisibility(View.GONE);
                    sig.setVisibility(View.VISIBLE);
                    textPrueba.setText("1");

                    String [] datos = dbHelper.obtenerInfoLugares(idioma, "lasmestas-cat1", "hurdes", 3);

                    text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    textPrueba.requestFocus();

                    obtenerImagenFirebase("mapas/otros/hurdes/" + sitio + "1.png", img1);
                    obtenerImagenFirebase("mapas/otros/hurdes/" + sitio + "2.png", img2);

                } else if (textPrueba.getText().toString().equalsIgnoreCase("3")){
                    textPrueba.clearFocus();
                    ant.setVisibility(View.VISIBLE);
                    sig.setVisibility(View.VISIBLE);
                    textPrueba.setText("2");

                    String [] datos = dbHelper.obtenerInfoLugares(idioma, "lasmestas-cat2", "hurdes", 3);

                    text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    textPrueba.requestFocus();

                    obtenerImagenFirebase("mapas/otros/hurdes/" + sitio + "3.png", img1);
                    obtenerImagenFirebase("mapas/otros/hurdes/" + sitio + "4.png", img2);

                }
            });

            sig.setOnClickListener(view1 -> {
                textPrueba.clearFocus();
                if (textPrueba.getText().toString().equalsIgnoreCase("1")){
                    ant.setVisibility(View.VISIBLE);
                    sig.setVisibility(View.VISIBLE);
                    textPrueba.setText("2");

                    String [] datos = dbHelper.obtenerInfoLugares(idioma, "lasmestas-cat2", "hurdes", 3);

                    text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    textPrueba.requestFocus();

                    obtenerImagenFirebase("mapas/otros/hurdes/" + sitio + "3.png", img1);
                    obtenerImagenFirebase("mapas/otros/hurdes/" + sitio + "4.png", img2);

                } else if (textPrueba.getText().toString().equalsIgnoreCase("2")){
                    textPrueba.clearFocus();
                    ant.setVisibility(View.VISIBLE);
                    sig.setVisibility(View.GONE);
                    textPrueba.setText("3");

                    String [] datos = dbHelper.obtenerInfoLugares(idioma, "lasmestas-cat3", "hurdes", 3);

                    text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    textPrueba.requestFocus();

                    obtenerImagenFirebase("mapas/otros/hurdes/" + sitio + "5.png", img1);
                    obtenerImagenFirebase("mapas/otros/hurdes/" + sitio + "6.png", img2);

                }
            });
        }
        

        Button volver = viewR.findViewById(R.id.buttonVolverMH3);
        volver.setOnClickListener(view -> dismiss());

        return builder.create();
    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        com.google.firebase.storage.StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }
}