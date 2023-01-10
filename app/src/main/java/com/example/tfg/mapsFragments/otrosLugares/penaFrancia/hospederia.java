package com.example.tfg.mapsFragments.otrosLugares.penaFrancia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.tfg.GestorDB;
import com.example.tfg.R;

public class hospederia extends Fragment {

    private RatingBar ratingBar;
    private TextView ubi, tel, text1;
    private Bundle args;
    private String categoria, alojamiento, telefono, idioma;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static hospederia newInstance(Bundle args) {
        hospederia fragment = new hospederia();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;

    }

    /** Required empty public constructor */
    public hospederia(){}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
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
        alojamiento = "Hospedería Peña de Francia";

        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        myToolbar.setNavigationOnClickListener(view1 -> {
            myToolbar.setNavigationIcon(null);
            Fragment fragment = monumentosPenaFrancia.newInstance(args);
            cargarFragment(fragment);
        });

    }

    /** El Fragment va a cargar su layout, el cual debemos especificar.
     Aquí se instanciarán los objetos que si son vistas */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_hospederia, container, false);
        if(v != null){
            text1 = v.findViewById(R.id.nombreHosp);
            tel = v.findViewById(R.id.telHosp2);
            ubi = v.findViewById(R.id.ubiHosp2);
            ratingBar = v.findViewById(R.id.ratingBarHosp);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Datos de la interfaz
        GestorDB dbHelper = new GestorDB(getContext());

        //Titulo
        text1.setText(alojamiento);

        //Datos informativos y ubicación
        double punt = dbHelper.obtenerPuntAloj("alojamiento", alojamiento);
        ratingBar.setRating((float) punt);

        String [] datos = dbHelper.obtenerDatosAloj("alojamiento", alojamiento);

        telefono = datos[0];

        SpannableString websubrayado = new SpannableString(datos[3]);
        websubrayado.setSpan(new UnderlineSpan(), 0, websubrayado.length(), 0);

        ubi.setText(websubrayado);
        ubi.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://" + datos[3]); // Creamos una uri con el numero de telefono
            Intent dial = new Intent(Intent.ACTION_VIEW, uri); // Creamos una llamada al Intent de llamadas
            startActivity(dial); // Ejecutamos el Intent
        });

        if (!telefono.equals("No Disponible")) {
            SpannableString telsubrayado = new SpannableString(telefono);
            telsubrayado.setSpan(new UnderlineSpan(), 0, telsubrayado.length(), 0);
            tel.setText(telsubrayado);
            tel.setOnClickListener(v -> {
                Uri number = Uri.parse("tel:" + telefono); // Creamos una uri con el número de telefono
                Intent dial = new Intent(Intent.ACTION_DIAL, number); // Creamos una llamada al Intent de llamadas
                startActivity(dial); // Ejecutamos el Intent
            });
        } else{
            tel.setText(telefono);
        }
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