package com.example.tfg.categorias.secundarias.tradiciones;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.categorias.principal.tradicionesInicio;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class laLoa extends Fragment {

    private Bundle args;
    private String idioma;
    private StorageReference storageRef;
    private ImageView img1, img2;
    private ImageButton atrasBtn;
    private TextView titulo, text1, text2, text3, text4, text5;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static laLoa newInstance(Bundle args) {
        laLoa fragment = new laLoa();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public laLoa() {}

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
        View v = inflater.inflate(R.layout.fragment_la_loa, container, false);
        if (v != null){
            //Textos de la interfaz
            titulo = v.findViewById(R.id.tituloloa);
            text1 = v.findViewById(R.id.loa1);
            text2 = v.findViewById(R.id.loa2);
            text3 = v.findViewById(R.id.loa3);
            text4 = v.findViewById(R.id.loa4);
            text5 = v.findViewById(R.id.loa5);
            //Imágenes de la interfaz
            img1 = v.findViewById(R.id.imgloa1);
            img2 = v.findViewById(R.id.imgloa2);
            //Botones
            atrasBtn = v.findViewById(R.id.loaAtras);
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
            String nombreTrad = "La Loa";
            titulo.setText(getResources().getString(R.string.la_loa));
            titulo.requestFocus();
            String nombreTradBBDD = nombreTrad.toLowerCase().replaceAll(" ", "");

            //Obtención de datos desde la bbdd
            textoTrad = dbHelper.obtenerInfoTrad(idioma, nombreTradBBDD, 5);
        }

        text1.setText(textoTrad[0]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text2.setText(textoTrad[1]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text3.setText(textoTrad[2]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text4.setText(textoTrad[3]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text5.setText(textoTrad[4]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        //Setter de las imagenes de la interfaz TODO
        storageRef = FirebaseStorage.getInstance().getReference();
        obtenerImagenFirebase("categorias/tradiciones/loa1.png", img1);
        obtenerImagenFirebase("categorias/tradiciones/loa2.png", img2);

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

}