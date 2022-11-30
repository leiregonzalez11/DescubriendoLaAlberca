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

    Bundle args;
    String idioma, categoria;
    ImageView img1, img2, img3;
    StorageReference storageRef;
    TextView text1, text2, text3;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment BlankFragment.
     */
    public static aspectoExterior newInstance(Bundle args) {
        aspectoExterior fragment = new aspectoExterior();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    // Required empty public constructor
    public aspectoExterior() {}

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
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        myToolbar.setNavigationOnClickListener(v -> {
            myToolbar.setNavigationIcon(null);
            Fragment fragment = arquitecturaInicio.newInstance(args);
            cargarFragment(fragment);
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

        ImageButton siguienteBtn = requireView().findViewById(R.id.arquisiguiente2);
        ImageButton siguienteBtn2 = requireView().findViewById(R.id.arquisiguiente22);
        ImageButton finBtn = requireView().findViewById(R.id.arquiAtras2);
        ImageButton finBtn2 = requireView().findViewById(R.id.arquiAtras22);

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