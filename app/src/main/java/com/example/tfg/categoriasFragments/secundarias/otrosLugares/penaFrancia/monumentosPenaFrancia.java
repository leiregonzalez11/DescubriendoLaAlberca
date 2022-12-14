package com.example.tfg.categoriasFragments.secundarias.otrosLugares.penaFrancia;

import android.annotation.SuppressLint;
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
import android.widget.ImageButton;

import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.categoriasFragments.principal.arquitecturaInicio;
import com.example.tfg.categoriasFragments.principal.artesaniaInicio;
import com.example.tfg.categoriasFragments.principal.culturaInicio;
import com.example.tfg.categoriasFragments.principal.fiestasInicio;
import com.example.tfg.categoriasFragments.principal.gastronomiaInicio;
import com.example.tfg.categoriasFragments.principal.historiaInicio;
import com.example.tfg.categoriasFragments.principal.monumentosInicio;
import com.example.tfg.categoriasFragments.principal.otrosLugaresInicio;
import com.example.tfg.categoriasFragments.principal.rutasInicio;
import com.example.tfg.categoriasFragments.principal.tradicionesInicio;
import com.example.tfg.categoriasFragments.secundarias.otrosLugares.penaDeFrancia;

public class monumentosPenaFrancia extends Fragment implements View.OnClickListener {

    private Bundle args;
    private String idioma;
    private String categoria;
    private Fragment fragment;
    private Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static monumentosPenaFrancia newInstance(Bundle args) {
        monumentosPenaFrancia fragment = new monumentosPenaFrancia();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public monumentosPenaFrancia() {}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
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

        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        myToolbar.setNavigationOnClickListener(view1 -> {
            myToolbar.setNavigationIcon(null);
            Fragment fragment = penaDeFrancia.newInstance(args);
            cargarFragment(fragment);
        });
    }

    /** El Fragment va a cargar su layout, el cual debemos especificar.
     Aquí se instanciarán los objetos que si son vistas */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_monumentos_penafrancia, container, false);
        if (v != null){
            btn1 = v.findViewById(R.id.santuario);
            btn2 = v.findViewById(R.id.sanandresexterior);
            btn3 = v.findViewById(R.id.santocristoexterior);
            btn4 = v.findViewById(R.id.santodomingo);
            btn5 = v.findViewById(R.id.rollo);
            btn6 = v.findViewById(R.id.hospederia);
            btn7 = v.findViewById(R.id.lablanca);
            btn8 = v.findViewById(R.id.balcon);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);

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

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        Button btn = (Button) view;

        switch (btn.getId()){

            case R.id.santuario:
                fragment = santuario.newInstance(args);
                break;

        }

        cargarFragment(fragment);
    }
}