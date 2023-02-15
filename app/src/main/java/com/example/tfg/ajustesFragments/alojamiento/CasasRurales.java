package com.example.tfg.ajustesFragments.alojamiento;

import android.annotation.SuppressLint;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class CasasRurales extends Fragment implements SearchView.OnQueryTextListener{

    Bundle args;
    String nombreAloj;
    ListView listView;
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
        setHasOptionsMenu(false);
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
            editsearch = (SearchView) v.findViewById(R.id.svCasas);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @Override
    @SuppressLint("SetTextI18n")
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ListaAlojamientos alojamientos = new ListaAlojamientos(requireContext());

        editsearch.setOnQueryTextListener(this);
        int idtext = editsearch.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView searchText = (TextView) editsearch.findViewById(idtext);
        Typeface typeface = ResourcesCompat.getFont(requireContext(), R.font.amiri);
        searchText.setTypeface(typeface);


        myAdapter = new listViewAdapter(getContext(), R.layout.list_casas, alojamientos.getListaCasas());
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener((adapterView, v, position, id) -> {
            //Obtenemos el nombre del elemento pulsado y cargamos su información
            nombreAloj = adapterView.getItemAtPosition(position).toString();
            args.putParcelable("aloj", alojamientos.buscarAloj(nombreAloj));
            DialogFragment alojamientoFragment = new alojamientoFragment();
            alojamientoFragment.setArguments(args);
            alojamientoFragment.setCancelable(false);
            alojamientoFragment.show(getChildFragmentManager(),"alojamiento_fragment");
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