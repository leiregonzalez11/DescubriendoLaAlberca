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
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import com.example.tfg.adapters.listViewAdapter;
import com.example.tfg.navigationmenu.Categorias;
import androidx.fragment.app.FragmentTransaction;
import com.example.tfg.categoriasFragments.secundarias.tradiciones.alboradas;
import com.example.tfg.categoriasFragments.secundarias.tradiciones.laLoa;
import com.example.tfg.categoriasFragments.secundarias.tradiciones.marranoSanAnton;
import com.example.tfg.categoriasFragments.secundarias.tradiciones.mozaDeAnimas;


public class tradicionesInicio extends Fragment {

    Bundle args;
    Fragment fragment;
    String opc1, opc2, opc3, opc4, idioma, categoria;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment BlankFragment.
     */
    public static tradicionesInicio newInstance(Bundle args) {
        tradicionesInicio fragment = new tradicionesInicio();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    public tradicionesInicio() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(false);

        if (getArguments() != null) {
            idioma = getArguments().getString("idioma");
            categoria = getArguments().getString("categoria");
        }

        args = new Bundle();
        args.putString("idioma", idioma);
        args.putString("categoria", categoria);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tradiciones, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //Toolbar
        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        myToolbar.setNavigationOnClickListener(v -> {
            myToolbar.setNavigationIcon(null);
            Fragment fragment = Categorias.newInstance();
            cargarFragment(fragment);
        });

        TextView text1 = requireView().findViewById(R.id.trad11);
        TextView text2 = requireView().findViewById(R.id.trad12);

        GestorDB dbHelper = new GestorDB(getContext());

        String [] datos = dbHelper.obtenerInfoTrad(idioma, "inicio", categoria, 2);

        text1.setText(datos[0]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text2.setText(datos[1]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        //Selección de tradiciones

        /*----------------
         | Moza de Ánimas |
         ----------------*/
        opc1 = "La Moza de Ánimas";
        ListView listView = requireView().findViewById(R.id.listviewtrad1);

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

        opc2 = "El Marrano de San Antón";
        ListView listView2 = requireView().findViewById(R.id.listviewtrad2);
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

        opc3 = "La Loa";
        ListView listView3 = requireView().findViewById(R.id.listviewtrad3);
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

        opc4 = "Las Alboradas";
        ListView listView4 = requireView().findViewById(R.id.listviewtrad4);
        ArrayList<String> lista4 = new ArrayList<>();
        lista4.add(opc4);

        listViewAdapter myAdapter4 = new listViewAdapter(getContext(), R.layout.listview_tradiciones, lista4);

        listView4.setAdapter(myAdapter4);

        listView4.setOnItemClickListener((adapterView, v, position, id) -> {
            fragment = alboradas.newInstance(args);
            cargarFragment(fragment);
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