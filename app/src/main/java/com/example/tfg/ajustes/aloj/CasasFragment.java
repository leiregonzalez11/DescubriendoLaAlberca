package com.example.tfg.ajustes.aloj;

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

public class CasasFragment extends Fragment {

    ArrayList lista1;

    public CasasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_casas, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        ListView listView = (ListView) getView().findViewById(R.id.listviewCasas);

        lista1 = new ArrayList<>();
        lista1.add("El Palaero");
        lista1.add("Casitas del Huerto");
        lista1.add("El Aserradero");
        lista1.add("Casa del tablao");
        lista1.add("Casa La Tía Bruja");
        lista1.add("Casa Rural Espeñitas");
        lista1.add("La Esquina de Ánimas");
        lista1.add("Castillo Alto");

        listViewAdapter myAdapter = new listViewAdapter(getContext(), R.layout.list_item, lista1);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener((adapterView, view, position, id) -> Toast.makeText(getActivity().getApplicationContext(), "Has pulsado: "+ lista1.get(position), Toast.LENGTH_LONG).show());

    }

}