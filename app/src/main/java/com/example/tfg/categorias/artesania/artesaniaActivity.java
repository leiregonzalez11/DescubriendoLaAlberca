package com.example.tfg.categorias.artesania;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.ajustes.ajustesActivity;
import com.example.tfg.categorias.categoriasActivity;
import com.example.tfg.inicio.MainActivity;
import com.example.tfg.adapters.SliderAdapter;
import com.example.tfg.mapa.MapsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.Objects;

public class artesaniaActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener {

    BottomNavigationView bottomNavigationView;
    String idioma, categoria;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artesania);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        myToolbar.setTitleTextColor(R.color.white);

        Bundle extra = getIntent().getExtras();
        idioma = extra.getString("idioma");
        categoria = "artesania";

        GestorDB dbHelper = new GestorDB(this);

        String [] datos = dbHelper.obtenerDescrInterfaz(idioma, "interfaz1", categoria, 3);

        TextView text1 = findViewById(R.id.arte11);
        TextView text2 = findViewById(R.id.arte12);
        TextView text3 = findViewById(R.id.arte13);

        text1.setText(datos[0] + Html.fromHtml("<br>"));
        text2.setText(datos[1] + Html.fromHtml("<br>"));
        text3.setText(datos[2] + Html.fromHtml("<br>"));

        //SLIDER
        SliderView sliderView = findViewById(R.id.imageSliderArte1);
        int[] images = new int[]{R.drawable.arte1, R.drawable.arte2, R.drawable.arte3};
        SliderAdapter adapterSlider = new SliderAdapter(images);
        sliderView.setSliderAdapter(adapterSlider);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.DROP);
        sliderView.setScrollTimeInSec(3);
        sliderView.startAutoCycle();

        //BOTON SIGUIENTE

        Button atrasBtn = findViewById(R.id.arteAtras1);
        atrasBtn.setOnClickListener(this);

        Button sigBtn = findViewById(R.id.arteSiguiente1);
        sigBtn.setOnClickListener(this);

        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewArte1);
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

        switch (btn.getId()) {

            case R.id.arteAtras1:
                Intent arteCat = new Intent(this, categoriasActivity.class);
                arteCat.putExtra("idioma", idioma);
                startActivity(arteCat);
                finish();
                break;

            case R.id.arteSiguiente1:
                Intent arte2 = new Intent(this, artesaniaSelectorActivity.class);
                arte2.putExtra("idioma", idioma);
                arte2.putExtra("categoria", categoria);
                startActivity(arte2);
                finish();
                break;


        }
    }

}