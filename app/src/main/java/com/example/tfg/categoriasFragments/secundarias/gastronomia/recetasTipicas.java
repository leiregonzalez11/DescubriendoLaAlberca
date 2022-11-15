package com.example.tfg.categoriasFragments.secundarias.gastronomia;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

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
    StorageReference storageRef;
    String categoria, idioma, nombreRecetaBBDD, tipoRecetas;

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
        myToolbar.setNavigationOnClickListener(v -> {

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

        dbHelper = new GestorDB(requireContext());
        storageRef = FirebaseStorage.getInstance().getReference();

        Spinner spinner1 = requireView().findViewById(R.id.spinnerTipo);
        Spinner spinner2 = requireView().findViewById(R.id.spinnerRecetas);
        TextView tituloreceta = requireView().findViewById(R.id.tituloreceta);

        String [] tipo = getResources().getStringArray(R.array.tipo);
        spinner1.setAdapter(new SpinnerAdapter(requireContext(), R.layout.dropsownitemsimple, tipo));
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
               tipoRecetas = (String) adapterView.getItemAtPosition(position);
               if (tipoRecetas.equalsIgnoreCase("dulce") || tipoRecetas.equalsIgnoreCase("sweet") ||
                       tipoRecetas.equalsIgnoreCase("gozoa")){
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

                tituloreceta.setText((String) adapterView.getItemAtPosition(position));

                if (tipoRecetas.equalsIgnoreCase("dulce") || tipoRecetas.equalsIgnoreCase("sweet") ||
                        tipoRecetas.equalsIgnoreCase("gozoa")){
                    nombreRecetaBBDD = determinarRecetaDulce((String) adapterView.getItemAtPosition(position));
                } else{
                    nombreRecetaBBDD = determinarRecetaSalada((String) adapterView.getItemAtPosition(position));
                }

                //String[] datos = dbHelper.obtenerDatosRecetas(idioma, nombreRecetaBBDD, categoria);
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

    /** Método utilizado para conocer la receta dulce elegida por el usuario para obtener la información */
    private String determinarRecetaDulce(String idReceta) {

        //Recetas dulces
        if(idReceta.toLowerCase().contains("leche")){
            nombreRecetaBBDD = "lechefrita";
        } else if (idReceta.toLowerCase().contains("perrunillas")) {
            nombreRecetaBBDD = "perrunillas";
        }else if (idReceta.toLowerCase().contains("turulete")) {
            nombreRecetaBBDD = "turuletes";
        }else if (idReceta.toLowerCase().contains("bollo")) {
            nombreRecetaBBDD = "bollomaimon";
        }else if (idReceta.toLowerCase().contains("flor")) {
            nombreRecetaBBDD = "floreta";
        }
        return nombreRecetaBBDD;
    }

    /** Método utilizado para conocer la receta salada elegida por el usuario para obtener la información */
    private String determinarRecetaSalada(String idReceta) {

        //Recetas dulces
        if(idReceta.toLowerCase().contains("patata")){
            nombreRecetaBBDD = "patatasmeneas";
        } else if (idReceta.toLowerCase().contains("toston")) {
            nombreRecetaBBDD = "tostonasado";
        }else if (idReceta.toLowerCase().contains("cabrito")) {
            nombreRecetaBBDD = "cabritocuchifrito";
        }else if (idReceta.toLowerCase().contains("hornazo")) {
            nombreRecetaBBDD = "hornazo";
        }else if (idReceta.toLowerCase().contains("serrano")) {
            nombreRecetaBBDD = "limonserrano";
        }else if (idReceta.toLowerCase().contains("zorongollo")) {
            nombreRecetaBBDD = "zorongollo";
        }
        return nombreRecetaBBDD;
    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }

}