package com.example.tfg.ajustes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfg.inicio.MainActivity;
import com.example.tfg.R;
import com.example.tfg.categorias.categoriasActivity;
import com.example.tfg.mapa.MapsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Locale;

public class ajustesActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private ListView listView, listView2, listView3, listView4;
    private ArrayList<String> lista1, lista2, lista3, lista4;
    String idioma, opc1, opc2, opc3, opc4;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        Bundle datos = getIntent().getExtras();
        idioma = datos.getString("idioma");

        listView = findViewById(R.id.listview);
        listView2 = findViewById(R.id.listview2);
        listView3 = findViewById(R.id.listview3);
        listView4 = findViewById(R.id.listview4);

        opc1 = getResources().getString(R.string.ajustes1);
        opc2 = getResources().getString(R.string.ajustes2);
        opc3 = getResources().getString(R.string.ajustes3);
        opc4 = getResources().getString(R.string.ajustes4);

        System.out.println("TEXTOOOOOO" + opc1);
        System.out.println("TEXTOOOOOO" + opc2);
        System.out.println("TEXTOOOOOO" + opc3);
        System.out.println("TEXTOOOOOO" + opc4);

        lista1 = new ArrayList<String>();
        lista1.add(opc1);

        AjustesAdapter myAdapter = new AjustesAdapter(this, R.layout.list_item, lista1);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(ajustesActivity.this, "Has pulsado: "+ opc1, Toast.LENGTH_LONG).show();
            }
        });

        lista2 = new ArrayList<String>();
        lista2.add(opc2);

        AjustesAdapter myAdapter2 = new AjustesAdapter(this, R.layout.list_item, lista2);
        listView2.setAdapter(myAdapter2);

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent inicio = new Intent(getApplicationContext(), idiomasActivity.class);
                startActivity(inicio);
                finish();
            }
        });

        lista3 = new ArrayList<String>();
        lista3.add(opc3);

        AjustesAdapter myAdapter3 = new AjustesAdapter(this, R.layout.list_item, lista3);
        listView3.setAdapter(myAdapter3);

        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(ajustesActivity.this, "Has pulsado: "+ opc3, Toast.LENGTH_LONG).show();
            }
        });

        lista4 = new ArrayList<String>();
        lista4.add(opc4);

        AjustesAdapter myAdapter4 = new AjustesAdapter(this, R.layout.list_item, lista4);
        listView4.setAdapter(myAdapter4);

        listView4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(ajustesActivity.this, "Has pulsado: "+ opc4, Toast.LENGTH_LONG).show();
            }
        });

        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewAjustes);
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

    @Override
    public void onBackPressed() {}

}