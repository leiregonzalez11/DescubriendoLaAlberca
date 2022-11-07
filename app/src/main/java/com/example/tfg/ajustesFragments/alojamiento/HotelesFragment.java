package com.example.tfg.ajustesFragments.alojamiento;

import android.annotation.SuppressLint;
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
import com.example.tfg.anterior.ajustes.aloj.alojamientoActivity;

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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        ListView listView = requireView().findViewById(R.id.listviewHoteles);

        GestorDB dbHelper = new GestorDB(getContext());

        lista1 = dbHelper.obtenerlistaAlojamientos("alojamiento", "hotel");


        listViewAdapter myAdapter = new listViewAdapter(getContext(), R.layout.list_hotel, lista1);
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