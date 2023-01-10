package com.example.tfg.mapsFragments.otrosLugares.penaFrancia;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.mapsFragments.otrosLugares.penaDeFrancia;

public class monumentosPenaFrancia extends Fragment implements View.OnClickListener {

    private Bundle args;
    private String idioma, categoria;
    private GestorDB dbHelper;
    private TextView text1, text2, text3;
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btnExtra;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static monumentosPenaFrancia newInstance(Bundle args) {
        monumentosPenaFrancia fragment = new monumentosPenaFrancia();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public monumentosPenaFrancia() {}

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
        args.putString("back", "true");

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
        View v = inflater.inflate(R.layout.fragment_monumentos_penafrancia, container, false);
        if (v != null){
            btn1 = v.findViewById(R.id.santuario);
            btn2 = v.findViewById(R.id.sanandresexterior);
            btn3 = v.findViewById(R.id.santocristoexterior);
            btn4 = v.findViewById(R.id.santodomingo);
            btn5 = v.findViewById(R.id.rollo);
            btn6 = v.findViewById(R.id.hospederia);
            btn7 = v.findViewById(R.id.lablanca);
            btn8 = v.findViewById(R.id.balcon);
            text1 = v.findViewById(R.id.monumentostext1);
            text2 = v.findViewById(R.id.monumentostext2);
            text3 = v.findViewById(R.id.monumentostext3);
            btnExtra = v.findViewById(R.id.buttonmonumentos);
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
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);

        dbHelper = new GestorDB(getContext());

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

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public void onClick(View view) {

        Button btn = (Button) view;

        switch (btn.getId()){

            case R.id.santuario:
                Fragment fragment = santuario.newInstance(args);
                cargarFragment(fragment);
                btnExtra.setVisibility(View.INVISIBLE);
                break;
            case R.id.hospederia:
                fragment = hospederia.newInstance(args);
                cargarFragment(fragment);
                btnExtra.setVisibility(View.INVISIBLE);
                break;
            case R.id.rollo:
                fragment = elrollo.newInstance(args);
                cargarFragment(fragment);
                btnExtra.setVisibility(View.INVISIBLE);
                break;
            case R.id.santodomingo:
                String[] datos = dbHelper.obtenerInfoPena(idioma, "miradordesantodomingo", categoria, "peñadefrancia", 1);
                text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text1.requestFocus();
                text2.setText("");
                text3.setText("");
                btnExtra.setVisibility(View.INVISIBLE);
                break;
            case R.id.lablanca:
                datos = dbHelper.obtenerInfoPena(idioma, "capilladelablanca", categoria, "peñadefrancia", 3);
                text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text1.requestFocus();
                text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                btnExtra.setVisibility(View.INVISIBLE);
                break;

            case R.id.sanandresexterior:
                datos = dbHelper.obtenerInfoPena(idioma, "capillaexteriordesanandrés", categoria, "peñadefrancia", 2);
                text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text1.requestFocus();
                text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setText("");
                btnExtra.setVisibility(View.INVISIBLE);
                break;

            case R.id.santocristoexterior:
                datos = dbHelper.obtenerInfoPena(idioma, "capillaexteriordelsantocristo", categoria, "peñadefrancia", 2);
                text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text1.requestFocus();
                text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setText("");
                btnExtra.setVisibility(View.INVISIBLE);
                break;

            case R.id.balcon:
                datos = dbHelper.obtenerInfoPena(idioma, "balcóndesantiago", categoria, "peñadefrancia", 2);
                text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text1.requestFocus();
                text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setText("");
                btnExtra.setVisibility(View.VISIBLE);
                btnExtra.setText("  El Salto del Niño  ");
                btnExtra.setOnClickListener(view1 -> {
                    DialogFragment saltoFragment = new saltoDelNinoFragment();
                    saltoFragment.setArguments(args);
                    saltoFragment.setCancelable(false);
                    saltoFragment.show(getChildFragmentManager(),"salto_fragment");
                });
                break;
        }
    }
}