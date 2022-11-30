package com.example.tfg.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.tfg.ajustesFragments.alojamiento.Apartamento;
import com.example.tfg.ajustesFragments.alojamiento.CasasRurales;
import com.example.tfg.ajustesFragments.alojamiento.Hoteles;

public class tabAdapter extends FragmentStateAdapter {


    public tabAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return Apartamento.newInstance();
            case 2:
                return CasasRurales.newInstance();
            default:
                return Hoteles.newInstance();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}