package com.example.tfg.mapOptions.otrosLugares;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.DialogFragment;
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
import com.example.tfg.GestorDB;
import com.example.tfg.mapOptions.otrosLugares.parquebatuecas.batuecasElector;
import com.example.tfg.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class batuecas extends Fragment {

    private Bundle args;
    private double lat, lon;
    private ImageView img1, img2;
    private StorageReference storageRef;
    private SupportMapFragment mapFragment;
    private String idioma;
    private Button btn1;
    private TextView text1, text2, text3, text4;

    /** Este callback se activa cuando el mapa está listo para ser utilizado. */
    private final OnMapReadyCallback callback = new OnMapReadyCallback() {
        /**
         * Manipula el mapa una vez haya sido creado.
         * Aquí es donde podemos añadir marcadores o líneas, añadir listeners o mover la cámara.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            //Localización de las batuecas
            LatLng location = new LatLng(lat, lon);
            //googleMap.addMarker(new MarkerOptions().position(location).title("Peña de Francia"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 13.5f));
            //Tipo de mapa: Hibrido
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }
    };

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static batuecas newInstance(Bundle args) {
        batuecas fragment = new batuecas();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public batuecas() {}

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
        View v = inflater.inflate(R.layout.fragment_batuecas, container, false);
        if (v != null){
            text1 = v.findViewById(R.id.bat11);
            text2 = v.findViewById(R.id.bat12);
            text3 = v.findViewById(R.id.bat13);
            text4 = v.findViewById(R.id.bat14);
            img1 = v.findViewById(R.id.batimg1);
            img2 = v.findViewById(R.id.batimg2);
            btn1 = v.findViewById(R.id.btnbat1);
            mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapViewbat);
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

            String[] datos = dbHelper.obtenerInfoLugares(idioma, "intro", "batuecas", 4);

            text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text4.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

            //Imagenes

            storageRef = FirebaseStorage.getInstance().getReference();

            obtenerImagenFirebase("mapas/otros/batuecas/batuecas1.png", img1);
            obtenerImagenFirebase("mapas/otros/batuecas/batuecas2.png", img2);

            //Ubicacion
            ubicacion = dbHelper.obtenerUbiLugares("batuecas");
        }
        lat = ubicacion[0];
        lon = ubicacion[1];

        //Mapa
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        /*---------------
         | ¿Qué visitar? |
         ---------------*/

        btn1.setOnClickListener(v ->  {
            DialogFragment fragment = new batuecasElector();
            fragment.setArguments(args);
            fragment.setCancelable(false);
            fragment.show(getChildFragmentManager(),"batuecaselector");

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