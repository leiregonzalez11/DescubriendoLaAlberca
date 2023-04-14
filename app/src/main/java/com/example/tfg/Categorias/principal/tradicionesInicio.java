package com.example.tfg.Categorias.principal;

import android.os.Bundle;
import com.example.tfg.R;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.tfg.Categorias.secundarias.tradiciones.elpendon;
import com.example.tfg.NavigationMenu.Categorias;
import androidx.fragment.app.FragmentTransaction;
import com.example.tfg.Categorias.secundarias.tradiciones.alboradas;
import com.example.tfg.Categorias.secundarias.tradiciones.laLoa;
import com.example.tfg.Categorias.secundarias.tradiciones.marranoSanAnton;
import com.example.tfg.Categorias.secundarias.tradiciones.mozaDeAnimas;


public class tradicionesInicio extends Fragment {

    private Bundle args;
    private Fragment fragment;
    private TextView text1, text2;
    private String idioma;
    private Button btn1, btn2, btn3, btn4, btn5, btn6;

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
        TextView name = myToolbar.findViewById(R.id.name);
        name.setText(R.string.categorias);
        name.setTextSize(20);
        myToolbar.setNavigationOnClickListener(v -> {
            myToolbar.setNavigationIcon(null);
            Fragment fragment = Categorias.newInstance();
            cargarFragment(fragment);
        });

        args = new Bundle();

        if (getArguments() != null) {
            idioma = getArguments().getString("idioma");
        }

        args.putString("idioma", idioma);

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
            btn1 = v.findViewById(R.id.btntrad1);
            btn2 = v.findViewById(R.id.btntrad2);
            btn3 = v.findViewById(R.id.btntrad3);
            btn4 = v.findViewById(R.id.btntrad4);
            btn5 = v.findViewById(R.id.btntrad5);
            btn6 = v.findViewById(R.id.btntrad6);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        GestorDB dbHelper = new GestorDB(getContext());

        String [] datos = dbHelper.obtenerInfoTrad(idioma, "inicio",2);

        text1.setText(datos[0]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text2.setText(datos[1]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        //Selección de tradiciones

        /*----------------
         | Moza de Ánimas |
         ----------------*/

        btn1.setOnClickListener(v -> {
            fragment = mozaDeAnimas.newInstance(args);
            cargarFragment(fragment);
        });

        /*----------------------
         | Marrano de San Antón |
         -----------------------*/

        btn2.setOnClickListener(v -> {
            fragment = marranoSanAnton.newInstance(args);
            cargarFragment(fragment);
        });

        /*--------
         | La Loa |
         --------*/

        btn3.setOnClickListener(v -> {
            fragment = laLoa.newInstance(args);
            cargarFragment(fragment);
        });

        /*---------------
         | Las alboradas |
         ---------------*/

        btn4.setOnClickListener(v -> {
            fragment = alboradas.newInstance(args);
            cargarFragment(fragment);
        });

        /*-----------
         | El Pendón |
         -----------*/

        btn5.setOnClickListener(v -> {
            fragment = elpendon.newInstance(args);
            cargarFragment(fragment);
        });

        /*-------------------
         | La boda albercana |
         -------------------*/

        btn6.setOnClickListener(v -> {
            /*fragment = elpendon.newInstance(args);
            cargarFragment(fragment);*/
            Toast.makeText(requireContext(), "HAS PULSADO LA BODA ALBERCANA", Toast.LENGTH_SHORT).show();
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