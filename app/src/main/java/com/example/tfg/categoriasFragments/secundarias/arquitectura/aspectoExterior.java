package com.example.tfg.categoriasFragments.secundarias.arquitectura;

import android.os.Bundle;
import com.example.tfg.R;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import android.widget.ImageButton;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import android.annotation.SuppressLint;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.example.tfg.categoriasFragments.principal.arquitecturaInicio;

public class aspectoExterior extends Fragment implements View.OnClickListener{

    private Bundle args;
    private String idioma, categoria;
    private ImageView img1, img2, img3;
    private StorageReference storageRef;
    private TextView text1, text2, text3;
    private ImageButton siguienteBtn, siguienteBtn2, finBtn, finBtn2;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static aspectoExterior newInstance(Bundle args) {
        aspectoExterior fragment = new aspectoExterior();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public aspectoExterior() {}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(false);
        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        myToolbar.setNavigationOnClickListener(v -> {
            myToolbar.setNavigationIcon(null);
            Fragment fragment = arquitecturaInicio.newInstance(args);
            cargarFragment(fragment);
        });

        args = new Bundle();

        if (getArguments() != null) {
            idioma = getArguments().getString("idioma");
            categoria = getArguments().getString("categoria");
        }

        args.putString("idioma", idioma);
        args.putString("categoria", categoria);

    }

    /** El Fragment va a cargar su layout, el cual debemos especificar.
     Aquí se instanciarán los objetos que si son vistas */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_aspecto_exterior, container, false);
        if(v != null){
            text1 = v.findViewById(R.id.arqui21);
            text2 = v.findViewById(R.id.arqui22);
            text3 = v.findViewById(R.id.arqui23);
            img1 = v.findViewById(R.id.arqui21img);
            img2 = v.findViewById(R.id.arqui22img);
            img3 = v.findViewById(R.id.arqui23img);
            siguienteBtn = v.findViewById(R.id.arquisiguiente2);
            siguienteBtn2 = v.findViewById(R.id.arquisiguiente22);
            finBtn = v.findViewById(R.id.arquiAtras2);
            finBtn2 = v.findViewById(R.id.arquiAtras22);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        GestorDB dbHelper = new GestorDB(getContext());

        String [] datos = dbHelper.obtenerDatosArqui(idioma, "exterior", categoria, 3);

        text1.setText(datos[0]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text2.setText(datos[1]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text3.setText(datos[2]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        storageRef = FirebaseStorage.getInstance().getReference();

        obtenerImagenFirebase("arquitectura/exterior1.jpg", img1);
        obtenerImagenFirebase("arquitectura/exterior3.jpg", img2);
        obtenerImagenFirebase("arquitectura/exterior2.jpg", img3);

        //BOTON SIGUIENTE y ATRAS
        siguienteBtn.setOnClickListener(this);
        siguienteBtn2.setOnClickListener(this);
        finBtn.setOnClickListener(this);
        finBtn2.setOnClickListener(this);

    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        //Cuando se presione el botón, realiza una acción aquí

        ImageButton btn = (ImageButton) view;
        Fragment fragment = null;

        int id = btn.getId();
        if ((btn.getId() == R.id.arquisiguiente2) || (id == R.id.arquisiguiente22)) {
            fragment = aspectoInterior.newInstance(args);
        } else if ((id == R.id.arquiAtras2) || (id == R.id.arquiAtras22)) {
            fragment = arquitecturaInicio.newInstance(args);
        }

        assert fragment != null;
        cargarFragment(fragment);
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