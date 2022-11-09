package com.example.tfg;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.tfg.dialogFragments.ExitFragment;
import com.example.tfg.navigationmenu.Ajustes;
import com.example.tfg.navigationmenu.Categorias;
import com.example.tfg.navigationmenu.Inicio;
import com.example.tfg.navigationmenu.Maps;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    public BottomNavigationView bottomNavigationView;
    String fragment_id;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.toolbarPrueba);
        setSupportActionBar(myToolbar);
        myToolbar.setNavigationIcon(null);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        myToolbar.setTitleTextColor(R.color.white);

        fragment_id = "inicio";

        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewPrueba);
        bottomNavigationView.setOnItemSelectedListener(this);
        loadFragment(new Inicio());

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.navigation_inicio:
                fragment = new Inicio();
                fragment_id = "inicio";
                break;

            case R.id.navigation_mapa:
                fragment = new Maps();
                fragment_id = "maps";
                break;

            case R.id.navigation_categoria:
                fragment = new Categorias();
                fragment_id = "categorias";
                break;

            case R.id.navigation_ajustes:
                fragment = new Ajustes();
                fragment_id = "ajustes";
                break;

        }

        if (fragment != null) {
            loadFragment(fragment);
        }
        return true;
    }

    void loadFragment(Fragment fragment) {
        //to attach fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout, fragment).commit();
    }

    @Override
    public void onBackPressed(){
        if (fragment_id.equals("inicio")){
            DialogFragment exitFragment = new ExitFragment();
            exitFragment.setCancelable(false);
            exitFragment.show(getSupportFragmentManager(),"exit_fragment");
        }
    }

}