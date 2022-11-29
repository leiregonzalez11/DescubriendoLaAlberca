package com.example.tfg.categoriasFragments.secundarias.artesania;

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
import com.example.tfg.categoriasFragments.principal.artesaniaInicio;
import com.example.tfg.navigationmenu.Categorias;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class trajeMasculino extends Fragment implements View.OnClickListener{

    Bundle args;
    Fragment fragment;
    String idioma, categoria;
    ImageView img1, img2, img3;
    StorageReference storageRef;

    public trajeMasculino() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);

        args = new Bundle();

        if (getArguments() != null) {
            idioma = getArguments().getString("idioma");
            categoria = getArguments().getString("categoria");
        }

        args.putString("idioma", idioma);
        args.putString("categoria", categoria);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_traje_masculino, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        myToolbar.setNavigationOnClickListener(view1 -> {
            myToolbar.setNavigationIcon(null);
            Fragment fragment = new trajesFemeninos();
            fragment.setArguments(args);
            cargarFragment(fragment);
        });

        img1 = requireView().findViewById(R.id.arte41img);
        img2 = requireView().findViewById(R.id.arte42img);
        img3 = requireView().findViewById(R.id.arte43img);

        GestorDB dbHelper = new GestorDB(getContext());

        String [] datos = dbHelper.obtenerDatosArte(idioma, "trajemasc", categoria, 4);
        setDatosEImagenes(datos);

        //BOTON SIGUIENTE

        Button finBtn = requireView().findViewById(R.id.artefintraje);
        finBtn.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        Button btn = (Button) view;

        if (btn.getId() == R.id.artefintraje) {
            fragment = new artesaniaInicio();
        }

        fragment.setArguments(args);
        cargarFragment(fragment);

    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }

    @SuppressLint("SetTextI18n")
    private void setDatosEImagenes(String [] datos){
        TextView text1 = requireView().findViewById(R.id.arte41);
        TextView text2 = requireView().findViewById(R.id.arte42);
        TextView text3 = requireView().findViewById(R.id.arte43);
        TextView text4 = requireView().findViewById(R.id.arte44);

        text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text4.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        storageRef = FirebaseStorage.getInstance().getReference();
        obtenerImagenFirebase("artesania/hombre1.jpg", img1);
        obtenerImagenFirebase("artesania/hombre2.jpg", img2);
        obtenerImagenFirebase("artesania/hombre3.jpg", img3);
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