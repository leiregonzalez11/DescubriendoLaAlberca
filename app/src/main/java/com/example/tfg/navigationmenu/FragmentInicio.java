package com.example.tfg.navigationmenu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tfg.R;
import com.example.tfg.adapters.SliderAdapter;
import com.example.tfg.ajustesFragments.formFragment;
import com.example.tfg.ajustesFragments.idiomasFragment;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;


public class FragmentInicio extends Fragment {

    String idioma, iu;
    Bundle args;
    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    public FragmentInicio() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        args = new Bundle();
        args.putString("iu", "inicio");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        //SLIDER
        SliderView sliderView = requireView().findViewById(R.id.imageSliderPrueba);
        int[] images = new int[]{R.drawable.laalberca1, R.drawable.laalberca2, R.drawable.laalberca3, R.drawable.laalberca4};
        SliderAdapter adapter = new SliderAdapter(images);
        sliderView.setSliderAdapter(adapter);
        sliderView.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.SCALE_DOWN);
        sliderView.setScrollTimeInSec(3);
        sliderView.startAutoCycle();

    }

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menuusuario, menu);
    }

    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.menu_contacto:
                //Toast.makeText(getContext(), "Has pulsado: Contacto", Toast.LENGTH_LONG).show();

                //Añadimos los argumentos
                fragment = new formFragment();
                fragment.setArguments(args);

                // Obtener el administrador de fragmentos a través de la actividad
                fragmentManager = requireActivity().getSupportFragmentManager();

                // Definir una transacción
                fragmentTransaction = fragmentManager.beginTransaction();

                // Remplazar el contenido principal por el fragmento
                fragmentTransaction.replace(R.id.relativelayout, fragment);
                fragmentTransaction.addToBackStack(null);

                // Cambiar
                fragmentTransaction.commit();
                return true;

            case R.id.menu_idioma:
                //Toast.makeText(getContext(), "Has pulsado: Idiomas", Toast.LENGTH_LONG).show();

                //Añadimos los argumentos
                fragment = new idiomasFragment();
                fragment.setArguments(args);

                // Obtener el administrador de fragmentos a través de la actividad
                fragmentManager = requireActivity().getSupportFragmentManager();

                // Definir una transacción
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Remplazar el contenido principal por el fragmento
                fragmentTransaction.replace(R.id.relativelayout, fragment);
                fragmentTransaction.addToBackStack(null);

                // Cambiar
                fragmentTransaction.commit();
                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

}