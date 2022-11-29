package com.example.tfg.categoriasFragments.secundarias.tradiciones;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.categoriasFragments.principal.tradicionesInicio;
import com.example.tfg.navigationmenu.Categorias;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class laLoa extends Fragment {

    private Bundle args;
    private String idioma, categoria;
    private StorageReference storageRef;

    public laLoa() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);

        if (getArguments() != null) {
            idioma = getArguments().getString("idioma");
            categoria = getArguments().getString("categoria");
        }

        args = new Bundle();
        args.putString("idioma", idioma);
        args.putString("categoria", categoria);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_la_loa, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //Toolbar
        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        myToolbar.setNavigationOnClickListener(v -> {
            myToolbar.setNavigationIcon(null);
            //Creamos el fragment
            Fragment fragment = new Categorias();
            cargarFragment(fragment);
        });

        //Textos de la interfaz
        TextView titulo = requireView().findViewById(R.id.tituloloa);
        TextView text1 = requireView().findViewById(R.id.loa1);
        TextView text2 = requireView().findViewById(R.id.loa2);
        TextView text3 = requireView().findViewById(R.id.loa3);
        TextView text4 = requireView().findViewById(R.id.loa4);
        TextView text5 = requireView().findViewById(R.id.loa5);

        //Imágenes de la interfaz
        ImageView img1 = requireView().findViewById(R.id.imgloa1);
        ImageView img2 = requireView().findViewById(R.id.imgloa2);

        GestorDB dbHelper = new GestorDB(getContext());

        //Setter de los textos de la interfaz
        String nombreTrad = "La Loa";
        titulo.setText(nombreTrad);
        titulo.requestFocus();
        String nombreTradBBDD = nombreTrad.toLowerCase().replaceAll(" ", "");

        //Obtención de datos desde la bbdd
        String[] textoTrad = dbHelper.obtenerInfoTrad(idioma, nombreTradBBDD, categoria, 5);

        text1.setText(textoTrad[0]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text2.setText(textoTrad[1]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text3.setText(textoTrad[2]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text4.setText(textoTrad[3]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text5.setText(textoTrad[4]+ HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        //Setter de las imagenes de la interfaz TODO
        storageRef = FirebaseStorage.getInstance().getReference();

        //Botón atrás
        Button atrasBtn = requireView().findViewById(R.id.loaAtras);
        atrasBtn.setOnClickListener(v -> {
            //Creamos el fragment y añadimos los args
            Fragment fragment = new tradicionesInicio();
            fragment.setArguments(args);
            cargarFragment(fragment);

        });

    }

    /** Método utilizado para obtener las imágenes de Firebase Storage */
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