package com.example.tfg.categoriasFragments.secundarias.arquitectura;

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
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.tfg.R;
import com.example.tfg.categoriasFragments.principal.arquitecturaInicio;
import com.example.tfg.navigationmenu.Categorias;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class casaAlbercana extends Fragment {

    Bundle args;
    TextView tel, web;
    ImageButton finBtn;
    String idioma, categoria;
    SupportMapFragment mapFragment;

    /** Este callback se activa cuando el mapa está listo para ser utilizado. */
    private final OnMapReadyCallback callback = new OnMapReadyCallback() {
        /**
         * Manipula el mapa una vez haya sido creado.
         * Aquí es donde podemos añadir marcadores o líneas, añadir listeners o mover la cámara.
         */
        public void onMapReady(GoogleMap googleMap) {
            LatLng location = new LatLng(40.488984, -6.109707);
            googleMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title("Casa Museo Satur y Juanela"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 17.5f));
            //Tipo de mapa: Hibrido
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }
    };

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static casaAlbercana newInstance(Bundle args) {
        casaAlbercana fragment = new casaAlbercana();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public casaAlbercana() {}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        myToolbar.setNavigationOnClickListener(view12 -> {
            Fragment fragment = arquitecturaInicio.newInstance(args);
            cargarFragment(fragment);
        });

        args = new Bundle();

        if (getArguments() != null) {
            idioma = getArguments().getString("idioma");
            categoria = getArguments().getString("categoria");
        }

        args.putString("idioma", idioma);
        args.putString("categoria", categoria);
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v = inflater.inflate(R.layout.fragment_casa_albercana, container, false);
         if (v != null){
             mapFragment =(SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapViewCasa);
             tel = v.findViewById(R.id.telcasa2);
             web = v.findViewById(R.id.webcasa2);
             finBtn = v.findViewById(R.id.arquiAtras5);
         }
         return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //Mapa
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        SpannableString telsubrayado = new SpannableString("625 75 58 19");
        telsubrayado.setSpan(new UnderlineSpan(), 0, telsubrayado.length(), 0);

        tel.setText(telsubrayado);
        tel.setOnClickListener(v -> {
            Uri number = Uri.parse("tel:" + "625 75 58 19"); // Creamos una uri con el numero de telefono
            Intent dial = new Intent(Intent.ACTION_DIAL, number); // Creamos una llamada al Intent de llamadas
            startActivity(dial); // Ejecutamos el Intent
        });


        SpannableString websubrayado = new SpannableString("www.casamuseosaturjuanela.com/");
        websubrayado.setSpan(new UnderlineSpan(), 0, websubrayado.length(), 0);

        web.setText(websubrayado);
        web.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://www.casamuseosaturjuanela.com/"); // Creamos una uri con el numero de telefono
            Intent dial = new Intent(Intent.ACTION_VIEW, uri); // Creamos una llamada al Intent de llamadas
            startActivity(dial); // Ejecutamos el Intent
        });

        finBtn.setOnClickListener(v -> {
            Fragment fragment = inscripciones.newInstance(args);
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