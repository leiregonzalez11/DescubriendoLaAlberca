package com.example.tfg.navigationmenu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.example.tfg.ajustesFragments.FormularioDeContacto;
import com.example.tfg.ajustesFragments.Idiomas;
import com.example.tfg.categoriasFragments.principal.arquitecturaInicio;
import com.example.tfg.categoriasFragments.principal.artesaniaInicio;
import com.example.tfg.categoriasFragments.principal.culturaInicio;
import com.example.tfg.categoriasFragments.principal.fiestasInicio;
import com.example.tfg.categoriasFragments.principal.gastronomiaInicio;
import com.example.tfg.categoriasFragments.principal.historiaInicio;
import com.example.tfg.categoriasFragments.principal.monumentosInicio;
import com.example.tfg.categoriasFragments.principal.otrosLugaresInicio;
import com.example.tfg.categoriasFragments.principal.rutasInicio;
import com.example.tfg.categoriasFragments.principal.tradicionesInicio;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Categorias extends Fragment implements View.OnClickListener{

    Bundle args, argsMenu;
    Fragment fragment;
    String idioma, path, categoria;
    private StorageReference storageRef;
    protected ImageButton btnhistoria, btnTrad, btnMonu, btnFiesta, btnGastro,
            btnCultura, btnRutas, btnOtros, btnArte, btnArqui;

    public Categorias() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        argsMenu = new Bundle();
        argsMenu.putString("iu", "categorias");

        args = new Bundle();
        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(null);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categorias, container, false);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        idioma = determinarIdioma();
        setBtnListeners();
    }

    @SuppressLint({"NonConstantResourceId", "ShowToast"})
    public void onClick(View view) {
        //Cuando se presione el botón, realiza una acción aquí

        ImageButton btn = (ImageButton) view;

        switch (btn.getId()){

            case R.id.botonhistoria:
                categoria = "historia";
                fragment = new historiaInicio();
                break;

            case R.id.botonartesania:
                categoria = "artesania";
                fragment = new artesaniaInicio();
                break;

            case R.id.botontradiciones:
                categoria = "tradiciones";
                fragment = new tradicionesInicio();
                break;

            case R.id.botonarquitectura:
                categoria = "arquitectura";
                fragment = new arquitecturaInicio();
                break;

            case R.id.botonmonumentos:
                categoria = "sitiosdeinteres";
                fragment = new monumentosInicio();
                break;

            case R.id.botonfiestas:
                categoria = "fiestas";
                fragment = new fiestasInicio();
                break;

            case R.id.botongastronomia:
                categoria = "gastronomia";
                fragment = new gastronomiaInicio();
                break;

            case R.id.botoncultura:
                categoria = "cultura";
                fragment = new culturaInicio();
                break;

            case R.id.botonruta:
                categoria = "rutas";
                fragment = new rutasInicio();
                break;

            case R.id.botonotros:
                categoria = "otroslugares";
                fragment = new otrosLugaresInicio();
                break;
        }

        idioma = determinarIdioma();
        args.putString("idioma", idioma);
        args.putString("categoria", categoria);
        fragment.setArguments(args);
        cargarFragment(fragment);
    }

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menuusuario, menu);
    }

    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.menu_contacto:
                //Creamos el fragmento
                fragment = new FormularioDeContacto();
                break;

            case R.id.menu_idioma:
                //Creamos el fragmento
                fragment = new Idiomas();
                break;

            default:
                return super.onOptionsItemSelected(menuItem);
        }

        //Añadimos los argumentos
        fragment.setArguments(argsMenu);
        cargarFragment(fragment);

        return true;
    }

    /** Setters de los listeners de las categorias */
    @SuppressLint("WrongViewCast")
    private void setBtnListeners() {

        storageRef = FirebaseStorage.getInstance().getReference();

        btnhistoria = requireView().findViewById(R.id.botonhistoria);
        path = "categorias/" + idioma + "/historia-" + idioma + ".jpg";
        btnhistoria.setOnClickListener(this);
        obtenerImagenFirebase(path, btnhistoria);

        btnArte = requireView().findViewById(R.id.botonartesania);
        path = "categorias/" + idioma + "/artesania-" + idioma + ".jpg";
        btnArte.setOnClickListener(this);
        obtenerImagenFirebase(path, btnArte);

        btnTrad = requireView().findViewById(R.id.botontradiciones);
        path = "categorias/" + idioma + "/tradicion-" + idioma + ".jpg";
        btnTrad.setOnClickListener(this);
        obtenerImagenFirebase(path, btnTrad);

        btnArqui = requireView().findViewById(R.id.botonarquitectura);
        path = "categorias/" + idioma + "/arquitectura-" + idioma + ".jpg";
        btnArqui.setOnClickListener(this);
        obtenerImagenFirebase(path, btnArqui);

        btnMonu = requireView().findViewById(R.id.botonmonumentos);
        path = "categorias/" + idioma + "/monumentos-" + idioma + ".jpg";
        btnMonu.setOnClickListener(this);
        obtenerImagenFirebase(path, btnMonu);

        btnFiesta = requireView().findViewById(R.id.botonfiestas);
        path = "categorias/" + idioma + "/fiestas-" + idioma + ".jpg";
        btnFiesta.setOnClickListener(this);
        obtenerImagenFirebase(path, btnFiesta);

        btnGastro = requireView().findViewById(R.id.botongastronomia);
        path = "categorias/" + idioma + "/gastronomia-" + idioma + ".jpg";
        btnGastro.setOnClickListener(this);
        obtenerImagenFirebase(path, btnGastro);

        btnCultura = requireView().findViewById(R.id.botoncultura);
        //TODO: Cambiar la ruta
        path = "categorias/" + idioma + "/alojamientos-" + idioma + ".jpg";
        btnCultura.setOnClickListener(this);
        obtenerImagenFirebase(path, btnCultura);

        btnRutas = requireView().findViewById(R.id.botonruta);
        path = "categorias/" + idioma + "/rutas-" + idioma + ".jpg";
        btnRutas.setOnClickListener(this);
        obtenerImagenFirebase(path, btnRutas);

        btnOtros = requireView().findViewById(R.id.botonotros);
        path = "categorias/" + idioma + "/otros-" + idioma + ".jpg";
        btnOtros.setOnClickListener(this);
        obtenerImagenFirebase(path, btnOtros);

    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageButton btn){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(btn));
    }

    /** Método utilizado para obtener el idioma actual de la app */
    public String determinarIdioma() {

        String idioma = null;
        TextView texto = requireView().findViewById(R.id.textidioma);
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