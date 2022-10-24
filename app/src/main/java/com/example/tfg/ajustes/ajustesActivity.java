package com.example.tfg.ajustes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tfg.inicio.MainActivity;
import com.example.tfg.R;
import com.example.tfg.categorias.categoriasActivity;
import com.example.tfg.listViewAdapter;
import com.example.tfg.mapa.MapsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Objects;

public class ajustesActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    String idioma, opc1, opc2, opc3, opc4, opc5, opc6;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        myToolbar.setTitleTextColor(R.color.white);

        Bundle datos = getIntent().getExtras();
        idioma = datos.getString("idioma");

        ListView listView = findViewById(R.id.listview);
        ListView listView2 = findViewById(R.id.listview2);
        ListView listView3 = findViewById(R.id.listview3);
        ListView listView4 = findViewById(R.id.listview4);
        ListView listView5 = findViewById(R.id.listview5);
        ListView listView6 = findViewById(R.id.listview6);

        opc1 = getResources().getString(R.string.ajustes1);
        opc2 = getResources().getString(R.string.ajustes2);
        opc3 = getResources().getString(R.string.ajustes3);
        opc4 = getResources().getString(R.string.ajustes4);
        opc5 = getResources().getString(R.string.contacto);
        opc6 = getResources().getString(R.string.ajustes6);

        ArrayList<String> lista1 = new ArrayList<>();
        lista1.add(opc1);

        listViewAdapter myAdapter = new listViewAdapter(this, R.layout.list_item, lista1);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            //Toast.makeText(ajustesActivity.this, "Has pulsado: "+ opc1, Toast.LENGTH_LONG).show());
            Intent comoLlegar = new Intent(getApplicationContext(), comoLlegarActivity.class);
            comoLlegar.putExtra("idioma", idioma);
            startActivity(comoLlegar);
                finish();
        });

        ArrayList<String> lista2 = new ArrayList<>();
        lista2.add(opc2);

        listViewAdapter myAdapter2 = new listViewAdapter(this, R.layout.list_item, lista2);
        listView2.setAdapter(myAdapter2);

        listView2.setOnItemClickListener((adapterView, view, position, id) -> {
            Intent inicio = new Intent(getApplicationContext(), idiomasActivity.class);
            startActivity(inicio);
            finish();
        });

        ArrayList<String> lista3 = new ArrayList<>();
        lista3.add(opc3);

        listViewAdapter myAdapter3 = new listViewAdapter(this, R.layout.list_item, lista3);
        listView3.setAdapter(myAdapter3);

        listView3.setOnItemClickListener((adapterView, view, position, id) -> {
                //Toast.makeText(ajustesActivity.this, "Has pulsado: "+ opc3, Toast.LENGTH_LONG).show());
                Intent rest = new Intent(getApplicationContext(), dondeComerActivity.class);
                rest.putExtra("idioma", idioma);
                startActivity(rest);
                finish();
        });

        ArrayList<String> lista4 = new ArrayList<>();
        lista4.add(opc4);

        listViewAdapter myAdapter4 = new listViewAdapter(this, R.layout.list_item, lista4);
        listView4.setAdapter(myAdapter4);

        listView4.setOnItemClickListener((adapterView, view, position, id) -> {
                    //Toast.makeText(ajustesActivity.this, "Has pulsado: "+ opc4, Toast.LENGTH_LONG).show());
                    Intent aloj = new Intent(getApplicationContext(), dondeDormirActivity.class);
                    aloj.putExtra("idioma", idioma);
                    startActivity(aloj);
                    finish();
        });

        ArrayList<String> lista5 = new ArrayList<>();
        lista5.add(opc5);

        listViewAdapter myAdapter5 = new listViewAdapter(this, R.layout.list_item, lista5);
        listView5.setAdapter(myAdapter5);

        listView5.setOnItemClickListener((adapterView, view, position, id) -> {
            //Toast.makeText(ajustesActivity.this, "Has pulsado: "+ opc5, Toast.LENGTH_LONG).show();
            Intent contacto = new Intent(getApplicationContext(), contactoActivity.class);
            contacto.putExtra("idioma", idioma);
            startActivity(contacto);
            finish();
        });

        ArrayList<String> lista6 = new ArrayList<>();
        lista6.add(opc6);

        listViewAdapter myAdapter6 = new listViewAdapter(this, R.layout.list_item, lista6);
        listView6.setAdapter(myAdapter6);

        listView6.setOnItemClickListener((adapterView, view, position, id) -> {
            Toast.makeText(ajustesActivity.this, "Has pulsado: "+ opc6, Toast.LENGTH_LONG).show();
            /*Intent comercio = new Intent(getApplicationContext(), comercioActivity.class);
            comercio.putExtra("idioma", idioma);
            startActivity(comercio);
            finish();*/
        });


        //MENU
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationViewAjustes);
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