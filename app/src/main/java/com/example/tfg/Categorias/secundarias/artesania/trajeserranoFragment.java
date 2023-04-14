package com.example.tfg.Categorias.secundarias.artesania;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.tfg.R;
import com.example.tfg.OtherFiles.Adapters.listViewAdapter;

import java.util.ArrayList;

public class trajeserranoFragment extends DialogFragment {

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View view = inflater.inflate(R.layout.fragment_trajeserrano, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view);

        Bundle args = new Bundle();

        assert getArguments() != null;
        String idioma = getArguments().getString("idioma");
        args.putString("idioma", idioma);


        /*---------------------------
         | El traje serrano femenino |
         ---------------------------*/

        String opc1 = "El traje femenino";

        ListView listView = view.findViewById(R.id.listviewfem);

        ArrayList<String> lista = new ArrayList<>();
        lista.add(opc1);

        listViewAdapter myAdapter = new listViewAdapter(getContext(), R.layout.listview_artesania, lista);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener((adapterView, v, position, id) -> {
            Fragment fragment = trajesFemeninos.newInstance(args);
            cargarFragment(fragment);
        });

        /*---------------------------
         | El traje serrano femenino |
         ---------------------------*/

        String opc2 = "El traje masculino";

        ListView listView2 = view.findViewById(R.id.listviewmasc);

        ArrayList<String> lista2 = new ArrayList<>();
        lista2.add(opc2);

        listViewAdapter myAdapter2 = new listViewAdapter(getContext(), R.layout.listview_artesania, lista2);
        listView2.setAdapter(myAdapter2);

        listView2.setOnItemClickListener((adapterView, v, position, id) -> {
            Fragment fragment = trajeMasculino.newInstance(args);
            cargarFragment(fragment);
        });

        Button volver = view.findViewById(R.id.buttonvolvertrajes);
        volver.setOnClickListener(v -> dismiss());

        return builder.create();
    }

    private void cargarFragment(Fragment fragment){
        // Obtenemos el administrador de fragmentos a través de la actividad
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        // Definimos una transacción
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // Remplazamos el contenido principal por el fragmento
        fragmentTransaction.replace(R.id.relativelayout, fragment);
        fragmentTransaction.addToBackStack(null);
        // Cambiamos el fragment en la interfaz
        fragmentTransaction.commit();
    }


}