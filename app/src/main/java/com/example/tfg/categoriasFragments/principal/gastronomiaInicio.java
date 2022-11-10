package com.example.tfg.categoriasFragments.principal;

import android.os.Bundle;
import android.text.Html;
import com.example.tfg.R;
import android.view.View;
import java.util.ArrayList;
import android.widget.Toast;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.example.tfg.GestorDB;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.annotation.SuppressLint;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import com.example.tfg.adapters.listViewAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.example.tfg.categoriasFragments.secundarias.gastronomia.recetasTipicas;
import com.example.tfg.categoriasFragments.secundarias.gastronomia.turroneras;
import com.example.tfg.navigationmenu.Categorias;

public class gastronomiaInicio extends Fragment {

    Bundle args;
    String categoria, idioma, opc1, opc2;

    public gastronomiaInicio() {
        // Required empty public constructor
    }


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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gastronomia, container, false);
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

        GestorDB dbHelper = new GestorDB(getContext());

        String[] datos = dbHelper.obtenerDescrInterfaz(idioma, "inicio", categoria, 2);

        TextView text1 = requireView().findViewById(R.id.gastro11);
        TextView text2 = requireView().findViewById(R.id.gastro12);

        text1.setText(datos[0] + Html.fromHtml("<br>"));
        text2.setText(datos[1] + Html.fromHtml("<br>"));

        ListView listView = requireView().findViewById(R.id.listviewGastro1);
        ListView listView2 = requireView().findViewById(R.id.listviewGastro2);

        opc1 = "Las Turroneras";
        opc2 = "Recetas típicas";

        ArrayList<String> lista1 = new ArrayList<>();
        lista1.add(opc1);

        listViewAdapter myAdapter = new listViewAdapter(getContext(), R.layout.list_alim, lista1);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener((adapterView, v, position, id) -> {
            //Toast.makeText(getContext(), "Has pulsado: "+ opc1, Toast.LENGTH_LONG).show();
            Fragment fragment = new turroneras();
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

        ArrayList<String> lista2 = new ArrayList<>();
        lista2.add(opc2);

        listViewAdapter myAdapter2 = new listViewAdapter(getContext(), R.layout.list_rest, lista2);
        listView2.setAdapter(myAdapter2);

        listView2.setOnItemClickListener((adapterView, v, position, id) -> {
            //Toast.makeText(getContext(), "Has pulsado: "+ opc2, Toast.LENGTH_LONG).show();
            Fragment fragment = new recetasTipicas();
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