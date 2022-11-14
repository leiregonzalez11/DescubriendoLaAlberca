package com.example.tfg.ajustesFragments.comercio;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.adapters.listViewAdapter;

import java.util.ArrayList;

public class AlimentacionTienda extends Fragment {

    ArrayList lista1;
    String nombreRest;

    public AlimentacionTienda() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alimentacion, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ListView listView = (ListView) requireView().findViewById(R.id.listviewAlimentacion);

        GestorDB dbHelper = new GestorDB(getContext());

        lista1 = dbHelper.obtenerlistaRestaurantes("restaurante", "bar");

        listViewAdapter myAdapter = new listViewAdapter(getContext(), R.layout.list_alim, lista1);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener((adapterView, v, position, id) -> {
            Toast.makeText(requireActivity().getApplicationContext(), "Has pulsado: "+ lista1.get(position), Toast.LENGTH_LONG).show();
            nombreRest = lista1.get(position).toString();

        });

    }


}