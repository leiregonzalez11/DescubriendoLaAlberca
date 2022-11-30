package com.example.tfg.ajustesFragments.alojamiento;

import android.annotation.SuppressLint;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.adapters.listViewAdapter;
import com.example.tfg.ajustesFragments.restauracion.Bares;

import java.util.ArrayList;
import java.util.List;


public class CasasRurales extends Fragment implements SearchView.OnQueryTextListener{

    List<String> lista1 = new ArrayList<>();
    Bundle args;
    String nombreAloj;
    listViewAdapter myAdapter;
    SearchView editsearch;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment BlankFragment.
     */
    public static CasasRurales newInstance() {
        return new CasasRurales();
    }

    public CasasRurales() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);

        args = new Bundle();
        args.putString("categoria", "alojamiento");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_casas, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ListView listView = requireView().findViewById(R.id.listviewCasas);

        GestorDB dbHelper = new GestorDB(getContext());

        lista1 = dbHelper.obtenerlistaAlojamientos("alojamiento", "casarural");

        editsearch = (SearchView) requireView().findViewById(R.id.svCasas);
        editsearch.setOnQueryTextListener(this);

        myAdapter = new listViewAdapter(getContext(), R.layout.list_casas, lista1);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener((adapterView, v, position, id) -> {
            //Obtenemos el nombre del elemento pulsado y cargamos su información
            nombreAloj = adapterView.getItemAtPosition(position).toString();
            args.putString("nombreAloj", nombreAloj);
            Fragment fragment = Alojamiento.newInstance(args);
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