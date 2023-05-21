package com.example.tfg.EspacioDelViajero.restauracion;

import static android.content.Context.ACCESSIBILITY_SERVICE;
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

public class Bares extends Fragment implements SearchView.OnQueryTextListener {

    private static boolean swapped;
    private Bundle args;
    private String nombreRest, ordenLista;
    private ImageButton ordenarBtn;
    private listViewAdapter myAdapter;
    List<Establecimiento> est;
    private SearchView editsearch;
    private ListView listView;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static Bares newInstance() {
        return new Bares();
    }

    /** Required empty public constructor */
    public Bares() {}

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
        View v =  inflater.inflate(R.layout.fragment_bares, container, false);
        if(v != null){
            listView = v.findViewById(R.id.listviewBares);
            ordenarBtn = v.findViewById(R.id.ordenarButtonR1);
            editsearch = v.findViewById(R.id.svBares);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setListView(view, "bar");
    }

    public void setListView(View infomView, String categoria){

        ListView listView = infomView.findViewById(R.id.listviewBares);

        //Instancia a la base de datos
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        //apuntamos al nodo que queremos leer
        DatabaseReference myRef = fdb.getReference().child("hosteleria").child(categoria);

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
                    LinkedList<Establecimiento> ps = new LinkedList<>();
                    HashMap dataMap = (HashMap) dataSnapshot.getValue();
                    assert dataMap != null;
                    for (Object key: dataMap.keySet()){
                        Object datos = dataMap.get(key);
                        HashMap userData = (HashMap) datos;
                        assert userData != null;
                        Establecimiento establecimiento = new Establecimiento((String) key, (String) userData.get("ubicacion"),
                                (String) userData.get("telefono"), (Double) userData.get("puntuación"));
                        ps.add(establecimiento);
                        Log.d(TAG, "Value of palabras is: " + establecimiento.getNombreEstabl());
                    }

                    //Por defecto, la opción seleccionada será "Ordenar alfabéticamente ascendente"
                    ordenLista = "atoz";
                    List<String> listaNombres = getListaNombresEstablecimiento(ps, ordenLista);

                    myAdapter = new listViewAdapter(getContext(), R.layout.list_bar, listaNombres);
                    listView.setAdapter(myAdapter);

                    Bundle argsD = new Bundle();

                    ordenarBtn.setOnClickListener(v ->{
                        ordenarFragment dialog = new ordenarFragment();
                        argsD.putString("ordenar", ordenLista);
                        argsD.putString("origen", "establecimiento");
                        dialog.setArguments(argsD);
                        //Se implementa la interfaz
                        dialog.setOnClickButtonListener(ordenar -> {
                            ordenLista = ordenar;
                            List<String> listanombres = getListaNombresEstablecimiento(ps, ordenLista);
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
                        nombreRest = adapterView.getItemAtPosition(position).toString();
                        args.putParcelable("establ", buscarEst(nombreRest, ps));
                        DialogFragment restFragment = new establecimientoFragment();
                        restFragment.setArguments(args);
                        restFragment.setCancelable(false);
                        restFragment.show(getChildFragmentManager(),"rest_fragment");

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

    public List<String> getListaNombresEstablecimiento(List<Establecimiento> est, String orden){

        if (orden.equalsIgnoreCase("asc")){
            est = organizedPuntuacionAscList(est);

        } else if (orden.equalsIgnoreCase("desc")){
            est = organizedPuntuacionAscList(est);
            Collections.reverse(est);
        }

        List<String> lista = new LinkedList<>();
        for (int i = 0; i < Objects.requireNonNull(est).size(); i++){
            lista.add(est.get(i).getNombreEstabl());
        }

        if (orden.equalsIgnoreCase("atoz")){
            organizedAlphabeticList(lista);
        } else if (orden.equalsIgnoreCase("ztoa")){
            organizedAlphabeticList(lista);
            Collections.reverse(lista);
        }

        return lista;
    }


    //Utilizando la Clase Collator que actúa como comparadora de cadena para solucionar el error de las tildes
    public static void organizedAlphabeticList(List<String> list) {
        list.sort(new Comparator<String>() {
            final Collator collator = Collator.getInstance();

            public int compare(String o1, String o2) {
                return collator.compare(o1, o2);
            }
        });
    }

    public static List<Establecimiento> organizedPuntuacionAscList (List<Establecimiento> est){

        boolean ordenada = false;
        List<Establecimiento> listaOrdenada = new LinkedList<>();

        while (!ordenada){
            int i=0;
            while (i< est.size()){
                Establecimiento actual = est.get(i);
                if (!listaOrdenada.contains(actual)) {
                    for (int j = 0; j < est.size(); j++) {
                        Establecimiento comp = est.get(j);
                        if (!listaOrdenada.contains(comp)){
                            if (actual.getPuntEstabl() >= comp.getPuntEstabl()){
                                actual = comp;
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

        return listaOrdenada;
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

}