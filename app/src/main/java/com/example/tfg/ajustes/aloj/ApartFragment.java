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
        ListView listView = (ListView) requireView().findViewById(R.id.listviewApart);

        lista1 = new ArrayList<>();
        lista1.add("Apartamentos Anita");
        lista1.add("Apartamento La Campanina");
        lista1.add("Villadolores");
        lista1.add("Apartamentos Rurality Home");


        listViewAdapter myAdapter = new listViewAdapter(getContext(), R.layout.list_item, lista1);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener((adapterView, view, position, id) -> Toast.makeText(requireActivity().getApplicationContext(), "Has pulsado: "+ lista1.get(position), Toast.LENGTH_LONG).show());

    }

}