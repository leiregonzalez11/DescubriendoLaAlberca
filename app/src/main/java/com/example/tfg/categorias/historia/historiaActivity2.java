package com.example.tfg.categorias.historia;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.tfg.R;
import com.example.tfg.ajustes.ajustesActivity;
import com.example.tfg.categorias.categoriasActivity;
import com.example.tfg.inicio.MainActivity;
import com.example.tfg.mapa.MapsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Objects;

public class historiaActivity2 extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener {

    BottomNavigationView bottomNavigationView;
    String idioma, categoria;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia2);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        myToolbar.setTitleTextColor(R.color.white);

        Bundle extra = getIntent().getExtras();
        idioma = extra.getString("idioma");
        categoria = extra.getString("categoria");

        /*GestorDB dbHelper = new GestorDB(getApplicationContext());

        String [] datos = dbHelper.obtenerDescrInterfaz(idioma, "interfaz1", categoria);

        TextView text1 = findViewById(R.id.arte11);
        text1.setText(datos[0]);

        TextView text2 = findViewById(R.id.arte12);
        text2.setText(datos[1]);

        TextView text3 = findViewById(R.id.arte13);
        text3.setText(datos[2]);

        //SLIDER
        SliderView sliderView = findViewById(R.id.imageSliderHist2);
        int[] images = new int[]{R.drawable.laalberca1, R.drawable.laalberca2, R.drawable.laalberca3, R.drawable.laalberca4};
        SliderAdapter adapter = new SliderAdapter(images);
        sliderView.setSliderAdapter(adapter);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.SLIDE);
        sliderView.startAutoCycle();

        //BOTON SIGUIENTE y ATRAS

        Button atrasBtn = findViewById(R.id.arteatras2);
        atrasBtn.setOnClickListener(this);

        Button siguienteBtn = findViewById(R.id.artesiguiente2);
        siguienteBtn.setOnClickListener(this);*/

        Button atrasBtn2 = findViewById(R.id.histAtras2);
        atrasBtn2.setOnClickListener(this);

        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewHist2);
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

            case R.id.histAtras2:
                Intent atras2 = new Intent(this, categoriasActivity.class);
                atras2.putExtra("idioma", idioma);
                startActivity(atras2);
                finish();
                break;

            /*case R.id.arteatras2:
                Intent atras = new Intent(this, artesaniaActivity.class);
                atras.putExtra("idioma", idioma);
                startActivity(atras);
                break;

            case R.id.artesiguiente2:
                Intent arte3 = new Intent(this, artesaniaActivity3.class);
                arte3.putExtra("idioma", idioma);
                arte3.putExtra("categoria", categoria);
                startActivity(arte3);
                break;*/
        }
    }
}