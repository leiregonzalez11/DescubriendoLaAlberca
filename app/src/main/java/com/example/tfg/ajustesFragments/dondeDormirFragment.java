package com.example.tfg.ajustesFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.tfg.R;
import com.example.tfg.adapters.SpinnerAdapter;
import com.example.tfg.adapters.tabAdapter;
import com.example.tfg.adapters.tabAdapterComer;
import com.example.tfg.navigationmenu.FragmentAjustes;
import com.example.tfg.navigationmenu.FragmentCategorias;
import com.example.tfg.navigationmenu.FragmentInicio;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;


public class dondeDormirFragment extends Fragment {

    public dondeDormirFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(false);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_donde_dormir, container, false);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        String text1 = getResources().getString(R.string.apart);
        String text2 = getResources().getString(R.string.hotel);
        String text3 = getResources().getString(R.string.casas);

        ViewPager2 viewPager = requireView().findViewById(R.id.viewPager);

        TabLayout tabLayout  = requireView().findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(text1.toUpperCase()));
        tabLayout.addTab(tabLayout.newTab().setText(text2.toUpperCase()));
        tabLayout.addTab(tabLayout.newTab().setText(text3.toUpperCase()));
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        tabAdapter myadapter = new tabAdapter(getParentFragmentManager(), getLifecycle());

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbarPrueba);
        myToolbar.setNavigationIcon(R.drawable.arrow_back);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myToolbar.setNavigationIcon(null);
                Fragment fragment = new FragmentAjustes();

                // Obtenemos el administrador de fragmentos a través de la actividad
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Definimos una transacción
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Remplazamos el contenido principal por el fragmento
                fragmentTransaction.replace(R.id.relativelayout, fragment);
                fragmentTransaction.addToBackStack(null);

                // Cambiamos el fragment en la interfaz
                fragmentTransaction.commit();
            }
        });
    }

}