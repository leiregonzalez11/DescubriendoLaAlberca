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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.tfg.R;
import com.example.tfg.ajustesFragments.formFragment;
import com.example.tfg.ajustesFragments.idiomasFragment;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;


public class FragmentCategorias extends Fragment implements View.OnClickListener{

    Bundle args;
    Fragment fragment;
    String idioma, path, iu;
    FragmentManager fragmentManager;
    private StorageReference storageRef;
    FragmentTransaction fragmentTransaction;
    protected ImageButton btnhistoria, btnTrad, btnMonu, btnFiesta, btnGastro, btnPers, btnRutas, btnOtros, btnArte, btnArqui;

    public FragmentCategorias() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        args = new Bundle();
        args.putString("iu", "categorias");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categorias, container, false);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        idioma = determinarIdioma();

        storageRef = FirebaseStorage.getInstance().getReference();
        setBtnListeners();

    }

    @SuppressLint("WrongViewCast")
    private void setBtnListeners() {

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

        btnPers = requireView().findViewById(R.id.botonpersonajes);
        //TODO: Cambiar la ruta
        path = "categorias/" + idioma + "/alojamientos-" + idioma + ".jpg";
        btnPers.setOnClickListener(this);
        obtenerImagenFirebase(path, btnPers);

        btnRutas = requireView().findViewById(R.id.botonruta);
        path = "categorias/" + idioma + "/rutas-" + idioma + ".jpg";
        btnRutas.setOnClickListener(this);
        obtenerImagenFirebase(path, btnRutas);

        btnOtros = requireView().findViewById(R.id.botonotros);
        path = "categorias/" + idioma + "/otros-" + idioma + ".jpg";
        btnOtros.setOnClickListener(this);
        obtenerImagenFirebase(path, btnOtros);

    }

    @SuppressLint({"NonConstantResourceId", "ShowToast"})
    public void onClick(View view) {
        //Cuando se presione el botón, realiza una acción aquí

        ImageButton btn = (ImageButton) view;

        switch (btn.getId()){

            case R.id.botonhistoria:
                Toast.makeText(getContext(), "Has pulsado historia", Toast.LENGTH_LONG).show();
                break;

            case R.id.botonartesania:
                Toast.makeText(getContext(), "Has pulsado artesania", Toast.LENGTH_LONG).show();
                break;

            case R.id.botontradiciones:
                Toast.makeText(getContext(), "Has pulsado tradiciones", Toast.LENGTH_LONG).show();
                break;

            case R.id.botonarquitectura:
                Toast.makeText(getContext(), "Has pulsado arquitectura", Toast.LENGTH_LONG).show();
                break;

            case R.id.botonmonumentos:
                Toast.makeText(getContext(), "Has pulsado monumentos", Toast.LENGTH_LONG).show();
                break;

            case R.id.botonfiestas:
                Toast.makeText(getContext(), "Has pulsado Fiestas", Toast.LENGTH_LONG).show();
                break;

            case R.id.botongastronomia:
                Toast.makeText(getContext(), "Has pulsado gastronomia", Toast.LENGTH_LONG).show();
                break;

            case R.id.botonpersonajes:
                Toast.makeText(getContext(), "Has pulsado cultura", Toast.LENGTH_LONG).show();
                break;

            case R.id.botonruta:
                Toast.makeText(getContext(), "Has pulsado rutas", Toast.LENGTH_LONG).show();
                break;

            case R.id.botonotros:
                Toast.makeText(getContext(), "Has pulsado otros", Toast.LENGTH_LONG).show();
                break;

        }
    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageButton btn){
        System.out.println(path);
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(btn));
    }

    public String determinarIdioma() {

        String idioma = null;
        TextView texto = requireView().findViewById(R.id.textidioma);
        String text = texto.getText().toString();
        System.out.println("TEXTO 2 DETERMINAR IDIOMA: " + text);

        if (text.equals("idioma")){
            idioma = "es";
        } else if (text.equals("hizkuntza")){
            idioma = "eu";
        }else if (text.equals("language")){
            idioma="en";
        }/*else if (text.equals("idiomaca")){
            idioma="ca";
        }*/

        return idioma;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menuusuario, menu);
    }

    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.menu_contacto:
                //Toast.makeText(getContext(), "Has pulsado: Contacto", Toast.LENGTH_LONG).show();
                //Añadimos los argumentos
                fragment = new formFragment();
                fragment.setArguments(args);

                // Obtener el administrador de fragmentos a través de la actividad
                fragmentManager = requireActivity().getSupportFragmentManager();

                // Definir una transacción
                fragmentTransaction = fragmentManager.beginTransaction();

                // Remplazar el contenido principal por el fragmento
                fragmentTransaction.replace(R.id.relativelayout, fragment);
                fragmentTransaction.addToBackStack(null);

                // Cambiar
                fragmentTransaction.commit();
                return true;

            case R.id.menu_idioma:
                //Toast.makeText(getContext(), "Has pulsado: Idiomas", Toast.LENGTH_LONG).show();

                //Añadimos los argumentos
                fragment = new idiomasFragment();
                fragment.setArguments(args);

                // Obtener el administrador de fragmentos a través de la actividad
                fragmentManager = requireActivity().getSupportFragmentManager();

                // Definir una transacción
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Remplazar el contenido principal por el fragmento
                fragmentTransaction.replace(R.id.relativelayout, fragment);
                fragmentTransaction.addToBackStack(null);

                // Cambiar
                fragmentTransaction.commit();
                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }



}