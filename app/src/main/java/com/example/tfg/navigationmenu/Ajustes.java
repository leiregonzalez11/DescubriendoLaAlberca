package com.example.tfg.navigationmenu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.tfg.R;
import com.example.tfg.adapters.listViewAdapter;
import com.example.tfg.ajustesFragments.Comercio;
import com.example.tfg.ajustesFragments.ComoLlegar;
import com.example.tfg.ajustesFragments.DondeComer;
import com.example.tfg.ajustesFragments.DondeDormir;
import com.example.tfg.ajustesFragments.FormularioDeContacto;
import com.example.tfg.ajustesFragments.Idiomas;
import java.util.ArrayList;


public class Ajustes extends Fragment {

    Bundle args;
    Fragment fragment;
    Toolbar myToolbar;
    ListView listView, listView2, listView3, listView4, listView5;
    String opc1, opc2, opc3, opc4, opc5;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static Ajustes newInstance() {
        return new Ajustes();
    }

    /** Required empty public constructor */
    public Ajustes() {}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(null);
        setHasOptionsMenu(true); //Indicamos que este Fragment tiene su propio menu de opciones
        args = new Bundle(); //Argumentos para el menu de opciones
        args.putString("iu", "ajustes");
    }

    /** El Fragment va a cargar su layout, el cual debemos especificar.
     Aquí se instanciarán los objetos que si son vistas */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_ajustes, container, false);
        if(v != null){
            listView = v.findViewById(R.id.listview);
            listView2 = v.findViewById(R.id.listview2);
            listView3 = v.findViewById(R.id.listview3);
            listView4 = v.findViewById(R.id.listview4);
            listView5 = v.findViewById(R.id.listview5);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        /*-----------------
         | ¿Comercio local |
         -----------------*/

        opc1 = getResources().getString(R.string.ajustes6);

        ArrayList<String> lista1 = new ArrayList<>();
        lista1.add(opc1);

        listViewAdapter myAdapter = new listViewAdapter(getContext(), R.layout.list_comercio, lista1);

        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener((adapterView, v, position, id) -> {
            fragment = Comercio.newInstance();
            cargarFragment(fragment);
        });

        /*---------------
         | ¿Cómo llegar? |
         ---------------*/

        opc2 = getResources().getString(R.string.ajustes1);

        ArrayList<String> lista2 = new ArrayList<>();
        lista2.add(opc2);

        listViewAdapter myAdapter2 = new listViewAdapter(getContext(), R.layout.list_direccion, lista2);
        listView2.setAdapter(myAdapter2);

        listView2.setOnItemClickListener((adapterView, v, position, id) -> {
            fragment = ComoLlegar.newInstance();
            cargarFragment(fragment);
        });

        /*---------------
         | ¿Dónde comer? |
         ---------------*/

        opc3 = getResources().getString(R.string.ajustes3);

        ArrayList<String> lista3 = new ArrayList<>();
        lista3.add(opc3);

        listViewAdapter myAdapter3 = new listViewAdapter(getContext(), R.layout.list_comer, lista3);
        listView3.setAdapter(myAdapter3);

        listView3.setOnItemClickListener((adapterView, v, position, id) -> {
            fragment = DondeComer.newInstance();
            cargarFragment(fragment);
        });

        /*----------------
         | ¿Dónde dormir? |
         ----------------*/

        opc4 = getResources().getString(R.string.ajustes4);

        ArrayList<String> lista4 = new ArrayList<>();
        lista4.add(opc4);

        listViewAdapter myAdapter4 = new listViewAdapter(getContext(), R.layout.list_dormir, lista4);
        listView4.setAdapter(myAdapter4);

        listView4.setOnItemClickListener((adapterView, v, position, id) -> {
            fragment = DondeDormir.newInstance();
            cargarFragment(fragment);
        });

        /*-----------
         | Servicios |
         -----------*/

        opc5 = getResources().getString(R.string.servicios);

        ArrayList<String> lista5 = new ArrayList<>();
        lista5.add(opc5);

        listViewAdapter myAdapter5 = new listViewAdapter(getContext(), R.layout.list_servicios, lista5);
        listView5.setAdapter(myAdapter5);

        listView5.setOnItemClickListener((adapterView, v, position, id) ->
                Toast.makeText(getContext(), "Has pulsado: "+ opc5, Toast.LENGTH_LONG).show());

    }


    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menuusuario, menu);
    }

    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.menu_contacto:
                //Creamos el fragmento
                fragment = FormularioDeContacto.newInstance(args);
                break;

            case R.id.menu_idioma:
                //Creamos el fragmento
                fragment = Idiomas.newInstance(args);
                break;

            default:
                return super.onOptionsItemSelected(menuItem);
        }

        cargarFragment(fragment);

        return true;
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

}