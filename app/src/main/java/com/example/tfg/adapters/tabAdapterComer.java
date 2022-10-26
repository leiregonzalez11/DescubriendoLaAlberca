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

public class tabAdapterComer extends FragmentStateAdapter {

    public tabAdapterComer(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new RestaurantesFragment();
            default:
                return new BaresFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}