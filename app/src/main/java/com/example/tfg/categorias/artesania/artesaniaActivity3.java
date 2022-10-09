package com.example.tfg.categorias.artesania;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tfg.R;
import com.example.tfg.ajustes.ajustesActivity;
import com.example.tfg.categorias.arquitectura.arquitecturaActivity2;
import com.example.tfg.categorias.arquitectura.arquitecturaActivity4;
import com.example.tfg.categorias.categoriasActivity;
import com.example.tfg.inicio.MainActivity;
import com.example.tfg.inicio.SliderAdapter;
import com.example.tfg.mapa.MapsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class artesaniaActivity3 extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, OnClickListener, AdapterView.OnItemSelectedListener{

    BottomNavigationView bottomNavigationView;
    String idioma, categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artesania3);

        //Spinner

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        String [] trajes = getResources().getStringArray(R.array.trajes_serranos);
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.dropdownitem, trajes));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // vacio

            }
        });

        Bundle extra = getIntent().getExtras();
        idioma = extra.getString("idioma");
        categoria = extra.getString("categoria");

        /*GestorDB dbHelper = new GestorDB(getApplicationContext());

        String [] datos = dbHelper.obtenerDatosInterfazSencilla(idioma, "interfaz3", categoria);

        TextView text1 = findViewById(R.id.arte31);
        text1.setText(datos[0]);

        TextView text2 = findViewById(R.id.arte32);
        text2.setText(datos[1]);

        TextView text3 = findViewById(R.id.arte33);
        text3.setText(datos[2]);*/

        //BOTON SIGUIENTE y ATRAS

        Button atrasBtn = findViewById(R.id.arteatras3);
        atrasBtn.setOnClickListener(this);

        Button siguienteBtn = findViewById(R.id.artesiguiente3);
        siguienteBtn.setOnClickListener(this);

        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewArte3);
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

            case R.id.arteatras3:
                Intent atras = new Intent(this, artesaniaActivity2.class);
                atras.putExtra("idioma", idioma);
                atras.putExtra("categoria", categoria);
                startActivity(atras);
                break;

            case R.id.artesiguiente3:
                Intent arte4 = new Intent(this, artesaniaActivity4.class);
                arte4.putExtra("idioma", idioma);
                arte4.putExtra("categoria", categoria);
                startActivity(arte4);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}