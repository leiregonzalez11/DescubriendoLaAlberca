package com.example.tfg.categorias.principal;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.annotation.SuppressLint;
import android.widget.Toast;

import com.example.tfg.R;
import com.example.tfg.GestorDB;
import com.example.tfg.categorias.secundarias.tradiciones.fuentes;
import com.example.tfg.categorias.secundarias.tradiciones.elpendon;
import com.example.tfg.navigationMenu.Categorias;
import com.example.tfg.categorias.secundarias.tradiciones.alboradas;
import com.example.tfg.categorias.secundarias.tradiciones.laLoa;
import com.example.tfg.categorias.secundarias.tradiciones.marranoSanAnton;
import com.example.tfg.categorias.secundarias.tradiciones.mozaDeAnimas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class tradicionesInicio extends Fragment {

    private Bundle args;
    private Fragment fragment;
    private TextView text1, text2;
    private String idioma;
    private Button fuentesLA, alborada, mozaanimas, marrano, boda, loa, pendon;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static tradicionesInicio newInstance(Bundle args) {
        tradicionesInicio fragment = new tradicionesInicio();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public tradicionesInicio() {}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Toolbar
        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        TextView name = myToolbar.findViewById(R.id.name);
        name.setText(R.string.categorias);
        name.setTextSize(20);
        myToolbar.setNavigationOnClickListener(v -> {
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
        View v =  inflater.inflate(R.layout.fragment_tradiciones, container, false);
        if(v != null){
            text1 = v.findViewById(R.id.trad11);
            text2 = v.findViewById(R.id.trad12);
            mozaanimas = v.findViewById(R.id.btntrad1);
            marrano = v.findViewById(R.id.btntrad2);
            loa = v.findViewById(R.id.btntrad3);
            alborada = v.findViewById(R.id.btntrad4);
            pendon = v.findViewById(R.id.btntrad5);
            boda = v.findViewById(R.id.btntrad6);
            fuentesLA = v.findViewById(R.id.btntrad7);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        String[] datos;
        try (GestorDB dbHelper = GestorDB.getInstance(getContext())) {
            datos = dbHelper.obtenerInfoTrad(idioma, "inicio", 2);
        }

        text1.setText(datos[0]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text2.setText(datos[1]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        //Selección de tradiciones

        /*----------------
         | Moza de Ánimas |
         ----------------*/

        mozaanimas.setOnClickListener(v -> {
            fragment = mozaDeAnimas.newInstance(args);
            cargarFragment(fragment);
        });

        /*----------------------
         | Marrano de San Antón |
         -----------------------*/

        marrano.setOnClickListener(v -> {
            fragment = marranoSanAnton.newInstance(args);
            cargarFragment(fragment);
        });

        /*--------
         | La Loa |
         --------*/

        loa.setOnClickListener(v -> {
            fragment = laLoa.newInstance(args);
            cargarFragment(fragment);
        });

        /*---------------
         | Las alboradas |
         ---------------*/

        alborada.setOnClickListener(v -> {
            fragment = alboradas.newInstance(args);
            cargarFragment(fragment);
        });

        /*-----------
         | El Pendón |
         -----------*/

        pendon.setOnClickListener(v -> {
            fragment = elpendon.newInstance(args);
            cargarFragment(fragment);
        });

        /*-------------------
         | La boda albercana |
         -------------------*/

        boda.setOnClickListener(v -> {
            /*fragment = boda.newInstance(args);
            cargarFragment(fragment);*/
            Toast.makeText(requireContext(), "HAS PULSADO LA BODA ALBERCANA", Toast.LENGTH_SHORT).show();
        });

        /*---------------------------
         | Las fuentes de La Alberca |
         ---------------------------*/

        fuentesLA.setOnClickListener(v -> {
            fragment = fuentes.newInstance(args);
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