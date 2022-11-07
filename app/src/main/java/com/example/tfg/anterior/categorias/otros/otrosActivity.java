package com.example.tfg.anterior.categorias.otros;

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
import com.example.tfg.anterior.ajustes.ajustesActivity;
import com.example.tfg.adapters.listViewAdapter;
import com.example.tfg.anterior.categorias.categoriasActivity;
import com.example.tfg.anterior.inicio.MainActivity;
import com.example.tfg.anterior.mapa.MapsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Objects;

public class otrosActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener {

    BottomNavigationView bottomNavigationView;
    String idioma, categoria, opc1, opc2, opc3, opc4;

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

        ListView listView = findViewById(R.id.listviewhurdes);
        ListView listView2 = findViewById(R.id.listviewpeñafrancia);
        ListView listView3 = findViewById(R.id.listviewpueblos);
        ListView listView4 = findViewById(R.id.listviewbatuecas);

        opc1 = "Las Hurdes";
        opc2 = "Peña de Francia";
        opc3 = "Pueblos de la Sierra";
        opc4 = "Valle de Las Batuecas";

        ArrayList<String> lista1 = new ArrayList<>();
        lista1.add(opc1);

        listViewAdapter myAdapter = new listViewAdapter(this, R.layout.list_bosque, lista1);

        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            Toast.makeText(otrosActivity.this, "Has pulsado: "+ opc1, Toast.LENGTH_LONG).show();
            /*Intent rest = new Intent(getApplicationContext(), dondeComerActivity.class);
            rest.putExtra("idioma", idioma);
            startActivity(rest);
            finish();*/

        });

        ArrayList<String> lista2 = new ArrayList<>();
        lista2.add(opc2);

        listViewAdapter myAdapter2 = new listViewAdapter(this, R.layout.list_monte, lista2);
        listView2.setAdapter(myAdapter2);

        listView2.setOnItemClickListener((adapterView, view, position, id) -> {
            Toast.makeText(otrosActivity.this, "Has pulsado: "+ opc2, Toast.LENGTH_LONG).show();
            /*Intent rest = new Intent(getApplicationContext(), dondeComerActivity.class);
            rest.putExtra("idioma", idioma);
            startActivity(rest);
            finish();*/
        });

        ArrayList<String> lista3 = new ArrayList<>();
        lista3.add(opc3);

        listViewAdapter myAdapter3 = new listViewAdapter(this, R.layout.list_pueblo, lista3);
        listView3.setAdapter(myAdapter3);

        listView3.setOnItemClickListener((adapterView, view, position, id) -> {
            Toast.makeText(otrosActivity.this, "Has pulsado: "+ opc3, Toast.LENGTH_LONG).show();
            /*Intent rest = new Intent(getApplicationContext(), dondeComerActivity.class);
            rest.putExtra("idioma", idioma);
            startActivity(rest);
            finish();*/
        });

        ArrayList<String> lista4 = new ArrayList<>();
        lista4
                .add(opc4);

        listViewAdapter myAdapter4 = new listViewAdapter(this, R.layout.list_bosque, lista4);
        listView4.setAdapter(myAdapter4);

        listView4.setOnItemClickListener((adapterView, view, position, id) -> {
            Toast.makeText(otrosActivity.this, "Has pulsado: "+ opc4, Toast.LENGTH_LONG).show();
            /*Intent rest = new Intent(getApplicationContext(), dondeComerActivity.class);
            rest.putExtra("idioma", idioma);
            startActivity(rest);
            finish();*/
        });

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