package com.example.tfg.mapOptions.otrosLugares;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.mapOptions.otrosLugares.majadas.lagunaSanMarcos;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class majadasYAlrededores extends Fragment {

    private Bundle args;
    private double lat, lon;
    private Fragment fragment;
    private ImageView img1, img2;
    private StorageReference storageRef;
    private SupportMapFragment mapFragment;
    private String idioma;
    private Button btn;
    private TextView text1, text2, text3, text4;

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
    public static majadasYAlrededores newInstance(Bundle args) {
        majadasYAlrededores fragment = new majadasYAlrededores();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public majadasYAlrededores() {}

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
        View v = inflater.inflate(R.layout.fragment_majadasyalrededores, container, false);
        if (v != null){
            text1 = v.findViewById(R.id.maj11);
            text2 = v.findViewById(R.id.maj12);
            text3 = v.findViewById(R.id.maj13);
            text4 = v.findViewById(R.id.maj14);
            img1 = v.findViewById(R.id.majimg1);
            img2 = v.findViewById(R.id.majimg2);
            btn = v.findViewById(R.id.btnmaj);
            mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapViewmaj);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Double[] ubicacion;
        try (GestorDB dbHelper = GestorDB.getInstance(getContext())) {

            String[] datos = dbHelper.obtenerInfoLugares(idioma, "majadas", "majadas", 4);

            text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text4.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

            //Imagenes

            storageRef = FirebaseStorage.getInstance().getReference();

            obtenerImagenFirebase("mapas/otros/majadas/majadas1.png", img1);
            obtenerImagenFirebase("mapas/otros/majadas/majadas2.png", img2);

            //Ubicacion
            ubicacion = dbHelper.obtenerUbiLugares("majadas");
        }
        
        lat = ubicacion[0];
        lon = ubicacion[1];

        //Mapa
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        /*------------
         | San Marcos |
         ------------*/

        btn.setOnClickListener(v -> {
            fragment = lagunaSanMarcos.newInstance(args);
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