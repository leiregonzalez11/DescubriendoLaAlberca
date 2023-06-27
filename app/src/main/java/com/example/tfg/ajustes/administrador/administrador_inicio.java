package com.example.tfg.ajustes.administrador;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tfg.R;
import com.example.tfg.ajustes.administrador.dialogs.whatToDo_Admin;
import com.example.tfg.navigationMenu.Ajustes;
import com.example.tfg.otherFiles.adapters.listViewAdapter;

import java.util.ArrayList;

public class administrador_inicio extends Fragment {


    Bundle args;
    Fragment fragment;
    Toolbar myToolbar;
    ListView listView, listView2, listView3, listView5;
    String opc1, opc2, opc3, opc5, origen;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static administrador_inicio newInstance() {
        return new administrador_inicio();
    }

    /** Required empty public constructor */
    public administrador_inicio() {}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(null);
        TextView name = myToolbar.findViewById(R.id.name);
        name.setText(R.string.ajustes);
        name.setTextSize(20);
        myToolbar.setNavigationOnClickListener(view12 -> {
            myToolbar.setNavigationIcon(null);
            Fragment fragment = Ajustes.newInstance();
            cargarFragment(fragment);
        });

        args = new Bundle(); //Argumentos para el menu de opciones
        args.putString("iu", "ajustes");

    }

    /** El Fragment va a cargar su layout, el cual debemos especificar.
     Aquí se instanciarán los objetos que si son vistas */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_administrador_inicio, container, false);
        if(v != null){
            listView = v.findViewById(R.id.listviewA1);
            listView2 = v.findViewById(R.id.listviewA2);
            listView3 = v.findViewById(R.id.listviewA3);
            listView5 = v.findViewById(R.id.listviewA5);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    /*-----------
     | Comercios |
     -----------*/

        opc1 = "Comercios";

        ArrayList<String> lista1 = new ArrayList<>();
        lista1.add(opc1);

        listViewAdapter myAdapter = new listViewAdapter(getContext(), R.layout.list_comercio, lista1);

        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener((adapterView, v, position, id) -> {
            origen = "comercio";
            cargarDialogFragment();
        });

    /*------------
     | Hostelería |
     ------------*/

        opc2 = "Hostelería";

        ArrayList<String> lista2 = new ArrayList<>();
        lista2.add(opc2);

        listViewAdapter myAdapter2 = new listViewAdapter(getContext(), R.layout.list_comer, lista2);
        listView2.setAdapter(myAdapter2);

        listView2.setOnItemClickListener((adapterView, v, position, id) -> {
            origen = "hosteleria";
            cargarDialogFragment();
        });

    /*-------------
     | Alojamiento |
     -------------*/

        opc3 = "Alojamiento";

        ArrayList<String> lista3 = new ArrayList<>();
        lista3.add(opc3);

        listViewAdapter myAdapter3 = new listViewAdapter(getContext(), R.layout.list_dormir, lista3);
        listView3.setAdapter(myAdapter3);

        listView3.setOnItemClickListener((adapterView, v, position, id) -> {
            origen = "alojamiento";
            cargarDialogFragment();
        });

    /*-------------
     | Diccionario |
     -------------*/

        opc5 = "Diccionario";

        ArrayList<String> lista5 = new ArrayList<>();
        lista5.add(opc5);

        listViewAdapter myAdapter5 = new listViewAdapter(getContext(), R.layout.list_idiomas, lista5);
        listView5.setAdapter(myAdapter5);

        listView5.setOnItemClickListener((adapterView, v, position, id) -> {
            origen = "diccionario";
            cargarDialogFragment();
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

    private void cargarDialogFragment(){
        DialogFragment fragment = new whatToDo_Admin();
        args.putString("origen", origen);
        fragment.setArguments(args);
        fragment.setCancelable(false);
        fragment.show(getChildFragmentManager(),"WHATTODOFRAGMENT");
    }

}