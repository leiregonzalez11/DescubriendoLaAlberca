package com.example.tfg.categorias.rutas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.ajustes.ajustesActivity;
import com.example.tfg.categorias.categoriasActivity;
import com.example.tfg.inicio.MainActivity;
import com.example.tfg.mapa.MapsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class rutasActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, AdapterView.OnItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    String idioma, categoria, nombreRuta;
    ImageView img1, img2, img3;
    TextView text1, text2, text3, text4, text5, text6;
    StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutas);

        Bundle extra = getIntent().getExtras();
        idioma = extra.getString("idioma");
        categoria = "rutas";

        GestorDB dbHelper = new GestorDB(getApplicationContext());
        storageRef = FirebaseStorage.getInstance().getReference();

        text1 = findViewById(R.id.rutas1);
        text2 = findViewById(R.id.rutas2);
        text3 = findViewById(R.id.rutas3);
        text4 = findViewById(R.id.rutas4);
        text5 = findViewById(R.id.rutas5);
        text6 = findViewById(R.id.rutas6);

        img1 = findViewById(R.id.rutasimg1);
        img2 = findViewById(R.id.rutasimg2);
        img3 = findViewById(R.id.rutasimg7);


        Spinner spinner = findViewById(R.id.spinnerRutas);
        String [] trajes = getResources().getStringArray(R.array.rutas);
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(new ArrayAdapter<>(this, R.layout.dropdownitem, trajes));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                nombreRuta = determinarRuta((String) adapterView.getItemAtPosition(position));
                System.out.println("RUTAAAAA " + nombreRuta);
                String[] datos = dbHelper.obtenerDatosInterfazRutas(idioma, nombreRuta, categoria);
                for (int i = 0; i < datos.length; i++){
                    System.out.println("RUTAAAAA " + i + datos[i]);
                }
                text1.setText(((String) adapterView.getItemAtPosition(position)).toUpperCase());
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
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewRutas);
        bottomNavigationView.setSelectedItemId(R.id.navigation_categoria);
        bottomNavigationView.setOnItemSelectedListener(this);

    }

    private String determinarRuta(String idRuta) {

        if(idRuta.toLowerCase().contains("peña")){
            nombreRuta = "peñadefrancia";
        } else if (idRuta.toLowerCase().contains("raíces")){
            nombreRuta = "raices";
        } else if (idRuta.toLowerCase().contains("torrita")){
            nombreRuta = "torrita";
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}