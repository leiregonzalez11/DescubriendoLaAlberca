package com.example.tfg.EspacioDelViajero;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;
import com.example.tfg.R;
import com.example.tfg.OtherFiles.Adapters.tabAdapter;
import com.example.tfg.NavigationMenu.EspacioDelViajero;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class DondeDormir extends Fragment {

    private Toolbar myToolbar;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static DondeDormir newInstance() {
        return new DondeDormir();
    }

    /** Required empty public constructor */
    public DondeDormir() {}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        myToolbar.setNavigationOnClickListener(v -> {
            myToolbar.setNavigationIcon(null);
            Fragment fragment = EspacioDelViajero.newInstance();
            cargarFragment(fragment);
        });
    }

    /** El Fragment va a cargar su layout, el cual debemos especificar.
     Aquí se instanciarán los objetos que si son vistas */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_donde_dormir, container, false);
        if(v != null){
            viewPager = v.findViewById(R.id.viewPager);
            tabLayout  = v.findViewById(R.id.tab_layout);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        String text1 = getResources().getString(R.string.apart);
        String text2 = getResources().getString(R.string.hotel);
        String text3 = getResources().getString(R.string.casas);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        tabAdapter myadapter = new tabAdapter(getParentFragmentManager(), getLifecycle(), "alojamiento");

        viewPager.setAdapter(myadapter);
        viewPager.setOffscreenPageLimit(3);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });

        viewPager.setAdapter(myadapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0){
                tab.setText(text2.toUpperCase());
            } else if (position == 1){
                tab.setText(text1.toUpperCase());
            }else if (position == 2){
                tab.setText(text3.toUpperCase());
            }
        }).attach();


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

    private void cargarFragment(Fragment fragment){
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

}