package com.example.tfg.ajustesFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tfg.R;
import com.example.tfg.navigationmenu.Ajustes;
import com.example.tfg.navigationmenu.Categorias;
import com.example.tfg.navigationmenu.Inicio;

import java.util.Locale;


public class Idiomas extends Fragment {

    RadioButton radioCas, radioEus, radioIng;
    String language, idioma, iu;

    public Idiomas() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);

        iu = requireArguments().getString("iu");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_idiomas, container, false);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        myToolbar.setNavigationOnClickListener(v -> {

            myToolbar.setNavigationIcon(null);
            Fragment fragment = null;

            switch (iu) {
                case "inicio":
                    fragment = new Inicio();
                    break;
                case "categorias":
                    fragment = new Categorias();
                    break;
                case "ajustes":
                    fragment = new Ajustes();
                    break;
            }

            cargarFragment(fragment);
        });

        radioCas = requireView().findViewById(R.id.radio_es);
        radioEus = requireView().findViewById(R.id.radio_eu);
        radioIng = requireView().findViewById(R.id.radio_en);

        TextView texto = requireView().findViewById(R.id.textAjustes);

        if (texto.getText().toString().equals("Seleccione un idioma:")){
            radioCas.setChecked(true);
            radioCas.setTextColor(R.color.purple_500);
            idioma = "es";
        } else if (texto.getText().toString().contains("aukeratu:")){
            radioEus.setChecked(true);
            radioEus.setTextColor(R.color.purple_500);
            idioma = "eu";
        }else if (texto.getText().toString().contains("language:")){
            radioIng.setChecked(true);
            radioIng.setTextColor(R.color.purple_500);
            idioma = "en";
        }

        comprobarIdioma();

    }

    private void start(String iu) {

        Fragment fragment = null;
        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(null);

        //Determinamos el fragment de retorno y creamos el Fragment

        switch (iu) {
            case "inicio":
                fragment = new Inicio();
                break;
            case "categorias":
                fragment = new Categorias();
                break;
            case "ajustes":
                fragment = new Ajustes();
                break;
        }

        cargarFragment(fragment);
    }

    private void changeLanguage(){

        Locale nuevaloc = new Locale(language);
        Locale.setDefault(nuevaloc);
        Configuration configuration = requireActivity().getBaseContext().getResources().getConfiguration();
        configuration.setLocale(nuevaloc);
        configuration.setLayoutDirection(nuevaloc);

        Context context = requireActivity().getBaseContext().createConfigurationContext(configuration);
        requireActivity().getBaseContext().getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());

    }

    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    private void comprobarIdioma(){

        radioCas.setOnClickListener(view -> {
            language ="es";
            changeLanguage();
            start(iu);

        });

        radioEus.setOnClickListener(view -> {
            language="eu";
            changeLanguage();
            start(iu);
        });

        radioIng.setOnClickListener(view -> {
            language="en";
            changeLanguage();
            start(iu);
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