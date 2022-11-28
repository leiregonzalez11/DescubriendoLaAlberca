package com.example.tfg.ajustesFragments.comercio;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.adapters.listViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ArtesaniaTienda extends Fragment implements SearchView.OnQueryTextListener {

    List<String> lista1 = new ArrayList<>();
    String nombreTienda;
    Bundle args;
    listViewAdapter myAdapter;
    SearchView editsearch;

    public ArtesaniaTienda() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        args = new Bundle();

        //TODO: Cambiar a COMERCIO
        args.putString("categoria", "alojamiento");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artesania, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ListView listView = (ListView) requireView().findViewById(R.id.listviewArtesania);

        GestorDB dbHelper = new GestorDB(getContext());

        //TODO: CAMBIAR A COMERCIOS
        lista1 = dbHelper.obtenerlistaAlojamientos("alojamiento", "apartamento");

        editsearch = (SearchView) requireView().findViewById(R.id.svArte);
        editsearch.setOnQueryTextListener(this);

        myAdapter = new listViewAdapter(getContext(), R.layout.list_arte, lista1);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener((adapterView, v, position, id) -> {
            //Obtenemos el nombre del elemento pulsado y cargamos su información
            nombreTienda = adapterView.getItemAtPosition(position).toString();
            Fragment fragment = new Tienda();
            args.putString("nombreCom", nombreTienda);
            fragment.setArguments(args);
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

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        myAdapter.getFilter().filter(s);
        return false;
    }

}