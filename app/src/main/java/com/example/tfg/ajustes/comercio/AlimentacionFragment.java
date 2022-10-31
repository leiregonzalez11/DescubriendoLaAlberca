package com.example.tfg.ajustes.comercio;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.adapters.listViewAdapter;
import com.example.tfg.ajustes.rest.establecimientoActivity;

import java.util.ArrayList;
import java.util.Objects;

public class AlimentacionFragment extends Fragment {

    ArrayList lista1;
    String nombreRest;

    public AlimentacionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alimentacion, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        ListView listView = (ListView) requireView().findViewById(R.id.listviewAlimentacion);

        GestorDB dbHelper = new GestorDB(getContext());

        lista1 = dbHelper.obtenerlistaRestaurantes("restaurante", "bar");

        listViewAdapter myAdapter = new listViewAdapter(getContext(), R.layout.list_alim, lista1);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            Toast.makeText(requireActivity().getApplicationContext(), "Has pulsado: "+ lista1.get(position), Toast.LENGTH_LONG).show();
            nombreRest = lista1.get(position).toString();

            /*Intent rest = new Intent(getContext(), establecimientoActivity.class);
            rest.putExtra("nombreRest", nombreRest);
            startActivity(rest);*/
        });
    }

}