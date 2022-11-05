package com.example.tfg.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.tfg.ajustesFragments.alojamiento.ApartFragment;
import com.example.tfg.ajustesFragments.alojamiento.CasasFragment;
import com.example.tfg.ajustesFragments.alojamiento.HotelesFragment;

public class tabAdapter extends FragmentStateAdapter {


    public tabAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new ApartFragment();
            case 2:
                return new CasasFragment();
            default:
                return new HotelesFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}