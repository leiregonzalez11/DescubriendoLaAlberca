package com.example.tfg.ajustesFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.tfg.R;
import com.example.tfg.adapters.SpinnerAdapter;
import com.example.tfg.adapters.tabAdapterComer;
import com.example.tfg.adapters.tabAdapterComercio;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class comercioFragment extends Fragment {

    public comercioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comercio, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        String text1 = getResources().getString(R.string.alimentacion);
        String text2 = getResources().getString(R.string.artesaniamayus);
        String text3 = getResources().getString(R.string.otras_tiendas);

        ViewPager2 viewPager = requireView().findViewById(R.id.viewPagerComercio);

        TabLayout tabLayout = requireView().findViewById(R.id.tab_layoutComercio);
        tabLayout.addTab(tabLayout.newTab().setText(text1.toUpperCase()));
        tabLayout.addTab(tabLayout.newTab().setText(text2.toUpperCase()));
        tabLayout.addTab(tabLayout.newTab().setText(text3.toUpperCase()));
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        tabAdapterComercio myadapter = new tabAdapterComercio(getParentFragmentManager(), getLifecycle());

        viewPager.setAdapter(myadapter);
        //viewPager.setCurrentItem(tabLayout.getSelectedTabPosition());

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                assert tab.parent != null;
                viewPager.setCurrentItem(tab.parent.getSelectedTabPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //viewPager.setCurrentItem(tab.getPosition());

            }
        });


    }
}