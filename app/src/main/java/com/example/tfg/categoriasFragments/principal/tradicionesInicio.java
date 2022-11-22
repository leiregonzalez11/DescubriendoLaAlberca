package com.example.tfg.categoriasFragments.principal;

import static java.lang.Thread.sleep;

import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.tfg.R;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.tfg.GestorDB;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import android.annotation.SuppressLint;
import android.widget.VideoView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tfg.adapters.SpinnerAdapter;
import com.example.tfg.adapters.listViewAdapter;
import com.example.tfg.ajustesFragments.Comercio;
import com.example.tfg.categoriasFragments.secundarias.tradiciones.marranoSanAnton;
import com.example.tfg.categoriasFragments.secundarias.tradiciones.mozaDeAnimas;
import com.example.tfg.navigationmenu.Categorias;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Objects;

public class tradicionesInicio extends Fragment {

    Bundle args;
    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    String opc1, opc2, idioma, categoria;

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
            Fragment fragment = new Categorias();
            cargarFragment(fragment);
        });

        TextView text1 = requireView().findViewById(R.id.trad11);

        GestorDB dbHelper = new GestorDB(getContext());

        String [] datos = dbHelper.obtenerInfoTrad(idioma, "inicio", categoria, 1);

        text1.setText(datos[0]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        //Selección de tradiciones

        /*----------------
         | Moza de Ánimas |
         ----------------*/
        opc1 = "La Moza de Ánimas";
        ListView listView = requireView().findViewById(R.id.listviewtrad1);

        ArrayList<String> lista1 = new ArrayList<>();
        lista1.add(opc1);

        listViewAdapter myAdapter = new listViewAdapter(getContext(), R.layout.list_item, lista1);

        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener((adapterView, v, position, id) -> {
            //Toast.makeText(getContext(), "Has pulsado: "+ opc1, Toast.LENGTH_LONG).show();
            fragment = new mozaDeAnimas();
            fragment.setArguments(args);
            cargarFragment(fragment);
        });

        /*----------------------
         | Marrano de San Antón |
         -----------------------*/
        opc2 = "El Marrano de San Antón";
        ListView listView2 = requireView().findViewById(R.id.listviewtrad2);
        ArrayList<String> lista2 = new ArrayList<>();
        lista2.add(opc2);

        listViewAdapter myAdapter2 = new listViewAdapter(getContext(), R.layout.list_item, lista2);

        listView2.setAdapter(myAdapter2);

        listView2.setOnItemClickListener((adapterView, v, position, id) -> {
            //Toast.makeText(getContext(), "Has pulsado: "+ opc2, Toast.LENGTH_LONG).show();
            fragment = new marranoSanAnton();
            fragment.setArguments(args);
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