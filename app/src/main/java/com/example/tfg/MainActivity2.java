package com.example.tfg;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.tfg.navigationmenu.FragmentAjustes;
import com.example.tfg.navigationmenu.FragmentCategorias;
import com.example.tfg.navigationmenu.FragmentInicio;
import com.example.tfg.navigationmenu.FragmentMapa;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity2 extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    public BottomNavigationView bottomNavigationView;
    FragmentInicio inicio;
    //String tour;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        inicio = new FragmentInicio();
        loadFragment(inicio);

        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewPrueba);
        bottomNavigationView.setOnItemSelectedListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.navigation_inicio:
                fragment = new FragmentInicio();
                break;

            case R.id.navigation_mapa:
                fragment = new FragmentMapa();
                break;

            case R.id.navigation_categoria:
                fragment = new FragmentCategorias();
                break;

            case R.id.navigation_ajustes:
                fragment = new FragmentAjustes();
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
        DialogFragment tourSiFragment = new ExitFragment();
        tourSiFragment.setCancelable(false);
        tourSiFragment.show(getSupportFragmentManager(),"exit_fragment");
    }

}