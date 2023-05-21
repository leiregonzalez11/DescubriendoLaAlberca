package com.example.tfg.EspacioDelViajero.alojamiento;

import static android.service.controls.ControlsProviderService.TAG;

import android.annotation.SuppressLint;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import com.example.tfg.R;
import com.example.tfg.OtherFiles.Adapters.listViewAdapter;
import com.example.tfg.OtherFiles.DialogFragments.ordenarFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class CasasRurales extends Fragment implements SearchView.OnQueryTextListener{

    Bundle args;
    String nombreAloj, ordenLista;
    ListView listView;
    ImageButton ordenarBtn;
    List<String> listanombres;
    List<Alojamiento> aloj;
    listViewAdapter myAdapter;
    SearchView editsearch;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static CasasRurales newInstance() {
        return new CasasRurales();
    }

    /** Required empty public constructor */
    public CasasRurales() {}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        args = new Bundle();
    }

    /** El Fragment va a cargar su layout, el cual debemos especificar.
     Aquí se instanciarán los objetos que si son vistas */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_casas, container, false);
        if(v != null){
            listView = v.findViewById(R.id.listviewCasas);
            ordenarBtn = v.findViewById(R.id.ordenarButton2);
            editsearch = v.findViewById(R.id.svCasas);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setListView(view, "casarural");
    }

    public void setListView(View infomView, String categoria){

        ListView listView = infomView.findViewById(R.id.listviewCasas);

        //Instancia a la base de datos
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        //apuntamos al nodo que queremos leer
        DatabaseReference myRef = fdb.getReference().child("alojamiento").child(categoria);

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
                    LinkedList<Alojamiento> ps = new LinkedList<>();
                    HashMap dataMap = (HashMap) dataSnapshot.getValue();
                    assert dataMap != null;
                    for (Object key: dataMap.keySet()){
                        Object datos = dataMap.get(key);
                        HashMap userData = (HashMap) datos;
                        assert userData != null;
                        Alojamiento alojamiento = new Alojamiento((String) key, (String) userData.get("ubicacion"),
                                (String) userData.get("telefono"), (Double) userData.get("puntuación"));
                        ps.add(alojamiento);
                        Log.d(TAG, "Value of palabras is: " + alojamiento.getNombreAloj());
                    }

                    //Por defecto, la opción seleccionada será "Ordenar alfabéticamente ascendente"
                    ordenLista = "atoz";
                    List<String> listaNombres = getListaNombresAlojamiento(ps, ordenLista);

                    myAdapter = new listViewAdapter(getContext(), R.layout.list_casas, listaNombres);
                    listView.setAdapter(myAdapter);

                    Bundle argsD = new Bundle();

                    ordenarBtn.setOnClickListener(v ->{
                        ordenarFragment dialog = new ordenarFragment();
                        argsD.putString("ordenar", ordenLista);
                        argsD.putString("origen", "alojamiento");
                        dialog.setArguments(argsD);
                        //Se implementa la interfaz
                        dialog.setOnClickButtonListener(ordenar -> {
                            ordenLista = ordenar;
                            List<String> listanombres = getListaNombresAlojamiento(ps, ordenLista);
                            myAdapter.setmData(listanombres);
                        });

                        dialog.show(getChildFragmentManager(), "OrdenarFragment");
                    });


                    editsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            myAdapter.getFilter().filter(newText);
                            return false;
                        }
                    });

                    @SuppressLint("DiscouragedApi")
                    int idtext = editsearch.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
                    TextView searchText = editsearch.findViewById(idtext);
                    Typeface typeface = ResourcesCompat.getFont(requireContext(), R.font.amiri);
                    searchText.setTypeface(typeface);

                    listView.setOnItemClickListener((adapterView, v, position, id) -> {
                        //Obtenemos el nombre del elemento pulsado y cargamos su información
                        nombreAloj = adapterView.getItemAtPosition(position).toString();
                        args.putParcelable("aloj", buscarAloj(nombreAloj, ps));
                        DialogFragment alojamientoFragment = new alojamientoFragment();
                        alojamientoFragment.setArguments(args);
                        alojamientoFragment.setCancelable(false);
                        alojamientoFragment.show(getChildFragmentManager(),"alojamiento_fragment");
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

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        myAdapter.getFilter().filter(s);
        return false;
    }

    public List<String> getListaNombresAlojamiento(LinkedList<Alojamiento> est, String orden){

        if (orden.equalsIgnoreCase("asc")){
            organizedPuntuacionAscList(est);

        } else if (orden.equalsIgnoreCase("desc")){
            organizedPuntuacionAscList(est);
            Collections.reverse(est);
        }

        List<String> lista = new LinkedList<>();
        for (int i = 0; i < Objects.requireNonNull(est).size(); i++){
            lista.add(est.get(i).getNombreAloj());
        }

        if (orden.equalsIgnoreCase("atoz")){
            organizedAlphabeticList(lista);
        } else if (orden.equalsIgnoreCase("ztoa")){
            organizedAlphabeticList(lista);
            Collections.reverse(lista);
        }

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

    public static List<Alojamiento> organizedPuntuacionAscList (List<Alojamiento> est){

        boolean ordenada = false;
        List<Alojamiento> listaOrdenada = new LinkedList<>();

        while (!ordenada){
            int i=0;
            while (i< est.size()){
                Log.d(TAG, "EST SIZE : " + est.size() + " ");
                Alojamiento actual = est.get(i);
                Log.d(TAG, "ACTUAL : " + i + " " + actual.getNombreAloj() + " PUNTUACION: " + actual.getPuntAloj());
                if (!listaOrdenada.contains(actual)) {
                    for (int j = 0; j < est.size(); j++) {
                        Alojamiento comp = est.get(j);
                        Log.d(TAG, "COMPARADOR : " + j + " " + comp.getNombreAloj() + " PUNTUACION: " + comp.getPuntAloj());
                        if (!listaOrdenada.contains(comp)){
                            if (actual.getPuntAloj() >= comp.getPuntAloj()){
                                actual = comp;
                                Log.d(TAG, "TEMPORAL : " + j + " " + actual.getNombreAloj() + " PUNTUACION: " + actual.getPuntAloj());
                            }
                        }
                    }
                }
                if (!listaOrdenada.contains(actual)){
                    listaOrdenada.add(actual);
                }

                if (listaOrdenada.size() == est.size()){
                    ordenada = true;
                }
                i++;
            }
        }

        System.out.println("LISTA ORDENADA FINAL");
        for (Alojamiento estab: listaOrdenada) {
            System.out.println("Alojamiento: "+ estab.getNombreAloj() + " PUNTUACION: "+ estab.getPuntAloj());
        }

        return listaOrdenada;
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