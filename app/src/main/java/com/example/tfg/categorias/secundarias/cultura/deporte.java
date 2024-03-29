package com.example.tfg.categorias.secundarias.cultura;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tfg.R;
import com.example.tfg.navigationMenu.Categorias;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class deporte extends Fragment {

    private Bundle args;
    private String idioma;
    private Button btntresvalles, btntirolinas;
    private ImageView img1, img2, img3;
    private StorageReference storageRef;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static deporte newInstance(Bundle args) {
        deporte fragment = new deporte();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public deporte() {}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        TextView name = myToolbar.findViewById(R.id.name);
        name.setText(R.string.cultura);
        name.setTextSize(20);
        myToolbar.setNavigationOnClickListener(v -> {
            myToolbar.setNavigationIcon(null);
            Fragment fragment = Categorias.newInstance();
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
        View v = inflater.inflate(R.layout.fragment_deporte, container, false);
        if (v != null){
            btntresvalles = v.findViewById(R.id.tresvalles);
            btntirolinas = v.findViewById(R.id.tirolinas);
            img1 = v.findViewById(R.id.imgdeporte1);
            img2 = v.findViewById(R.id.imgdeporte2);
            img3 = v.findViewById(R.id.imgdeporte3);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        btntresvalles.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://www.tresvalles.es/"); // Creamos una uri con la web
            Intent dial = new Intent(Intent.ACTION_VIEW, uri); // Creamos una llamada al Intent de llamadas
            startActivity(dial); // Ejecutamos el Intent
        });

        btntirolinas.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://www.gredostormes.com/parque-la-alberca/"); // Creamos una uri con la web
            Intent dial = new Intent(Intent.ACTION_VIEW, uri); // Creamos una llamada al Intent de llamadas
            startActivity(dial); // Ejecutamos el Intent
        });

        storageRef = FirebaseStorage.getInstance().getReference();

        obtenerImagenFirebase("categorias/cultura/vueltaciclistalaalberca.png", img1);
        obtenerImagenFirebase("categorias/cultura/piraguaslaalberca.png", img2);
        obtenerImagenFirebase("categorias/cultura/bolosdeporte.png", img3);

    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
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