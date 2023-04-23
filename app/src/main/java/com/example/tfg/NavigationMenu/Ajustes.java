package com.example.tfg.NavigationMenu;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tfg.Ajustes.Agradecimientos;
import com.example.tfg.Ajustes.QuienesSomos;
import com.example.tfg.R;
import com.example.tfg.OtherFiles.Adapters.listViewAdapter;
import com.example.tfg.Ajustes.FormularioDeContacto;
import com.example.tfg.Ajustes.Idiomas;

import java.util.ArrayList;

public class Ajustes extends Fragment {

    Bundle args;
    Fragment fragment;
    Toolbar myToolbar;
    ListView listView, listView2, listView3, listView4;
    String opc1, opc2, opc3, opc4;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static Ajustes newInstance() {
        return new Ajustes();
    }

    /** Required empty public constructor */
    public Ajustes() {}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(null);
        TextView name = myToolbar.findViewById(R.id.name);
        name.setText(R.string.ajustes);
        name.setTextSize(20);

        args = new Bundle(); //Argumentos para el menu de opciones
        args.putString("iu", "ajustes");

    }

    /** El Fragment va a cargar su layout, el cual debemos especificar.
     Aquí se instanciarán los objetos que si son vistas */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_ajustes, container, false);
        if(v != null){
            listView = v.findViewById(R.id.listviewA);
            listView2 = v.findViewById(R.id.listview2A);
            listView3 = v.findViewById(R.id.listview3A);
            listView4 = v.findViewById(R.id.listview4A);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        /*------------------
         | Idioma de la app |
         ------------------*/

        opc1 = getResources().getString(R.string.ajustes2);

        ArrayList<String> lista1 = new ArrayList<>();
        lista1.add(opc1);

        listViewAdapter myAdapter = new listViewAdapter(getContext(), R.layout.list_idiomas, lista1);

        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener((adapterView, v, position, id) -> {
            fragment = Idiomas.newInstance();
            cargarFragment(fragment);
        });

        /*----------
         | Contacto |
         ----------*/

        opc2 = getResources().getString(R.string.contacto);

        ArrayList<String> lista2 = new ArrayList<>();
        lista2.add(opc2);

        listViewAdapter myAdapter2 = new listViewAdapter(getContext(), R.layout.list_contacto, lista2);
        listView2.setAdapter(myAdapter2);

        listView2.setOnItemClickListener((adapterView, v, position, id) -> {
            fragment = FormularioDeContacto.newInstance();
            cargarFragment(fragment);
        });

        /*-----------------
         | Agradecimientos |
         -----------------*/

        opc3 = getResources().getString(R.string.agradecimientos);

        ArrayList<String> lista3 = new ArrayList<>();
        lista3.add(opc3);

        listViewAdapter myAdapter3 = new listViewAdapter(getContext(), R.layout.list_agradecimientos, lista3);
        listView3.setAdapter(myAdapter3);

        listView3.setOnItemClickListener((adapterView, v, position, id) -> {
            fragment = Agradecimientos.newInstance();
            cargarFragment(fragment);
        });

        /*---------------
         | Quienes somos |
         ---------------*/

        opc4 = getResources().getString(R.string.quienes_somos);

        ArrayList<String> lista4 = new ArrayList<>();
        lista4.add(opc4);

        listViewAdapter myAdapter4 = new listViewAdapter(getContext(), R.layout.list_quienesomos, lista4);
        listView4.setAdapter(myAdapter4);

        listView4.setOnItemClickListener((adapterView, v, position, id) -> {
            fragment = QuienesSomos.newInstance();
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