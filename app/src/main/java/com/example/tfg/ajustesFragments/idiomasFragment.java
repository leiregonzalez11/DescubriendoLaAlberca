package com.example.tfg.ajustesFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tfg.R;
import com.example.tfg.navigationmenu.FragmentAjustes;
import com.example.tfg.navigationmenu.FragmentCategorias;
import com.example.tfg.navigationmenu.FragmentInicio;

import java.util.Locale;
import java.util.Objects;


public class idiomasFragment extends Fragment implements View.OnClickListener {

    RadioButton radioCas, radioEus, radioIng;
    String language, idioma, iu;

    public idiomasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(false);
        iu = requireArguments().getString("iu");
        System.out.println("IU IDIOMAS: " + iu);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_idiomas, container, false);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

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

        //Botón atras
        //ImageButton atrasBtn = requireView().findViewById(R.id.idiomasBtn);
        //atrasBtn.setOnClickListener(this);

        comprobarIdioma();

    }


    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        //Cuando se presione el botón, realiza una acción aquí

        /*ImageButton btn = (ImageButton) view;

        if (btn.getId() == R.id.idiomasBtn){
            //Definimos los argumentos

            //Creamos el Fragment
            Fragment fragment = new FragmentAjustes();

            // Obtenemos el administrador de fragmentos a través de la actividad
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

            // Definimos una transacción
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Remplazamos el contenido principal por el fragmento
            fragmentTransaction.replace(R.id.relativelayout, fragment);
            fragmentTransaction.addToBackStack(null);

            // Cambiamos el fragment en la interfaz
            fragmentTransaction.commit();
        }*/
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

    private void start(String iu) {

        Fragment fragment = null;

        //Determinamos el fragment de retorno y creamos el Fragment

        switch (iu) {
            case "inicio":
                fragment = new FragmentInicio();
                break;
            case "categorias":
                fragment = new FragmentCategorias();
                break;
            case "ajustes":
                fragment = new FragmentAjustes();
                break;
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

}