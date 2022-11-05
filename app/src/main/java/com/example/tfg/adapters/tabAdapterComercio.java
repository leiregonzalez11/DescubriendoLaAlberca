package com.example.tfg.adapters;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.tfg.ajustesFragments.comercio.AlimentacionFragment;
import com.example.tfg.ajustesFragments.comercio.ArtesaniaFragment;
import com.example.tfg.ajustesFragments.comercio.OtrosComerciosFragment;

public class tabAdapterComercio extends FragmentStateAdapter {

    public tabAdapterComercio(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new ArtesaniaFragment();
            case 2:
                return new OtrosComerciosFragment();
            default:
                return new AlimentacionFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}