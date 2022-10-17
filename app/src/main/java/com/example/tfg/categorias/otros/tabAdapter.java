package com.example.tfg.categorias.otros;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class tabAdapter extends FragmentStateAdapter {

    private Context myContext;
    private int NUM_TABS = 3;
    int totalTabs;

    public tabAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                ApartFragment apartFragment = new ApartFragment();
                return apartFragment;
            default:
                HotelesFragment hotelFragment = new HotelesFragment();
                return hotelFragment;
        }
    }

    @Override
    public int getItemCount() {
        return NUM_TABS;
    }
}