package com.example.tfg.Categorias.principal;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tfg.R;
import com.example.tfg.NavigationMenu.Categorias;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class fiestasInicio extends Fragment implements View.OnClickListener {

    private String idioma, path, mes;
    private ImageButton btnenero, btnfebrero, btnmarzo, btnabril, btnmayo, 
            btnjunio, btnjulio, btnagosto, btnseptiembre, btnoctubre, btnnoviembre, btndiciembre;
    private StorageReference storageRef;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static fiestasInicio newInstance(Bundle args) {
        fiestasInicio fragment = new fiestasInicio();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public fiestasInicio() {}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(false);
        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        TextView name = myToolbar.findViewById(R.id.name);
        name.setText(R.string.categorias);
        name.setTextSize(20);
        myToolbar.setNavigationOnClickListener(view12 -> {
            myToolbar.setNavigationIcon(null);
            Fragment fragment = Categorias.newInstance();
            cargarFragment(fragment);
        });

        if (getArguments() != null) {
            idioma = getArguments().getString("idioma");
        }

        Bundle args = new Bundle();
        args.putString("idioma", idioma);

    }

    /** El Fragment va a cargar su layout, el cual debemos especificar.
     Aquí se instanciarán los objetos que si son vistas */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_fiestas_inicio, container, false);
        if(v != null){
            btnenero = v.findViewById(R.id.enerobtn);
            btnfebrero = v.findViewById(R.id.febrerobtn);
            btnmarzo = v.findViewById(R.id.marzobtn);
            btnabril = v.findViewById(R.id.abrilbtn);
            btnmayo = v.findViewById(R.id.mayobtn);
            btnjunio = v.findViewById(R.id.juniobtn);
            btnjulio = v.findViewById(R.id.juliobtn);
            btnagosto = v.findViewById(R.id.agostobtn);
            btnseptiembre = v.findViewById(R.id.septiembrebtn);
            btnoctubre = v.findViewById(R.id.octubrebtn);
            btnnoviembre = v.findViewById(R.id.noviembrebtn);
            btndiciembre = v.findViewById(R.id.diciembrebtn);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setBtnListeners();
    }

    @SuppressLint({"NonConstantResourceId", "ShowToast"})
    public void onClick(View view) {
        //Cuando se presione el botón, realiza una acción aquí

        ImageButton btn = (ImageButton) view;

        switch (btn.getId()){

            case R.id.enerobtn:
                mes = "enero";
                break;
            case R.id.febrerobtn:
                mes = "febrero";
                break;
            case R.id.marzobtn:
                mes = "marzo";
                break;
            case R.id.abrilbtn:
                mes = "abril";
                break;
            case R.id.mayobtn:
                mes = "mayo";
                break;
            case R.id.juniobtn:
                mes = "junio";
                break;
            case R.id.juliobtn:
                mes = "julio";
                break;
            case R.id.agostobtn:
                mes = "agosto";
                break;
            case R.id.septiembrebtn:
                mes = "septiembre";
                break;
            case R.id.octubrebtn:
                mes = "octubre";
                break;
            case R.id.noviembrebtn:
                mes = "noviembre";
                break;
            case R.id.diciembrebtn:
                mes = "diciembre";
                break;
        }

        Toast.makeText(requireContext(), "Has pulsado: " + mes, Toast.LENGTH_SHORT).show();
    }

    /** Setters de los listeners de las categorias */
    @SuppressLint("WrongViewCast")
    private void setBtnListeners() {

        storageRef = FirebaseStorage.getInstance().getReference();

        path = "categorias/fiestas/portadameses/" + idioma + "/enero-" + idioma + ".png";
        btnenero.setOnClickListener(this);
        obtenerImagenFirebase(path, btnenero);

        path = "categorias/fiestas/portadameses/" + idioma + "/febrero-" + idioma + ".png";
        btnfebrero.setOnClickListener(this);
        obtenerImagenFirebase(path, btnfebrero);

        path = "categorias/fiestas/portadameses/" + idioma + "/marzo-" + idioma + ".png";
        btnmarzo.setOnClickListener(this);
        obtenerImagenFirebase(path, btnmarzo);

        path = "categorias/fiestas/portadameses/" + idioma + "/abril-" + idioma + ".png";
        btnabril.setOnClickListener(this);
        obtenerImagenFirebase(path, btnabril);

        path = "categorias/fiestas/portadameses/" + idioma + "/mayo-" + idioma + ".png";
        btnmayo.setOnClickListener(this);
        obtenerImagenFirebase(path, btnmayo);

        path = "categorias/fiestas/portadameses/" + idioma + "/junio-" + idioma + ".png";
        btnjunio.setOnClickListener(this);
        obtenerImagenFirebase(path, btnjunio);

        path = "categorias/fiestas/portadameses/" + idioma + "/julio-" + idioma + ".png";
        btnjulio.setOnClickListener(this);
        obtenerImagenFirebase(path, btnjulio);

        path = "categorias/fiestas/portadameses/" + idioma + "/agosto-" + idioma + ".png";
        btnagosto.setOnClickListener(this);
        obtenerImagenFirebase(path, btnagosto);

        path = "categorias/fiestas/portadameses/" + idioma + "/septiembre-" + idioma + ".png";
        btnseptiembre.setOnClickListener(this);
        obtenerImagenFirebase(path, btnseptiembre);

        path = "categorias/fiestas/portadameses/" + idioma + "/octubre-" + idioma + ".png";
        btnoctubre.setOnClickListener(this);
        obtenerImagenFirebase(path, btnoctubre);

        path = "categorias/fiestas/portadameses/" + idioma + "/noviembre-" + idioma + ".png";
        btnnoviembre.setOnClickListener(this);
        obtenerImagenFirebase(path, btnnoviembre);

        path = "categorias/fiestas/portadameses/" + idioma + "/diciembre-" + idioma + ".png";
        btndiciembre.setOnClickListener(this);
        obtenerImagenFirebase(path, btndiciembre);

    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageButton btn){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(btn));
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