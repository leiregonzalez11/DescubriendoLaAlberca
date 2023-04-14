package com.example.tfg.EspacioDelViajero.restauracion;

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

import com.example.tfg.R;
import com.example.tfg.OtherFiles.Adapters.listViewAdapter;
import com.example.tfg.OtherFiles.DialogFragments.ordenarFragment;

import java.util.List;

public class Bares extends Fragment implements SearchView.OnQueryTextListener {

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
    public static Bares newInstance() {
        return new Bares();
    }

    /** Required empty public constructor */
    public Bares() {}

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
        View v =  inflater.inflate(R.layout.fragment_bares, container, false);
        if(v != null){
            listView = v.findViewById(R.id.listviewBares);
            ordenarBtn = v.findViewById(R.id.ordenarButtonR1);
            editsearch = (SearchView) v.findViewById(R.id.svBares);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @Override
    @SuppressLint("SetTextI18n")
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ListaEstablecimiento listaRest = ListaEstablecimiento.getMiListaestablecimientos();
        listaRest.setContext(requireContext());

        //Por defecto, la opción seleccionada será "Ordenar alfabéticamente ascendente"
        ordenLista = "atoz";
        listanombres = determinarOrden(listaRest);

        myAdapter = new listViewAdapter(getContext(), R.layout.list_bar, listanombres);
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
            est = establecimientos.getListaEstablecimientos("bar", false, "alfabetico");
            nombres = establecimientos.getListaNombres(est, ordenLista);
        }
        else{
            est = establecimientos.getListaEstablecimientos("bar", true, ordenLista);
            nombres = establecimientos.getListaNombres(est, "asc/desc");
        }

        return nombres;
    }

}