package com.example.tfg.categorias.gastronomia;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import com.example.tfg.mapa.MapsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class gastronomiaActivity2 extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener {

    BottomNavigationView bottomNavigationView;
    String idioma, categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastronomia2);

        Bundle extra = getIntent().getExtras();
        idioma = extra.getString("idioma");
        categoria = extra.getString("categoria");

        /*GestorDB dbHelper = new GestorDB(getApplicationContext());

        String [] datos = dbHelper.obtenerDescrInterfaz(idioma, "interfaz2", categoria);

        TextView text1 = findViewById(R.id.gastro11);
        text1.setText(datos[0]);

        TextView text2 = findViewById(R.id.gastro12);
        text2.setText(datos[1]);

        TextView text3 = findViewById(R.id.gastro13);
        text3.setText(datos[2]);

        //SLIDER
        SliderView sliderView = findViewById(R.id.imageSliderGastro2);
        int[] images = new int[]{R.drawable.laalberca1, R.drawable.laalberca2, R.drawable.laalberca3, R.drawable.laalberca4};
        SliderAdapter adapter = new SliderAdapter(images);
        sliderView.setSliderAdapter(adapter);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.SLIDE);
        sliderView.startAutoCycle();

        //BOTON SIGUIENTE y ATRAS

        Button atrasBtn = findViewById(R.id.gastroatras2);
        atrasBtn.setOnClickListener(this);

        Button siguienteBtn = findViewById(R.id.gastrosiguiente2);
        siguienteBtn.setOnClickListener(this);*/

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

         /*Button btn = (Button) view;

        switch (btn.getId()){

            case R.id.gastroatras2:
                Intent atras = new Intent(this, gastronomiaActivity.class);
                atras.putExtra("idioma", idioma);
                startActivity(atras);
                break;

            case R.id.gastrosiguiente2:
                Intent gastro3 = new Intent(this, gastronomiaActivity3.class);
                gastro3.putExtra("idioma", idioma);
                gastro3.putExtra("categoria", categoria);
                startActivity(gastro);
                break;
        }*/
    }
}