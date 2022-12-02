package com.example.tfg.categoriasFragments.principal;

import android.os.Bundle;
import com.example.tfg.R;
import android.view.View;
import java.util.ArrayList;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.example.tfg.GestorDB;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import android.annotation.SuppressLint;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import com.example.tfg.adapters.listViewAdapter;
import com.example.tfg.categoriasFragments.secundarias.tradiciones.elpendon;
import com.example.tfg.navigationmenu.Categorias;
import androidx.fragment.app.FragmentTransaction;
import com.example.tfg.categoriasFragments.secundarias.tradiciones.alboradas;
import com.example.tfg.categoriasFragments.secundarias.tradiciones.laLoa;
import com.example.tfg.categoriasFragments.secundarias.tradiciones.marranoSanAnton;
import com.example.tfg.categoriasFragments.secundarias.tradiciones.mozaDeAnimas;


public class tradicionesInicio extends Fragment {

    private Bundle args;
    private Fragment fragment;
    private TextView text1, text2;
    private String idioma, categoria;
    private ListView listView, listView2, listView3, listView4, listView5, listView6;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static tradicionesInicio newInstance(Bundle args) {
        tradicionesInicio fragment = new tradicionesInicio();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public tradicionesInicio() {}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(false);
        //Toolbar
        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        myToolbar.setNavigationOnClickListener(v -> {
            myToolbar.setNavigationIcon(null);
            Fragment fragment = Categorias.newInstance();
            cargarFragment(fragment);
        });

        args = new Bundle();

        if (getArguments() != null) {
            idioma = getArguments().getString("idioma");
            categoria = getArguments().getString("categoria");
        }

        args.putString("idioma", idioma);
        args.putString("categoria", categoria);
    }

    /** El Fragment va a cargar su layout, el cual debemos especificar.
     Aquí se instanciarán los objetos que si son vistas */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_tradiciones, container, false);
        if(v != null){
            text1 = v.findViewById(R.id.trad11);
            text2 = v.findViewById(R.id.trad12);
            listView = v.findViewById(R.id.listviewtrad1);
            listView2 = v.findViewById(R.id.listviewtrad2);
            listView3 = v.findViewById(R.id.listviewtrad3);
            listView4 = v.findViewById(R.id.listviewtrad4);
            listView5 = v.findViewById(R.id.listviewtrad5);
            listView6 = v.findViewById(R.id.listviewtrad6);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        GestorDB dbHelper = new GestorDB(getContext());

        String [] datos = dbHelper.obtenerInfoTrad(idioma, "inicio", categoria, 2);

        text1.setText(datos[0]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text2.setText(datos[1]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        //Selección de tradiciones

        /*----------------
         | Moza de Ánimas |
         ----------------*/
        String opc1 = "La Moza de Ánimas";

        ArrayList<String> lista1 = new ArrayList<>();
        lista1.add(opc1);

        listViewAdapter myAdapter = new listViewAdapter(getContext(), R.layout.listview_tradiciones, lista1);

        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener((adapterView, v, position, id) -> {
            fragment = mozaDeAnimas.newInstance(args);
            cargarFragment(fragment);
        });

        /*----------------------
         | Marrano de San Antón |
         -----------------------*/

        String opc2 = "El Marrano de San Antón";
        ArrayList<String> lista2 = new ArrayList<>();
        lista2.add(opc2);

        listViewAdapter myAdapter2 = new listViewAdapter(getContext(), R.layout.listview_tradiciones, lista2);

        listView2.setAdapter(myAdapter2);

        listView2.setOnItemClickListener((adapterView, v, position, id) -> {
            fragment = marranoSanAnton.newInstance(args);
            cargarFragment(fragment);
        });

        /*--------
         | La Loa |
         --------*/

        String opc3 = "La Loa";
        ArrayList<String> lista3 = new ArrayList<>();
        lista3.add(opc3);

        listViewAdapter myAdapter3 = new listViewAdapter(getContext(), R.layout.listview_tradiciones, lista3);

        listView3.setAdapter(myAdapter3);

        listView3.setOnItemClickListener((adapterView, v, position, id) -> {
            fragment = laLoa.newInstance(args);
            cargarFragment(fragment);
        });

        /*---------------
         | Las alboradas |
         ---------------*/

        String opc4 = "Las Alboradas";
        ArrayList<String> lista4 = new ArrayList<>();
        lista4.add(opc4);

        listViewAdapter myAdapter4 = new listViewAdapter(getContext(), R.layout.listview_tradiciones, lista4);

        listView4.setAdapter(myAdapter4);

        listView4.setOnItemClickListener((adapterView, v, position, id) -> {
            fragment = alboradas.newInstance(args);
            cargarFragment(fragment);
        });

        /*-----------
         | El Pendón |
         -----------*/

        String opc5 = "El Pendón";
        ArrayList<String> lista5 = new ArrayList<>();
        lista5.add(opc5);

        listViewAdapter myAdapter5 = new listViewAdapter(getContext(), R.layout.listview_tradiciones, lista5);

        listView5.setAdapter(myAdapter5);

        listView5.setOnItemClickListener((adapterView, v, position, id) -> {
            fragment = elpendon.newInstance(args);
            cargarFragment(fragment);
        });

        /*--------------
         | Las Campanas |
         --------------*/

        String opc6 = "Las Campanas";
        ArrayList<String> lista6 = new ArrayList<>();
        lista6.add(opc6);

        listViewAdapter myAdapter6 = new listViewAdapter(getContext(), R.layout.listview_tradiciones, lista6);

        listView6.setAdapter(myAdapter6);

        listView6.setOnItemClickListener((adapterView, v, position, id) -> {
            Toast.makeText(getContext(), "Has pulsado: "+ opc6, Toast.LENGTH_LONG).show();
        });

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