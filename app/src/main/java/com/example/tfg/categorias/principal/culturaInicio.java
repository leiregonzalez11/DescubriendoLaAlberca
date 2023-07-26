package com.example.tfg.categorias.principal;

import android.os.Bundle;
import com.example.tfg.R;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.annotation.SuppressLint;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.example.tfg.categorias.secundarias.cultura.deporte;
import com.example.tfg.categorias.secundarias.cultura.diccionario.diccionario;
import com.example.tfg.categorias.secundarias.cultura.medios;
import com.example.tfg.categorias.secundarias.cultura.personajesilustres;
import com.example.tfg.navigationMenu.Categorias;
import androidx.fragment.app.FragmentTransaction;


public class culturaInicio extends Fragment {

    Bundle args;
    private String idioma;
    Button btndic, btndeporte, btnmedios, btnpers;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static culturaInicio newInstance(Bundle args) {
        culturaInicio fragment = new culturaInicio();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public culturaInicio() {}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        TextView name = myToolbar.findViewById(R.id.name);
        name.setText(R.string.categorias);
        name.setTextSize(20);
        myToolbar.setNavigationOnClickListener(view12 -> {
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
        View v =  inflater.inflate(R.layout.fragment_cultura_inicio, container, false);
        if(v != null){
            btndic = v.findViewById(R.id.diccionario);
            btndeporte = v.findViewById(R.id.deporte);
            btnmedios = v.findViewById(R.id.medios);
            btnpers = v.findViewById(R.id.personajesilustres);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        btndic.setOnClickListener(v -> {
            Fragment fragment = diccionario.newInstance(args);
            cargarFragment(fragment);
        });

        btndeporte.setOnClickListener(v -> {
            Fragment fragment = deporte.newInstance(args);
            cargarFragment(fragment);
        });

        btnmedios.setOnClickListener(v -> {
            Fragment fragment = medios.newInstance(args);
            cargarFragment(fragment);
        });

        btnpers.setOnClickListener(v -> {
            Fragment fragment = personajesilustres.newInstance(args);
            cargarFragment(fragment);
        });

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