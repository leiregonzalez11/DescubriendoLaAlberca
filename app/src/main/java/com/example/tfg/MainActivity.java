package com.example.tfg;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.tfg.navigationmenu.FragmentAjustes;
import com.example.tfg.navigationmenu.FragmentCategorias;
import com.example.tfg.navigationmenu.FragmentInicio;
import com.example.tfg.navigationmenu.MapsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    public BottomNavigationView bottomNavigationView;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar myToolbar = findViewById(R.id.toolbarPrueba);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        myToolbar.setTitleTextColor(R.color.white);


        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewPrueba);
        bottomNavigationView.setOnItemSelectedListener(this);
        loadFragment(new FragmentInicio());

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
                fragment = new MapsFragment();
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
        DialogFragment exitFragment = new ExitFragment();
        exitFragment.setCancelable(false);
        exitFragment.show(getSupportFragmentManager(),"exit_fragment");
    }

}