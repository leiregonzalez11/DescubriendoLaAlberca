package com.example.tfg.mapOptions.otrosLugares.penaFrancia;

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
import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.tfg.GestorDB;
import com.example.tfg.R;

public class santuario extends Fragment implements View.OnClickListener {

    private Bundle args;
    private String idioma;
    private Button btn1,btn2,btn3;
    private TextView text1;

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

        args = new Bundle();

        if (getArguments() != null) {
            idioma = getArguments().getString("idioma");
        }

        args.putString("idioma", idioma);

        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        TextView name = myToolbar.findViewById(R.id.name);
        name.setText(R.string.quevisitar);
        name.setTextSize(20);
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
            text1 = v.findViewById(R.id.santuariotext);
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

        String[] datos;
        try (GestorDB dbHelper = GestorDB.getInstance(getContext())) {

            datos = dbHelper.obtenerInfoLugares(idioma, "elsantuario", "peñadefrancia", 1);
        }
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
                break;

            case R.id.pozoverde:

                String monumento = "pozoverde";
                DialogFragment monFragment = new monumentopenaFragment();
                args.putString("monumento", monumento);
                monFragment.setArguments(args);
                monFragment.setCancelable(false);
                monFragment.show(getChildFragmentManager(),"mon_fragment");
                break;

            case R.id.convento:

                DialogFragment convFragment = new conventoFragment();
                convFragment.setArguments(args);
                convFragment.setCancelable(false);
                convFragment.show(getChildFragmentManager(),"conv_fragment");
                break;
        }
    }

}