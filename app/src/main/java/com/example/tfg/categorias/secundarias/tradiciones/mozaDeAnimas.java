package com.example.tfg.categorias.secundarias.tradiciones;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.VideoView;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import android.annotation.SuppressLint;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tfg.R;
import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.example.tfg.categorias.principal.tradicionesInicio;

public class mozaDeAnimas extends Fragment implements View.OnClickListener {

    private Bundle args;
    private String idioma;
    private StorageReference storageRef;
    private ImageView img1, img2;
    private VideoView videoView, videoView2;
    private ImageButton btnPlay1, btnPause1, btnStop1, btnPlay2, btnPause2, btnStop2, atrasBtn;
    private TextView titulo, text2, text3, text4, text5, text6, text7, text8;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static mozaDeAnimas newInstance(Bundle args) {
        mozaDeAnimas fragment = new mozaDeAnimas();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public mozaDeAnimas() {}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            idioma = getArguments().getString("idioma");
        }

        args = new Bundle();
        args.putString("idioma", idioma);

        //Toolbar
        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        TextView name = myToolbar.findViewById(R.id.name);
        name.setText(R.string.tradicionesmayus);
        name.setTextSize(20);
        myToolbar.setNavigationOnClickListener(v -> {
            myToolbar.setNavigationIcon(null);
            //Creamos el fragment
            Fragment fragment = tradicionesInicio.newInstance(args);
            cargarFragment(fragment);
        });

    }

    /** El Fragment va a cargar su layout, el cual debemos especificar.
     Aquí se instanciarán los objetos que si son vistas */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_moza_de_animas, container, false);
        if (v != null){
            //Textos de la interfaz
            titulo = v.findViewById(R.id.titulotrad);
            text2 = v.findViewById(R.id.trad12);
            text3 = v.findViewById(R.id.trad13);
            text4 = v.findViewById(R.id.trad14);
            text5 = v.findViewById(R.id.trad15);
            text6 = v.findViewById(R.id.trad16);
            text7 = v.findViewById(R.id.trad17);
            text8 = v.findViewById(R.id.trad18);
            //Imágenes de la interfaz
            img1 = v.findViewById(R.id.imgTrad1);
            img2 = v.findViewById(R.id.imgTrad2);
            //Videos de la interfaz
            videoView = v.findViewById(R.id.videoView1);
            videoView2 = v.findViewById(R.id.videoView2);
            //Botones
            btnPlay1 = v.findViewById(R.id.play1);
            btnPlay2 = v.findViewById(R.id.play2);
            btnStop1 = v.findViewById(R.id.stop1);
            btnStop2 = v.findViewById(R.id.stop2);
            btnPause1 = v.findViewById(R.id.pause1);
            btnPause2 = v.findViewById(R.id.pause2);
            atrasBtn = v.findViewById(R.id.mozaAtras);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        String[] textoTrad;
        try (GestorDB dbHelper = GestorDB.getInstance(getContext())) {

            //Setter de los textos de la interfaz
            String nombreTrad = "La Moza de Ánimas";
            titulo.setText(nombreTrad);
            titulo.requestFocus();
            String nombreTradBBDD = nombreTrad.toLowerCase().replaceAll(" ", "");

            //Obtención de datos desde la bbdd
            textoTrad = dbHelper.obtenerInfoTrad(idioma, nombreTradBBDD, 7);
        }

        text2.setText(textoTrad[0]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text3.setText(textoTrad[1]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text4.setText(textoTrad[2]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text5.setText(textoTrad[3]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text6.setText(textoTrad[4]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text7.setText(textoTrad[5]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text8.setText(textoTrad[6]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        //Setter de las imagenes de la interfaz
        storageRef = FirebaseStorage.getInstance().getReference();
        obtenerImagenFirebase("categorias/tradiciones/mozaanimas1.png", img1);
        obtenerImagenFirebase("categorias/tradiciones/mozaanimas2.png", img2);

        //Setter de los videos de la interfaz
        Uri uri = Uri.parse("android.resource://"+ requireActivity().getPackageName()+"/"+R.raw.mozadeanimasprimeraparte);
        Uri uri2 = Uri.parse("android.resource://"+ requireActivity().getPackageName()+"/"+R.raw.mozadeanimassegundaparte);
        videoView.setVideoURI(uri);
        videoView2.setVideoURI(uri2);

        btnPlay1.setOnClickListener(this);
        btnPlay2.setOnClickListener(this);
        btnPause1.setOnClickListener(this);
        btnPause2.setOnClickListener(this);
        btnStop1.setOnClickListener(this);
        btnStop2.setOnClickListener(this);


        //Botón atrás
        atrasBtn.setOnClickListener(v -> {
            //Creamos el fragment y añadimos los args
            Fragment fragment = tradicionesInicio.newInstance(args);
            cargarFragment(fragment);
        });

    }

    /** Método utilizado para obtener las imágenes de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
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


    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public void onClick(View view) {

        ImageButton btn = (ImageButton) view;
        switch (btn.getId()){
            case R.id.play1:
                videoView.start();
                break;
            case R.id.play2:
                videoView2.start();
                break;
            case R.id.pause1:
                videoView.pause();
                break;
            case R.id.pause2:
                videoView2.pause();
                break;
            case R.id.stop1:
                videoView.pause();
                videoView.seekTo(0);
            case R.id.stop2:
                videoView2.pause();
                videoView2.seekTo(0);
                break;
        }
    }
}