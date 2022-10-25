package com.example.tfg.ajustes.rest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.ajustes.ajustesActivity;
import com.example.tfg.ajustes.dondeComerActivity;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class establecimientoActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener, OnMapReadyCallback {

    BottomNavigationView bottomNavigationView;
    GoogleMap mMap;
    String idioma, categoria, restaurante, ubicacion, telefono;
    double lat, lon;
    private StorageReference storageRef;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establecimiento);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        myToolbar.setTitleTextColor(R.color.white);

        Bundle extra = getIntent().getExtras();
        restaurante = extra.getString("nombreRest");
        categoria = "restaurante";
        determinarIdioma();

        TextView text1 = findViewById(R.id.nombreRest);
        text1.setText(restaurante);


        //MAPA
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapViewRestaurante);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        GestorDB dbHelper = new GestorDB(this);

        RatingBar ratingBar = findViewById(R.id.ratingBar);
        double punt = dbHelper.obtenerPuntRest(categoria, restaurante);
        ratingBar.setRating((float) punt);

        TextView tel = findViewById(R.id.telrest2);
        TextView ubi = findViewById(R.id.ubirest2);

        String [] datos = dbHelper.obtenerDatosRest(categoria, restaurante);

        telefono = datos[0];
        ubicacion = datos[1];
        lat = Double.parseDouble(datos[2]);
        lon = Double.parseDouble(datos[3]);

        SpannableString telsubrayado = new SpannableString(telefono);
        telsubrayado.setSpan(new UnderlineSpan(), 0, telsubrayado.length(), 0);

        tel.setText(telsubrayado);
        tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri number = Uri.parse("tel:" + telefono); // Creamos una uri con el numero de telefono
                Intent dial = new Intent(Intent.ACTION_DIAL, number); // Creamos una llamada al Intent de llamadas
                startActivity(dial); // Ejecutamos el Intent
            }
        });

        ubi.setText(ubicacion);

        storageRef = FirebaseStorage.getInstance().getReference();
        ImageView img = findViewById(R.id.fotoRest);
        obtenerImagenFirebase("/restaurante/" + restaurante.toLowerCase().replaceAll(" ", "") + ".jpg", img);


        //BOTON ATRAS

        ImageButton atrasBtn = findViewById(R.id.restAtras);
        atrasBtn.setOnClickListener(this);

        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewRest);
        bottomNavigationView.setSelectedItemId(R.id.navigation_ajustes);
        bottomNavigationView.setOnItemSelectedListener(this);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;
        // Añadimos un marcador a la ubicación elegida y hacemos zoom
        LatLng location = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions()
                .position(location)
                .title(restaurante));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 17.5f));
        //Tipo de mapa: Hibrido
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

    }

    private String determinarIdioma() {

        TextView ubitv = findViewById(R.id.ubirest);
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

    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(getApplicationContext()).load(uri).into(img));
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

        ImageButton btn = (ImageButton) view;

        switch (btn.getId()){

            case R.id.restAtras:
                Intent atras = new Intent(this, dondeComerActivity.class);
                atras.putExtra("idioma", idioma);
                startActivity(atras);
                break;
        }
    }
}