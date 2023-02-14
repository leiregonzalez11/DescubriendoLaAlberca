package com.example.tfg.ajustesFragments.comercio;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import com.example.tfg.R;
import com.example.tfg.adapters.listViewAdapter;

public class AlimentacionTienda extends Fragment implements SearchView.OnQueryTextListener{

    String nombreTienda;
    Bundle args;
    listViewAdapter myAdapter;
    ListView listView;
    SearchView editsearch;

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
        setHasOptionsMenu(false);
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
            listView = v.findViewById(R.id.listviewAlimentacion);
            editsearch = v.findViewById(R.id.svAlim);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @Override
    @SuppressLint("SetTextI18n")
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ListaComercio comercios = new ListaComercio(requireContext());

        editsearch.setOnQueryTextListener(this);

        myAdapter = new listViewAdapter(getContext(), R.layout.list_alim,comercios.getListaAlim());
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener((adapterView, v, position, id) -> {
            //Obtenemos el nombre del elemento pulsado y cargamos su información
            nombreTienda = adapterView.getItemAtPosition(position).toString();
            args.putParcelable("comercio", comercios.buscarComercio(nombreTienda));
            DialogFragment tiendaFragment = new tiendaFragment();
            tiendaFragment.setArguments(args);
            tiendaFragment.setCancelable(false);
            tiendaFragment.show(getChildFragmentManager(),"tienda_fragment");

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

}