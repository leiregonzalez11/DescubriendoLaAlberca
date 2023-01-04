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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tfg.GestorDB;
import com.example.tfg.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link iglesia#newInstance} factory method to
 * create an instance of this fragment.
 */
public class iglesia extends Fragment implements View.OnClickListener{

    private Bundle args;
    private String idioma;
    private ImageView img1;
    private String categoria;
    private GestorDB dbHelper;
    private Button btn1,btn2,btn3, btn4;
    private TextView text1, text2, text3, text4, text5, text6, text7, text8;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static iglesia newInstance(Bundle args) {
        iglesia fragment = new iglesia();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public iglesia() {}

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
            Fragment fragment = santuario.newInstance(args);
            cargarFragment(fragment);
        });
    }

    /** El Fragment va a cargar su layout, el cual debemos especificar.
     Aquí se instanciarán los objetos que si son vistas */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_iglesia, container, false);
        if (v != null){
            btn1 = v.findViewById(R.id.naveizqda);
            btn2 = v.findViewById(R.id.navedcha);
            btn3 = v.findViewById(R.id.coropeña);
            btn4 = v.findViewById(R.id.altar);
            text1 = v.findViewById(R.id.iglesiatext);
            text2 = v.findViewById(R.id.iglesiatext2);
            text3 = v.findViewById(R.id.iglesiatext3);
            text4 = v.findViewById(R.id.iglesiatext4);
            text5 = v.findViewById(R.id.iglesiatext5);
            text6 = v.findViewById(R.id.iglesiatext6);
            text7 = v.findViewById(R.id.iglesiatext7);
            text8 = v.findViewById(R.id.iglesiatext8);
            img1 = v.findViewById(R.id.imageZona);
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

        dbHelper = new GestorDB(getContext());

        String [] datos = dbHelper.obtenerInfoPena(idioma, "iglesia", categoria, "peñadefrancia", 2);
        text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        String [] datos2 = dbHelper.obtenerInfoPena(idioma, "vidrieras", categoria, "peñadefrancia", 1);
        text3.setText(datos2[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

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
            case R.id.altar:
                String [] datos = dbHelper.obtenerInfoPena(idioma, "altarmayor", categoria, "peñadefrancia", 1);
                img1.setImageResource(R.drawable.planoiglesiacentro);
                text4.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                img1.requestFocus();
                text5.setText("");
                text6.setText("");
                text7.setText("");
                text8.setText("");
                break;
            case R.id.coropeña:
                String [] datos1 = dbHelper.obtenerInfoPena(idioma, "coro", categoria, "peñadefrancia", 1);
                img1.setImageResource(R.drawable.planoiglesiacoro);
                text4.setText(datos1[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                img1.requestFocus();
                text5.setText("");
                text6.setText("");
                text7.setText("");
                text8.setText("");
                break;
            case R.id.naveizqda:
                String [] datos2 = dbHelper.obtenerInfoPena(idioma, "naveizquierda", categoria, "peñadefrancia", 5);
                img1.setImageResource(R.drawable.planoiglesiaizqda);
                text4.setText(datos2[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                img1.requestFocus();
                text5.setText(datos2[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text6.setText(datos2[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text7.setText(datos2[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text8.setText(datos2[4] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                break;
            case R.id.navedcha:
                String [] datos3 = dbHelper.obtenerInfoPena(idioma, "navederecha", categoria, "peñadefrancia", 2);
                img1.setImageResource(R.drawable.planoiglesiadcha);
                text4.setText(datos3[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                img1.requestFocus();
                text5.setText(datos3[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text6.setText("");
                text7.setText("");
                text8.setText("");
                break;
        }
    }
}