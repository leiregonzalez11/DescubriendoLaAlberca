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
import com.example.tfg.navigationmenu.Categorias;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class inscripciones extends Fragment implements View.OnClickListener {

    Bundle args;
    String idioma, categoria;
    StorageReference storageRef;
    ImageView img1, img2;

    public inscripciones() {
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
        return inflater.inflate(R.layout.fragment_inscripciones, container, false);
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

        String [] datos = dbHelper.obtenerDescrInterfaz(idioma, "inscripciones", categoria, 3);

        TextView text1 = requireView().findViewById(R.id.arqui41);
        TextView text2 = requireView().findViewById(R.id.arqui42);
        TextView text3 = requireView().findViewById(R.id.arqui43);

        text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        storageRef = FirebaseStorage.getInstance().getReference();

        img1 = requireView().findViewById(R.id.arqui41img);
        img2 = requireView().findViewById(R.id.arqui42img);

        obtenerImagenFirebase("arquitectura/inscripciones2.jpg", img1);
        obtenerImagenFirebase("arquitectura/inscripciones3.jpg", img2);


        //BOTON SIGUIENTE y ATRAS
        Button atrasBtnCat = requireView().findViewById(R.id.arquiAtras4);
        atrasBtnCat.setOnClickListener(this);


        Button finBtn = requireView().findViewById(R.id.arquisiguiente4);
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

            case R.id.arquisiguiente4:
                fragment = new casaAlbercana();
                break;

            case R.id.arquiAtras4:
                fragment = new aspectoInterior();
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