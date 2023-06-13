package com.example.tfg.otherFiles.adapters;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.tfg.espacioDelViajero.alojamiento.Hoteles;
import com.example.tfg.espacioDelViajero.alojamiento.CasasRurales;
import com.example.tfg.espacioDelViajero.alojamiento.Apartamento;
import com.example.tfg.espacioDelViajero.comercio.AlimentacionTienda;
import com.example.tfg.espacioDelViajero.comercio.ArtesaniaTienda;
import com.example.tfg.espacioDelViajero.restauracion.Bares;
import com.example.tfg.espacioDelViajero.restauracion.Restaurantes;

public class tabAdapter extends FragmentStateAdapter {

    private final String clase;

    public tabAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, String pClase) {
        super(fragmentManager, lifecycle);
        clase = pClase;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        Fragment fragment = new Fragment();

        if (clase.equalsIgnoreCase("alojamiento")){
            fragment = returnAloj(position);
        } else if (clase.equalsIgnoreCase("hosteleria")){
            fragment = returnHost(position);
        } else if (clase.equalsIgnoreCase("comercio")){
            fragment = returnCom(position);
        }
        return fragment;
    }

    private Fragment returnCom(int position) {
        if (position == 1) {
            return ArtesaniaTienda.newInstance();
        }
        return AlimentacionTienda.newInstance();
    }

    private Fragment returnHost(int position) {
        if (position == 1) {
            return Restaurantes.newInstance();
        }
        return Bares.newInstance();
    }

    private Fragment returnAloj(int position){
        switch (position) {
            case 1:
                return Apartamento.newInstance();
            case 2:
                return CasasRurales.newInstance();
            default:
                return Hoteles.newInstance();
        }
    }

    @Override
    public int getItemCount() {
        if (clase.equalsIgnoreCase("alojamiento")){
            return 3;
        } else{
            return 2;
        }
    }

}