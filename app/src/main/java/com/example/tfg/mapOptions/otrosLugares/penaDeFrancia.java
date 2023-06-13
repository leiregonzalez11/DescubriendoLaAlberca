package com.example.tfg.mapOptions.otrosLugares;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.tfg.mapOptions.otrosLugares.penaFrancia.monumentosPenaFrancia;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.mapOptions.otrosLugares.penaFrancia.historiaPenaFrancia;
import com.example.tfg.mapOptions.otrosLugares.penaFrancia.leyendaPenaFrancia;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class penaDeFrancia extends Fragment {

    private Bundle args;
    private double lat, lon;
    private Fragment fragment;
    private ImageView img1, img2, img3;
    private StorageReference storageRef;
    private SupportMapFragment mapFragment;
    private String idioma, back;
    private Button btn, btn2, btn3;
    private TextView text1, text2, text3, text4, text5, text6;

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

        args = new Bundle();

        if (getArguments() != null) {
            idioma = getArguments().getString("idioma");
            back = getArguments().getString("back");
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
        View v = inflater.inflate(R.layout.fragment_pena_de_francia, container, false);
        if (v != null){
            text1 = v.findViewById(R.id.pena11);
            text2 = v.findViewById(R.id.pena12);
            text3 = v.findViewById(R.id.pena13);
            text4 = v.findViewById(R.id.pena14);
            text5 = v.findViewById(R.id.pena15);
            text6 = v.findViewById(R.id.pena16);
            img1 = v.findViewById(R.id.penaimg1);
            img2 = v.findViewById(R.id.penaimg2);
            img3 = v.findViewById(R.id.penaimg3);
            btn = v.findViewById(R.id.btnPena1);
            btn2 = v.findViewById(R.id.btnPena2);
            btn3 = v.findViewById(R.id.btnPena3);
            mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapViewPena);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Double[] ubicacion;
        try (GestorDB dbHelper = new GestorDB(getContext())) {

            if (back.equalsIgnoreCase("true")) {
                text6.requestFocus();
            }

            String[] datos = dbHelper.obtenerInfoLugares(idioma, "inicio", "peñadefrancia", 5);

            text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text4.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text5.setText(datos[4] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

            //Imagenes

            storageRef = FirebaseStorage.getInstance().getReference();

            obtenerImagenFirebase("mapas/otros/penafrancia/peñafrancia1.png", img1);
            obtenerImagenFirebase("mapas/otros/penafrancia/peñafrancia2.png", img2);
            obtenerImagenFirebase("mapas/otros/penafrancia/peñafrancia3.png", img3);

            //Ubicacion
            ubicacion = dbHelper.obtenerUbiLugares("peñadefrancia");
        }
        lat = ubicacion[0];
        lon = ubicacion[1];

        //Mapa
        if (mapFragment != null) {
          mapFragment.getMapAsync(callback);
        }

        /*----------
         | Historia |
         ----------*/

        btn.setOnClickListener(v -> {
            fragment = historiaPenaFrancia.newInstance(args);
            cargarFragment(fragment);
        });

        /*---------------
         | ¿Qué visitar? |
         ---------------*/

        btn2.setOnClickListener(v -> {
            fragment = monumentosPenaFrancia.newInstance(args);
            cargarFragment(fragment);
        });

        /*---------
         | Leyenda |
         ---------*/

        btn3.setOnClickListener(v -> {
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

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }

}