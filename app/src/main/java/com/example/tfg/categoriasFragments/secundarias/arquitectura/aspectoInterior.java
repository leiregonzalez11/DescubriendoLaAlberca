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

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.categoriasFragments.principal.arquitecturaInicio;
import com.example.tfg.navigationmenu.Categorias;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class aspectoInterior extends Fragment implements View.OnClickListener{

    Bundle args;
    String idioma, categoria;
    StorageReference storageRef;
    ImageView img1, img2, img3, img4, img5;
    TextView text1, text2, text3, text4, text5;

    public aspectoInterior() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);

        args = new Bundle();

        if (getArguments() != null) {
            idioma = getArguments().getString("idioma");
            categoria = getArguments().getString("categoria");
        }

        args.putString("idioma", idioma);
        args.putString("categoria", categoria);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_aspecto_interior, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.arrow_back);
        myToolbar.setNavigationOnClickListener(view12 -> {

            myToolbar.setNavigationIcon(null);
            Fragment fragment = new Categorias();

            // Obtenemos el administrador de fragmentos a través de la actividad
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

            // Definimos una transacción
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Remplazamos el contenido principal por el fragmento
            fragmentTransaction.replace(R.id.relativelayout, fragment);
            fragmentTransaction.addToBackStack(null);

            // Cambiamos el fragment en la interfaz
            fragmentTransaction.commit();
        });

        GestorDB dbHelper = new GestorDB(getContext());

        String [] datos = dbHelper.obtenerDescrInterfaz(idioma, "interior", categoria, 5);

        text1 = requireView().findViewById(R.id.arqui31);
        text2 = requireView().findViewById(R.id.arqui32);
        text3 = requireView().findViewById(R.id.arqui33);
        text4 = requireView().findViewById(R.id.arqui34);
        text5 = requireView().findViewById(R.id.arqui35);

        text1.setText(datos[0]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text2.setText(datos[1]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text3.setText(datos[2]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text4.setText(datos[3]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text5.setText(datos[4]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        storageRef = FirebaseStorage.getInstance().getReference();

        img1 = requireView().findViewById(R.id.arqui31img);
        img2 = requireView().findViewById(R.id.arqui32img);
        img3 = requireView().findViewById(R.id.arqui33img);
        img4 = requireView().findViewById(R.id.arqui34img);
        img5 = requireView().findViewById(R.id.arqui35img);

        obtenerImagenFirebase("arquitectura/interior1.jpg", img1);
        obtenerImagenFirebase("arquitectura/interior5.jpg", img2);
        obtenerImagenFirebase("arquitectura/interior2.jpg", img3);
        obtenerImagenFirebase("arquitectura/interior4.jpg", img4);
        obtenerImagenFirebase("arquitectura/interior3.jpg", img5);

        //BOTON SIGUIENTE y ATRAS

        Button atrasBtn = requireView().findViewById(R.id.arquiAtras3);
        atrasBtn.setOnClickListener(this);

        Button siguienteBtn = requireView().findViewById(R.id.arquisiguiente3);
        siguienteBtn.setOnClickListener(this);


    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        //Cuando se presione el botón, realiza una acción aquí

        Button btn = (Button) view;
        Fragment fragment = null;

        switch (btn.getId()){

            case R.id.arquisiguiente3:
                fragment = new inscripciones();
                break;

            case R.id.arquiAtras3:
                fragment = new aspectoExterior();
                break;
        }

        if (fragment != null) {
            fragment.setArguments(args);
        }

        // Obtenemos el administrador de fragmentos a través de la actividad
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        // Definimos una transacción
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Remplazamos el contenido principal por el fragmento
        assert fragment != null;
        fragmentTransaction.replace(R.id.relativelayout, fragment);
        fragmentTransaction.addToBackStack(null);

        // Cambiamos el fragment en la interfaz
        fragmentTransaction.commit();
    }
}