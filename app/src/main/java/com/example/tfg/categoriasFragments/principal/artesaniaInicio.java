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
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.annotation.SuppressLint;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import com.example.tfg.adapters.SliderAdapter;
import com.example.tfg.adapters.listViewAdapter;
import com.example.tfg.ajustesFragments.restauracion.establecimientoFragment;
import com.example.tfg.categoriasFragments.secundarias.artesania.bordadoSerrano;
import com.example.tfg.categoriasFragments.secundarias.artesania.orfebreria;
import com.example.tfg.categoriasFragments.secundarias.artesania.trajesFemeninos;
import com.example.tfg.categoriasFragments.secundarias.artesania.trajeserranoFragment;
import com.smarteist.autoimageslider.SliderView;
import com.example.tfg.navigationmenu.Categorias;
import androidx.fragment.app.FragmentTransaction;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;

import java.util.ArrayList;

public class artesaniaInicio extends Fragment {

    private Bundle args;
    private SliderView sliderView;
    private String idioma;
    private TextView text1, text2, text3;
    private ListView listView, listView2, listView3;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static artesaniaInicio newInstance(Bundle args) {
        artesaniaInicio fragment = new artesaniaInicio();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public artesaniaInicio() {}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(false);
        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        myToolbar.setNavigationOnClickListener(view12 -> {
            myToolbar.setNavigationIcon(null);
            Fragment fragment = Categorias.newInstance();
            cargarFragment(fragment);
        });

        args = new Bundle();

        if (getArguments() != null) {
            idioma = getArguments().getString("idioma");

        }

        args.putString("idioma", idioma);

    }

    /** El Fragment va a cargar su layout, el cual debemos especificar.
     Aquí se instanciarán los objetos que si son vistas */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_artesania_inicio, container, false);
        if(v != null){
            text1 = v.findViewById(R.id.arte11);
            text2 = v.findViewById(R.id.arte12);
            text3 = v.findViewById(R.id.arte13);
            listView = v.findViewById(R.id.listviewArte1);
            listView2 = v.findViewById(R.id.listviewArte2);
            listView3 = v.findViewById(R.id.listviewArte3);
            sliderView = v.findViewById(R.id.imageSliderArte1);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        GestorDB dbHelper = new GestorDB(getContext());

        String [] datos = dbHelper.obtenerDatosArte(idioma, "inicio",3);

        /*------------------
         | El traje serrano |
         ------------------*/

        text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        String opc2 = getResources().getString(R.string.traje_serrano);

        ArrayList<String> lista2 = new ArrayList<>();
        lista2.add(opc2);

        listViewAdapter myAdapter2 = new listViewAdapter(getContext(), R.layout.listview_artesania, lista2);
        listView2.setAdapter(myAdapter2);

        listView2.setOnItemClickListener((adapterView, v, position, id) -> {
            DialogFragment trajes = new trajeserranoFragment();
            trajes.setArguments(args);
            trajes.setCancelable(false);
            trajes.show(getChildFragmentManager(),"trajes");
        });

        /*------------
         | Orfebrería |
         ------------*/

        text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        String opc3 = getString(R.string.alhajas);

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

        String opc1 = getResources().getString(R.string.el_bordado_serrano);

        ArrayList<String> lista1 = new ArrayList<>();
        lista1.add(opc1);

        listViewAdapter myAdapter = new listViewAdapter(getContext(), R.layout.listview_artesania, lista1);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener((adapterView, v, position, id) -> {
            Fragment fragment = bordadoSerrano.newInstance(args);
            cargarFragment(fragment);
        });

        //SLIDER
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