package com.example.tfg.categorias;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.tfg.categorias.arquitectura.ArquitecturaActivity;
import com.example.tfg.inicio.MainActivity;
import com.example.tfg.R;
import com.example.tfg.ajustes.ajustesActivity;
import com.example.tfg.mapa.MapsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class categoriasActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewCategorias);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_categoria);
    }

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
                startActivity(mapa);
                finish();
                return true;

            case R.id.navigation_categoria:
                return true;

            case R.id.navigation_ajustes:
                Intent intent = new Intent(this, ajustesActivity.class);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void onClick(View view) {
        //Cuando se presione el botón, realiza una acción aquí

        switch (view.getId()){

            //case R.id.botontradiciones:


            case R.id.botonarquitectura:
                Intent inicio = new Intent(this, ArquitecturaActivity.class);
                startActivity(inicio);
                finish();

            //case R.id.botonmonumentos:

            //case R.id.botonfiestas:

            //case R.id.botongastronomia:

            //case R.id.botonalojamientos:

            //case R.id.botonruta:

            //ase R.id.botonotros:

        }
    }

}