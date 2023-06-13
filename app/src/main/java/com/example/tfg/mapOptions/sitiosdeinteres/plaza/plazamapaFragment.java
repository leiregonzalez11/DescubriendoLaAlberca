package com.example.tfg.mapOptions.sitiosdeinteres.plaza;

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
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.tfg.mapOptions.sitiosdeinteres.info.infomonu2Fragment;
import com.example.tfg.mapOptions.sitiosdeinteres.monumentos2;
import com.example.tfg.mapOptions.sitiosdeinteres.monumentos3;
import com.example.tfg.mapOptions.sitiosdeinteres.monumentos4;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class plazamapaFragment extends DialogFragment implements View.OnClickListener {

    Animation slideAnimation;
    View plazaMView;
    String monumento;
    private final Bundle args = new Bundle();
    private StorageReference storageRef;

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        plazaMView = inflater.inflate(R.layout.fragmentdialog_plazamapa, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(plazaMView);

        assert getArguments() != null;
        String idioma = getArguments().getString("idioma");
        args.putString("idioma", idioma);

        TextView text1 = plazaMView.findViewById(R.id.plaza1text);
        TextView text2 = plazaMView.findViewById(R.id.plaza2text);
        TextView text3 = plazaMView.findViewById(R.id.plaza3text);

        String[] datos;
        try (GestorDB dbHelper = new GestorDB(getContext())) {

            datos = dbHelper.obtenerInfoMonumentos(idioma, "plaza", 3);
        }

        text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        setListeners(plazaMView);

        ImageView img1 = plazaMView.findViewById(R.id.plazafoto1);
        ImageView img2 = plazaMView.findViewById(R.id.plazafoto2);

        //Imagen
        storageRef = FirebaseStorage.getInstance().getReference();
        obtenerImagenFirebase("mapas/monumentos/plaza1.png", img1);
        obtenerImagenFirebase("mapas/monumentos/plaza2.png", img2);

        ImageButton info = plazaMView.findViewById(R.id.moninfo2);
        info.setOnClickListener(view -> {
            DialogFragment fragment = new infomonu2Fragment();
            fragment.setCancelable(false);
            fragment.show(getChildFragmentManager(),"plaza_fragment");
        });

        Button volver = plazaMView.findViewById(R.id.buttonVolverPlaza);
        volver.setOnClickListener(view -> zoomOut());

        return builder.create();
    }

    private void setListeners(View plazaMView) {
        ImageButton ayunt = plazaMView.findViewById(R.id.buttonAyuntamiento);
        ImageButton teatro = plazaMView.findViewById(R.id.buttonTeatro);
        ImageButton biblio = plazaMView.findViewById(R.id.buttonBiblioteca);
        ImageButton crucero = plazaMView.findViewById(R.id.buttonCrucero);
        ImageButton unamuno = plazaMView.findViewById(R.id.buttonUnamuno);
        ImageButton escuelas = plazaMView.findViewById(R.id.buttonEscuelas);
        ayunt.setOnClickListener(this);
        teatro.setOnClickListener(this);
        biblio.setOnClickListener(this);
        crucero.setOnClickListener(this);
        unamuno.setOnClickListener(this);
        escuelas.setOnClickListener(this);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        ImageButton btn = (ImageButton) view;
        DialogFragment fragment;

        switch (btn.getId()) {
            case R.id.buttonEscuelas:
                monumento = "antiguasescuelas";
                args.putString("monumento", monumento);
                args.putString("titulo", "Antiguas escuelas");
                fragment = new monumentos2();
                zoomIn(fragment, btn);
                break;
            case R.id.buttonBiblioteca:
                monumento = "biblioteca";
                Toast.makeText(getContext(), "Has pulsado: " + monumento, Toast.LENGTH_LONG).show();
                break;
            case R.id.buttonUnamuno:
                monumento = "retratomigueldeunamuno";
                args.putString("monumento", monumento);
                args.putString("titulo", "Retrato Miguel de Unamuno");
                fragment = new monumentos4();
                zoomIn(fragment, btn);
                break;
            case R.id.buttonCrucero:
                monumento = "crucero";
                args.putString("monumento", monumento);
                args.putString("titulo", "Cruz de la plaza");
                fragment = new monumentos3();
                zoomIn(fragment, btn);
                break;
            case R.id.buttonAyuntamiento:
                monumento = "ayuntamiento";
                args.putString("monumento", monumento);
                args.putString("titulo", "Ayuntamiento");
                fragment = new monumentos2();
                zoomIn(fragment, btn);
                break;
            case R.id.buttonTeatro:
                monumento = "teatro";
                fragment = new teatroFragment();
                zoomIn(fragment, btn);
                break;
        }
    }

    public void zoomIn (DialogFragment fragment, View view){
        slideAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_in2);
        view.startAnimation(slideAnimation);

        new Handler().postDelayed(() -> {
            fragment.setArguments(args);
            fragment.setCancelable(false);
            fragment.show(getChildFragmentManager(),"fragment");
        },900);
    }

    public void zoomOut (){
        slideAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_out);
        plazaMView.startAnimation(slideAnimation);

        new Handler().postDelayed(this::dismiss,900);
    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }

}