package com.example.tfg.navigationmenu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tfg.R;
import com.example.tfg.adapters.SliderAdapter;
import com.example.tfg.adapters.listViewAdapter;
import com.example.tfg.ajustes.ajustesActivity;
import com.example.tfg.ajustes.comercioLocalActivity;
import com.example.tfg.ajustes.comoLlegarActivity;
import com.example.tfg.ajustes.contactoActivity;
import com.example.tfg.ajustes.dondeComerActivity;
import com.example.tfg.ajustes.dondeDormirActivity;
import com.example.tfg.ajustes.idiomasActivity;
import com.example.tfg.ajustesFragments.comercioFragment;
import com.example.tfg.ajustesFragments.comoLlegarFragment;
import com.example.tfg.ajustesFragments.dondeComerFragment;
import com.example.tfg.ajustesFragments.dondeDormirFragment;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Objects;


public class FragmentAjustes extends Fragment {

    String opc1, opc2, opc3, opc4, opc5, idioma;

    public FragmentAjustes() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        idioma = requireArguments().getString("idioma");
        System.out.println("IDIOMAAAAA: " + idioma);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ajustes, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        ListView listView = requireView().findViewById(R.id.listview);
        ListView listView2 = requireView().findViewById(R.id.listview2);
        ListView listView3 = requireView().findViewById(R.id.listview3);
        ListView listView4 = requireView().findViewById(R.id.listview4);
        ListView listView5 = requireView().findViewById(R.id.listview5);

        opc1 = getResources().getString(R.string.ajustes6);
        opc2 = getResources().getString(R.string.ajustes1);
        opc3 = getResources().getString(R.string.ajustes3);
        opc4 = getResources().getString(R.string.ajustes4);
        opc5 = "Servicios";

        determinarIdioma(opc1);

        ArrayList<String> lista1 = new ArrayList<>();
        lista1.add(opc1);

        listViewAdapter myAdapter = new listViewAdapter(getContext(), R.layout.list_comercio, lista1);

        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            //Toast.makeText(getContext(), "Has pulsado: "+ opc1, Toast.LENGTH_LONG).show();
            Fragment fragment = new comercioFragment();
            // Obtener el administrador de fragmentos a través de la actividad
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            // Definir una transacción
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            // Remplazar el contenido principal por el fragmento
            fragmentTransaction.replace(R.id.relativelayout, fragment);
            fragmentTransaction.addToBackStack(null);
            // Cambiar
            fragmentTransaction.commit();

        });

        ArrayList<String> lista2 = new ArrayList<>();
        lista2.add(opc2);

        listViewAdapter myAdapter2 = new listViewAdapter(getContext(), R.layout.list_direccion, lista2);
        listView2.setAdapter(myAdapter2);

        listView2.setOnItemClickListener((adapterView, view, position, id) -> {
            //Toast.makeText(getContext(), "Has pulsado: "+ opc2, Toast.LENGTH_LONG).show();
            Fragment fragment = new comoLlegarFragment();
            // Obtener el administrador de fragmentos a través de la actividad
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            // Definir una transacción
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            // Remplazar el contenido principal por el fragmento
            fragmentTransaction.replace(R.id.relativelayout, fragment);
            fragmentTransaction.addToBackStack(null);
            // Cambiar
            fragmentTransaction.commit();

        });

        ArrayList<String> lista3 = new ArrayList<>();
        lista3.add(opc3);

        listViewAdapter myAdapter3 = new listViewAdapter(getContext(), R.layout.list_comer, lista3);
        listView3.setAdapter(myAdapter3);

        listView3.setOnItemClickListener((adapterView, view, position, id) -> {
            //Toast.makeText(getContext(), "Has pulsado: "+ opc3, Toast.LENGTH_LONG).show();
            Fragment fragment = new dondeComerFragment();
            // Obtener el administrador de fragmentos a través de la actividad
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            // Definir una transacción
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            // Remplazar el contenido principal por el fragmento
            fragmentTransaction.replace(R.id.relativelayout, fragment);
            fragmentTransaction.addToBackStack(null);
            // Cambiar
            fragmentTransaction.commit();
        });

        ArrayList<String> lista4 = new ArrayList<>();
        lista4.add(opc4);

        listViewAdapter myAdapter4 = new listViewAdapter(getContext(), R.layout.list_dormir, lista4);
        listView4.setAdapter(myAdapter4);

        listView4.setOnItemClickListener((adapterView, view, position, id) -> {
            //Toast.makeText(getContext(), "Has pulsado: "+ opc4, Toast.LENGTH_LONG).show();
            Fragment fragment = new dondeDormirFragment();
            // Obtener el administrador de fragmentos a través de la actividad
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            // Definir una transacción
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            // Remplazar el contenido principal por el fragmento
            fragmentTransaction.replace(R.id.relativelayout, fragment);
            fragmentTransaction.addToBackStack(null);
            // Cambiar
            fragmentTransaction.commit();

        });

        ArrayList<String> lista5 = new ArrayList<>();
        lista5.add(opc5);

        listViewAdapter myAdapter5 = new listViewAdapter(getContext(), R.layout.list_servicios, lista5);
        listView5.setAdapter(myAdapter5);

        listView5.setOnItemClickListener((adapterView, view, position, id) -> {
            Toast.makeText(getContext(), "Has pulsado: "+ opc5, Toast.LENGTH_LONG).show();
        });

    }

    private void determinarIdioma(String opc1) {

        if (opc1.contains("Comercio")){
            idioma = "es";
        } else if (opc1.contains("dendak")){
            idioma = "eu";
        }else if (opc1.contains("Commerce")){
            idioma="en";
        }else if (opc1.contains("Comerç")){
            idioma="ca";
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.menuusuario, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_contacto:
                Toast.makeText(getContext(), "Has pulsado: Contacto", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_idioma:
                Toast.makeText(getContext(), "Has pulsado: Idiomas", Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

}