package com.example.tfg.categoriasFragments.principal;

import android.os.Bundle;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import android.annotation.SuppressLint;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.tfg.navigationmenu.Categorias;

public class historiaInicio extends Fragment {

    private String idioma, categoria;
    private ImageButton btn;
    private TextView text1, text2, text3, pruebatexto;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static historiaInicio newInstance(Bundle args) {
        historiaInicio fragment = new historiaInicio();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public historiaInicio() {}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(false);
        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        myToolbar.setNavigationOnClickListener(view12 -> {
            myToolbar.setNavigationIcon(null);
            Fragment fragment = Categorias.newInstance();
            cargarFragment(fragment);
        });

        Bundle args = new Bundle();

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
        View v =  inflater.inflate(R.layout.fragment_historia_inicio, container, false);
        if(v != null){
            pruebatexto = v.findViewById(R.id.pruebatextoh);
            text1 = v.findViewById(R.id.historia1);
            text2 = v.findViewById(R.id.historia2);
            text3 = v.findViewById(R.id.historia3);
            btn = v.findViewById(R.id.historiabtn);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        GestorDB dbHelper = new GestorDB(getContext());

        if (pruebatexto.getText().toString().equalsIgnoreCase("1")){
            btn.setImageResource(R.drawable.ic_circle_arrow_right_solid);

            String [] datos = dbHelper.obtenerInfoHist("cat1", idioma, categoria, 3);

            text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        }

        btn.setOnClickListener(view1 -> {
            if (pruebatexto.getText().toString().equalsIgnoreCase("1")){
                btn.setImageResource(R.drawable.ic_circle_arrow_left_solid);
                pruebatexto.setText("2");

                String [] datos = dbHelper.obtenerInfoHist("cat1", idioma, categoria, 3);

                text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));


            } else if (pruebatexto.getText().toString().equalsIgnoreCase("2")){
                btn.setImageResource(R.drawable.ic_circle_arrow_right_solid);
                pruebatexto.setText("1");

                String [] datos = dbHelper.obtenerInfoHist("cat2", idioma, categoria, 3);

                text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

            }
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