package com.example.tfg.ajustes.administrador;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tfg.R;
import com.example.tfg.ajustes.administrador.dialogs.confirmModifDelete;
import com.example.tfg.categorias.secundarias.cultura.diccionario.Palabra;
import com.example.tfg.espacioDelViajero.alojamiento.Alojamiento;
import com.example.tfg.espacioDelViajero.comercio.Comercio;
import com.example.tfg.espacioDelViajero.restauracion.Establecimiento;
import com.example.tfg.otherFiles.adapters.SpinnerAdapter;
import com.example.tfg.otherFiles.adapters.listViewAdapter;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addModif#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addModif extends Fragment {

    Bundle args;
    Fragment fragment;

    Comercio comercio;
    TextView rate;
    EditText nombre, telefono, ubi, rating;
    Palabra palabra;
    Alojamiento aloj;
    Establecimiento establ;

    ImageView img;

    Toolbar myToolbar;
    String origen, opcionElegida;
    private StorageReference storageRef;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static addModif newInstance(Bundle args) {
        addModif fragment = new addModif();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public addModif() {}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        TextView name = myToolbar.findViewById(R.id.name);
        name.setText(getResources().getString(R.string.administracion_ajustes));
        name.setTextSize(20);
        myToolbar.setNavigationOnClickListener(view12 -> {
            myToolbar.setNavigationIcon(null);
            Fragment fragment = administrador_inicio.newInstance();
            cargarFragment(fragment);
        });

        if (getArguments() != null) {
            opcionElegida = getArguments().getString("choose");
            origen = getArguments().getString("origen");
        }

        if (opcionElegida.equals("modif")){
            switch (origen) {
                case "comercio":
                    comercio = getArguments().getParcelable("comercio");
                    break;
                case "alojamiento":
                    aloj = getArguments().getParcelable("aloj");
                    break;
                case "hosteleria":
                    establ = getArguments().getParcelable("establ");
                    break;
                case "diccionario":
                    palabra = getArguments().getParcelable("palabra");
                    break;
            }
        }


        args = new Bundle();

        args.putString("choose", opcionElegida);
        args.putString("origen", origen);

    }

    /** El Fragment va a cargar su layout, el cual debemos especificar.
     Aquí se instanciarán los objetos que si son vistas */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_add_modif, container, false);
        if(v != null){
            nombre = v.findViewById(R.id.textNombreModif);
            telefono = v.findViewById(R.id.textTelModif);
            ubi = v.findViewById(R.id.textUbiModif);
            rating = v.findViewById(R.id.textRatingModif);
            rate = v.findViewById(R.id.textrating);
            img = v.findViewById(R.id.imagenperfil);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        storageRef = FirebaseStorage.getInstance().getReference();

        if (opcionElegida.equals("modif")){
            switch (origen){
                case "comercio":
                    nombre.setText(comercio.getNombreComercio());
                    ubi.setText(comercio.getLocationComercio());
                    telefono.setText(comercio.getTelComercio());
                    rating.setVisibility(View.GONE);
                    rate.setVisibility(View.GONE);

                    //Imagen
                    obtenerImagenFirebase("ajustes/tiendas/" + comercio.getNombreComercio().toLowerCase().replace(" ", "") + ".png", img);

                    break;

                case "alojamiento":
                    nombre.setText(aloj.getNombreAloj());
                    ubi.setText(aloj.getLocationAloj());
                    telefono.setText(aloj.getTelAloj());
                    rating.setVisibility(View.VISIBLE);
                    rate.setVisibility(View.VISIBLE);
                    rating.setText("" + aloj.getPuntAloj());

                    //Imagen
                    obtenerImagenFirebase("ajustes/alojamientos/" + aloj.getNombreAloj().toLowerCase().replace(" ", "") + ".png", img);

                    break;

                case "hosteleria":
                    nombre.setText(establ.getNombreEstabl());
                    ubi.setText(establ.getLocationEstabl());
                    telefono.setText(establ.getTelEstabl());
                    rating.setVisibility(View.VISIBLE);
                    rate.setVisibility(View.VISIBLE);
                    rating.setText("" + establ.getPuntEstabl().toString());

                    //Imagen
                    obtenerImagenFirebase("ajustes/hosteleria/" + establ.getNombreEstabl().toLowerCase().replace(" ", "") + ".png", img);

                    break;
            }
        }
        
    }

    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
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

    private void cargarDialogFragment(){
        DialogFragment fragment = new confirmModifDelete();
        fragment.setArguments(args);
        fragment.setCancelable(false);
        fragment.show(getChildFragmentManager(),"fragment");
    }
}