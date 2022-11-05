package com.example.tfg.ajustesFragments.restauracion;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.adapters.listViewAdapter;
import com.example.tfg.ajustes.rest.establecimientoActivity;

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

        GestorDB dbHelper = new GestorDB(getContext());

        lista1 = dbHelper.obtenerlistaRestaurantes("restaurante", "restaurante");

        listViewAdapter myAdapter = new listViewAdapter(getContext(), R.layout.list_rest, lista1);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            //Toast.makeText(getActivity().getApplicationContext(), "Has pulsado: "+ lista1.get(position), Toast.LENGTH_LONG).show();
            nombreRest = lista1.get(position).toString();

            Intent rest = new Intent(getContext(), establecimientoActivity.class);
            rest.putExtra("nombreRest", nombreRest);
            startActivity(rest);
        });
    }

}