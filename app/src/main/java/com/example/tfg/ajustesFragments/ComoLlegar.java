package com.example.tfg.ajustesFragments;

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
import com.example.tfg.adapters.SpinnerAdapter;
import com.example.tfg.navigationmenu.Ajustes;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class ComoLlegar extends Fragment implements  AdapterView.OnItemSelectedListener {

    private StorageReference storageRef;
    private View img1;
    String idioma;
    private String nombreBus;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment BlankFragment.
     */
    public static ComoLlegar newInstance() {
        return new ComoLlegar();
    }

    public ComoLlegar() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_como_llegar, container, false);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        determinarRuta((String) adapterView.getItemAtPosition(position));
        determinarIdioma();
        obtenerImagenFirebase("ajustes/" + nombreBus + "-" + idioma + ".jpg", (ImageView) img1);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        myToolbar.setNavigationOnClickListener(v -> {
            myToolbar.setNavigationIcon(null);
            Fragment fragment = new Ajustes();
            cargarFragment(fragment);
        });

        storageRef = FirebaseStorage.getInstance().getReference();
        img1 = requireView().findViewById(R.id.comollegar3);

        //Spinner
        Spinner spinner = requireView().findViewById(R.id.spinnerBus);
        String [] bus = getResources().getStringArray(R.array.bus);
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(new SpinnerAdapter(getContext(), R.layout.dropdownitenbus, bus));
        spinner.setOnItemSelectedListener(this);
    }

    /** Método utilizado para obtener el idioma actual de la app */
    public void determinarIdioma() {

        TextView texto = requireView().findViewById(R.id.ajustestext);
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