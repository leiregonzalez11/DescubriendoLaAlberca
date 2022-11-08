package com.example.tfg.anterior.inicio;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageSwitcher;
import android.widget.TextView;

import com.example.tfg.ExitFragment;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.adapters.SliderAdapter;
import com.example.tfg.anterior.ajustes.ajustesActivity;
import com.example.tfg.anterior.categorias.categoriasActivity;
import com.example.tfg.anterior.mapa.MapsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.Objects;
import java.util.Timer;


public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    public BottomNavigationView bottomNavigationView;
    String idioma;

    private ImageSwitcher imageSwitcher;

    private int[] gallery = { R.drawable.laalberca2, R.drawable.laalberca3, R.drawable.laalberca4};

    private int position;

    private static final Integer DURATION = 2500;

    private Timer timer = null;


    //String tour;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        myToolbar.setTitleTextColor(R.color.white);

        /*Bundle datos = getIntent().getExtras();
        tour = datos.getString("tour");*/

        TextView texto = findViewById(R.id.bienvenidatext);

        if (texto.getText().toString().contains("¡Bienvenid@s")){
            idioma = "es";
        } else if (texto.getText().toString().contains("Ongi")){
            idioma = "eu";
        }else if (texto.getText().toString().contains("Welcome")){
            idioma="en";
        }else if (texto.getText().toString().contains("Benvinguts")){
            idioma="ca";
        }

        /*Cargamos la BD...*/
        GestorDB dbHelper =  new GestorDB(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //SLIDER
        SliderView sliderView = findViewById(R.id.imageSlider);
        int[] images = new int[]{R.drawable.laalberca1, R.drawable.laalberca2, R.drawable.laalberca3, R.drawable.laalberca4};
        SliderAdapter adapter = new SliderAdapter(images);
        sliderView.setSliderAdapter(adapter);
        sliderView.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.SCALE_DOWN);
        sliderView.setScrollTimeInSec(3);
        sliderView.startAutoCycle();

        //MENU
        bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_inicio);

        /*if (tour.equals("tour")){
            DialogFragment tourSiFragment = new tourSiFragment();
            tourSiFragment.setCancelable(false);
            tourSiFragment.show(getSupportFragmentManager(),"toursi_dialog");
        }*/

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.navigation_inicio:
                return true;

            case R.id.navigation_mapa:
                finish();
                Intent mapa = new Intent(this, MapsActivity.class);
                mapa.putExtra("idioma", idioma);
                startActivity(mapa);
                return true;

            case R.id.navigation_categoria:
                finish();
                Intent categorias = new Intent(this, categoriasActivity.class);
                categorias.putExtra("idioma", idioma);
                startActivity(categorias);
                return true;

            case R.id.navigation_ajustes:
                finish();
                Intent ajustes = new Intent(this, ajustesActivity.class);
                ajustes.putExtra("idioma", idioma);
                startActivity(ajustes);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed(){
        DialogFragment tourSiFragment = new ExitFragment();
        tourSiFragment.setCancelable(false);
        tourSiFragment.show(getSupportFragmentManager(),"exit_fragment");
    }

}