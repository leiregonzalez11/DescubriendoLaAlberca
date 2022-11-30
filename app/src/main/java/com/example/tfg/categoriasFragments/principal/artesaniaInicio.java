package com.example.tfg.categoriasFragments.principal;

import android.os.Bundle;
import com.example.tfg.R;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
import com.example.tfg.adapters.listViewAdapter;
import com.example.tfg.categoriasFragments.secundarias.artesania.bordadoSerrano;
import com.example.tfg.categoriasFragments.secundarias.artesania.orfebreria;
import com.example.tfg.categoriasFragments.secundarias.artesania.trajesFemeninos;
import com.smarteist.autoimageslider.SliderView;
import com.example.tfg.navigationmenu.Categorias;
import androidx.fragment.app.FragmentTransaction;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;

import java.util.ArrayList;

public class artesaniaInicio extends Fragment {

    Bundle args;
    String idioma, categoria, opc1, opc2, opc3;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment BlankFragment.
     */
    public static artesaniaInicio newInstance(Bundle args) {
        artesaniaInicio fragment = new artesaniaInicio();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    public artesaniaInicio() {
        // Required empty public constructor
    }

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
            Fragment fragment = Categorias.newInstance();
            cargarFragment(fragment);
        });

        GestorDB dbHelper = new GestorDB(getContext());

        String [] datos = dbHelper.obtenerDatosArte(idioma, "inicio", categoria, 3);

        TextView text1 = requireView().findViewById(R.id.arte11);
        TextView text2 = requireView().findViewById(R.id.arte12);
        TextView text3 = requireView().findViewById(R.id.arte13);

        /*------------------
         | El traje serrano |
         ------------------*/

        text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        opc2 = getResources().getString(R.string.traje_serrano);
        ListView listView2 = requireView().findViewById(R.id.listviewArte2);

        ArrayList<String> lista2 = new ArrayList<>();
        lista2.add(opc2);

        listViewAdapter myAdapter2 = new listViewAdapter(getContext(), R.layout.listview_artesania, lista2);
        listView2.setAdapter(myAdapter2);

        listView2.setOnItemClickListener((adapterView, v, position, id) -> {
            Fragment fragment = trajesFemeninos.newInstance(args);
            cargarFragment(fragment);
        });


        /*------------
         | Orfebrería |
         ------------*/

        text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        opc3 = getString(R.string.alhajas);
        ListView listView3 = requireView().findViewById(R.id.listviewArte3);

        ArrayList<String> lista3 = new ArrayList<>();
        lista3.add(opc3);

        listViewAdapter myAdapter3 = new listViewAdapter(getContext(), R.layout.listview_artesania, lista3);
        listView3.setAdapter(myAdapter3);

        listView3.setOnItemClickListener((adapterView, v, position, id) -> {
            Fragment fragment = orfebreria.newInstance(args);
            cargarFragment(fragment);
        });

        /*--------------------
         | El bordado serrano |
         --------------------*/

        text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        opc1 = getResources().getString(R.string.el_bordado_serrano);
        ListView listView = requireView().findViewById(R.id.listviewArte1);

        ArrayList<String> lista1 = new ArrayList<>();
        lista1.add(opc1);

        listViewAdapter myAdapter = new listViewAdapter(getContext(), R.layout.listview_artesania, lista1);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener((adapterView, v, position, id) -> {
            Fragment fragment = bordadoSerrano.newInstance(args);
            cargarFragment(fragment);
        });

        //SLIDER
        SliderView sliderView = requireView().findViewById(R.id.imageSliderArte1);
        int[] images = new int[]{R.drawable.arte1, R.drawable.arte2, R.drawable.arte3};
        SliderAdapter adapterSlider = new SliderAdapter(images);
        sliderView.setSliderAdapter(adapterSlider);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.DROP);
        sliderView.setScrollTimeInSec(3);
        sliderView.startAutoCycle();

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