package com.example.tfg.Maps.sitiosdeinteres.plaza;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class teatroFragment extends DialogFragment {

    private String idioma;
    private ImageView img1, img2, img3;
    private ImageButton ant, sig;
    private StorageReference storageRef;
    private TextView text1, text2, text3, text4, pruebatexto;
    private final Bundle args = new Bundle();
    Animation slideAnimation;
    View teatroView;

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        teatroView = inflater.inflate(R.layout.fragment_teatro, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(teatroView);

        assert getArguments()!=null;
        idioma = getArguments().getString("idioma");
        args.putString("idioma", idioma);

        text1 = teatroView.findViewById(R.id.t1);
        text2 = teatroView.findViewById(R.id.t2);
        text3 = teatroView.findViewById(R.id.t3);
        text4 = teatroView.findViewById(R.id.t4);
        pruebatexto = teatroView.findViewById(R.id.pruebatextot);
        img1 = teatroView.findViewById(R.id.timg1);
        img2 = teatroView.findViewById(R.id.timg2);
        img3 = teatroView.findViewById(R.id.timg3);
        ant = teatroView.findViewById(R.id.tbtn);
        sig = teatroView.findViewById(R.id.tbtn2);

        setInfo();

        Button volver = teatroView.findViewById(R.id.buttonVolverTeatro);
        volver.setOnClickListener(view -> zoomOut());

        return builder.create();
    }

    @SuppressLint("SetTextI18n")
    private void setInfo() {
        GestorDB dbHelper = new GestorDB(getContext());
        storageRef = FirebaseStorage.getInstance().getReference();

        if (pruebatexto.getText().toString().equalsIgnoreCase("1")){

            ant.setVisibility(View.GONE);
            sig.setVisibility(View.VISIBLE);

            String [] datos = dbHelper.obtenerInfoMonumentos(idioma, "teatro-cat1",4);

            if (idioma.equalsIgnoreCase("en")){
                text1.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text2.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text4.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            } else {
                text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text4.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            }


            obtenerImagenFirebase("mapas/monumentos/teatro1.png", img1);
            obtenerImagenFirebase("mapas/monumentos/teatro2.png", img2);
            obtenerImagenFirebase("mapas/monumentos/teatro3.png", img3);
        }


        ant.setOnClickListener(view1 -> {
            pruebatexto.clearFocus();
            if (pruebatexto.getText().toString().equalsIgnoreCase("2")){

                ant.setVisibility(View.GONE);
                sig.setVisibility(View.VISIBLE);

                pruebatexto.setText("1");

                String [] datos = dbHelper.obtenerInfoMonumentos(idioma, "teatro-cat1",4);

                if (idioma.equalsIgnoreCase("en")){
                    text1.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text2.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text3.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text4.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                } else {
                    text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text4.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                }
                pruebatexto.requestFocus();

                obtenerImagenFirebase("mapas/monumentos/teatro1.png", img1);
                obtenerImagenFirebase("mapas/monumentos/teatro2.png", img2);
                obtenerImagenFirebase("mapas/monumentos/teatro3.png", img3);

            }
        });

        sig.setOnClickListener(view1 -> {
            pruebatexto.clearFocus();
            if (pruebatexto.getText().toString().equalsIgnoreCase("1")){

                ant.setVisibility(View.VISIBLE);
                sig.setVisibility(View.GONE);

                pruebatexto.setText("2");

                String [] datos = dbHelper.obtenerInfoMonumentos(idioma, "teatro-cat2",4);

                text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text4.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                pruebatexto.requestFocus();

                obtenerImagenFirebase("mapas/monumentos/teatro4.png", img1);
                obtenerImagenFirebase("mapas/monumentos/teatro5.png", img2);
                obtenerImagenFirebase("mapas/monumentos/teatro6.png", img3);

            }

        });

    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }

    public void zoomOut (){
        slideAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_out);
        teatroView.startAnimation(slideAnimation);

        new Handler().postDelayed(this::dismiss,900);
    }

   
}