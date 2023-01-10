package com.example.tfg.mapsFragments.otrosLugares.penaFrancia;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfg.GestorDB;
import com.example.tfg.R;

public class santuario extends Fragment implements View.OnClickListener {

    private Bundle args;
    private String idioma;
    private String categoria;
    private Button btn1,btn2,btn3, btnExtra;
    private GestorDB dbHelper;
    private TextView text1, text2, text3, text4, text5, text6;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static santuario newInstance(Bundle args) {
        santuario fragment = new santuario();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public santuario() {}

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
            Fragment fragment = monumentosPenaFrancia.newInstance(args);
            cargarFragment(fragment);
        });
    }

    /** El Fragment va a cargar su layout, el cual debemos especificar.
     Aquí se instanciarán los objetos que si son vistas */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_santuario, container, false);
        if (v != null){
            btn1 = v.findViewById(R.id.iglesiapeña);
            btn2 = v.findViewById(R.id.pozoverde);
            btn3 = v.findViewById(R.id.convento);
            btnExtra = v.findViewById(R.id.buttonsantuario);
            text1 = v.findViewById(R.id.santuariotext);
            text2 = v.findViewById(R.id.santuariotext2);
            text3 = v.findViewById(R.id.santuariotext3);
            text4 = v.findViewById(R.id.santuariotext4);
            text5 = v.findViewById(R.id.santuariotext5);
            text6 = v.findViewById(R.id.santuariotext6);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);

        dbHelper = new GestorDB(getContext());

        String [] datos = dbHelper.obtenerInfoPena(idioma, "elsantuario", categoria, "peñadefrancia", 1);
        text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

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

    @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
    @Override
    public void onClick(View view) {

        Button btn = (Button) view;

        switch (btn.getId()){

            case R.id.iglesiapeña:
                Fragment fragment = iglesia.newInstance(args);
                cargarFragment(fragment);
                btnExtra.setVisibility(View.INVISIBLE);
                break;
            case R.id.pozoverde:
                String [] datos = dbHelper.obtenerInfoPena(idioma, "pozoverde", categoria, "peñadefrancia", 1);
                text2.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setText("");
                text4.setText("");
                text5.setText("");
                text6.setText("");
                btnExtra.setVisibility(View.INVISIBLE);
                break;
            case R.id.convento:
                String [] datos2 = dbHelper.obtenerInfoPena(idioma, "convento", categoria, "peñadefrancia", 5);
                text2.setText(datos2[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setText(datos2[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text4.setText(datos2[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text5.setText(datos2[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text6.setText(datos2[4] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                btnExtra.setVisibility(View.VISIBLE);
                btnExtra.setText("   La Casa Baja   ");
                btnExtra.setOnClickListener(view1 -> Toast.makeText(getContext(),"Has pulsado: La Casa Baja", Toast.LENGTH_LONG).show());

                break;
        }
    }
}