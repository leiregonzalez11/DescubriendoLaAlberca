package com.example.tfg.adapters;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.tfg.ajustes.aloj.ApartFragment;
import com.example.tfg.ajustes.aloj.CasasFragment;
import com.example.tfg.ajustes.aloj.HotelesFragment;
import com.example.tfg.ajustes.rest.BaresFragment;
import com.example.tfg.ajustes.rest.RestaurantesFragment;

import java.util.ArrayList;
import java.util.List;

public class tabAdapterComercio extends FragmentStateAdapter {

    public tabAdapterComercio(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        /*switch (position) {
            case 1:
                return new OtrosFragment();
            case 2:
                return new ArtesaniaFragment();
            default:
                return new SuperFragment();
        }*/
        //TODO:ELIMINAR
        return new BaresFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}