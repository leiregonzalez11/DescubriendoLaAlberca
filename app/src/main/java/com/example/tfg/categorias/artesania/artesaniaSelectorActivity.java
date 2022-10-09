package com.example.tfg.categorias.artesania;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.tfg.ajustes.listViewAdapter;
import com.example.tfg.inicio.MainActivity;
import com.example.tfg.R;
import com.example.tfg.categorias.categoriasActivity;
import com.example.tfg.mapa.MapsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class artesaniaSelectorActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener {

    private BottomNavigationView bottomNavigationView;
    private ListView listView, listView2;
    private ArrayList<String> lista1, lista2;
    String idioma, categoria, opc1, opc2;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artesania_selector);

        Bundle datos = getIntent().getExtras();
        idioma = datos.getString("idioma");
        categoria = datos.getString("categoria");

        listView = findViewById(R.id.listviewArte1);
        listView2 = findViewById(R.id.listviewArte2);

        opc1 = getResources().getString(R.string.el_bordado_serrano);
        opc2 = getResources().getString(R.string.traje_serrano);

        System.out.println("TEXTOOOOOO" + opc1);
        System.out.println("TEXTOOOOOO" + opc2);

        lista1 = new ArrayList<String>();
        lista1.add(opc1);

        listViewAdapter myAdapter = new listViewAdapter(this, R.layout.list_item, lista1);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent arte2 = new Intent(getApplicationContext(), artesaniaActivity2.class);
                arte2.putExtra("idioma", idioma);
                arte2.putExtra("categoria", categoria);
                startActivity(arte2);
            }
        });

        lista2 = new ArrayList<String>();
        lista2.add(opc2);

        listViewAdapter myAdapter2 = new listViewAdapter(this, R.layout.list_item, lista2);
        listView2.setAdapter(myAdapter2);

        listView2.setOnItemClickListener((adapterView, view, position, id) -> {
            Intent arte3 = new Intent(getApplicationContext(), artesaniaActivity3.class);
            arte3.putExtra("idioma", idioma);
            arte3.putExtra("categoria", categoria);
            startActivity(arte3);
        });

        //BOTON SIGUIENTE y ATRAS

        Button atrasBtn = findViewById(R.id.arteatrass);
        atrasBtn.setOnClickListener(this);

        Button finBtn = findViewById(R.id.artefin2);
        finBtn.setOnClickListener(this);

        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewSelector);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_ajustes);

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
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        //Cuando se presione el botón, realiza una acción aquí

        Button btn = (Button) view;

        switch (btn.getId()){

            case R.id.arteatrass:
                Intent atras = new Intent(this, artesaniaActivity.class);
                atras.putExtra("idioma", idioma);
                atras.putExtra("categoria", categoria);
                startActivity(atras);
                break;

            case R.id.artefin2:
                Intent cat = new Intent(this, categoriasActivity.class);
                cat.putExtra("idioma", idioma);
                startActivity(cat);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {}

}