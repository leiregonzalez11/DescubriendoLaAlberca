package com.example.tfg.navigationmenu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tfg.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.Objects;


public class FragmentMapa extends SupportMapFragment implements OnMapReadyCallback {

    public FragmentMapa() {
        // Required empty public constructor
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        getMapAsync(this);

        return rootView;
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_mapa, container, false);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        /*
        * //Toolbar
        Toolbar myToolbar = requireView().findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(myToolbar);
        Objects.requireNonNull(Objects.requireNonNull((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowTitleEnabled(false);
        myToolbar.setTitleTextColor(R.color.white);
        * */

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Añadimos un marcador a la ubicación elegida y hacemos zoom
        LatLng location = new LatLng(40.48890, -6.11050);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15.3f));
        //Tipo de mapa: Hibrido
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

    }


}