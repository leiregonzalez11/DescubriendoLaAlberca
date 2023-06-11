package com.example.tfg.NavigationMenu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.tfg.R;
import com.example.tfg.Categorias.principal.arquitecturaInicio;
import com.example.tfg.Categorias.principal.artesaniaInicio;
import com.example.tfg.Categorias.principal.culturaInicio;
import com.example.tfg.Categorias.principal.fiestasInicio;
import com.example.tfg.Categorias.principal.gastronomiaInicio;
import com.example.tfg.Categorias.principal.historiaInicio;
import com.example.tfg.Categorias.principal.rutasInicio;
import com.example.tfg.Categorias.principal.tradicionesInicio;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Categorias extends Fragment implements View.OnClickListener{

    TextView texto;
    Fragment fragment;
    Toolbar myToolbar;
    Bundle args, argsMenu;
    String idioma, path;
    private StorageReference storageRef;
    protected ImageButton btnhistoria, btnTrad, btnFiesta, btnGastro,
            btnCultura, btnRutas, btnArte, btnArqui;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static Categorias newInstance() {
        return new Categorias();
    }

    /** Required empty public constructor */
    public Categorias() {}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(null);
        TextView name = myToolbar.findViewById(R.id.name);
        name.setText(R.string.categorias);
        name.setTextSize(20);

        argsMenu = new Bundle(); //Argumentos para el menu de opciones
        argsMenu.putString("iu", "categorias");
        args = new Bundle();
    }

    /** El Fragment va a cargar su layout, el cual debemos especificar.
        Aquí se instanciarán los objetos que si son vistas */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_categorias, container, false);
        if(v != null){
            myToolbar = requireActivity().findViewById(R.id.toolbar);
            btnhistoria = v.findViewById(R.id.botonhistoria);
            btnArte = v.findViewById(R.id.botonartesania);
            btnTrad = v.findViewById(R.id.botontradiciones);
            btnArqui = v.findViewById(R.id.botonarquitectura);
            btnFiesta = v.findViewById(R.id.botonfiestas);
            btnGastro = v.findViewById(R.id.botongastronomia);
            btnCultura = v.findViewById(R.id.botoncultura);
            btnRutas = v.findViewById(R.id.botonruta);
            texto = v.findViewById(R.id.textidioma);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
        Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        myToolbar.setNavigationIcon(null);
        idioma = determinarIdioma();
        setBtnListeners();
    }

    @SuppressLint({"NonConstantResourceId", "ShowToast"})
    public void onClick(View view) {
        //Cuando se presione el botón, realiza una acción aquí

        ImageButton btn = (ImageButton) view;

        idioma = determinarIdioma();
        args.putString("idioma", idioma);

        switch (btn.getId()){

            case R.id.botonhistoria:
                fragment = historiaInicio.newInstance(args);
                break;

            case R.id.botonartesania:
                fragment = artesaniaInicio.newInstance(args);
                break;

            case R.id.botontradiciones:
                fragment = tradicionesInicio.newInstance(args);
                break;

            case R.id.botonarquitectura:
                fragment = arquitecturaInicio.newInstance(args);
                break;

            case R.id.botonfiestas:
                fragment = fiestasInicio.newInstance(args);
                break;

            case R.id.botongastronomia:
                fragment = gastronomiaInicio.newInstance(args);
                break;

            case R.id.botoncultura:
                fragment = culturaInicio.newInstance(args);
                break;

            case R.id.botonruta:
                fragment = rutasInicio.newInstance(args);
                break;

        }

        cargarFragment(fragment);
    }

    /** Setters de los listeners de las categorias */
    @SuppressLint("WrongViewCast")
    private void setBtnListeners() {

        storageRef = FirebaseStorage.getInstance().getReference();

        path = "categorias/portadacategorias/" + idioma + "/historia-" + idioma + ".png";
        btnhistoria.setOnClickListener(this);
        obtenerImagenFirebase(path, btnhistoria);

        path = "categorias/portadacategorias/" + idioma + "/artesania-" + idioma + ".png";
        btnArte.setOnClickListener(this);
        obtenerImagenFirebase(path, btnArte);

        path = "categorias/portadacategorias/" + idioma + "/tradiciones-" + idioma + ".png";
        btnTrad.setOnClickListener(this);
        obtenerImagenFirebase(path, btnTrad);

        path = "categorias/portadacategorias/" + idioma + "/arquitectura-" + idioma + ".png";
        btnArqui.setOnClickListener(this);
        obtenerImagenFirebase(path, btnArqui);

        path = "categorias/portadacategorias/" + idioma + "/fiestas-" + idioma + ".png";
        btnFiesta.setOnClickListener(this);
        obtenerImagenFirebase(path, btnFiesta);

        path = "categorias/portadacategorias/" + idioma + "/gastronomia-" + idioma + ".png";
        btnGastro.setOnClickListener(this);
        obtenerImagenFirebase(path, btnGastro);

        path = "categorias/portadacategorias/" + idioma + "/cultura-" + idioma + ".png";
        btnCultura.setOnClickListener(this);
        obtenerImagenFirebase(path, btnCultura);

        path = "categorias/portadacategorias/" + idioma + "/rutas-" + idioma + ".png";
        btnRutas.setOnClickListener(this);
        obtenerImagenFirebase(path, btnRutas);

    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageButton btn){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(btn));
    }

    /** Método utilizado para obtener el idioma actual de la app */
    public String determinarIdioma() {

        String idioma = null;
        String text = texto.getText().toString();

        switch (text) {
            case "idioma":
                idioma = "es";
                break;
            case "hizkuntza":
                idioma = "eu";
                break;
            case "language":
                idioma = "en";
                break;
        }

        return idioma;
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