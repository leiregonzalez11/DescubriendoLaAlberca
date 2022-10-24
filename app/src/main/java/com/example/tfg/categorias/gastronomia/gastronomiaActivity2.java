package com.example.tfg.categorias.gastronomia;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.ajustes.ajustesActivity;
import com.example.tfg.categorias.arquitectura.arquitecturaActivity2;
import com.example.tfg.categorias.artesania.artesaniaActivity;
import com.example.tfg.categorias.artesania.artesaniaActivity2;
import com.example.tfg.categorias.artesania.artesaniaActivity3;
import com.example.tfg.categorias.categoriasActivity;
import com.example.tfg.inicio.MainActivity;
import com.example.tfg.inicio.SliderAdapter;
import com.example.tfg.listViewAdapter;
import com.example.tfg.mapa.MapsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Objects;

public class gastronomiaActivity2 extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener {

    BottomNavigationView bottomNavigationView;
    String idioma, categoria, opc1, opc2;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastronomia2);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        myToolbar.setTitleTextColor(R.color.white);

        Bundle extra = getIntent().getExtras();
        idioma = extra.getString("idioma");
        categoria = extra.getString("categoria");

        ListView listView = findViewById(R.id.listviewGastro1);
        ListView listView2 = findViewById(R.id.listviewGastro2);

        opc1 = "Las Turroneras";
        opc2 = "Recetas típicas";

        System.out.println("TEXTOOOOOO" + opc1);
        System.out.println("TEXTOOOOOO" + opc2);

        ArrayList<String> lista1 = new ArrayList<>();
        lista1.add(opc1);

        listViewAdapter myAdapter = new listViewAdapter(this, R.layout.list_item, lista1);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Toast.makeText(gastronomiaActivity2.this, "Has pulsado: "+ opc1, Toast.LENGTH_LONG).show();
                Intent gastro3 = new Intent(getApplicationContext(), gastronomiaActivity3.class);
                gastro3.putExtra("idioma", idioma);
                gastro3.putExtra("categoria", categoria);
                startActivity(gastro3);
            }
        });

        ArrayList<String> lista2 = new ArrayList<>();
        lista2.add(opc2);

        listViewAdapter myAdapter2 = new listViewAdapter(this, R.layout.list_item, lista2);
        listView2.setAdapter(myAdapter2);

        listView2.setOnItemClickListener((adapterView, view, position, id) -> {
            //Toast.makeText(gastronomiaActivity2.this, "Has pulsado: "+ opc2, Toast.LENGTH_LONG).show();
            Intent gastro4 = new Intent(getApplicationContext(), gastronomiaActivity4.class);
            gastro4.putExtra("idioma", idioma);
            gastro4.putExtra("categoria", categoria);
            startActivity(gastro4);
        });

        //BOTON SIGUIENTE y ATRAS

        Button atrasBtn = findViewById(R.id.gastroatrass);
        atrasBtn.setOnClickListener(this);

        Button atrasBtn2 = findViewById(R.id.gastroAtras2);
        atrasBtn2.setOnClickListener(this);

        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewGastro2);
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

            case R.id.gastroatrass:
                Intent atras = new Intent(this, gastronomiaActivity.class);
                atras.putExtra("idioma", idioma);
                startActivity(atras);
                break;

            case R.id.gastroAtras2:
                Intent atras2 = new Intent(this, categoriasActivity.class);
                atras2.putExtra("idioma", idioma);
                startActivity(atras2);
                break;

        }
    }
}