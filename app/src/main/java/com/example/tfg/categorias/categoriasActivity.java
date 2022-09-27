package com.example.tfg.categorias;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.tfg.categorias.arquitectura.ArquitecturaActivity;
import com.example.tfg.inicio.MainActivity;
import com.example.tfg.R;
import com.example.tfg.ajustes.ajustesActivity;
import com.example.tfg.mapa.MapsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class categoriasActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    BottomNavigationView bottomNavigationView;
    protected View btnTrad, btnArqui, btnMonu, btnFiesta, btnGastro, btnAloj, btnRutas, btnOtros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        setBtnListeners();

        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewCategorias);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_categoria);
    }

    private void setBtnListeners() {

        //btnTrad = (Button) findViewById(R.id.botontradiciones);
        //btnTrad.setOnClickListener(this);

        btnArqui = (Button) findViewById(R.id.botonarquitectura);
        btnArqui.setOnClickListener(this);

        /*btnMonu = (Button) findViewById(R.id.botonmonumentos);
        btnMonu.setOnClickListener(this);

        btnFiesta = (Button) findViewById(R.id.botonfiestas);
        btnFiesta.setOnClickListener(this);

        btnGastro = (Button) findViewById(R.id.botongastronomia);
        btnGastro.setOnClickListener(this);

        btnAloj = (Button) findViewById(R.id.botonalojamientos);
        btnAloj.setOnClickListener(this);

        btnRutas = (Button) findViewById(R.id.botonruta);
        btnRutas.setOnClickListener(this);

        btnOtros = (Button) findViewById(R.id.botonotros);
        btnOtros.setOnClickListener(this);*/

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

        Button btn = (Button) view;

        switch (btn.getId()){

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