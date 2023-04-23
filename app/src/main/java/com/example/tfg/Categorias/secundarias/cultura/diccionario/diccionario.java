package com.example.tfg.Categorias.secundarias.cultura.diccionario;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tfg.R;
import com.bumptech.glide.Glide;
import com.example.tfg.NavigationMenu.Categorias;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class diccionario extends Fragment implements View.OnClickListener {

    private Bundle args;
    private String idioma, letra;
    private StorageReference storageRef;
    private ImageButton btna, btnb, btnc, btnd, btne, btnfg, btnhj, btnmn, btnp, btnrs, btnt, btnuvz;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static diccionario newInstance(Bundle args) {
        diccionario fragment = new diccionario();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public diccionario() {}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        TextView name = myToolbar.findViewById(R.id.name);
        name.setText(R.string.cultura);
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
        View v = inflater.inflate(R.layout.fragment_diccionario, container, false);
        if (v != null){
            btna = v.findViewById(R.id.a);
            btnb = v.findViewById(R.id.b);
            btnc = v.findViewById(R.id.c);
            btnd = v.findViewById(R.id.d);
            btne = v.findViewById(R.id.e);
            btnfg = v.findViewById(R.id.fg);
            btnhj = v.findViewById(R.id.hj);
            btnmn = v.findViewById(R.id.mn);
            btnp = v.findViewById(R.id.p);
            btnrs = v.findViewById(R.id.rs);
            btnt = v.findViewById(R.id.t);
            btnuvz = v.findViewById(R.id.uvz);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        storageRef = FirebaseStorage.getInstance().getReference();
        setListeners();
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

    /** Setter de Listeners para los botones de la interfaz*/
    private void setListeners() {
        btna.setOnClickListener(this);
        btnb.setOnClickListener(this);
        btnc.setOnClickListener(this);
        btnd.setOnClickListener(this);
        btne.setOnClickListener(this);
        btnfg.setOnClickListener(this);
        btnhj.setOnClickListener(this);
        btnmn.setOnClickListener(this);
        btnp.setOnClickListener(this);
        btnrs.setOnClickListener(this);
        btnt.setOnClickListener(this);
        btnuvz.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        ImageButton btn = (ImageButton) view;

        switch (btn.getId()) {
            case R.id.a:
                letra = "a";
                break;
            case R.id.b:
                letra = "b";
                break;
            case R.id.c:
                letra = "c";
                break;
            case R.id.d:
                letra = "d";
                break;
            case R.id.e:
                letra = "e";
                break;
            case R.id.fg:
                letra = "fg";
                break;
            case R.id.hj:
                letra = "hj";
                break;
            case R.id.mn:
                letra = "mnñ";
                break;
            case R.id.p:
                letra = "p";
                break;
            case R.id.rs:
                letra = "rs";
                break;
            case R.id.t:
                letra = "t";
                break;
            case R.id.uvz:
                letra = "uvz";
                break;
        }

        args.putString("letra", letra);
        DialogFragment fragment = new listapalabrasf();
        fragment.setArguments(args);
        fragment.setCancelable(false);
        fragment.show(getChildFragmentManager(),"listap_fragment");
    }
}