package com.example.tfg.categorias.secundarias.tradiciones;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.categorias.principal.tradicionesInicio;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class bodaAlbercana extends Fragment {

    private Bundle args;
    private ImageButton ant, sig;
    private ImageView img1, img2, img3;
    private String idioma;
    private StorageReference storageRef;
    private TextView text1, text2, text3, text4, text5, pruebatexto, pruebatexto2, titulo;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static bodaAlbercana newInstance(Bundle args) {
        bodaAlbercana fragment = new bodaAlbercana();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public bodaAlbercana() {}

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_boda_albercana, container, false);
        if (v != null){
            pruebatexto = v.findViewById(R.id.pruebatextob);
            pruebatexto2 = v.findViewById(R.id.pruebatextob2);
            titulo = v.findViewById(R.id.bodatitulo);
            text1 = v.findViewById(R.id.boda1);
            text2 = v.findViewById(R.id.boda2);
            text3 = v.findViewById(R.id.boda3);
            text4 = v.findViewById(R.id.boda4);
            text5 = v.findViewById(R.id.boda5);
            ant = v.findViewById(R.id.bodabtn);
            sig = v.findViewById(R.id.bodabtn2);
            img1 = v.findViewById(R.id.bodaimg1);
            img2 = v.findViewById(R.id.bodaimg2);
            img3 = v.findViewById(R.id.bodaimg3);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        try (GestorDB dbHelper = GestorDB.getInstance(getContext())) {
            storageRef = FirebaseStorage.getInstance().getReference();

            if (pruebatexto.getText().toString().equalsIgnoreCase("1")) {
                ant.setVisibility(View.GONE);
                sig.setVisibility(View.VISIBLE);
                titulo.setVisibility(View.VISIBLE);

                String[] datos = dbHelper.obtenerInfoTrad(idioma, "labodaalbercana-cat1", 5);
                text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text4.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text5.setText(datos[4] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                obtenerImagenFirebase("categorias/tradiciones/bodaalbercana1.png", img1);
                obtenerImagenFirebase("categorias/tradiciones/bodaalbercana2.png", img2);
                obtenerImagenFirebase("categorias/tradiciones/bodaalbercana3.png", img3);
            }


            ant.setOnClickListener(view1 -> {
                pruebatexto.clearFocus();
                if (pruebatexto.getText().toString().equalsIgnoreCase("2")) {
                    ant.setVisibility(View.GONE);
                    sig.setVisibility(View.VISIBLE);
                    titulo.setVisibility(View.VISIBLE);
                    pruebatexto.setText("1");
                    pruebatexto2.setText("- 1 -");

                    String[] datos = dbHelper.obtenerInfoTrad(idioma, "labodaalbercana-cat1", 5);

                    text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text4.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text5.setText(datos[4] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    pruebatexto.requestFocus();

                    obtenerImagenFirebase("categorias/tradiciones/bodaalbercana1.png", img1);
                    obtenerImagenFirebase("categorias/tradiciones/bodaalbercana2.png", img2);
                    obtenerImagenFirebase("categorias/tradiciones/bodaalbercana3.png", img3);

                }
            });

            sig.setOnClickListener(view1 -> {
                pruebatexto.clearFocus();
                if (pruebatexto.getText().toString().equalsIgnoreCase("1")) {
                    ant.setVisibility(View.VISIBLE);
                    sig.setVisibility(View.GONE);
                    titulo.setVisibility(View.GONE);
                    pruebatexto.setText("2");
                    pruebatexto2.setText("- 2 -");

                    String[] datos = dbHelper.obtenerInfoTrad(idioma, "labodaalbercana-cat2", 5);

                    text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text4.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text5.setText(datos[4] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    
                    pruebatexto.requestFocus();

                    obtenerImagenFirebase("categorias/tradiciones/bodaalbercana5.png", img1);
                    obtenerImagenFirebase("categorias/tradiciones/bodaalbercana6.png", img2);
                    obtenerImagenFirebase("categorias/tradiciones/bodaalbercana7.png", img3);

                }
            });
        }

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