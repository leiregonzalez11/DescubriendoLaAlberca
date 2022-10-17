package com.example.tfg.ajustes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tfg.categorias.otros.otrosActivity;
import com.example.tfg.inicio.MainActivity;
import com.example.tfg.R;
import com.example.tfg.categorias.categoriasActivity;
import com.example.tfg.listViewAdapter;
import com.example.tfg.mapa.MapsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class dondeComerActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    ArrayList lista1;
    String idioma;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donde_comer);

        Bundle datos = getIntent().getExtras();
        idioma = datos.getString("idioma");

        ListView listView = findViewById(R.id.listviewComer);

        lista1 = new ArrayList<>();
        lista1.add("Bar El Porrón");
        lista1.add("Bar El Rincón de Lola");
        lista1.add("Bar La Peña");
        lista1.add("Bar Marcos");
        lista1.add("Bar La Balsá");
        lista1.add("Bar La Nogal");
        lista1.add("El Balcón de la Plaza");
        lista1.add("La Barrera");
        lista1.add("Restaurante La Catedral");
        lista1.add("Restaurante La Taberna");
        lista1.add("Restaurante Ibéricos de la Alberca Doña Consuelo");
        lista1.add("Restaurante El Soportal");
        lista1.add("Restaurante El Encuentro");
        lista1.add("¡Oh! Espacio del Jamón");
        lista1.add("Mesón La Colmena");
        lista1.add("Restaurante La Cantina de Elías");
        lista1.add("La Parrilla");
        lista1.add("Bar Restaurante 1830");
        lista1.add("Tetería Singular");


        listViewAdapter myAdapter = new listViewAdapter(this, R.layout.list_item, lista1);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener((adapterView, view, position, id) -> Toast.makeText(dondeComerActivity.this, "Has pulsado: "+ lista1.get(position), Toast.LENGTH_LONG).show());



        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewdondeComer);
        bottomNavigationView.setSelectedItemId(R.id.navigation_ajustes);
        bottomNavigationView.setOnItemSelectedListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
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
    public void onBackPressed() {
    }
}