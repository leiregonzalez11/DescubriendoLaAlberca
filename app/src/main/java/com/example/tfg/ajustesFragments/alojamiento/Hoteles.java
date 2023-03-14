package com.example.tfg.ajustesFragments.alojamiento;

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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.tfg.R;
import com.example.tfg.adapters.listViewAdapter;
import com.example.tfg.dialogFragments.ordenarFragment;

import java.util.List;

public class Hoteles extends Fragment implements SearchView.OnQueryTextListener {

    Bundle args;
    String nombreAloj, ordenLista;
    ListView listView;
    ImageButton ordenarBtn;
    List<String> listanombres;
    List<Alojamiento> aloj;
    SearchView editsearch;
    listViewAdapter myAdapter;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static Hoteles newInstance() {
        return new Hoteles();
    }

    /** Required empty public constructor */
    public Hoteles() {}

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
        View v =  inflater.inflate(R.layout.fragment_hoteles, container, false);
        if(v != null){
            listView = v.findViewById(R.id.listviewHoteles);
            ordenarBtn = v.findViewById(R.id.ordenarButton3);
            editsearch = v.findViewById(R.id.svHoteles);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @Override
    @SuppressLint("SetTextI18n")
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ListaAlojamientos listaAloj = ListaAlojamientos.getMiListaAlojamientos();
        listaAloj.setContext(requireContext());

        //Por defecto, la opción seleccionada será "Ordenar alfabéticamente ascendente"
        ordenLista = "atoz";
        listanombres = determinarOrden(listaAloj);

        myAdapter = new listViewAdapter(getContext(), R.layout.list_hotel, listanombres);
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
                listanombres = determinarOrden(listaAloj);
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
            nombreAloj = adapterView.getItemAtPosition(position).toString();
            args.putParcelable("aloj", listaAloj.buscarAloj(nombreAloj, aloj));
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

    private List <String> determinarOrden(ListaAlojamientos alojamientos){

        List<String> nombres;

        if (ordenLista.equalsIgnoreCase("atoz") || ordenLista.equalsIgnoreCase("ztoa")){
            aloj = alojamientos.getListaAlojamientos("hotel", false, "alfabetico");
            nombres = alojamientos.getListaNombres(aloj, ordenLista);
        }
        else{
            aloj = alojamientos.getListaAlojamientos("hotel", true, ordenLista);
            nombres = alojamientos.getListaNombres(aloj, "asc/desc");
        }

        return nombres;
    }
}