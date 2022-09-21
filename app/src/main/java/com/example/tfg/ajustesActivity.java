package com.example.tfg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class ajustesActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

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

        radioCas.setChecked(true);

        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewAjustes);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_ajustes);

        comprobarIdioma();
    }

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

        //intent.setClassName("com.android.settings", "com.android.settings.LanguageSettings");
        radioCas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                language ="es";
                changeLanguage();
                finish();
                radioCas.setChecked(true);
                radioEus.setChecked(false);
                radioIng.setChecked(false);
                startActivity(getIntent());

            }

        });

        radioEus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                language="eu";
                changeLanguage();
                finish();
                radioCas.setChecked(false);
                radioEus.setChecked(true);
                radioIng.setChecked(false);
                startActivity(getIntent());

            }

        });

        radioIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                language="en";
                changeLanguage();
                finish();
                radioCas.setChecked(false);
                radioEus.setChecked(false);
                radioIng.setChecked(true);
                startActivity(getIntent());
            }

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

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("language",language );
    }
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        language = savedInstanceState.getString("language");
    }

}