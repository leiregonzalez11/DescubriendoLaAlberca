package com.example.tfg.EspacioDelViajero.Maps.otrosLugares;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tfg.R;
import com.example.tfg.OtherFiles.Adapters.SpinnerAdapter;
import com.example.tfg.EspacioDelViajero.Maps.otrosLugares.pueblos.ListaPueblos;
import com.example.tfg.EspacioDelViajero.Maps.otrosLugares.pueblos.Pueblo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class otrosPueblos extends Fragment {

    private Bundle args;
    private ImageView img;
    private Pueblo pueblo;
    private Spinner spinner;
    private StorageReference storageRef;
    private String puebloSelected, idioma;
    private SupportMapFragment mapFragment;
    private TextView km, fiestamayor, descr;

    /** Este callback se activa cuando el mapa está listo para ser utilizado. */
    private final OnMapReadyCallback callback = new OnMapReadyCallback() {
        /**
         * Manipula el mapa una vez haya sido creado.
         * Aquí es donde podemos añadir marcadores o líneas, añadir listeners o mover la cámara.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng location = new LatLng(pueblo.getLatitud(), pueblo.getLongitud());
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15.5f));
            //Tipo de mapa: Hibrido
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }
    };

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static otrosPueblos newInstance(Bundle args) {
        otrosPueblos fragment = new otrosPueblos();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public otrosPueblos() {}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        args = new Bundle();

        if (getArguments() != null) {
            idioma = getArguments().getString("idioma");
        }

        args.putString("idioma", idioma);

        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        TextView name = myToolbar.findViewById(R.id.name);
        name.setText(R.string.otrosmayus);
        name.setTextSize(20);
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
        View v = inflater.inflate(R.layout.fragment_otros_pueblos, container, false);
        if (v != null){
            spinner = v.findViewById(R.id.spinnerPueblos);
            km = v.findViewById(R.id.km2);
            fiestamayor = v.findViewById(R.id.fiesta2);
            descr = v.findViewById(R.id.pueblosDescr);
            mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapViewPueblo);
            img = v.findViewById(R.id.imagepueblo);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ListaPueblos listaPueblos = new ListaPueblos(requireContext(), idioma);
        spinner.setAdapter(new SpinnerAdapter(requireContext(), R.layout.dropdownitempueblos, listaPueblos.listaNombres()));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                puebloSelected = (String) adapterView.getItemAtPosition(position);

                pueblo = listaPueblos.buscarPueblo(puebloSelected);

                descr.setText(pueblo.getDescrPueblo() + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                km.setText(pueblo.getKmDesdeLA());
                fiestamayor.setText(pueblo.getFiestamayor());

                //Mapa
                if (mapFragment != null) {
                    mapFragment.getMapAsync(callback);
                }

                //Imagenes
                storageRef = FirebaseStorage.getInstance().getReference();
                obtenerImagenFirebase("mapas/otros/otrospueblos/" + puebloSelected.replaceAll(" ", "").toLowerCase() +".png", img);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
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

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }


}