package com.example.tfg.Categorias.secundarias.fiestas;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tfg.Categorias.principal.fiestasInicio;
import com.example.tfg.NavigationMenu.Categorias;
import com.example.tfg.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class fiestasElector extends Fragment {

    private String idioma, mesBBDD, mes;
    LinearLayout nofiesta, sifiesta;
    Button fiesta1, fiesta2, fiesta3, fiesta4;
    TextView mesElector;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static fiestasElector newInstance(Bundle args) {
        fiestasElector fragment = new fiestasElector();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public fiestasElector() {}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            idioma = getArguments().getString("idioma");
            mes = getArguments().getString("mes");
            mesBBDD = getArguments().getString("mesBBDD");
        }

        Bundle args = new Bundle();
        args.putString("idioma", idioma);

        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        TextView name = myToolbar.findViewById(R.id.name);
        name.setText(R.string.fiestasmayus);
        name.setTextSize(20);
        myToolbar.setNavigationOnClickListener(view12 -> {
            myToolbar.setNavigationIcon(null);
            Fragment fragment = fiestasInicio.newInstance(args);
            cargarFragment(fragment);
        });

    }

    /** El Fragment va a cargar su layout, el cual debemos especificar.
     Aquí se instanciarán los objetos que si son vistas */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_fiestas_elector, container, false);
        if(v != null){
            nofiesta = v.findViewById(R.id.nofiestasLy);
            sifiesta = v.findViewById(R.id.sifiestasLy);
            mesElector = v.findViewById(R.id.fiestasElectorMes);
            fiesta1 = v.findViewById(R.id.btnfiesta1);
            fiesta2 = v.findViewById(R.id.btnfiesta2);
            fiesta3 = v.findViewById(R.id.btnfiesta3);
            fiesta4 = v.findViewById(R.id.btnfiesta4);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mesElector.setText(mes);

        if (mesBBDD.equalsIgnoreCase("julio") || mesBBDD.equalsIgnoreCase("octubre")
                || mesBBDD.equalsIgnoreCase("noviembre")){
            nofiesta.setVisibility(View.VISIBLE);
            sifiesta.setVisibility(View.GONE);
        } else{
            nofiesta.setVisibility(View.GONE);
            sifiesta.setVisibility(View.VISIBLE);

            if (mesBBDD.equalsIgnoreCase("marzo") || mesBBDD.equalsIgnoreCase("agosto")
                    || mesBBDD.equalsIgnoreCase("septiembre") || mesBBDD.equalsIgnoreCase("diciembre")){
                fiesta1.setVisibility(View.VISIBLE);
                fiesta2.setVisibility(View.GONE);
                fiesta3.setVisibility(View.GONE);
                fiesta4.setVisibility(View.GONE);
            } else if (mesBBDD.equalsIgnoreCase("enero") || mesBBDD.equalsIgnoreCase("mayo")
                    || mesBBDD.equalsIgnoreCase("abril")){
                fiesta1.setVisibility(View.VISIBLE);
                fiesta2.setVisibility(View.VISIBLE);
                fiesta3.setVisibility(View.GONE);
                fiesta4.setVisibility(View.GONE);
            } else if (mesBBDD.equalsIgnoreCase("junio")){
                fiesta1.setVisibility(View.VISIBLE);
                fiesta2.setVisibility(View.VISIBLE);
                fiesta3.setVisibility(View.VISIBLE);
                fiesta4.setVisibility(View.GONE);
            } else if (mesBBDD.equalsIgnoreCase("febrero")){
                fiesta1.setVisibility(View.VISIBLE);
                fiesta2.setVisibility(View.VISIBLE);
                fiesta3.setVisibility(View.VISIBLE);
                fiesta4.setVisibility(View.VISIBLE);
            }
        }


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