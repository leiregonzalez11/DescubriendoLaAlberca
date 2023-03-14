package com.example.tfg.ajustesFragments.comercio;

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
import com.example.tfg.adapters.listViewAdapter;
import com.example.tfg.ajustesFragments.restauracion.Establecimiento;
import com.example.tfg.ajustesFragments.restauracion.ListaEstablecimiento;
import com.example.tfg.dialogFragments.ordenarFragment;

import java.util.List;

public class AlimentacionTienda extends Fragment implements SearchView.OnQueryTextListener{

    private Bundle args;
    private String nombreTienda, ordenLista;
    private ListView listView;
    private ImageButton ordenarBtn;
    private listViewAdapter myAdapter;
    List<String> listanombres;
    List<Comercio> com;
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

        ListaComercio listaCom = ListaComercio.getMiListaComercios();
        listaCom.setContext(requireContext());

        //Por defecto, la opción seleccionada será "Ordenar alfabéticamente ascendente"
        ordenLista = "atoz";
        listanombres = determinarOrden(listaCom);

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
                listanombres = determinarOrden(listaCom);
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
            nombreTienda = adapterView.getItemAtPosition(position).toString();
            args.putParcelable("comercio", listaCom.buscarComercio(nombreTienda, com));
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

    private List <String> determinarOrden(ListaComercio comercio){
        com = comercio.getListaComercios("alimentacion");
        List<String> nombres = comercio.getListaNombres(com, ordenLista);
        return nombres;
    }

}