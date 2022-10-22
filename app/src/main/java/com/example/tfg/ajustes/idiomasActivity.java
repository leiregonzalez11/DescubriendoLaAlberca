package com.example.tfg.ajustes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

public class idiomasActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    RadioButton radioCas, radioEus, radioIng, radioCat;
    String language, idioma;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idiomas);

        Toolbar myToolbar = findViewById(R.id.toolbarId);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        myToolbar.setTitleTextColor(R.color.white);

        radioCas = findViewById(R.id.radio_castellano);
        radioEus = findViewById(R.id.radio_euskera);
        radioIng = findViewById(R.id.radio_ingles);
        //radioCat = findViewById(R.id.radio_catalan);

        TextView texto = findViewById(R.id.textoajustes);

        if (texto.getText().toString().equals("Seleccione un idioma:")){
            radioCas.setChecked(true);
            radioCas.setTextColor(R.color.purple_500);
            idioma = "es";
        } else if (texto.getText().toString().contains("aukeratu:")){
            radioEus.setChecked(true);
            radioEus.setTextColor(R.color.purple_500);
            idioma = "eu";
        }else if (texto.getText().toString().contains("language:")){
            radioIng.setChecked(true);
            radioIng.setTextColor(R.color.purple_500);
            idioma = "en";
        } /*else if (texto.getText().toString().contains("Seleccioneu")){
            radioCat.setChecked(true);
            radioCat.setTextColor(R.color.purple_500);
            idioma = "ca";
        }*/

        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewAjustes);
        bottomNavigationView.setSelectedItemId(R.id.navigation_ajustes);
        bottomNavigationView.setOnItemSelectedListener(this);

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
                mapa.putExtra("idioma", idioma);
                startActivity(mapa);
                return true;

            case R.id.navigation_categoria:
                Intent categorias = new Intent(this, categoriasActivity.class);
                categorias.putExtra("idioma", idioma);
                startActivity(categorias);
                return true;

            case R.id.navigation_ajustes:
                Intent ajustes = new Intent(this, ajustesActivity.class);
                ajustes.putExtra("idioma", idioma);
                startActivity(ajustes);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed() {}

    private void comprobarIdioma(){

        Intent ajustes = new Intent(this, ajustesActivity.class);

        radioCas.setOnClickListener(view -> {
            language ="es";
            ajustes.putExtra("idioma", language);
            changeLanguage();
            finish();
            startActivity(ajustes);

        });

        radioEus.setOnClickListener(view -> {
            language="eu";
            ajustes.putExtra("idioma", language);
            changeLanguage();
            finish();
            startActivity(ajustes);
        });

        radioIng.setOnClickListener(view -> {
            language="en";
            ajustes.putExtra("idioma", language);
            changeLanguage();
            finish();
            startActivity(ajustes);
        });

        /*radioCat.setOnClickListener(view -> {
            language="ca";
            changeLanguage();
            finish();
            startActivity(getIntent());
        });*/

    }

    public String getIdioma (){ return idioma;}

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