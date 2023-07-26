package com.example.tfg.espacioDelViajero.comercio;

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
import com.example.tfg.otherFiles.adapters.listViewAdapter;
import com.example.tfg.otherFiles.dialogFragments.ordenarFragment;
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

public class AlimentacionTienda extends Fragment implements ListaComercios {

    private Bundle args;
    private String nombreTienda, ordenLista;
    private ImageButton ordenarBtn;
    private listViewAdapter myAdapter;
    List<String> listanombres;
    private SearchView editsearch;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static AlimentacionTienda newInstance() {
        return new AlimentacionTienda();
    }

    /** Required empty public constructor */
    public AlimentacionTienda() {}

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
        View v =  inflater.inflate(R.layout.fragment_alimentacion, container, false);
        if(v != null){
            ordenarBtn = v.findViewById(R.id.ordenarButtonC1);
            editsearch = v.findViewById(R.id.svAlim);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @Override
    @SuppressLint("SetTextI18n")
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        setListView(view, "alimentacion");

    }

    public void setListView(View infomView, String categoria){
        ListView listView = infomView.findViewById(R.id.listviewAlimentacion);

        //Instancia a la base de datos
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        //apuntamos al nodo que queremos leer
        DatabaseReference myRef = fdb.getReference().child("comercio").child(categoria);

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
                    LinkedList<Comercio> ps = new LinkedList<>();
                    HashMap dataMap = (HashMap) dataSnapshot.getValue();
                    assert dataMap != null;
                    for (Object key: dataMap.keySet()){
                        Object datos = dataMap.get(key);
                        HashMap userData = (HashMap) datos;
                        assert userData != null;
                        Comercio comercio = new Comercio((String) key, (String) userData.get("ubicacion"), (String) userData.get("telefono"));
                        ps.add(comercio);
                        Log.d(TAG, "Value of palabras is: " + comercio.getNombreComercio());
                    }

                    //Por defecto, la opción seleccionada será "Ordenar alfabéticamente ascendente"
                    ordenLista = "atoz";
                    listanombres = ListaComercios.obtenerListaNombresComercio(ps, ordenLista);

                    myAdapter = new listViewAdapter(getContext(), R.layout.list_alim, listanombres);
                    listView.setAdapter(myAdapter);

                    Bundle argsD = new Bundle();

                    ordenarBtn.setOnClickListener(v ->{
                        ordenarFragment dialog = new ordenarFragment();
                        argsD.putString("ordenar", ordenLista);
                        argsD.putString("origen", "comercio");
                        dialog.setArguments(argsD);
                        //Se implementa la interfaz
                        dialog.setOnClickButtonListener(ordenar -> {
                            ordenLista = ordenar;
                            listanombres = ListaComercios.obtenerListaNombresComercio(ps, ordenLista);
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
                        nombreTienda = adapterView.getItemAtPosition(position).toString();
                        args.putParcelable("comercio", ListaComercios.buscarComercio(nombreTienda, ps));
                        DialogFragment tiendaFragment = new tiendaFragment();
                        tiendaFragment.setArguments(args);
                        tiendaFragment.setCancelable(false);
                        tiendaFragment.show(getChildFragmentManager(),"tienda_fragment");

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

}