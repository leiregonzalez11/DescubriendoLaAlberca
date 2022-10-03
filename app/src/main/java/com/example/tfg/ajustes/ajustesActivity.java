package com.example.tfg.ajustes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.tfg.inicio.MainActivity;
import com.example.tfg.R;
import com.example.tfg.categorias.categoriasActivity;
import com.example.tfg.mapa.MapsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Locale;

public class ajustesActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    RadioButton radioCas, radioEus, radioIng;
    String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        radioCas = findViewById(R.id.radio_castellano);
        radioEus = findViewById(R.id.radio_euskera);
        radioIng = findViewById(R.id.radio_ingles);

        TextView texto = findViewById(R.id.textoajustes);

        if (texto.getText().toString().contains("idioma:")){
            radioCas.setChecked(true);
        } else if (texto.getText().toString().contains("aukeratu:")){
            radioEus.setChecked(true);
        }else if (texto.getText().toString().contains("language:")){
            radioIng.setChecked(true);
        }

        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewAjustes);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_ajustes);

        comprobarIdioma();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.navigation_inicio:
                Intent inicio = new Intent(this, MainActivity.class);
                startActivity(inicio);
                return true;

            case R.id.navigation_mapa:
                Intent mapa = new Intent(this, MapsActivity.class);
                startActivity(mapa);
                return true;

            case R.id.navigation_categoria:
                Intent categorias = new Intent(this, categoriasActivity.class);
                startActivity(categorias);
                return true;

            case R.id.navigation_ajustes:
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void comprobarIdioma(){

        radioCas.setOnClickListener(view -> {
            language ="es";
            changeLanguage();
            finish();
            startActivity(getIntent());

        });

        radioEus.setOnClickListener(view -> {
            language="eu";
            changeLanguage();
            finish();
            startActivity(getIntent());
        });

        radioIng.setOnClickListener(view -> {
            language="en";
            changeLanguage();
            finish();
            startActivity(getIntent());
        });

    }

    private void changeLanguage(){

        Locale nuevaloc = new Locale(language);
        Locale.setDefault(nuevaloc);
        Configuration configuration = getBaseContext().getResources().getConfiguration();
        configuration.setLocale(nuevaloc);
        configuration.setLayoutDirection(nuevaloc);

        Context context = getBaseContext().createConfigurationContext(configuration);
        getBaseContext().getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());

    }

    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("language",language );
    }
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        language = savedInstanceState.getString("language");
    }

}