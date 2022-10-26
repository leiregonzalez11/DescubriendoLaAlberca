package com.example.tfg.ajustes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.example.tfg.R;
import com.example.tfg.adapters.SpinnerAdapter;
import com.example.tfg.adapters.listViewAdapter;
import com.example.tfg.categorias.categoriasActivity;
import com.example.tfg.inicio.MainActivity;
import com.example.tfg.mapa.MapsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class comoLlegarActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener, AdapterView.OnItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    StorageReference storageRef;
    String idioma, nombreBus;
    ImageView img1;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_como_llegar);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        myToolbar.setTitleTextColor(R.color.white);

        Bundle extra = getIntent().getExtras();
        idioma = extra.getString("idioma");

        storageRef = FirebaseStorage.getInstance().getReference();
        img1 = findViewById(R.id.comollegar3);

        //BOTON ATRAS

        ImageButton atrasBtn = findViewById(R.id.atrasBtn);
        atrasBtn.setOnClickListener(this);

        //Spinner
        Spinner spinner = findViewById(R.id.spinnerBus);
        String [] bus = getResources().getStringArray(R.array.bus);
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(new SpinnerAdapter(this, R.layout.dropdownitenbus, bus));
        //spinner.setAdapter(new ArrayAdapter<>(this, R.layout.dropdownitenbus, bus));
        spinner.setOnItemSelectedListener(this);

        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewComoLlegar);
        bottomNavigationView.setSelectedItemId(R.id.navigation_ajustes);
        bottomNavigationView.setOnItemSelectedListener(this);

    }

    /** Método utilizado para conocer la ruta elegida por el usuario para obtener la información */
    private void determinarRuta(String idBus) {

        if(idBus.toLowerCase().contains("salamanca")){
            nombreBus = "laalbercasalamanca";
        } else if (idBus.toLowerCase().contains("béjar")){
            nombreBus = "laalbercabejar";
        } else if (idBus.toLowerCase().contains("ciudad")){
            nombreBus = "laalbercaciudi";
        }
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

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        //Cuando se presione el botón, realiza una acción aquí

         ImageButton btn = (ImageButton) view;

        if (btn.getId() == R.id.atrasBtn) {
            Intent atras = new Intent(this, ajustesActivity.class);
            atras.putExtra("idioma", idioma);
            startActivity(atras);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        determinarRuta((String) adapterView.getItemAtPosition(position));
        obtenerImagenFirebase("ajustes/" + nombreBus + ".jpg", img1);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}