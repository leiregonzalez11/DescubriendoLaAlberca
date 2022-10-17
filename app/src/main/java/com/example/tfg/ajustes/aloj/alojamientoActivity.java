package com.example.tfg.ajustes.aloj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.ajustes.ajustesActivity;
import com.example.tfg.ajustes.dondeDormirActivity;
import com.example.tfg.categorias.categoriasActivity;
import com.example.tfg.inicio.MainActivity;
import com.example.tfg.mapa.MapsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class alojamientoActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener, OnMapReadyCallback {

    BottomNavigationView bottomNavigationView;
    GoogleMap mMap;
    String idioma, categoria, alojamiento;
    double lat, lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alojamiento);

        Bundle extra = getIntent().getExtras();
        alojamiento = extra.getString("nombreAloj");
        categoria = "alojamiento";
        determinarIdioma();

        TextView text1 = findViewById(R.id.nombreAloj);
        text1.setText(alojamiento);

        //MAPA
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapViewAlojamiento);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        GestorDB dbHelper = new GestorDB(this);

        String [] datos = dbHelper.obtenerDatosAloj(categoria, alojamiento);

        lat = Double.parseDouble(datos[0]);
        System.out.println("DESCRIIIIIIIP" + lat);
        lon = Double.parseDouble(datos[1]);
        System.out.println("DESCRIIIIIIIP" + lon);

        /*ImageView img = findViewById(R.id.fotoAloj);*/


        //BOTON ATRAS

        Button atrasBtn = findViewById(R.id.alojAtras);
        atrasBtn.setOnClickListener(this);

        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewAloj);
        bottomNavigationView.setSelectedItemId(R.id.navigation_ajustes);
        bottomNavigationView.setOnItemSelectedListener(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        // Añadimos un marcador a la ubicación elegida y hacemos zoom
        LatLng location = new LatLng(lat, lon);
        //LatLng location = new LatLng(40.499432367526026, -6.115758261002924);
        mMap.addMarker(new MarkerOptions()
                .position(location)
                .title(alojamiento));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 16f));
        //Tipo de mapa: Hibrido
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

    }

    private String determinarIdioma() {

        TextView ubitv = findViewById(R.id.ubi);
        String ubi = ubitv.getText().toString();

        if (ubi.equalsIgnoreCase("ubicación")){
            idioma = "es";
        } else if (ubi.equalsIgnoreCase("location")){
            idioma = "en";
        } else if (ubi.equalsIgnoreCase("kokapena")){
            idioma = "eu";
        } else if (ubi.equalsIgnoreCase("ubicació")){
            idioma = "ca";
        }

        return idioma;
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

    @Override
    public void onBackPressed() {}

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        //Cuando se presione el botón, realiza una acción aquí

        Button btn = (Button) view;

        switch (btn.getId()){

            case R.id.alojAtras:
                Intent atras = new Intent(this, dondeDormirActivity.class);
                atras.putExtra("idioma", idioma);
                startActivity(atras);
                break;
        }
    }
}