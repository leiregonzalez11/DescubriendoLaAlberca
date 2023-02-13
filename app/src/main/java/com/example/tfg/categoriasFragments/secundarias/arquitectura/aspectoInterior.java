package com.example.tfg.categoriasFragments.secundarias.arquitectura;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.categoriasFragments.principal.arquitecturaInicio;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class aspectoInterior extends Fragment implements View.OnClickListener{

    Bundle args;
    String idioma;
    StorageReference storageRef;
    ImageView img1, img2, img3, img4, img5;
    TextView text1, text2, text3, text4, text5;
    ImageButton siguienteBtn, siguienteBtn2, atrasBtn, atrasBtn2;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static aspectoInterior newInstance(Bundle args) {
        aspectoInterior fragment = new aspectoInterior();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public aspectoInterior() {
        // Required empty public constructor
    }

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        TextView name = myToolbar.findViewById(R.id.name);
        name.setText(R.string.arquitecturamayus);
        name.setTextSize(30);
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

    /** El Fragment va a cargar su layout, el cual debemos especificar.
     Aquí se instanciarán los objetos que si son vistas */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_aspecto_interior, container, false);
        if (v != null){
            text1 = v.findViewById(R.id.arqui31);
            text2 = v.findViewById(R.id.arqui32);
            text3 = v.findViewById(R.id.arqui33);
            text4 = v.findViewById(R.id.arqui34);
            text5 = v.findViewById(R.id.arqui35);
            img1 = v.findViewById(R.id.arqui31img);
            img2 = v.findViewById(R.id.arqui32img);
            img3 = v.findViewById(R.id.arqui33img);
            img4 = v.findViewById(R.id.arqui34img);
            img5 = v.findViewById(R.id.arqui35img);
            atrasBtn = v.findViewById(R.id.arquiAtras3);
            atrasBtn2 = v.findViewById(R.id.arquiAtras33);
            siguienteBtn = v.findViewById(R.id.arquisiguiente3);
            siguienteBtn2 = v.findViewById(R.id.arquisiguiente33);
        }

        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        GestorDB dbHelper = new GestorDB(getContext());

        String [] datos = dbHelper.obtenerDatosArqui(idioma, "interior", 5);

        text1.setText(datos[0]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text2.setText(datos[1]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text3.setText(datos[2]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text4.setText(datos[3]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text5.setText(datos[4]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        storageRef = FirebaseStorage.getInstance().getReference();

        obtenerImagenFirebase("categorias/arquitectura/interior1.png", img1);
        obtenerImagenFirebase("categorias/arquitectura/interior5.png", img2);
        obtenerImagenFirebase("categorias/arquitectura/interior2.png", img3);
        obtenerImagenFirebase("categorias/arquitectura/interior4.png", img4);
        obtenerImagenFirebase("categorias/arquitectura/interior3.png", img5);

        //BOTON SIGUIENTE y ATRAS
        siguienteBtn.setOnClickListener(this);
        siguienteBtn2.setOnClickListener(this);
        atrasBtn.setOnClickListener(this);
        atrasBtn2.setOnClickListener(this);
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
        if ((id == R.id.arquisiguiente3) || (id == R.id.arquisiguiente33)) {
            fragment = inscripciones.newInstance(args);
        } else if ((id == R.id.arquiAtras3) || (id == R.id.arquiAtras33)){
            fragment = aspectoExterior.newInstance(args);
        }

        if (fragment != null) {
            cargarFragment(fragment);
        }
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