package com.example.tfg.categorias.tradiciones;

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
import com.example.tfg.adapters.listViewAdapter;
import com.example.tfg.ajustes.ajustesActivity;
import com.example.tfg.categorias.artesania.artesaniaActivity2;
import com.example.tfg.categorias.artesania.artesaniaActivity3;
import com.example.tfg.categorias.artesania.artesaniaActivity5;
import com.example.tfg.categorias.categoriasActivity;
import com.example.tfg.categorias.otros.otrosActivity;
import com.example.tfg.inicio.MainActivity;
import com.example.tfg.mapa.MapsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Objects;

public class tradicionesActivity2 extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener {

    BottomNavigationView bottomNavigationView;
    String idioma, categoria;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tradiciones2);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        myToolbar.setTitleTextColor(R.color.white);

        Bundle extra = getIntent().getExtras();
        idioma = extra.getString("idioma");
        categoria = extra.getString("categoria");

        ListView listView = findViewById(R.id.listviewtrad1);
        ListView listView2 = findViewById(R.id.listviewtrad2);
        ListView listView3 = findViewById(R.id.listviewtrad3);
        ListView listView4 = findViewById(R.id.listviewtrad4);

        String opc1 = "La Moza de Ánimas";
        String opc2 = "Tradición 2";
        String opc3 = "Tradición 3";
        String opc4 = "Tradición 4";

        ArrayList<String> lista1 = new ArrayList<>();
        lista1.add(opc1);

        listViewAdapter myAdapter = new listViewAdapter(this, R.layout.list_item, lista1);

        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            Toast.makeText(tradicionesActivity2.this, "Has pulsado: "+ opc1, Toast.LENGTH_LONG).show();
            /*Intent rest = new Intent(getApplicationContext(), dondeComerActivity.class);
            rest.putExtra("idioma", idioma);
            startActivity(rest);
            finish();*/

        });

        ArrayList<String> lista2 = new ArrayList<>();
        lista2.add(opc2);

        listViewAdapter myAdapter2 = new listViewAdapter(this, R.layout.list_item, lista2);
        listView2.setAdapter(myAdapter2);

        listView2.setOnItemClickListener((adapterView, view, position, id) -> {
            Toast.makeText(tradicionesActivity2.this, "Has pulsado: "+ opc2, Toast.LENGTH_LONG).show();
            /*Intent rest = new Intent(getApplicationContext(), dondeComerActivity.class);
            rest.putExtra("idioma", idioma);
            startActivity(rest);
            finish();*/
        });

        ArrayList<String> lista3 = new ArrayList<>();
        lista3.add(opc3);

        listViewAdapter myAdapter3 = new listViewAdapter(this, R.layout.list_item, lista3);
        listView3.setAdapter(myAdapter3);

        listView3.setOnItemClickListener((adapterView, view, position, id) -> {
            Toast.makeText(tradicionesActivity2.this, "Has pulsado: "+ opc3, Toast.LENGTH_LONG).show();
            /*Intent rest = new Intent(getApplicationContext(), dondeComerActivity.class);
            rest.putExtra("idioma", idioma);
            startActivity(rest);
            finish();*/
        });

        ArrayList<String> lista4 = new ArrayList<>();
        lista4
                .add(opc4);

        listViewAdapter myAdapter4 = new listViewAdapter(this, R.layout.list_item, lista4);
        listView4.setAdapter(myAdapter4);

        listView4.setOnItemClickListener((adapterView, view, position, id) -> {
            Toast.makeText(tradicionesActivity2.this, "Has pulsado: "+ opc4, Toast.LENGTH_LONG).show();
            /*Intent rest = new Intent(getApplicationContext(), dondeComerActivity.class);
            rest.putExtra("idioma", idioma);
            startActivity(rest);
            finish();*/
        });

        //BOTON SIGUIENTE y ATRAS

        Button atrasBtn = findViewById(R.id.tradatras2);
        atrasBtn.setOnClickListener(this);

        Button atrasBtn2 = findViewById(R.id.tradAtras2);
        atrasBtn.setOnClickListener(this);

        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewTrad2);
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

        switch (btn.getId()){

            case R.id.tradatras2:
                Intent atras = new Intent(this, tradicionesActivity.class);
                atras.putExtra("idioma", idioma);
                startActivity(atras);
                break;

            case R.id.tradAtras2:
                Intent atras2 = new Intent(this, categoriasActivity.class);
                atras2.putExtra("idioma", idioma);
                startActivity(atras2);
                break;

        }
    }
}