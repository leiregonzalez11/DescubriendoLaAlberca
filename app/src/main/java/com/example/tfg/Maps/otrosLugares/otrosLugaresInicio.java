package com.example.tfg.Maps.otrosLugares;

import android.os.Bundle;
import com.example.tfg.R;
import android.view.View;
import java.util.ArrayList;

import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup;
import android.widget.ListView;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import com.example.tfg.OtherFiles.Adapters.listViewAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.example.tfg.NavigationMenu.Maps;

public class otrosLugaresInicio extends Fragment {

    private Bundle args;
    private String idioma;
    private String opc1;
    private String opc4;
    private String opc5;
    private ListView listView, listView2, listView3, listView4, listView5;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static otrosLugaresInicio newInstance(Bundle args) {
        otrosLugaresInicio fragment = new otrosLugaresInicio();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public otrosLugaresInicio() {}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        TextView name = myToolbar.findViewById(R.id.name);
        name.setText(R.string.mapa);
        name.setTextSize(20);
        myToolbar.setNavigationOnClickListener(view12 -> {
            myToolbar.setNavigationIcon(null);
            Fragment fragment = Maps.newInstance();
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
        View v =  inflater.inflate(R.layout.fragment_otros, container, false);
        if(v != null){
            listView = v.findViewById(R.id.listviewhurdes);
            listView2 = v.findViewById(R.id.listviewpeñafrancia);
            listView3 = v.findViewById(R.id.listviewpueblos);
            listView4 = v.findViewById(R.id.listviewbatuecas);
            listView5 = v.findViewById(R.id.listviewmajadas);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        opc1 = getString(R.string.hurdes);
        String opc2 = getString(R.string.penafrancia);
        String opc3 = getString(R.string.pueblos_de_la_sierra);
        opc4 = getString(R.string.batuecas);
        opc5 = "Majadas Viejas y alrededores";

        //Majadas Viejas y Laguna de San Marcos
        ArrayList<String> lista5 = new ArrayList<>();
        lista5.add(opc5);

        listViewAdapter myAdapter5 = new listViewAdapter(getContext(), R.layout.list_monte, lista5);
        listView5.setAdapter(myAdapter5);

        listView5.setOnItemClickListener((adapterView, v, position, id) ->
                Toast.makeText(getContext(), "Has pulsado: "+ opc5, Toast.LENGTH_LONG).show());

        //Hurdes
        ArrayList<String> lista1 = new ArrayList<>();
        lista1.add(opc1);

        listViewAdapter myAdapter = new listViewAdapter(getContext(), R.layout.list_bosque, lista1);

        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener((adapterView, v, position, id) ->
                Toast.makeText(getContext(), "Has pulsado: "+ opc1, Toast.LENGTH_LONG).show());


        //Peña de Francia
        ArrayList<String> lista2 = new ArrayList<>();
        lista2.add(opc2);

        listViewAdapter myAdapter2 = new listViewAdapter(getContext(), R.layout.list_monte, lista2);
        listView2.setAdapter(myAdapter2);

        listView2.setOnItemClickListener((adapterView, v, position, id) -> {
            args.putString("back", "false");
            Fragment fragment = penaDeFrancia.newInstance(args);
            cargarFragment(fragment);
        });


        //Pueblos
        ArrayList<String> lista3 = new ArrayList<>();
        lista3.add(opc3);

        listViewAdapter myAdapter3 = new listViewAdapter(getContext(), R.layout.list_pueblo, lista3);
        listView3.setAdapter(myAdapter3);

        listView3.setOnItemClickListener((adapterView, v, position, id) -> {
            Fragment fragment = otrosPueblos.newInstance(args);
            cargarFragment(fragment);
        });


        //Batuecas
        ArrayList<String> lista4 = new ArrayList<>();
        lista4.add(opc4);

        listViewAdapter myAdapter4 = new listViewAdapter(getContext() , R.layout.list_bosque, lista4);
        listView4.setAdapter(myAdapter4);

        listView4.setOnItemClickListener((adapterView, v, position, id) ->
                Toast.makeText(getContext(), "Has pulsado: "+ opc4, Toast.LENGTH_LONG).show());


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