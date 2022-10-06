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

public class idiomasActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    RadioButton radioCas, radioEus, radioIng, radioCat, radioFr, radioDe;
    String language, idioma;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idiomas);

        radioCas = findViewById(R.id.radio_castellano);
        radioEus = findViewById(R.id.radio_euskera);
        radioIng = findViewById(R.id.radio_ingles);
        radioCat = findViewById(R.id.radio_catalan);
        //radioFr = findViewById(R.id.radio_frances);
        //radioDe = findViewById(R.id.radio_aleman);

        TextView texto = findViewById(R.id.textoajustes);

        if (texto.getText().toString().equals("Seleccione un idioma:")){
            radioCas.setChecked(true);
            radioCas.setTextColor(R.color.purple_500);
            Bundle extra = getIntent().getExtras();
            idioma = "es";
        } else if (texto.getText().toString().contains("aukeratu:")){
            radioEus.setChecked(true);
            radioEus.setTextColor(R.color.purple_500);
            idioma = "eu";
        }else if (texto.getText().toString().contains("language:")){
            radioIng.setChecked(true);
            radioIng.setTextColor(R.color.purple_500);
            idioma = "en";
        }else if (texto.getText().toString().contains("Seleccioneu")){
            radioCat.setChecked(true);
            radioCat.setTextColor(R.color.purple_500);
            idioma = "ca";
        }/*else if (texto.getText().toString().contains("Wählen")){
            radioDe.setChecked(true);
            radioDe.setTextColor(R.color.purple_500);
            idioma = "de";
        }else if (texto.getText().toString().contains("Sélectionnez")){
            radioFr.setChecked(true);
            radioFr.setTextColor(R.color.purple_500);
            idioma = "fr";
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

        radioCat.setOnClickListener(view -> {
            language="ca";
            changeLanguage();
            finish();
            startActivity(getIntent());
        });

        /*radioDe.setOnClickListener(view -> {
            language="de";
            changeLanguage();
            finish();
            startActivity(getIntent());
        });

        radioFr.setOnClickListener(view -> {
            language="fr";
            changeLanguage();
            finish();
            startActivity(getIntent());
        });*/

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