package com.example.tfg.ajustes.aloj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import com.example.tfg.R;
import com.example.tfg.ajustes.ajustesActivity;
import com.example.tfg.categorias.categoriasActivity;
import com.example.tfg.inicio.MainActivity;
import com.example.tfg.mapa.MapsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class alojamientoActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener {

    BottomNavigationView bottomNavigationView;
    String idioma, categoria, alojamiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alojamiento);

        Bundle extra = getIntent().getExtras();
        idioma = extra.getString("idioma");
        alojamiento = extra.getString("alojamiento");
        categoria = "alojamiento";

        /*GestorDB dbHelper = new GestorDB(getApplicationContext());

        String [] datos = dbHelper.obtenerDatosAloj(idioma, categoria, alojamiento);

        TextView text1 = findViewById(R.id.arte11);
        text1.setText(datos[0]);

        TextView text2 = findViewById(R.id.arte12);
        text2.setText(datos[1]);

        TextView text3 = findViewById(R.id.arte13);
        text3.setText(datos[2]);

        //BOTON ATRAS

        Button atrasBtn = findViewById(R.id.arteatras2);
        atrasBtn.setOnClickListener(this);

        Button siguienteBtn = findViewById(R.id.artesiguiente2);
        siguienteBtn.setOnClickListener(this);*/

        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewAloj);
        bottomNavigationView.setSelectedItemId(R.id.navigation_ajustes);
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

         /*Button btn = (Button) view;

        switch (btn.getId()){

            case R.id.alojatras:
                Intent atras = new Intent(this, artesaniaActivity.class);
                atras.putExtra("idioma", idioma);
                startActivity(atras);
                break;
        }*/
    }
}