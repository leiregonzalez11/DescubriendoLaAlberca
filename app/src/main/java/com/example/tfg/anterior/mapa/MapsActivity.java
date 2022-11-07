package com.example.tfg.anterior.mapa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.tfg.anterior.inicio.MainActivity;
import com.example.tfg.R;
import com.example.tfg.anterior.ajustes.ajustesActivity;
import com.example.tfg.anterior.categorias.categoriasActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Objects;

public class MapsActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, OnMapReadyCallback {

    public BottomNavigationView bottomNavigationView;
    String idioma;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        myToolbar.setTitleTextColor(R.color.white);

        //RUTA PREDETERMINADA

        /*Bundle datos = getIntent().getExtras();
        tour = datos.getString("ruta si");*/

        Bundle extra = getIntent().getExtras();
        idioma = extra.getString("idioma");

        //MAPA
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);


        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewMaps);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_mapa);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Añadimos un marcador a la ubicación elegida y hacemos zoom
        LatLng location = new LatLng(40.48890, -6.11050);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15.3f));
        //Tipo de mapa: Hibrido
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.navigation_inicio:
                Intent inicio = new Intent(this, MainActivity.class);
                startActivity(inicio);
                finish();
                return true;

            case R.id.navigation_mapa:
                return true;

            case R.id.navigation_categoria:
                Intent categorias = new Intent(this, categoriasActivity.class);
                categorias.putExtra("idioma", idioma);
                startActivity(categorias);
                finish();
                return true;

            case R.id.navigation_ajustes:
                Intent ajustes = new Intent(this, ajustesActivity.class);
                ajustes.putExtra("idioma", idioma);
                startActivity(ajustes);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent inicio = new Intent(this, MainActivity.class);
        startActivity(inicio);

    }

}