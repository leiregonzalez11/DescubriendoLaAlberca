package com.example.tfg.anterior.ajustes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.example.tfg.adapters.tabAdapterComer;
import com.example.tfg.anterior.inicio.MainActivity;
import com.example.tfg.R;
import com.example.tfg.anterior.categorias.categoriasActivity;
import com.example.tfg.anterior.mapa.MapsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;


public class dondeComerActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener {

    BottomNavigationView bottomNavigationView;
    String idioma;

    @SuppressLint({"ResourceAsColor", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donde_comer);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        myToolbar.setTitleTextColor(R.color.white);

        Button btn = findViewById(R.id.btnFiltrosComer);
        btn.setOnClickListener(view -> {
            //Creating the instance of PopupMenu
            PopupMenu popup = new PopupMenu(dondeComerActivity.this, btn);
            //Inflating the Popup using xml file
            popup.getMenuInflater().inflate(R.menu.ajustes_menu, popup.getMenu());

            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(item -> {
                //Toast.makeText(dondeComerActivity.this,"You Clicked : filtros", Toast.LENGTH_SHORT).show();
                return true;
            });
            popup.show();
        });

        Bundle datos = getIntent().getExtras();
        idioma = datos.getString("idioma");

        String text1 = getResources().getString(R.string.bares);
        String text2 = getResources().getString(R.string.rest);

        ViewPager2 viewPager = findViewById(R.id.viewPagerComer);

        TabLayout tabLayout  = findViewById(R.id.tab_layoutComer);
        tabLayout.addTab(tabLayout.newTab().setText(text1.toUpperCase()));
        tabLayout.addTab(tabLayout.newTab().setText(text2.toUpperCase()));
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        tabAdapterComer myadapter = new tabAdapterComer(getSupportFragmentManager(), getLifecycle());

        viewPager.setAdapter(myadapter);
        //viewPager.setCurrentItem(tabLayout.getSelectedTabPosition());

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                assert tab.parent != null;
                viewPager.setCurrentItem(tab.parent.getSelectedTabPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //viewPager.setCurrentItem(tab.getPosition());

            }
        });

        //BOTON ATRAS

        ImageButton sigBtn = findViewById(R.id.btnAtrasComer);
        sigBtn.setOnClickListener(this);

        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewdondeComer);
        bottomNavigationView.setSelectedItemId(R.id.navigation_ajustes);
        bottomNavigationView.setOnItemSelectedListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navigation_inicio:
                Intent inicio = new Intent(this, MainActivity.class);
                startActivity(inicio);
                return true;

            case R.id.navigation_mapa:
                Intent mapa = new Intent(this, MapsActivity.class);
                mapa.putExtra("idioma", idioma);
                startActivity(mapa);
                return true;

            case R.id.navigation_categoria:
                Intent categorias = new Intent(this, categoriasActivity.class);
                categorias.putExtra("idioma", idioma);
                startActivity(categorias);
                return true;

            case R.id.navigation_ajustes:
                Intent ajustes = new Intent(this, ajustesActivity.class);
                ajustes.putExtra("idioma", idioma);
                startActivity(ajustes);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onClick(View view) {
        //Cuando se presione el botón, realiza una acción aquí

        ImageButton btn = (ImageButton) view;

        if (btn.getId() == R.id.btnAtrasComer) {
            Intent arte2 = new Intent(this, ajustesActivity.class);
            arte2.putExtra("idioma", idioma);
            startActivity(arte2);
            finish();
        }
    }
}