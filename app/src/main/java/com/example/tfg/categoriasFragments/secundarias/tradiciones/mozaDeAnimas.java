package com.example.tfg.categoriasFragments.secundarias.tradiciones;

import android.net.Uri;
import com.example.tfg.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.VideoView;
import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import android.annotation.SuppressLint;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tfg.categoriasFragments.secundarias.gastronomia.recetasTipicas;
import com.example.tfg.navigationmenu.Categorias;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.example.tfg.categoriasFragments.principal.tradicionesInicio;

public class mozaDeAnimas extends Fragment {

    private Bundle args;
    private String idioma, categoria;
    private StorageReference storageRef;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment BlankFragment.
     */
    public static mozaDeAnimas newInstance(Bundle args) {
        mozaDeAnimas fragment = new mozaDeAnimas();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    public mozaDeAnimas() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);

        if (getArguments() != null) {
            idioma = getArguments().getString("idioma");
            categoria = getArguments().getString("categoria");
        }

        args = new Bundle();
        args.putString("idioma", idioma);
        args.putString("categoria", categoria);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_moza_de_animas, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //Toolbar
        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        myToolbar.setNavigationOnClickListener(v -> {
            myToolbar.setNavigationIcon(null);
            Fragment fragment = Categorias.newInstance();
            cargarFragment(fragment);
        });

        //Textos de la interfaz
        TextView titulo = requireView().findViewById(R.id.titulotrad);
        TextView text2 = requireView().findViewById(R.id.trad12);
        TextView text3 = requireView().findViewById(R.id.trad13);
        TextView text4 = requireView().findViewById(R.id.trad14);
        TextView text5 = requireView().findViewById(R.id.trad15);
        TextView text6 = requireView().findViewById(R.id.trad16);
        TextView text7 = requireView().findViewById(R.id.trad17);
        TextView text8 = requireView().findViewById(R.id.trad18);

        //Imágenes de la interfaz
        ImageView img1 = requireView().findViewById(R.id.imgTrad1);
        ImageView img2 = requireView().findViewById(R.id.imgTrad2);

        //Videos de la interfaz
        VideoView videoView = requireView().findViewById(R.id.videoView1);
        VideoView videoView2 = requireView().findViewById(R.id.videoView2);
        Button btnPlay1 = requireView().findViewById(R.id.play1);
        Button btnPlay2 = requireView().findViewById(R.id.play2);

        GestorDB dbHelper = new GestorDB(getContext());

        //Setter de los textos de la interfaz
        String nombreTrad = "La Moza de Ánimas";
        titulo.setText(nombreTrad);
        titulo.requestFocus();
        String nombreTradBBDD = nombreTrad.toLowerCase().replaceAll(" ", "");

        //Obtención de datos desde la bbdd
        String[] textoTrad = dbHelper.obtenerInfoTrad(idioma, nombreTradBBDD, categoria, 7);

        text2.setText(textoTrad[0]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text3.setText(textoTrad[1]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text4.setText(textoTrad[2]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text5.setText(textoTrad[3]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text6.setText(textoTrad[4]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text7.setText(textoTrad[5]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text8.setText(textoTrad[6]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        //Setter de las imagenes de la interfaz TODO
        storageRef = FirebaseStorage.getInstance().getReference();

        //Setter de los videos de la interfaz
        Uri uri = Uri.parse("android.resource://"+ requireActivity().getPackageName()+"/"+R.raw.mozadeanimasprimeraparte);
        Uri uri2 = Uri.parse("android.resource://"+ requireActivity().getPackageName()+"/"+R.raw.mozadeanimassegundaparte);
        videoView.setVideoURI(uri);
        videoView2.setVideoURI(uri2);

        btnPlay1.setOnClickListener(v -> {
            videoView.start();
            videoView2.pause();
            videoView2.seekTo(0);

        });
        btnPlay2.setOnClickListener(v -> {
            videoView.pause();
            videoView.seekTo(0);
            videoView2.start();
        });

        //Botón atrás
        Button atrasBtn = requireView().findViewById(R.id.mozaAtras);
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


}