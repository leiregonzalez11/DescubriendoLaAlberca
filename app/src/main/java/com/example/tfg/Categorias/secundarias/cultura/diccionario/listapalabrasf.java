package com.example.tfg.Categorias.secundarias.cultura.diccionario;

import static android.service.controls.ControlsProviderService.TAG;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


import com.example.tfg.R;
import com.example.tfg.OtherFiles.Adapters.listViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
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

       setListView(infomView, letra);

        Button volver = infomView.findViewById(R.id.buttonVolverPalabras);
        volver.setOnClickListener(view -> dismiss());

        return builder.create();
    }

    public void setListView(View infomView, String letra){
        ListView listView = infomView.findViewById(R.id.listviewpalabras);

        //Instancia a la base de datos
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        //apuntamos al nodo que queremos leer
        DatabaseReference myRef = fdb.getReference().child("diccionario").child(letra);

        //Agregamos un ValueEventListener para que los cambios que se hagan en la base de datos
        //se reflejen en la aplicacion
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {

            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()){
                    LinkedList<Palabra> ps = new LinkedList<>();
                    HashMap dataMap = (HashMap) dataSnapshot.getValue();
                    assert dataMap != null;
                    for (Object key: dataMap.keySet()){
                        Object datos = dataMap.get(key);
                        HashMap userData = (HashMap) datos;
                        assert userData != null;
                        Palabra palabra = new Palabra((String) key, (String) userData.get("descrEs"), (String) userData.get("descrEu"), (String) userData.get("descrEn"));
                        ps.add(palabra);
                        Log.d(TAG, "Value of palabras is: " + palabra.getNombrePalabra());
                    }

                    List<String> listaNombres = obtenerListaPalabras(ps);

                    listViewAdapter myAdapter = new listViewAdapter(getContext(), R.layout.list_apart, listaNombres);
                    listView.setAdapter(myAdapter);

                    listView.setOnItemClickListener((adapterView, v, position, id) -> {
                        //Obtenemos el nombre del elemento pulsado y cargamos su información
                        String palabra = adapterView.getItemAtPosition(position).toString();
                        args.putString("palabra", palabra);
                        args.putParcelable("palabra", buscarPalabra(ps, palabra));

                        DialogFragment defFr = new definicionpalabra();
                        defFr.setArguments(args);
                        defFr.setCancelable(false);
                        defFr.show(getChildFragmentManager(),"def_fragment");
                    });
                }

            }


            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public Palabra buscarPalabra(List<Palabra> palabras1, String nombrePalabra){
        for (int i = 0; i< palabras1.size(); i++){
            Palabra palabra = palabras1.get(i);
            if (palabra.getNombrePalabra().equalsIgnoreCase(nombrePalabra)){
                return palabra;
            }
        }
        return null;
    }

    public List<String> obtenerListaPalabras(List<Palabra> palabras1){

        List<String> lista = new ArrayList<>();
        for (int i = 0; i< palabras1.size(); i++){
            Palabra palabra = palabras1.get(i);
            lista.add(palabra.getNombrePalabra());
        }
        return organizedAlphabeticList(lista);
    }

    //Utilizando la Clase Collator que actúa como comparadora de cadena para solucionar el error de las tildes
    public static List<String> organizedAlphabeticList(List<String> list) {
        list.sort(new Comparator<String>() {
            final Collator collator = Collator.getInstance();

            public int compare(String o1, String o2) {
                return collator.compare(o1, o2);
            }
        });
        return list;
    }

}