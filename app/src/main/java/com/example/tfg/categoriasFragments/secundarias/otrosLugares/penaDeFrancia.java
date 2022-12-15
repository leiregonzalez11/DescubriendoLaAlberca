package com.example.tfg.categoriasFragments.secundarias.otrosLugares;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.adapters.SpinnerAdapter;
import com.example.tfg.adapters.listViewAdapter;
import com.example.tfg.categoriasFragments.principal.otrosLugaresInicio;
import com.example.tfg.categoriasFragments.secundarias.gastronomia.recetasTipicas;
import com.example.tfg.categoriasFragments.secundarias.gastronomia.turroneras;
import com.example.tfg.categoriasFragments.secundarias.otrosLugares.penaFrancia.historiaPenaFrancia;
import com.example.tfg.categoriasFragments.secundarias.otrosLugares.penaFrancia.leyendaPenaFrancia;
import com.example.tfg.categoriasFragments.secundarias.otrosLugares.penaFrancia.monumentosPenaFrancia;
import com.example.tfg.navigationmenu.Categorias;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link penaDeFrancia#newInstance} factory method to
 * create an instance of this fragment.
 */
public class penaDeFrancia extends Fragment {

    private Bundle args;
    private double lat, lon;
    private Fragment fragment;
    private TextView text1, text2, text3, text4, text5;
    private ListView listView, listView2, listView3;
    private SupportMapFragment mapFragment;
    private String idioma;
    private String categoria;

    /** Este callback se activa cuando el mapa está listo para ser utilizado. */
    private final OnMapReadyCallback callback = new OnMapReadyCallback() {
        /**
         * Manipula el mapa una vez haya sido creado.
         * Aquí es donde podemos añadir marcadores o líneas, añadir listeners o mover la cámara.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            //Localización de la peña de francia
            LatLng location = new LatLng(lat, lon);
            //googleMap.addMarker(new MarkerOptions().position(location).title("Peña de Francia"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 16.5f));
            //Tipo de mapa: Hibrido
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }
    };

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static penaDeFrancia newInstance(Bundle args) {
        penaDeFrancia fragment = new penaDeFrancia();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public penaDeFrancia() {}

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
            Fragment fragment = otrosLugaresInicio.newInstance(args);
            cargarFragment(fragment);
        });
    }

    /** El Fragment va a cargar su layout, el cual debemos especificar.
     Aquí se instanciarán los objetos que si son vistas */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pena_de_francia, container, false);
        if (v != null){
            text1 = v.findViewById(R.id.pena11);
            text2 = v.findViewById(R.id.pena12);
            text3 = v.findViewById(R.id.pena13);
            text4 = v.findViewById(R.id.pena14);
            text5 = v.findViewById(R.id.pena15);
            listView = v.findViewById(R.id.listviewPena1);
            listView2 = v.findViewById(R.id.listviewPena2);
            listView3 = v.findViewById(R.id.listviewPena3);
            mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapViewPena);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        GestorDB dbHelper = new GestorDB(getContext());

        String[] datos = dbHelper.obtenerInfoPena(idioma, "inicio", categoria, "peñadefrancia", 5);

        text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text4.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text5.setText(datos[4] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        //Ubicacion
        String [] ubicacion = dbHelper.obtenerUbiOtros("peñadefrancia");
        lat = Double.parseDouble(ubicacion[0]);
        lon = Double.parseDouble(ubicacion[1]);

        //Mapa
        if (mapFragment != null) {
          mapFragment.getMapAsync(callback);
        }

        /*----------
         | Historia |
         ----------*/

        String opc1 = getString(R.string.historiamayus);

        ArrayList<String> lista1 = new ArrayList<>();
        lista1.add(opc1);

        listViewAdapter myAdapter = new listViewAdapter(getContext(), R.layout.list_monte2, lista1);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener((adapterView, v, position, id) -> {
            fragment = historiaPenaFrancia.newInstance(args);
            cargarFragment(fragment);
        });

        /*---------------
         | ¿Qué visitar? |
         ---------------*/

        String opc2 = "¿Qué visitar?";

        ArrayList<String> lista2 = new ArrayList<>();
        lista2.add(opc2);

        listViewAdapter myAdapter2 = new listViewAdapter(getContext(), R.layout.list_monte2, lista2);
        listView2.setAdapter(myAdapter2);

        listView2.setOnItemClickListener((adapterView, v, position, id) -> {
            fragment = monumentosPenaFrancia.newInstance(args);
            cargarFragment(fragment);
        });

        /*---------
         | Leyenda |
         ---------*/

        String opc3 = "El Descubrimiento de la Virgen";

        ArrayList<String> lista3 = new ArrayList<>();
        lista3.add(opc3);

        listViewAdapter myAdapter3 = new listViewAdapter(getContext(), R.layout.list_monte2, lista3);
        listView3.setAdapter(myAdapter3);

        listView3.setOnItemClickListener((adapterView, v, position, id) -> {
            fragment = leyendaPenaFrancia.newInstance(args);
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