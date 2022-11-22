package com.example.tfg.categoriasFragments.principal;

import android.os.Bundle;
import com.example.tfg.R;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.tfg.GestorDB;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import android.annotation.SuppressLint;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import com.example.tfg.adapters.SliderAdapter;
import com.smarteist.autoimageslider.SliderView;
import com.example.tfg.navigationmenu.Categorias;
import androidx.fragment.app.FragmentTransaction;
import com.smarteist.autoimageslider.SliderAnimations;
import com.example.tfg.categoriasFragments.secundarias.artesania.artesaniaSelector;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;

public class artesaniaInicio extends Fragment {

    Bundle args;
    String idioma, categoria;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);

        args = new Bundle();

        if (getArguments() != null) {
            idioma = getArguments().getString("idioma");
            categoria = getArguments().getString("categoria");
        }

        args.putString("idioma", idioma);
        args.putString("categoria", categoria);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artesania_inicio, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        myToolbar.setNavigationOnClickListener(view12 -> {

            myToolbar.setNavigationIcon(null);
            Fragment fragment = new Categorias();
            cargarFragment(fragment);
        });

        GestorDB dbHelper = new GestorDB(getContext());

        String [] datos = dbHelper.obtenerDatosArte(idioma, "inicio", categoria, 3);

        TextView text1 = requireView().findViewById(R.id.arte11);
        TextView text2 = requireView().findViewById(R.id.arte12);
        TextView text3 = requireView().findViewById(R.id.arte13);

        text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        //SLIDER
        SliderView sliderView = requireView().findViewById(R.id.imageSliderArte1);
        int[] images = new int[]{R.drawable.arte1, R.drawable.arte2, R.drawable.arte3};
        SliderAdapter adapterSlider = new SliderAdapter(images);
        sliderView.setSliderAdapter(adapterSlider);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.DROP);
        sliderView.setScrollTimeInSec(3);
        sliderView.startAutoCycle();

        //BOTON SIGUIENTE

        Button sigBtn = requireView().findViewById(R.id.arteSiguiente1);
        sigBtn.setOnClickListener(view1 -> {
            Fragment fragment = new artesaniaSelector();
            fragment.setArguments(args);
            cargarFragment(fragment);
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