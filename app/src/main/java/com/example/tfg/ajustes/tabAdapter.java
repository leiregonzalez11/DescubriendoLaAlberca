package com.example.tfg.ajustes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.tfg.ajustes.aloj.ApartFragment;
import com.example.tfg.ajustes.aloj.CasasFragment;
import com.example.tfg.ajustes.aloj.HotelesFragment;

import java.util.ArrayList;
import java.util.List;

public class tabAdapter extends FragmentStateAdapter {

    private final List<String> fragmentTitle = new ArrayList<>();

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