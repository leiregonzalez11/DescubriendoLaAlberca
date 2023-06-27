package com.example.tfg.categorias.secundarias.cultura.diccionario;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tfg.R;
import com.example.tfg.navigationMenu.Categorias;


public class diccionario extends Fragment implements View.OnClickListener {

    private Bundle args;
    private String idioma, letra;
    private ImageButton btnab, btnde, btnc, btnhi, btnjkl, btnfg, btnopq, btnmn, btntuv, btnrs, btnwxyz;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static diccionario newInstance(Bundle args) {
        diccionario fragment = new diccionario();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public diccionario() {}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        TextView name = myToolbar.findViewById(R.id.name);
        name.setText(R.string.cultura);
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
        View v = inflater.inflate(R.layout.fragment_diccionario, container, false);
        if (v != null){
            btnab = v.findViewById(R.id.ab);
            btnc = v.findViewById(R.id.c);
            btnde = v.findViewById(R.id.de);
            btnfg = v.findViewById(R.id.fg);
            btnhi = v.findViewById(R.id.hi);
            btnjkl = v.findViewById(R.id.jkl);
            btnmn = v.findViewById(R.id.mn);
            btnopq = v.findViewById(R.id.opq);
            btnrs = v.findViewById(R.id.rs);
            btntuv = v.findViewById(R.id.tuv);
            btnwxyz = v.findViewById(R.id.wxyz);
        }
        setListeners();
        return v;
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

    /** Setter de Listeners para los botones de la interfaz*/
    private void setListeners() {
        btnab.setOnClickListener(this);
        btnc.setOnClickListener(this);
        btnde.setOnClickListener(this);
        btnfg.setOnClickListener(this);
        btnhi.setOnClickListener(this);
        btnjkl.setOnClickListener(this);
        btnmn.setOnClickListener(this);
        btnopq.setOnClickListener(this);
        btnrs.setOnClickListener(this);
        btntuv.setOnClickListener(this);
        btnwxyz.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        ImageButton btn = (ImageButton) view;

        switch (btn.getId()) {
            case R.id.ab:
                letra = "ab";
                break;
            case R.id.c:
                letra = "c";
                break;
            case R.id.de:
                letra = "de";
                break;
            case R.id.fg:
                letra = "fg";
                break;
            case R.id.hi:
                letra = "hi";
                break;
            case R.id.jkl:
                letra = "jkl";
                break;
            case R.id.mn:
                letra = "mnñ";
                break;
            case R.id.opq:
                letra = "opq";
                break;
            case R.id.rs:
                letra = "rs";
                break;
            case R.id.tuv:
                letra = "tuv";
                break;
            case R.id.wxyz:
                letra = "wxyz";
                break;
        }

        args.putString("letra", letra);
        DialogFragment fragment = new listapalabrasf();
        fragment.setArguments(args);
        fragment.setCancelable(false);
        fragment.show(getChildFragmentManager(),"listap_fragment");
    }
}