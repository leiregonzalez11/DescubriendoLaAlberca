package com.example.tfg.categorias.secundarias.arquitectura;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.view.LayoutInflater;
import android.text.SpannableString;
import android.annotation.SuppressLint;
import android.text.style.UnderlineSpan;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.tfg.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.example.tfg.categorias.principal.arquitecturaInicio;


public class casaAlbercana extends Fragment {

    private Bundle args;
    private TextView tel, web;
    private ImageView img;
    private ImageButton finBtn;
    private String idioma;
    private StorageReference storageRef;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static casaAlbercana newInstance(Bundle args) {
        casaAlbercana fragment = new casaAlbercana();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public casaAlbercana() {}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        TextView name = myToolbar.findViewById(R.id.name);
        name.setText(R.string.arquitecturamayus);
        name.setTextSize(20);
        myToolbar.setNavigationOnClickListener(view12 -> {
            Fragment fragment = arquitecturaInicio.newInstance(args);
            cargarFragment(fragment);
        });

        args = new Bundle();

        if (getArguments() != null) {
            idioma = getArguments().getString("idioma");
        }

        args.putString("idioma", idioma);

    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v = inflater.inflate(R.layout.fragment_casa_albercana, container, false);
         if (v != null){
             tel = v.findViewById(R.id.telcasa2);
             web = v.findViewById(R.id.webcasa2);
             img = v.findViewById(R.id.imgcasa);
             finBtn = v.findViewById(R.id.arquiAtras5);
         }
         return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        SpannableString telsubrayado = new SpannableString("625 75 58 19");
        telsubrayado.setSpan(new UnderlineSpan(), 0, telsubrayado.length(), 0);

        tel.setText(telsubrayado);
        tel.setOnClickListener(v -> {
            Uri number = Uri.parse("tel:" + "625 75 58 19"); // Creamos una uri con el numero de telefono
            Intent dial = new Intent(Intent.ACTION_DIAL, number); // Creamos una llamada al Intent de llamadas
            startActivity(dial); // Ejecutamos el Intent
        });


        SpannableString websubrayado = new SpannableString("www.casamuseosaturjuanela.com/");
        websubrayado.setSpan(new UnderlineSpan(), 0, websubrayado.length(), 0);

        web.setText(websubrayado);
        web.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://www.casamuseosaturjuanela.com/"); // Creamos una uri con el numero de telefono
            Intent dial = new Intent(Intent.ACTION_VIEW, uri); // Creamos una llamada al Intent de llamadas
            startActivity(dial); // Ejecutamos el Intent
        });

        storageRef = FirebaseStorage.getInstance().getReference();
        obtenerImagenFirebase(img);

        finBtn.setOnClickListener(v -> {
            Fragment fragment = inscripciones.newInstance(args);
            cargarFragment(fragment);
        });

    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(ImageView img){
        StorageReference pathReference = storageRef.child("categorias/arquitectura/casamuseo.png");
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }

    private void cargarFragment(Fragment fragment){
        // Obtenemos el administrador de fragmentos a través de la actividad
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        // Definimos una transacción
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // Remplazamos el contenido principal por el fragmento
        fragmentTransaction.replace(R.id.relativelayout, fragment);
        fragmentTransaction.addToBackStack(null);
        // Cambiamos el fragment en la interfaz
        fragmentTransaction.commit();
    }

}