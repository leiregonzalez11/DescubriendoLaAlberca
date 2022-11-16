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

public class aspectoExterior extends Fragment implements View.OnClickListener{

    Bundle args;
    String idioma, categoria;
    ImageView img1, img2, img3;
    StorageReference storageRef;
    TextView text1, text2, text3;

    public aspectoExterior() {
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
        return inflater.inflate(R.layout.fragment_aspecto_exterior, container, false);
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

        String [] datos = dbHelper.obtenerDatosArqui(idioma, "exterior", categoria, 3);

        text1 = requireView().findViewById(R.id.arqui21);
        text2 = requireView().findViewById(R.id.arqui22);
        text3 = requireView().findViewById(R.id.arqui23);

        text1.setText(datos[0]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text2.setText(datos[1]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text3.setText(datos[2]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        storageRef = FirebaseStorage.getInstance().getReference();

        img1 = requireView().findViewById(R.id.arqui21img);
        img2 = requireView().findViewById(R.id.arqui22img);
        img3 = requireView().findViewById(R.id.arqui23img);
        obtenerImagenFirebase("arquitectura/exterior1.jpg", img1);
        //TODO: Cambiar la foto
        obtenerImagenFirebase("arquitectura/exterior3.jpeg", img2);
        obtenerImagenFirebase("arquitectura/exterior2.jpg", img3);

        //BOTON SIGUIENTE y ATRAS

        Button siguienteBtn = requireView().findViewById(R.id.arquisiguiente2);
        siguienteBtn.setOnClickListener(this);

        Button finBtn = requireView().findViewById(R.id.arquiAtras2);
        finBtn.setOnClickListener(this);

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

            case R.id.arquisiguiente2:
                fragment = new aspectoInterior();
                break;

            case R.id.arquiAtras2:
                fragment = new arquitecturaInicio();
                break;
        }

        assert fragment != null;
        fragment.setArguments(args);

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