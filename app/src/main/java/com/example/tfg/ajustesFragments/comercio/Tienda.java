package com.example.tfg.ajustesFragments.comercio;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.ajustesFragments.Comercio;
import com.example.tfg.ajustesFragments.DondeDormir;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Tienda extends DialogFragment {

    String categoria, tienda, telefono;
    double lat, lon;

    private final OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng location = new LatLng(lat, lon);
            googleMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title(tienda));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 17.5f));
            //Tipo de mapa: Hibrido
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(false);

        tienda = requireArguments().getString("nombreCom");
        categoria = requireArguments().getString("categoria");

        return inflater.inflate(R.layout.fragment_tienda, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        myToolbar.setNavigationOnClickListener(view1 -> {

            myToolbar.setNavigationIcon(null);
            Fragment fragment = new Comercio();
            cargarFragment(fragment);
        });

        //Mapa
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapViewCom);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        TextView text1 = requireView().findViewById(R.id.nombreCom);
        text1.setText(tienda);

        //Datos de la interfaz

        GestorDB dbHelper = new GestorDB(getContext());

        RatingBar ratingBar = requireView().findViewById(R.id.ratingBarCom);
        double punt = dbHelper.obtenerPuntAloj(categoria, tienda);
        ratingBar.setRating((float) punt);

        TextView tel = requireView().findViewById(R.id.telcom);
        TextView ubi = requireView().findViewById(R.id.ubicom);

        //TODO: CAMBIAR
        String [] datos = dbHelper.obtenerDatosAloj(categoria, tienda);

        telefono = datos[0];
        lat = Double.parseDouble(datos[1]);
        lon = Double.parseDouble(datos[2]);

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

        ubi.setText(datos[3]);

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