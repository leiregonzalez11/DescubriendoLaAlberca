package com.example.tfg.anterior.categorias.arquitectura;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.adapters.SliderAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.Objects;

public class ArquitecturaActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener {

    BottomNavigationView bottomNavigationView;
    String idioma = "";
    String categoria = "";

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arquitectura);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        myToolbar.setTitleTextColor(R.color.white);

        Bundle extra = getIntent().getExtras();
        idioma = extra.getString("idioma");
        categoria = "arquitectura";

        GestorDB dbHelper = new GestorDB(getApplicationContext());

        String [] datos = dbHelper.obtenerDescrInterfaz(idioma, "inicio", categoria, 2);

        TextView interfaz1 = findViewById(R.id.arqui11);
        interfaz1.setText(datos[0] + Html.fromHtml("<br>"));

        TextView interfaz2 = findViewById(R.id.arqui12);
        interfaz2.setText(datos[1] + Html.fromHtml("<br>"));

        //SLIDER
        SliderView sliderView = findViewById(R.id.imageSliderArqui1);
        int[] images = new int[]{R.drawable.laalberca1, R.drawable.laalberca2, R.drawable.laalberca3, R.drawable.laalberca4};
        SliderAdapter adapter = new SliderAdapter(images);
        sliderView.setSliderAdapter(adapter);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.SLIDE);
        sliderView.startAutoCycle();

        //BOTON SIGUIENTE
        Button sigBtn = findViewById(R.id.arquisiguiente1);
        sigBtn.setOnClickListener(this);

        Button finBtn = findViewById(R.id.arquiAtras1);
        finBtn.setOnClickListener(this);

        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewArqui1);
        bottomNavigationView.setSelectedItemId(R.id.navigation_categoria);
        bottomNavigationView.setOnItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {}

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.navigation_inicio:
                Intent inicio = new Intent(this, null);
                startActivity(inicio);
                finish();
                return true;

            case R.id.navigation_mapa:

            case R.id.navigation_categoria:

            case R.id.navigation_ajustes:
                Intent mapa = new Intent(this, null);
                mapa.putExtra("idioma",idioma);
                startActivity(mapa);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        //Cuando se presione el botón, realiza una acción aquí

        Button btn = (Button) view;

        switch (btn.getId()){
            case R.id.arquisiguiente1:
                Intent arqui2 = new Intent(this, arquitecturaActivity2.class);
                arqui2.putExtra("idioma", idioma);
                arqui2.putExtra("categoria", categoria);
                startActivity(arqui2);
                finish();
                break;

            case R.id.arquiAtras1:
                Intent mapa = new Intent(this, null);
                mapa.putExtra("idioma",idioma);
                startActivity(mapa);
                finish();
                break;

        }
    }
}