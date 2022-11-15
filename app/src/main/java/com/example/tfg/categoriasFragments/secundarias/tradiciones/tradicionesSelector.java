package com.example.tfg.categoriasFragments.secundarias.tradiciones;


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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tfg.R;
import com.example.tfg.adapters.listViewAdapter;
import com.example.tfg.categoriasFragments.principal.tradicionesInicio;
import com.example.tfg.navigationmenu.Categorias;

import java.util.ArrayList;


public class tradicionesSelector extends Fragment {

    Bundle args;
    private String idioma, categoria;

    public tradicionesSelector() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(false);

        if (getArguments() != null) {
            idioma = getArguments().getString("idioma");
            categoria = getArguments().getString("categoria");
        }

        args = new Bundle();
        args.putString("idioma", idioma);
        args.putString("categoria", categoria);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tradiciones_selector, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.arrow_back);
        myToolbar.setNavigationOnClickListener(v -> {

            myToolbar.setNavigationIcon(null);
            Fragment fragment = new Categorias();

            // Obtenemos el administrador de fragmentos a través de la actividad
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

            // Definimos una transacción
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Remplazamos el contenido principal por el fragmento
            fragmentTransaction.replace(R.id.relativelayout, fragment);
            fragmentTransaction.addToBackStack(null);

            // Cambiamos el fragment en la interfaz
            fragmentTransaction.commit();
        });

        ListView listView = requireView().findViewById(R.id.listviewtrad1);
        ListView listView2 = requireView().findViewById(R.id.listviewtrad2);
        ListView listView3 = requireView().findViewById(R.id.listviewtrad3);
        ListView listView4 = requireView().findViewById(R.id.listviewtrad4);
        ListView listView5 = requireView().findViewById(R.id.listviewtrad5);
        ListView listView6 = requireView().findViewById(R.id.listviewtrad6);

        String opc1 = "Alboradas";
        String opc2 = "El Marrano de San Antón";
        String opc3 = "El Pendón";
        String opc4 = "La Loa";
        String opc5 = "La Moza de Ánimas";
        String opc6 = getString(R.string.otras_tradiciones);

        ArrayList<String> lista1 = new ArrayList<>();
        lista1.add(opc1);

        listViewAdapter myAdapter = new listViewAdapter(getContext(), R.layout.list_item, lista1);

        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener((adapterView, v, position, id) ->
                Toast.makeText(getContext(), "Has pulsado: "+ opc1, Toast.LENGTH_LONG).show());

        ArrayList<String> lista2 = new ArrayList<>();
        lista2.add(opc2);

        listViewAdapter myAdapter2 = new listViewAdapter(getContext(), R.layout.list_item, lista2);
        listView2.setAdapter(myAdapter2);

        listView2.setOnItemClickListener((adapterView, v, position, id) ->
                Toast.makeText(getContext(), "Has pulsado: "+ opc2, Toast.LENGTH_LONG).show());

        ArrayList<String> lista3 = new ArrayList<>();
        lista3.add(opc3);

        listViewAdapter myAdapter3 = new listViewAdapter(getContext(), R.layout.list_item, lista3);
        listView3.setAdapter(myAdapter3);

        listView3.setOnItemClickListener((adapterView, v, position, id) ->
                Toast.makeText(getContext(), "Has pulsado: "+ opc3, Toast.LENGTH_LONG).show());

        ArrayList<String> lista4 = new ArrayList<>();
        lista4.add(opc4);

        listViewAdapter myAdapter4 = new listViewAdapter(getContext(), R.layout.list_item, lista4);
        listView4.setAdapter(myAdapter4);

        listView4.setOnItemClickListener((adapterView, v, position, id) ->
                Toast.makeText(getContext(), "Has pulsado: "+ opc4, Toast.LENGTH_LONG).show());

        //
        ArrayList<String> lista5 = new ArrayList<>();
        lista5.add(opc5);

        listViewAdapter myAdapter5 = new listViewAdapter(getContext(), R.layout.list_item, lista5);
        listView5.setAdapter(myAdapter5);

        listView5.setOnItemClickListener((adapterView, v, position, id) ->
                Toast.makeText(getContext(), "Has pulsado: "+ opc5, Toast.LENGTH_LONG).show());

        ArrayList<String> lista6 = new ArrayList<>();
        lista6.add(opc6);

        listViewAdapter myAdapter6 = new listViewAdapter(getContext(), R.layout.list_item, lista6);
        listView6.setAdapter(myAdapter6);

        listView6.setOnItemClickListener((adapterView, v, position, id) ->
                Toast.makeText(getContext(), "Has pulsado: "+ opc6, Toast.LENGTH_LONG).show());

        //BOTON SIGUIENTE y ATRAS

        Button atrasBtn = requireView().findViewById(R.id.tradAtras2);
        atrasBtn.setOnClickListener(v -> {
            Fragment fragment = new tradicionesInicio();
            fragment.setArguments(args);

            // Obtenemos el administrador de fragmentos a través de la actividad
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

            // Definimos una transacción
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Remplazamos el contenido principal por el fragmento
            fragmentTransaction.replace(R.id.relativelayout, fragment);
            fragmentTransaction.addToBackStack(null);

            // Cambiamos el fragment en la interfaz
            fragmentTransaction.commit();
        });

    }
}