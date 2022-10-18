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
import com.example.tfg.listViewAdapter;

import java.util.ArrayList;

public class RestaurantesFragment extends Fragment {

    ArrayList lista1;
    String nombreRest;

    public RestaurantesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurantes, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        ListView listView = (ListView) getView().findViewById(R.id.listviewRest);

        lista1 = new ArrayList<>();
        lista1.add("El Balcón de la Plaza");
        lista1.add("La Parrilla");
        lista1.add("Mesón La Colmena");
        lista1.add("¡Oh! Espacio del Jamón");
        lista1.add("Restaurante El Encuentro");
        lista1.add("Restaurante El Soportal");
        lista1.add("Restaurante Ibéricos de la Alberca Doña Consuelo");
        lista1.add("Restaurante La Cantina de Elías");
        lista1.add("Restaurante La Catedral");
        lista1.add("Restaurante La Taberna");

        listViewAdapter myAdapter = new listViewAdapter(getContext(), R.layout.list_item, lista1);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            Toast.makeText(getActivity().getApplicationContext(), "Has pulsado: "+ lista1.get(position), Toast.LENGTH_LONG).show();
            nombreRest = lista1.get(position).toString();

            /*Intent est = new Intent(getContext(), establecimientoActivity.class);
            est.putExtra("nombreRest", nombreRest);
            startActivity(est);*/
        });
    }

}