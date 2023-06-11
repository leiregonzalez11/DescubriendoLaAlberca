package com.example.tfg.EspacioDelViajero.Maps.sitiosdeinteres.iglesia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.DialogFragment;

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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class pulpito extends DialogFragment {

    private String idioma, mon;
    private ImageView img1, img2, img3;
    private StorageReference storageRef;
    private TextView text1, text2, text3, text4, text5, text6, text7;
    private final Bundle args = new Bundle();
    Animation slideAnimation;
    View pulpitoView;

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        pulpitoView = inflater.inflate(R.layout.fragment_pulpito, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(pulpitoView);

        assert getArguments()!=null;
        idioma = getArguments().getString("idioma");
        mon = getArguments().getString("monumento");

        args.putString("idioma", idioma);

        text1 = pulpitoView.findViewById(R.id.p1);
        text2 = pulpitoView.findViewById(R.id.p2);
        text3 = pulpitoView.findViewById(R.id.p3);
        text4 = pulpitoView.findViewById(R.id.p4);
        text5 = pulpitoView.findViewById(R.id.p5);
        text6 = pulpitoView.findViewById(R.id.p6);
        text7 = pulpitoView.findViewById(R.id.p7);
        img1 = pulpitoView.findViewById(R.id.pimg1);
        img2 = pulpitoView.findViewById(R.id.pimg2);
        img3 = pulpitoView.findViewById(R.id.pimg3);

        setInfo();

        Button volver = pulpitoView.findViewById(R.id.buttonVolverPulpito);
        volver.setOnClickListener(view -> zoomOut());

        return builder.create();
    }

    @SuppressLint("SetTextI18n")
    private void setInfo() {
        GestorDB dbHelper = new GestorDB(getContext());
        storageRef = FirebaseStorage.getInstance().getReference();

        String [] datos = dbHelper.obtenerInfoMonumentos(idioma, mon, 7);
        obtenerImagenFirebase("mapas/monumentos/pulpito1.png", img1);
        obtenerImagenFirebase("mapas/monumentos/pulpito2.png", img2);
        obtenerImagenFirebase("mapas/monumentos/pulpito3.png", img3);

        text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text4.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text5.setText(datos[4] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text6.setText(datos[5] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text7.setText(datos[6] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }

    public void zoomOut (){
        slideAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_out);
        pulpitoView.startAnimation(slideAnimation);

        new Handler().postDelayed(this::dismiss,900);
    }

}