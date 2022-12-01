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
    TextView texto;
    Fragment fragment;
    Toolbar myToolbar;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static Idiomas newInstance(Bundle args) {
        Idiomas fragment = new Idiomas();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public Idiomas() {}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        iu = requireArguments().getString("iu");
        myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        myToolbar.setNavigationOnClickListener(v -> start(iu));
    }

    /** El Fragment va a cargar su layout, el cual debemos especificar.
     Aquí se instanciarán los objetos que si son vistas */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_idiomas, container, false);
        if(v != null){
            radioCas = v.findViewById(R.id.radio_es);
            radioEus = v.findViewById(R.id.radio_eu);
            radioIng = v.findViewById(R.id.radio_en);
            texto = v.findViewById(R.id.textAjustes);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

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

        myToolbar.setNavigationIcon(null);

        //Determinamos el fragment de retorno y creamos el Fragment

        switch (iu) {
            case "inicio":
                fragment = Inicio.newInstance();
                break;
            case "categorias":
                fragment = Categorias.newInstance();
                break;
            case "ajustes":
                fragment = Ajustes.newInstance();
                break;
        }

        assert !(fragment == null);
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