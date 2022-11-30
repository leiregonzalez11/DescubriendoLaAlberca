package com.example.tfg.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.tfg.ajustesFragments.restauracion.Bares;
import com.example.tfg.ajustesFragments.restauracion.Restaurantes;

public class tabAdapterComer extends FragmentStateAdapter {

    public tabAdapterComer(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return Restaurantes.newInstance();
            default:
                return Bares.newInstance();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}