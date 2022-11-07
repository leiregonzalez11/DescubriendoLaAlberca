package com.example.tfg.anterior.ajustes.aloj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.anterior.ajustes.ajustesActivity;
import com.example.tfg.anterior.ajustes.dondeDormirActivity;
import com.example.tfg.anterior.categorias.categoriasActivity;
import com.example.tfg.anterior.inicio.MainActivity;
import com.example.tfg.anterior.mapa.MapsActivity;
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

import java.util.Objects;


public class alojamientoActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener, OnMapReadyCallback {

    BottomNavigationView bottomNavigationView;
    GoogleMap mMap;
    String idioma, categoria, alojamiento;
    double lat, lon;
    private StorageReference storageRef;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alojamiento);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        myToolbar.setTitleTextColor(R.color.white);

        Bundle extra = getIntent().getExtras();
        alojamiento = extra.getString("nombreAloj");
        categoria = "alojamiento";
        determinarIdioma();

        GestorDB dbHelper = new GestorDB(this);

        TextView text1 = findViewById(R.id.nombreAloj);
        TextView ubi = findViewById(R.id.ubi2);
        TextView tel = findViewById(R.id.telaloj2);

        text1.setText(alojamiento);

        //MAPA
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapViewAlojamiento);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        //Datos de la interfaz

        String [] datos = dbHelper.obtenerDatosAloj(categoria, alojamiento);

        String telefono = datos[0];
        lat = Double.parseDouble(datos[1]);
        lon = Double.parseDouble(datos[2]);
        ubi.setText(datos[3]);

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

        //RATING BAR

        RatingBar ratingBar = findViewById(R.id.ratingBarAloj);
        double punt = dbHelper.obtenerPuntAloj(categoria, alojamiento);
        ratingBar.setRating((float) punt);

        storageRef = FirebaseStorage.getInstance().getReference();
        ImageView img = findViewById(R.id.fotoAloj);
        obtenerImagenFirebase("/ajustes/" + alojamiento.toLowerCase().replaceAll(" ", "") + ".jpg", img);


        //BOTON ATRAS

        ImageButton atrasBtn = findViewById(R.id.alojAtras);
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
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 17f));
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

        if (btn.getId() == R.id.alojAtras) {
            Intent atras = new Intent(this, dondeDormirActivity.class);
            atras.putExtra("idioma", idioma);
            startActivity(atras);
        }
    }
}