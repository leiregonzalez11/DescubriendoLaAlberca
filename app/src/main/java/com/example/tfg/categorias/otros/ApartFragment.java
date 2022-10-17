package com.example.tfg.categorias.otros;

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

public class ApartFragment extends Fragment {

    ArrayList lista1;

    public ApartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_apart, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        ListView listView = (ListView) getView().findViewById(R.id.listviewApart);

        lista1 = new ArrayList<>();
        lista1.add("Cepeda");
        lista1.add("Herguijuela de la Sierra");
        lista1.add("Mogarraz");
        lista1.add("Madroñal");
        lista1.add("Miranda del Castañar");
        lista1.add("San Esteban de la Sierra ");
        lista1.add("San Martin del Castañar ");
        lista1.add("Sequeros");
        lista1.add("Sotoserrano");


        listViewAdapter myAdapter = new listViewAdapter(getContext(), R.layout.list_item, lista1);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener((adapterView, view, position, id) -> Toast.makeText(getActivity().getApplicationContext(), "Has pulsado: "+ lista1.get(position), Toast.LENGTH_LONG).show());

    }

}