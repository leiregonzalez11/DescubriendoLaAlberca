package com.example.tfg.ajustes.aloj;

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

public class HotelesFragment extends Fragment {

    ArrayList lista1;
    String idioma, nombreAloj;

    public HotelesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hoteles, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        ListView listView = (ListView) getView().findViewById(R.id.listviewHoteles);

        lista1 = new ArrayList<>();

        lista1.add("Camping Al-Bereka");
        lista1.add("Hostal El Castillo");
        lista1.add("Hostal La Alberca");
        lista1.add("Hostal La Balsá");
        lista1.add("Hostal San Blas");
        lista1.add("Hotel Antiguas Eras La Alberca");
        lista1.add("Hotel Doña Teresa");
        lista1.add("Hotel Las Batuecas");
        lista1.add("Hotel Termal Abadía de los Templarios");


        listViewAdapter myAdapter = new listViewAdapter(getContext(), R.layout.list_item, lista1);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            //Toast.makeText(getActivity().getApplicationContext(), "Has pulsado: "+ lista1.get(position), Toast.LENGTH_LONG).show()

            nombreAloj = lista1.get(position).toString();

            Intent aloj = new Intent(getContext(), alojamientoActivity.class);
            aloj.putExtra("nombreAloj", nombreAloj);
            startActivity(aloj);

        });

    }

}