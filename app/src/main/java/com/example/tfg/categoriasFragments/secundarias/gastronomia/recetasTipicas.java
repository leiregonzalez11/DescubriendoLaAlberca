package com.example.tfg.categoriasFragments.secundarias.gastronomia;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.adapters.SpinnerAdapter;
import com.example.tfg.categoriasFragments.principal.gastronomiaInicio;
import com.example.tfg.navigationmenu.Categorias;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class recetasTipicas extends Fragment {

    Bundle args;
    GestorDB dbHelper;
    String [] recetas;
    ImageView img1, img2;
    StorageReference storageRef;
    TextView text1, text2, text3;
    String categoria, idioma, nombreReceta, tipoRecetas;

    public recetasTipicas() {
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
        return inflater.inflate(R.layout.fragment_recetas_tipicas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.arrow_back);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
            }
        });

        dbHelper = new GestorDB(requireContext());
        storageRef = FirebaseStorage.getInstance().getReference();
        tipoRecetas = "dulce";


        Spinner spinner1 = requireView().findViewById(R.id.spinnerTipo);
        Spinner spinner2 = requireView().findViewById(R.id.spinnerRecetas);
        TextView tituloreceta = requireView().findViewById(R.id.tituloreceta);

        String [] tipo = getResources().getStringArray(R.array.tipo);
        spinner1.setAdapter(new SpinnerAdapter(requireContext(), R.layout.dropsownitemsimple, tipo));
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
               tipoRecetas = (String) adapterView.getItemAtPosition(position);
               if (tipoRecetas.equalsIgnoreCase("dulce")){
                   recetas = getResources().getStringArray(R.array.recetasDulces);
               } else {
                   recetas = getResources().getStringArray(R.array.recetasSaladas);
               }

               spinner2.setAdapter(new SpinnerAdapter(requireContext(), R.layout.dropdownitemrecetas, recetas));

           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                //Toast.makeText(getContext(), "Has pulsado " + (String) adapterView.getItemAtPosition(position), Toast.LENGTH_LONG).show();
                nombreReceta = determinarReceta((String) adapterView.getItemAtPosition(position));
                tituloreceta.setText((String) adapterView.getItemAtPosition(position));
                //String[] datos = dbHelper.obtenerDatosRecetas(idioma, nombreReceta, categoria);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button atrasBtn = requireView().findViewById(R.id.recetasAtras);
        atrasBtn.setOnClickListener(v -> {
            Fragment fragment = new gastronomiaInicio();
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
        });


    }

    /** Método utilizado para conocer la ruta elegida por el usuario para obtener la información */
    private String determinarReceta(String idRuta) {

        if(idRuta.toLowerCase().contains("francia")){
            nombreReceta = "peñadefrancia";
        } else if (idRuta.toLowerCase().contains("raíces")) {
            nombreReceta = "raices";
        }

        return nombreReceta;
    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }

}