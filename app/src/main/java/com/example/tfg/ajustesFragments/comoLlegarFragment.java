package com.example.tfg.ajustesFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.tfg.R;
import com.example.tfg.adapters.SpinnerAdapter;
import com.example.tfg.navigationmenu.FragmentAjustes;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;


public class comoLlegarFragment extends Fragment implements  AdapterView.OnItemSelectedListener, View.OnClickListener{

    private StorageReference storageRef;
    private View img1;
    private String nombreBus;

    public comoLlegarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(false);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_como_llegar, container, false);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        storageRef = FirebaseStorage.getInstance().getReference();
        img1 = requireView().findViewById(R.id.comollegar3);

        //Spinner
        Spinner spinner = requireView().findViewById(R.id.spinnerBus);
        String [] bus = getResources().getStringArray(R.array.bus);
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(new SpinnerAdapter(getContext(), R.layout.dropdownitenbus, bus));
        //spinner.setAdapter(new ArrayAdapter<>(this, R.layout.dropdownitenbus, bus));
        spinner.setOnItemSelectedListener(this);

        //Botón atras
        //ImageButton atrasBtn = requireView().findViewById(R.id.atrasBtnComoLlegar);
        //atrasBtn.setOnClickListener(this);

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


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        determinarRuta((String) adapterView.getItemAtPosition(position));
        obtenerImagenFirebase("ajustes/" + nombreBus + ".jpg", (ImageView) img1);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        //Cuando se presione el botón, realiza una acción aquí

        /*ImageButton btn = (ImageButton) view;

        if (btn.getId() == R.id.atrasBtnComoLlegar){
            //Definimos los argumentos

            //Creamos el Fragment
            Fragment fragment = new FragmentAjustes();

            // Obtenemos el administrador de fragmentos a través de la actividad
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

            // Definimos una transacción
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Remplazamos el contenido principal por el fragmento
            fragmentTransaction.replace(R.id.relativelayout, fragment);
            fragmentTransaction.addToBackStack(null);

            // Cambiamos el fragment en la interfaz
            fragmentTransaction.commit();
        }*/
    }


}