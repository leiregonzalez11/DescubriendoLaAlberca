package com.example.tfg.ajustesFragments.restauracion;

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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.ajustesFragments.dondeComerFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class establecimientoFragment extends Fragment {

    String idioma, categoria, establecimiento, ubicacion, telefono;
    double lat, lon;
    private StorageReference storageRef;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

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
            googleMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title(establecimiento));
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

        establecimiento = requireArguments().getString("nombreEst");
        categoria = requireArguments().getString("categoria");

        return inflater.inflate(R.layout.fragment_establecimiento, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbarPrueba);
        myToolbar.setNavigationIcon(R.drawable.arrow_back);
        myToolbar.setNavigationOnClickListener(view1 -> {

            myToolbar.setNavigationIcon(null);
            Fragment fragment = new dondeComerFragment();

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

        //Mapa
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapViewRestaurante);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        TextView text1 = requireView().findViewById(R.id.nombreRest);
        text1.setText(establecimiento);

        //Datos de la interfaz

        GestorDB dbHelper = new GestorDB(getContext());

        RatingBar ratingBar = requireView().findViewById(R.id.ratingBar);
        double punt = dbHelper.obtenerPuntRest(categoria, establecimiento);
        ratingBar.setRating((float) punt);

        TextView tel = requireView().findViewById(R.id.telrest2);
        TextView ubi = requireView().findViewById(R.id.ubirest2);

        String [] datos = dbHelper.obtenerDatosRest(categoria, establecimiento);

        telefono = datos[0];
        ubicacion = datos[1];
        lat = Double.parseDouble(datos[2]);
        lon = Double.parseDouble(datos[3]);

        if (!telefono.equals("No Disponible")) {
            SpannableString telsubrayado = new SpannableString(telefono);
            telsubrayado.setSpan(new UnderlineSpan(), 0, telsubrayado.length(), 0);

            tel.setText(telsubrayado);
            tel.setOnClickListener(view12 -> {
                Uri number = Uri.parse("tel:" + telefono); // Creamos una uri con el numero de telefono
                Intent dial = new Intent(Intent.ACTION_DIAL, number); // Creamos una llamada al Intent de llamadas
                startActivity(dial); // Ejecutamos el Intent
            });
        } else{
            tel.setText(telefono);
        }

        ubi.setText(ubicacion);

        storageRef = FirebaseStorage.getInstance().getReference();
        ImageView img = requireView().findViewById(R.id.fotoRest);
        obtenerImagenFirebase("/restaurante/" + establecimiento.toLowerCase().replaceAll(" ", "") + ".jpg", img);

    }

    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }

}