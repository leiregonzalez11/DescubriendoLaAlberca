package com.example.tfg.categorias.otros;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import java.util.Objects;

public class otrosActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener {

    BottomNavigationView bottomNavigationView;
    String idioma, categoria;
    private ArrayList<String> lista1;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otros);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        myToolbar.setTitleTextColor(R.color.white);

        Bundle extra = getIntent().getExtras();
        idioma = extra.getString("idioma");
        categoria = "otros";

        /*GestorDB dbHelper = new GestorDB(getApplicationContext());

        String [] datos = dbHelper.obtenerDescrInterfaz(idioma, "interfaz1", categoria);

        TextView text1 = findViewById(R.id.otrostext);
        text1.setText(datos[0]);*/

        ListView listView = findViewById(R.id.listviewOtros);

        lista1 = new ArrayList<>();
        lista1.add("Cepeda");
        lista1.add("Garcibuey");
        lista1.add("Herguijuela de la Sierra");
        lista1.add("Las Casas del Conde");
        lista1.add("Madroñal");
        lista1.add("Miranda del Castañar");
        lista1.add("Mogarraz");
        lista1.add("Monforte de la Sierra ");
        lista1.add("San Martin del Castañar ");
        lista1.add("San Miguel de Robledo");
        lista1.add("Sequeros");
        lista1.add("Sotoserrano");
        lista1.add("Valero");
        lista1.add("Villanueva del Conde");


        listViewAdapter myAdapter = new listViewAdapter(this, R.layout.list_item, lista1);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener((adapterView, view, position, id) -> Toast.makeText(otrosActivity.this, "Has pulsado: "+ lista1.get(position), Toast.LENGTH_LONG).show());

        //Boton Atras
        Button btnAtras = findViewById(R.id.otrosAtras1);
        btnAtras.setOnClickListener(this);

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

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        //Cuando se presione el botón, realiza una acción aquí

        Button btn = (Button) view;

        if (btn.getId() == R.id.otrosAtras1) {
            Intent arteCat = new Intent(this, categoriasActivity.class);
            arteCat.putExtra("idioma", idioma);
            startActivity(arteCat);
            finish();
        }
    }

}