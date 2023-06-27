package com.example.tfg.ajustes.administrador;

import static android.service.controls.ControlsProviderService.TAG;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tfg.R;
import com.example.tfg.ajustes.administrador.dialogs.confirmModifDelete;
import com.example.tfg.ajustes.administrador.dialogs.whatToDo_Admin;
import com.example.tfg.categorias.secundarias.cultura.diccionario.Palabra;
import com.example.tfg.categorias.secundarias.cultura.diccionario.definicionpalabra;
import com.example.tfg.espacioDelViajero.alojamiento.Alojamiento;
import com.example.tfg.espacioDelViajero.comercio.Comercio;
import com.example.tfg.espacioDelViajero.restauracion.Establecimiento;
import com.example.tfg.otherFiles.adapters.SpinnerAdapter;
import com.example.tfg.otherFiles.adapters.listViewAdapter;
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
import java.util.Objects;

public class elegirOpcionModDel extends Fragment {

    Bundle args;
    Fragment fragment;
    Toolbar myToolbar;
    private Spinner spinner;
    String [] opcionesModifElim;
    private listViewAdapter myAdapter;
    List<String> listanombres;
    ListView listView;
    String origen, opcionElegida;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static elegirOpcionModDel newInstance(Bundle args) {
        elegirOpcionModDel fragment = new elegirOpcionModDel();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public elegirOpcionModDel() {}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        TextView name = myToolbar.findViewById(R.id.name);
        name.setText(getResources().getString(R.string.administracion_ajustes));
        name.setTextSize(20);
        myToolbar.setNavigationOnClickListener(view12 -> {
            myToolbar.setNavigationIcon(null);
            Fragment fragment = administrador_inicio.newInstance();
            cargarFragment(fragment);
        });

        if (getArguments() != null) {
            opcionElegida = getArguments().getString("choose");
            origen = getArguments().getString("origen");
        }

        args = new Bundle();

        args.putString("choose", opcionElegida);
        args.putString("origen", origen);

    }

