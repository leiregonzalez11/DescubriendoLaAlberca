package com.example.tfg.ajustes.rest;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.tfg.R;
import com.example.tfg.ajustes.aloj.alojamientoActivity;
import com.example.tfg.listViewAdapter;

import java.util.ArrayList;

public class BaresFragment extends Fragment {

    ArrayList lista1;
    String nombreRest;

    public BaresFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bares, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        ListView listView = (ListView) getView().findViewById(R.id.listviewBares);

        lista1 = new ArrayList<>();
        lista1.add("Bar El Porrón");
        lista1.add("Bar El Rincón de Lola");
        lista1.add("Bar La Peña");
        lista1.add("Bar Marcos");
        lista1.add("Bar La Balsá");
        lista1.add("Bar La Nogal");
        lista1.add("La Barrera");
        lista1.add("Bar Restaurante 1830");
        lista1.add("Tetería Singular");

        listViewAdapter myAdapter = new listViewAdapter(getContext(), R.layout.list_item, lista1);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            Toast.makeText(getActivity().getApplicationContext(), "Has pulsado: "+ lista1.get(position), Toast.LENGTH_LONG).show();
            nombreRest = lista1.get(position).toString();

            /*Intent est = new Intent(getContext(), establecimientoActivity.class);
            est.putExtra("nombreRest", nombreRest);
            startActivity(rest);*/
        });
    }

}