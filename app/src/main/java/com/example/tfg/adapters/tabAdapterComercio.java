package com.example.tfg.adapters;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.tfg.ajustesFragments.comercio.AlimentacionTienda;
import com.example.tfg.ajustesFragments.comercio.ArtesaniaTienda;
import com.example.tfg.ajustesFragments.comercio.OtrosComercios;

public class tabAdapterComercio extends FragmentStateAdapter {

    public tabAdapterComercio(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return ArtesaniaTienda.newInstance();
            default:
                return AlimentacionTienda.newInstance();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}