    /** El Fragment va a cargar su layout, el cual debemos especificar.
     Aquí se instanciarán los objetos que si son vistas */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_elegiropcion_moddel, container, false);
        if(v != null){
            listView = v.findViewById(R.id.listviewAdmin);
            spinner = v.findViewById(R.id.spinnerAdmin);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        switch (origen){
            case "comercio":
                opcionesModifElim = getResources().getStringArray(R.array.spinnerComercio);
                break;
            case "alojamiento":
                if (opcionElegida.equals("modif")){
                    opcionesModifElim = getResources().getStringArray(R.array.spinnerAlojamientoModif);
                } else {
                    opcionesModifElim = getResources().getStringArray(R.array.spinnerAlojamientoDelete);
                }
                break;
            case "hosteleria":
                opcionesModifElim = getResources().getStringArray(R.array.spinnerHosteleria);
                break;
            case "servicios":
                opcionesModifElim = getResources().getStringArray(R.array.spinnerServicios);
                break;
            case "diccionario":
                opcionesModifElim = getResources().getStringArray(R.array.spinnerDiccionario);
                break;

        }

        spinner.setAdapter(new SpinnerAdapter(requireContext(), R.layout.dropsownitemsimple, opcionesModifElim));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String categoria = (String) parent.getItemAtPosition(position);
                args.putString("categoria", categoria);
                cargarLista(categoria);
            }

            @Override
            public void onNothingSelected (AdapterView < ? > parent){}
        });
    }



    public void cargarLista (String categoria) {

        //Instancia a la base de datos
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        //apuntamos al nodo que queremos leer
        DatabaseReference myRef = fdb.getReference().child(origen).child(categoria.toLowerCase().replace(" ", ""));

        //Agregamos un ValueEventListener para que los cambios que se hagan en la base de datos
        //se reflejen en la aplicacion
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {

            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()) {

                    switch (origen) {
                        case "comercio":
                            LinkedList<Comercio> ps = new LinkedList<>();
                            HashMap dataMap = (HashMap) dataSnapshot.getValue();
                            assert dataMap != null;
                            for (Object key : dataMap.keySet()) {
                                Object datos = dataMap.get(key);
                                HashMap userData = (HashMap) datos;
                                assert userData != null;
                                Comercio comercio = new Comercio((String) key, (String) userData.get("ubicacion"), (String) userData.get("telefono"));
                                ps.add(comercio);
                            }

                            listanombres = obtenerListaNombresComercio(ps);

                            myAdapter = new listViewAdapter(getContext(), R.layout.list_alim, listanombres);
                            listView.setAdapter(myAdapter);

                            listView.setOnItemClickListener((adapterView, v, position, id) -> {
                                //Obtenemos el nombre del elemento pulsado y cargamos su información
                                String nombreTienda = adapterView.getItemAtPosition(position).toString();
                                args.putParcelable("comercio", buscarComercio(nombreTienda, ps));
                                if (opcionElegida.equals("del")){
                                    cargarDialogFragment();
                                } else {
                                    fragment = addModif.newInstance(args);
                                    cargarFragment(fragment);
                                }
                            });
                            break;

                        case "alojamiento":
                            LinkedList<Alojamiento> ps2 = new LinkedList<>();
                            HashMap dataMap2 = (HashMap) dataSnapshot.getValue();
                            assert dataMap2 != null;
                            for (Object key: dataMap2.keySet()){
                                Object datos = dataMap2.get(key);
                                HashMap userData = (HashMap) datos;
                                assert userData != null;
                                Alojamiento alojamiento = new Alojamiento((String) key, (String) userData.get("ubicacion"),
                                        (String) userData.get("telefono"), (Double) userData.get("puntuación"));
                                ps2.add(alojamiento);
                            }

                            //Por defecto, la opción seleccionada será "Ordenar alfabéticamente ascendente"
                            listanombres = getListaNombresAlojamiento(ps2);

                            myAdapter = new listViewAdapter(getContext(), R.layout.list_apart, listanombres);
                            listView.setAdapter(myAdapter);

                            listView.setOnItemClickListener((adapterView, v, position, id) -> {
                                //Obtenemos el nombre del elemento pulsado y cargamos su información
                                String nombreAloj = adapterView.getItemAtPosition(position).toString();
                                args.putParcelable("aloj", buscarAloj(nombreAloj, ps2));
                                if (opcionElegida.equals("del")){
                                    cargarDialogFragment();
                                } else {
                                    fragment = addModif.newInstance(args);
                                    cargarFragment(fragment);
                                }
                            });
                            break;

                        case "hosteleria":
                            LinkedList<Establecimiento> ps3 = new LinkedList<>();
                            HashMap dataMap3 = (HashMap) dataSnapshot.getValue();
                            assert dataMap3 != null;
                            for (Object key: dataMap3.keySet()){
                                Object datos = dataMap3.get(key);
                                HashMap userData = (HashMap) datos;
                                assert userData != null;
                                Establecimiento establecimiento = new Establecimiento((String) key, (String) userData.get("ubicacion"),
                                        (String) userData.get("telefono"), (Double) userData.get("puntuación"));
                                ps3.add(establecimiento);
                            }

                            //Por defecto, la opción seleccionada será "Ordenar alfabéticamente ascendente"
                            listanombres = getListaNombresEstablecimiento(ps3);

                            myAdapter = new listViewAdapter(getContext(), R.layout.list_apart, listanombres);
                            listView.setAdapter(myAdapter);

                            listView.setOnItemClickListener((adapterView, v, position, id) -> {
                                //Obtenemos el nombre del elemento pulsado y cargamos su información
                                String nombreRest = adapterView.getItemAtPosition(position).toString();
                                args.putParcelable("establ", buscarEst(nombreRest, ps3));
                                if (opcionElegida.equals("del")){
                                    cargarDialogFragment();
                                } else {
                                    fragment = addModif.newInstance(args);
                                    cargarFragment(fragment);
                                }
                            });
                            break;

                        case "diccionario":
                            LinkedList<Palabra> ps4 = new LinkedList<>();
                            HashMap dataMap4 = (HashMap) dataSnapshot.getValue();
                            assert dataMap4 != null;
                            for (Object key: dataMap4.keySet()){
                                Object datos = dataMap4.get(key);
                                HashMap userData = (HashMap) datos;
                                assert userData != null;
                                Palabra palabra = new Palabra((String) key, (String) userData.get("descrEs"), (String) userData.get("descrEu"), (String) userData.get("descrEn"));
                                ps4.add(palabra);
                            }

                            List<String> listaNombres = obtenerListaPalabras(ps4);

                            listViewAdapter myAdapter = new listViewAdapter(getContext(), R.layout.list_apart, listaNombres);
                            listView.setAdapter(myAdapter);

                            listView.setOnItemClickListener((adapterView, v, position, id) -> {
                                //Obtenemos el nombre del elemento pulsado y cargamos su información
                                String palabra = adapterView.getItemAtPosition(position).toString();
                                args.putString("palabra", palabra);
                                args.putParcelable("palabra", buscarPalabra(ps4, palabra));
                                cargarDialogFragment();
                            });
                            break;
                    }
                    
                    
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

    public List<String> obtenerListaNombresComercio(List<Comercio> est){

        List<String> lista = new LinkedList<>();
        for (int i = 0; i < est.size(); i++){
            lista.add(est.get(i).getNombreComercio());
        }

        organizedAlphabeticList(lista);

        return lista;
    }

    public Comercio buscarComercio(String nombreCom, List<Comercio> com){
        for (int i = 0; i < com.size(); i++){
            Comercio comr = com.get(i);
            if (comr.getNombreComercio().equalsIgnoreCase(nombreCom)){
                return comr;
            }
        }
        return null;
    }

    //Utilizando la Clase Collator que actúa como comparadora de cadena para solucionar el error de las tildes
    
    public List<String> getListaNombresAlojamiento(LinkedList<Alojamiento> est){

        List<String> lista = new LinkedList<>();
        for (int i = 0; i < Objects.requireNonNull(est).size(); i++){
            lista.add(est.get(i).getNombreAloj());
        }

        organizedAlphabeticList(lista);

        return lista;

    }

    public Alojamiento buscarAloj(String nombreAloj, List<Alojamiento> aloj){
        for (int i = 0; i <aloj.size(); i++){
            Alojamiento aloja = aloj.get(i);
            if (aloja.getNombreAloj().equalsIgnoreCase(nombreAloj)){
                return aloja;
            }
        }
        return null;
    }

    public List<String> getListaNombresEstablecimiento(List<Establecimiento> est){

        List<String> lista = new LinkedList<>();
        for (int i = 0; i < Objects.requireNonNull(est).size(); i++){
            lista.add(est.get(i).getNombreEstabl());
        }

        organizedAlphabeticList(lista);

        return lista;
    }

    public Establecimiento buscarEst(String nombreAloj, List<Establecimiento> est){
        for (int i = 0; i <est.size(); i++){
            Establecimiento estbl = est.get(i);
            if (estbl.getNombreEstabl().equalsIgnoreCase(nombreAloj)){
                return estbl;
            }
        }
        return null;
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
        organizedAlphabeticList(lista);
        return lista;
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

    private void cargarDialogFragment(){
        DialogFragment fragment = new confirmModifDelete();
        fragment.setArguments(args);
        fragment.setCancelable(false);
        fragment.show(getChildFragmentManager(),"fragment");
    }

    public static void organizedAlphabeticList(List<String> list) {
        list.sort(new Comparator<String>() {
            final Collator collator = Collator.getInstance();

            public int compare(String o1, String o2) {
                return collator.compare(o1, o2);
            }
        });
    }
    

}