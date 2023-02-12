package com.example.tfg.categoriasFragments.secundarias.cultura.diccionario;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.adapters.listViewAdapter;
import java.util.List;
import java.util.Locale;

public class listapalabrasf extends DialogFragment {

    private final Bundle args = new Bundle();

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View infomView = inflater.inflate(R.layout.fragment_listapalabras, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(infomView);

        assert getArguments()!=null;
        String letra = getArguments().getString("letra");
        String idioma = getArguments().getString("idioma");

        args.putString("idioma", idioma);

        TextView letraTV = infomView.findViewById(R.id.letratitulo);
        if (letra.length() == 1){
            letraTV.setText(letra.toUpperCase(Locale.ROOT));
        } else if (letra.length() == 2){
            String tit = letra.charAt(0) + " - " + letra.charAt(1);
            letraTV.setText(tit.toUpperCase());
        }else if (letra.length() == 3){
            String tit = letra.charAt(0) + " - " + letra.charAt(1) + " - " + letra.charAt(2);
            letraTV.setText(tit.toUpperCase());
        }

        ListaPalabras palabras = new ListaPalabras(requireContext(), idioma);
        List <String> lista = palabras.obtenerListaPalabras(letra);

        ListView listView = infomView.findViewById(R.id.listviewpalabras);

        listViewAdapter myAdapter = new listViewAdapter(getContext(), R.layout.list_apart, lista);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener((adapterView, v, position, id) -> {
            //Obtenemos el nombre del elemento pulsado y cargamos su información
            String palabra = adapterView.getItemAtPosition(position).toString();
            args.putString("palabra", palabra);
            DialogFragment defFr = new definicionpalabra();
            defFr.setArguments(args);
            defFr.setCancelable(false);
            defFr.show(getChildFragmentManager(),"def_fragment");
        });

        Button volver = infomView.findViewById(R.id.buttonVolverPalabras);
        volver.setOnClickListener(view -> dismiss());

        return builder.create();
    }

}