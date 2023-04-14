package com.example.tfg.EspacioDelViajero;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.tfg.R;
import com.example.tfg.OtherFiles.Adapters.SpinnerAdapter;
import com.example.tfg.NavigationMenu.EspacioDelViajero;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class ComoLlegar extends Fragment implements  AdapterView.OnItemSelectedListener {

    private StorageReference storageRef;
    private ImageView img1;
    private String idioma, nombreBus;
    private Spinner spinner;
    TextView texto;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static ComoLlegar newInstance() {
        return new ComoLlegar();
    }

    /** Required empty public constructor */
    public ComoLlegar() {}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        myToolbar.setNavigationOnClickListener(v -> {
            myToolbar.setNavigationIcon(null);
            Fragment fragment = new EspacioDelViajero();
            cargarFragment(fragment);
        });
    }

    /** El Fragment va a cargar su layout, el cual debemos especificar.
     Aquí se instanciarán los objetos que si son vistas */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_como_llegar, container, false);
        if(v != null){
            img1 = v.findViewById(R.id.comollegar3);
            spinner = v.findViewById(R.id.spinnerBus);
            texto = v.findViewById(R.id.ajustestext);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        storageRef = FirebaseStorage.getInstance().getReference();
        //Spinner
        String [] bus = getResources().getStringArray(R.array.bus);
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(new SpinnerAdapter(getContext(), R.layout.dropdownitenbus, bus));
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        determinarRuta((String) adapterView.getItemAtPosition(position));
        determinarIdioma();
        obtenerImagenFirebase("ajustes/comollegar/" + nombreBus + "-" + idioma + ".png", img1);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    /** Método utilizado para obtener el idioma actual de la app */
    public void determinarIdioma() {
        String text = texto.getText().toString();
        switch (text) {
            case "¿Cómo llegar hasta La Alberca?":
                idioma = "es";
                break;
            case "Nola iritsi La Albercara?":
                idioma = "eu";
                break;
            case "How to get to La Alberca?":
                idioma = "en";
                break;
        }
    }

    /** Método utilizado para conocer la ruta elegida por el usuario para obtener la información */
    private void determinarRuta(String idBus) {
        if(idBus.toLowerCase().contains("salamanca")){
            nombreBus = "laalbercasalamanca";
        } else if (idBus.toLowerCase().contains("béjar")){
            nombreBus = "laalbercabejar";
        } else if (idBus.toLowerCase().contains("ciudad")){
            nombreBus = "laalbercaciudi";
        }
    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
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
}