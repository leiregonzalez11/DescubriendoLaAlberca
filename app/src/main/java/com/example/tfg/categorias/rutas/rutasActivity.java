package com.example.tfg.categorias.rutas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.adapters.SpinnerAdapter;
import com.example.tfg.ajustes.ajustesActivity;
import com.example.tfg.categorias.categoriasActivity;
import com.example.tfg.inicio.MainActivity;
import com.example.tfg.mapa.MapsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;


public class rutasActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, AdapterView.OnItemSelectedListener, View.OnClickListener {

    private GestorDB dbHelper;
    private String idioma, categoria, nombreRuta;
    private ImageView img1, img2, img3;
    private TextView text2, text3, text4, text5, text6;
    private StorageReference storageRef;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutas);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        myToolbar.setTitleTextColor(R.color.white);

        Bundle extra = getIntent().getExtras();
        idioma = extra.getString("idioma");
        categoria = "rutas";

        dbHelper = new GestorDB(getApplicationContext());
        storageRef = FirebaseStorage.getInstance().getReference();

        text2 = findViewById(R.id.rutas2);
        text3 = findViewById(R.id.rutas3);
        text4 = findViewById(R.id.rutas4);
        text5 = findViewById(R.id.rutas5);
        text6 = findViewById(R.id.rutas6);

        img1 = findViewById(R.id.rutasimg1);
        img2 = findViewById(R.id.rutasimg2);
        img3 = findViewById(R.id.rutasimg7);

        Spinner spinner = findViewById(R.id.spinnerRutas);
        String [] rutas = getResources().getStringArray(R.array.rutas);
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(new SpinnerAdapter(this, R.layout.dropdownitemrutas, rutas));
        spinner.setOnItemSelectedListener(this);

        //Boton Atras
        Button btnAtras = findViewById(R.id.rutasAtras1);
        btnAtras.setOnClickListener(this);

        //MENU
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationViewRutas);
        bottomNavigationView.setSelectedItemId(R.id.navigation_categoria);
        bottomNavigationView.setOnItemSelectedListener(this);

    }

    /** Método utilizado para conocer la ruta elegida por el usuario para obtener la información */
    private String determinarRuta(String idRuta) {

        if(idRuta.toLowerCase().contains("francia")){
            nombreRuta = "peñadefrancia";
        } else if (idRuta.toLowerCase().contains("raíces")){
            nombreRuta = "raices";
        } else if (idRuta.toLowerCase().contains("torrita")){
            nombreRuta = "torrita";
        }else if (idRuta.toLowerCase().contains("huevo")){
            nombreRuta = "peñadelhuevo";
        }else if (idRuta.toLowerCase().contains("mogarraz")){
            nombreRuta = "mogarraz";
        }else if (idRuta.toLowerCase().contains("martin")){
            nombreRuta = "sanmartin";
        }

        return nombreRuta;
    }

        /** Método utilizado para obtener la imagen de Firebase Storage */
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        nombreRuta = determinarRuta((String) adapterView.getItemAtPosition(position));

        String[] datos = dbHelper.obtenerDatosRutas(idioma, nombreRuta, categoria);

        System.out.println("RUTAAAAA " + nombreRuta);
        for (int i = 0; i < datos.length; i++){
            System.out.println("RUTAAAAA " + i + datos[i]);
        }

        text2.setText(datos[0]);
        text3.setText(datos[1]);
        text4.setText(datos[2]);
        text5.setText(datos[4]);
        text6.setText(datos[3]);
        obtenerImagenFirebase("rutas/" + nombreRuta + "1.jpg", img1);
        obtenerImagenFirebase("rutas/" + nombreRuta + "2.jpg", img2);
        obtenerImagenFirebase("rutas/" + nombreRuta + "3.jpg", img3);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        //Cuando se presione el botón, realiza una acción aquí

        Button btn = (Button) view;

        if (btn.getId() == R.id.rutasAtras1) {
            Intent arteCat = new Intent(this, categoriasActivity.class);
            arteCat.putExtra("idioma", idioma);
            startActivity(arteCat);
            finish();
        }
    }
}