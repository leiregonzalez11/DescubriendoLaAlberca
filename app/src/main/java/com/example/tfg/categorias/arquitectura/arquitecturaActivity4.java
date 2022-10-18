package com.example.tfg.categorias.arquitectura;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.ajustes.ajustesActivity;
import com.example.tfg.categorias.categoriasActivity;
import com.example.tfg.inicio.MainActivity;
import com.example.tfg.mapa.MapsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class arquitecturaActivity4 extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, OnClickListener {

    BottomNavigationView bottomNavigationView;
    String idioma, categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arquitectura4);

        /*Obtenemos los datos agregados desde la actividad anterior...*/
        Bundle extra = getIntent().getExtras();
        idioma = extra.getString("idioma");
        categoria = extra.getString("categoria");

        GestorDB dbHelper = new GestorDB(getApplicationContext());

        String [] datos = dbHelper.obtenerDescrInterfaz(idioma, "interfaz4", categoria, 3);

        TextView text1 = findViewById(R.id.arqui41);
        text1.setText(datos[0]);

        TextView text2 = findViewById(R.id.arqui42);
        text2.setText(datos[1]);

        TextView text3 = findViewById(R.id.arqui43);
        text3.setText(datos[2]);


        //BOTON SIGUIENTE y ATRAS
        Button atrasBtn = findViewById(R.id.arquiatras4);
        atrasBtn.setOnClickListener(this);

        Button finBtn = findViewById(R.id.arquisiguiente4);
        finBtn.setOnClickListener(this);

        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewArqui4);
        bottomNavigationView.setSelectedItemId(R.id.navigation_categoria);
        bottomNavigationView.setOnItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {}

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

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        //Cuando se presione el botón, realiza una acción aquí

        Button btn = (Button) view;

        switch (btn.getId()) {

            case R.id.arquiatras4:
                Intent atras = new Intent(this, arquitecturaActivity3.class);
                atras.putExtra("idioma", idioma);
                atras.putExtra("categoria", categoria);
                startActivity(atras);
                finish();
                break;

            case R.id.arquisiguiente4:
                Intent arqui5 = new Intent(this, arquitecturaActivity5.class);
                arqui5.putExtra("idioma", idioma);
                arqui5.putExtra("categoria", categoria);
                startActivity(arqui5);
                finish();
                break;
        }
    }
}