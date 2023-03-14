package com.example.tfg.ajustesFragments.restauracion;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.adapters.listViewAdapter;
import com.example.tfg.ajustesFragments.alojamiento.Alojamiento;
import com.example.tfg.ajustesFragments.alojamiento.ListaAlojamientos;
import com.example.tfg.dialogFragments.ordenarFragment;

import java.util.ArrayList;
import java.util.List;

public class Restaurantes extends Fragment implements SearchView.OnQueryTextListener {

    private Bundle args;
    private String nombreRest, ordenLista;
    private ListView listView;
    private ImageButton ordenarBtn;
    private listViewAdapter myAdapter;
    List<String> listanombres;
    List<Establecimiento> est;
    private SearchView editsearch;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static Restaurantes newInstance() {
        return new Restaurantes();
    }

    /** Required empty public constructor */
    public Restaurantes() {}

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
        View v =  inflater.inflate(R.layout.fragment_restaurantes, container, false);
        if(v != null){
            listView = v.findViewById(R.id.listviewRest);
            ordenarBtn = v.findViewById(R.id.ordenarButtonR2);
            editsearch = (SearchView) v.findViewById(R.id.svRest);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ListaEstablecimiento listaRest = ListaEstablecimiento.getMiListaestablecimientos();
        listaRest.setContext(requireContext());

        //Por defecto, la opción seleccionada será "Ordenar alfabéticamente ascendente"
        ordenLista = "atoz";
        listanombres = determinarOrden(listaRest);

        myAdapter = new listViewAdapter(getContext(), R.layout.list_rest, listanombres);
        listView.setAdapter(myAdapter);

        Bundle argsD = new Bundle();
        argsD.putString("ordenar", ordenLista);

        ordenarBtn.setOnClickListener(v ->{
            ordenarFragment dialog = new ordenarFragment();
            argsD.putString("ordenar", ordenLista);
            argsD.putString("origen", "establecimiento");
            dialog.setArguments(argsD);
            //Se implementa la interfaz
            dialog.setOnClickButtonListener(ordenar -> {
                ordenLista = ordenar;
                listanombres = determinarOrden(listaRest);
                myAdapter.setmData(listanombres);
            });

            dialog.show(getChildFragmentManager(), "OrdenarFragment");
        });


        editsearch.setOnQueryTextListener(this);
        int idtext = editsearch.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView searchText = editsearch.findViewById(idtext);
        Typeface typeface = ResourcesCompat.getFont(requireContext(), R.font.amiri);
        searchText.setTypeface(typeface);

        listView.setOnItemClickListener((adapterView, v, position, id) -> {
            //Obtenemos el nombre del elemento pulsado y cargamos su información
            nombreRest = adapterView.getItemAtPosition(position).toString();
            args.putParcelable("establ", listaRest.buscarEst(nombreRest, est));
            DialogFragment restFragment = new establecimientoFragment();
            restFragment.setArguments(args);
            restFragment.setCancelable(false);
            restFragment.show(getChildFragmentManager(),"rest_fragment");

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

    private List <String> determinarOrden(ListaEstablecimiento establecimientos){

        List<String> nombres;

        if (ordenLista.equalsIgnoreCase("atoz") || ordenLista.equalsIgnoreCase("ztoa")){
            est = establecimientos.getListaEstablecimientos("restaurante", false, "alfabetico");
            nombres = establecimientos.getListaNombres(est, ordenLista);
        }
        else{
            est = establecimientos.getListaEstablecimientos("restaurante", true, ordenLista);
            nombres = establecimientos.getListaNombres(est, "asc/desc");
        }

        return nombres;
    }

}