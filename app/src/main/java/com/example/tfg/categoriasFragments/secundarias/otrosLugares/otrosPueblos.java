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
import android.widget.Spinner;
import android.widget.TextView;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.adapters.SpinnerAdapter;
import com.example.tfg.categoriasFragments.principal.otrosLugaresInicio;
import com.example.tfg.navigationmenu.Categorias;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;


public class otrosPueblos extends Fragment {

    Bundle args;
    double lat, lon;
    String pueblo, idioma, categoria;

    private final OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng location = new LatLng(lat, lon);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15.5f));
            //Tipo de mapa: Hibrido
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }
    };

    public otrosPueblos() {
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
        return inflater.inflate(R.layout.fragment_otros_pueblos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        GestorDB dbHelper = new GestorDB(getContext());

        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.arrow_back);
        myToolbar.setNavigationOnClickListener(view1 -> {

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

        Spinner spinner = requireView().findViewById(R.id.spinnerPueblos);

        String [] pueblos = getResources().getStringArray(R.array.pueblosdelasierra);
        spinner.setAdapter(new SpinnerAdapter(requireContext(), R.layout.dropdownitempueblos, pueblos));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                //Toast.makeText(getContext(), "Has pulsado: "+ pueblo, Toast.LENGTH_LONG).show();
                pueblo = (String) adapterView.getItemAtPosition(position);

                String puebloBBDD = pueblo.replaceAll(" ", "").toLowerCase();

                String [] datos = dbHelper.obtenerInfoPueblos(idioma, puebloBBDD, categoria, "pueblo");

                TextView km = requireView().findViewById(R.id.km2);
                TextView fiestamayor = requireView().findViewById(R.id.fiesta2);
                TextView descr = requireView().findViewById(R.id.pueblosDescr);

                km.setText(datos[1]);
                fiestamayor.setText(datos[2]);
                descr.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                lat = Double.parseDouble(datos[3]);
                lon = Double.parseDouble(datos[4]);

                //Mapa
                SupportMapFragment mapFragment =
                        (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapViewPueblo);
                if (mapFragment != null) {
                    mapFragment.getMapAsync(callback);
                }

                //TODO: AÑADIR FOTOS

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Button atrasBtn = requireView().findViewById(R.id.pueblosAtras);
        atrasBtn.setOnClickListener(v -> {
            Fragment fragment = new otrosLugaresInicio();
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


}