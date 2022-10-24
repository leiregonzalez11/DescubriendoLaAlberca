package com.example.tfg.categorias.artesania;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.util.Locale;
import java.util.Objects;


public class artesaniaActivity3 extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, OnClickListener, AdapterView.OnItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    String idioma, categoria, nombreTraje;
    ImageView img1, img2, img3;
    StorageReference storageRef;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artesania3);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        myToolbar.setTitleTextColor(R.color.white);

        GestorDB dbHelper = new GestorDB(getApplicationContext());
        TextView text1 = findViewById(R.id.arte31);
        TextView text2 = findViewById(R.id.arte32);
        TextView text3 = findViewById(R.id.arte33);
        img1 = findViewById(R.id.arte31img);
        img2 = findViewById(R.id.arte32img);
        img3 = findViewById(R.id.arte33img);

        //Spinner

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        String [] trajes = getResources().getStringArray(R.array.trajes_serranos);
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(new ArrayAdapter<>(this, R.layout.dropdownitem, trajes));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                nombreTraje = determinarTraje((String) adapterView.getItemAtPosition(position));
                System.out.println("TRAJEEEEE " + nombreTraje);

                String [] datos = dbHelper.obtenerDatosTrajes(idioma, "interfaz3", categoria, 3, nombreTraje);
                text1.setText(datos[0]);
                text2.setText(datos[1]);
                text3.setText(datos[2]);
                obtenerImagenFirebase("artesania/" + nombreTraje + "1.jpg", img1);
                obtenerImagenFirebase("artesania/" + nombreTraje + "2.jpg", img2);
                obtenerImagenFirebase("artesania/" + nombreTraje + "3.jpg", img3);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        storageRef = FirebaseStorage.getInstance().getReference();

        Bundle extra = getIntent().getExtras();
        idioma = extra.getString("idioma");
        categoria = extra.getString("categoria");

        //BOTON SIGUIENTE y ATRAS

        Button atrasBtn = findViewById(R.id.arteatras3);
        atrasBtn.setOnClickListener(this);

        Button atrasBtn2 = findViewById(R.id.arteAtras3);
        atrasBtn2.setOnClickListener(this);

        Button siguienteBtn = findViewById(R.id.artesiguiente3);
        siguienteBtn.setOnClickListener(this);

        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewArte3);
        bottomNavigationView.setSelectedItemId(R.id.navigation_categoria);
        bottomNavigationView.setOnItemSelectedListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private String determinarTraje(String idtraje) {

        if(idtraje.toLowerCase().contains("sayas")){
            nombreTraje = "sayas";
        } else if (idtraje.toLowerCase().contains("ventioseno")){
            nombreTraje = "ventioseno";
        } else if (idtraje.toLowerCase().contains("vistas")){
            nombreTraje = "vistas";
        } else if (idtraje.toLowerCase().contains("zagalejo")){
            nombreTraje = "zagalejo";
        } else if (idtraje.toLowerCase().contains("manteo")){
            nombreTraje = "manteo";
        }

        return nombreTraje;
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

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(getApplicationContext()).load(uri).into(img));
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        //Cuando se presione el botón, realiza una acción aquí

        Button btn = (Button) view;

        switch (btn.getId()){

            case R.id.arteatras3:
                Intent atras = new Intent(this, artesaniaSelectorActivity.class);
                atras.putExtra("idioma", idioma);
                atras.putExtra("categoria", categoria);
                startActivity(atras);
                break;

            case R.id.arteAtras3:
                Intent mapa = new Intent(this, categoriasActivity.class);
                mapa.putExtra("idioma",idioma);
                startActivity(mapa);
                finish();
                break;

            case R.id.artesiguiente3:
                Intent arte4 = new Intent(this, artesaniaActivity4.class);
                arte4.putExtra("idioma", idioma);
                arte4.putExtra("categoria", categoria);
                startActivity(arte4);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {}

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    @Override
    public void onBackPressed() {}

}