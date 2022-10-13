package com.example.tfg.categorias.otros;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tfg.R;
import com.example.tfg.ajustes.ajustesActivity;
import com.example.tfg.listViewAdapter;
import com.example.tfg.categorias.categoriasActivity;
import com.example.tfg.inicio.MainActivity;
import com.example.tfg.mapa.MapsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class otrosActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    String idioma, opc1, opc2, opc3, opc4, opc5, opc6, categoria;
    private ArrayList<String> lista1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otros);

        Bundle extra = getIntent().getExtras();
        idioma = extra.getString("idioma");
        categoria = "otros";

        /*GestorDB dbHelper = new GestorDB(getApplicationContext());

        String [] datos = dbHelper.obtenerDatosInterfaz(idioma, "interfaz1", categoria);

        TextView text1 = findViewById(R.id.otrostext);
        text1.setText(datos[0]);*/

        ListView listView = findViewById(R.id.listviewOtros);

        lista1 = new ArrayList<>();
        lista1.add("Cepeda");
        lista1.add("Herguijuela de la Sierra");
        lista1.add("Mogarraz");
        lista1.add("Madroñal");
        lista1.add("Miranda del Castañar");
        lista1.add("San Esteban de la Sierra ");
        lista1.add("San Martin del Castañar ");
        lista1.add("Sequeros");
        lista1.add("Sotoserrano");


        listViewAdapter myAdapter = new listViewAdapter(this, R.layout.list_item, lista1);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(otrosActivity.this, "Has pulsado: "+ lista1.get(position).toString(), Toast.LENGTH_LONG).show();
            }
        });

        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewOtros);
        bottomNavigationView.setSelectedItemId(R.id.navigation_categoria);
        bottomNavigationView.setOnItemSelectedListener(this);

    }

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

    @Override
    public void onBackPressed() {}

}