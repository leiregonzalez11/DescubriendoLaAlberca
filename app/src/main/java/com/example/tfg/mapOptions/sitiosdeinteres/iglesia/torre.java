package com.example.tfg.mapOptions.sitiosdeinteres.iglesia;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class torre extends DialogFragment {

    private String idioma, mon;
    private ImageView img1, img2, img3;
    private StorageReference storageRef;
    private TextView text1, text2, text3, text4, text5, text6, text7, titulo;
    private final Bundle args = new Bundle();
    VideoView videoView;
    private ImageButton btnPlay, btnPause, btnStop;
    Animation slideAnimation;
    View torreView;

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        torreView = inflater.inflate(R.layout.fragment_torre, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(torreView);

        assert getArguments()!=null;
        idioma = getArguments().getString("idioma");
        mon = getArguments().getString("monumento");

        args.putString("idioma", idioma);

        titulo = torreView.findViewById(R.id.tot1);
        text1 = torreView.findViewById(R.id.to1);
        text2 = torreView.findViewById(R.id.to2);
        text3 = torreView.findViewById(R.id.to3);
        text4 = torreView.findViewById(R.id.to4);
        text5 = torreView.findViewById(R.id.to5);
        text6 = torreView.findViewById(R.id.to6);
        text7 = torreView.findViewById(R.id.to7);
        img1 = torreView.findViewById(R.id.toimg1);
        img2 = torreView.findViewById(R.id.toimg2);
        img3 = torreView.findViewById(R.id.toimg3);
        videoView = torreView.findViewById(R.id.videoViewTorre);
        btnPlay = torreView.findViewById(R.id.playT);
        btnPause = torreView.findViewById(R.id.pauseT);
        btnStop = torreView.findViewById(R.id.stopT);

        setInfo();

        Button volver = torreView.findViewById(R.id.buttonVolverTorre);
        volver.setOnClickListener(view -> zoomOut());

        return builder.create();
    }

    @SuppressLint("SetTextI18n")
    private void setInfo() {
        storageRef = FirebaseStorage.getInstance().getReference();
        String[] datos;
        titulo.requestFocus();
        try (GestorDB dbHelper = GestorDB.getInstance(getContext())) {
            datos = dbHelper.obtenerInfoMonumentos(idioma, mon, 7);
        }

        obtenerImagenFirebase("mapas/monumentos/torre1.png", img1);
        obtenerImagenFirebase("mapas/monumentos/torre2.png", img2);
        obtenerImagenFirebase("mapas/monumentos/torre3.png", img3);

        text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text4.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text5.setText(datos[4] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text6.setText(datos[5] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text7.setText(datos[6] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        //Setter de los videos de la interfaz
        Uri uri = Uri.parse("android.resource://"+ requireActivity().getPackageName()+"/"+R.raw.matraca);
        videoView.setVideoURI(uri);

        btnPlay.setOnClickListener(v -> videoView.start());
        btnPause.setOnClickListener(v -> videoView.pause());
        btnStop.setOnClickListener(v -> {
            videoView.pause();
            videoView.seekTo(0);
        });
        
    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }

    public void zoomOut (){
        slideAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_out);
        torreView.startAnimation(slideAnimation);

        new Handler().postDelayed(this::dismiss,900);
    }
}