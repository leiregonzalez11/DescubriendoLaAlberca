package com.example.tfg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private  int images[];
    private SliderAdapter adapter;
    private SliderView sliderView;
    BottomNavigationView bottomNavigationView;
    String tour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Bundle datos = getIntent().getExtras();
        tour = datos.getString("tour");*/

        //SLIDER
        sliderView=findViewById(R.id.imageSlider);
        images= new int[]{R.drawable.laalberca1, R.drawable.laalberca2, R.drawable.laalberca3, R.drawable.laalberca4};
        adapter=new SliderAdapter(images);
        sliderView.setSliderAdapter(adapter);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.SLIDE);
        sliderView.startAutoCycle();

        //MENU
        bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_inicio);

        /*if (tour.equals("tour")){
            DialogFragment tourSiFragment = new tourSiFragment();
            tourSiFragment.setCancelable(false);
            tourSiFragment.show(getSupportFragmentManager(),"toursi_dialog");
        }*/

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.navigation_inicio:
                return true;

            case R.id.navigation_mapa:
                Intent mapa = new Intent(this, MapsActivity.class);
                startActivity(mapa);
                finish();
                return true;

            case R.id.navigation_categoria:
                Intent categorias = new Intent(this, categoriasActivity.class);
                startActivity(categorias);
                finish();
                return true;

            case R.id.navigation_ajustes:
                Intent intent = new Intent(this, ajustesActivity.class);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}