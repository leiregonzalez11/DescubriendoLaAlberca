package com.example.tfg.categoriasFragments.secundarias.otrosLugares.penaFrancia;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.categoriasFragments.secundarias.otrosLugares.penaDeFrancia;

public class leyendaPenaFrancia extends Fragment {

    private Bundle args;
    private String idioma;
    private String categoria;
    private ImageButton btn;
    private TextView pruebatexto, text1, text2, text3, text4;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static leyendaPenaFrancia newInstance(Bundle args) {
        leyendaPenaFrancia fragment = new leyendaPenaFrancia();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public leyendaPenaFrancia() {}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
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

        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        myToolbar.setNavigationOnClickListener(view1 -> {
            myToolbar.setNavigationIcon(null);
            Fragment fragment = penaDeFrancia.newInstance(args);
            cargarFragment(fragment);
        });
    }

    /** El Fragment va a cargar su layout, el cual debemos especificar.
     Aquí se instanciarán los objetos que si son vistas */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_leyenda_penafrancia, container, false);
        if (v != null){
            pruebatexto = v.findViewById(R.id.pruebatexto);
            text1 = v.findViewById(R.id.leyendap1);
            text2 = v.findViewById(R.id.leyendap2);
            text3 = v.findViewById(R.id.leyendap3);
            text4 = v.findViewById(R.id.leyendap4);
            btn = v.findViewById(R.id.leyendapbtn);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        GestorDB dbHelper = new GestorDB(getContext());

        if (pruebatexto.getText().toString().equalsIgnoreCase("1")){
            btn.setImageResource(R.drawable.ic_circle_arrow_right_solid);
            String [] datos = dbHelper.obtenerInfoPena(idioma, "leyenda1", categoria, "peñadefrancia", 4);
            text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text2.setTypeface(Typeface.create(text2.getTypeface(), Typeface.ITALIC));
            text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text3.setTypeface(Typeface.create(text3.getTypeface(), Typeface.NORMAL));
            text4.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text4.setTypeface(Typeface.create(text4.getTypeface(), Typeface.ITALIC));
        }

        btn.setOnClickListener(view1 -> {
            if (pruebatexto.getText().toString().equalsIgnoreCase("1")){
                btn.setImageResource(R.drawable.ic_circle_arrow_left_solid);
                pruebatexto.setText("2");

                String [] datos = dbHelper.obtenerInfoPena(idioma, "leyenda2", categoria, "peñadefrancia", 4);

                text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text2.setTypeface(Typeface.create(text2.getTypeface(), Typeface.NORMAL));
                text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setTypeface(Typeface.create(text3.getTypeface(), Typeface.ITALIC));
                text4.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text4.setTypeface(Typeface.create(text4.getTypeface(), Typeface.NORMAL));

            } else if (pruebatexto.getText().toString().equalsIgnoreCase("2")){
                btn.setImageResource(R.drawable.ic_circle_arrow_right_solid);
                pruebatexto.setText("1");

                String [] datos = dbHelper.obtenerInfoPena(idioma, "leyenda1", categoria, "peñadefrancia", 4);

                text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text2.setTypeface(Typeface.create(text2.getTypeface(), Typeface.ITALIC));
                text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setTypeface(Typeface.create(text3.getTypeface(), Typeface.NORMAL));
                text4.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text4.setTypeface(Typeface.create(text4.getTypeface(), Typeface.ITALIC));

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