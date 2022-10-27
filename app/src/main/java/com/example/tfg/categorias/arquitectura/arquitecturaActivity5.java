package com.example.tfg.categorias.arquitectura;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.tfg.R;
import com.example.tfg.ajustes.ajustesActivity;
import com.example.tfg.categorias.categoriasActivity;
import com.example.tfg.inicio.MainActivity;
import com.example.tfg.adapters.SliderAdapter;
import com.example.tfg.mapa.MapsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.Objects;

public class arquitecturaActivity5 extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener, OnMapReadyCallback {

    BottomNavigationView bottomNavigationView;
    String categoria, idioma;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arquitectura5);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        myToolbar.setTitleTextColor(R.color.white);

        /*Obtenemos los datos agregados desde la actividad anterior...*/
        Bundle extra = getIntent().getExtras();
        idioma = extra.getString("idioma");
        categoria = extra.getString("categoria");

        //MAPA
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapViewSatur);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);


        //BOTON SIGUIENTE y ATRAS
        Button atrasBtn = findViewById(R.id.arqui5atras);
        atrasBtn.setOnClickListener(this);

        Button finBtn = findViewById(R.id.arquiAtras5);
        finBtn.setOnClickListener(this);

        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewArqui5);
        bottomNavigationView.setSelectedItemId(R.id.navigation_categoria);
        bottomNavigationView.setOnItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {}

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
                Intent mapa = new Intent(this, MapsActivity.class);
                mapa.putExtra("idioma",idioma);
                startActivity(mapa);
                finish();
                return true;

            case R.id.navigation_categoria:
                Intent categorias = new Intent(this, categoriasActivity.class);
                categorias.putExtra("idioma",idioma);
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

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        //Cuando se presione el botón, realiza una acción aquí

        Button btn = (Button) view;

        switch (btn.getId()) {

            case R.id.arqui5atras:
                Intent atras = new Intent(this, arquitecturaActivity4.class);
                atras.putExtra("idioma", idioma);
                atras.putExtra("categoria", categoria);
                startActivity(atras);
                finish();
                break;

            case R.id.arquiAtras5:
                Intent arquifin = new Intent(this, categoriasActivity.class);
                arquifin.putExtra("idioma", idioma);
                startActivity(arquifin);
                finish();
                break;
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        GoogleMap mMap = googleMap;
        // Añadimos un marcador a la ubicación elegida y hacemos zoom
        LatLng location = new LatLng(40.488984, -6.109707);
        mMap.addMarker(new MarkerOptions()
                .position(location)
                .title("Casa Museo Satur y Juanela"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 17.5f));
        //Tipo de mapa: Hibrido
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }
}