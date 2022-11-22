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
import com.example.tfg.navigationmenu.Categorias;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class bordadoSerrano extends Fragment {

    Bundle args;
    String idioma, categoria;
    ImageView img1, img2, img3;
    StorageReference storageRef;
    TextView text1, text2, text3, text4;

    public bordadoSerrano() {
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
        return inflater.inflate(R.layout.fragment_bordado_serrano, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        myToolbar.setNavigationOnClickListener(view12 -> {
            myToolbar.setNavigationIcon(null);
            Fragment fragment = new Categorias();
            cargarFragment(fragment);
        });

        GestorDB dbHelper = new GestorDB(getContext());

        //OBTENEMOS LOS TEXTOS Y LAS IMAGENES DE LA INTERFAZ

        String [] datos = dbHelper.obtenerDatosArte(idioma, "bordado", categoria, 4);

        text1 = requireView().findViewById(R.id.arte21);
        text2 = requireView().findViewById(R.id.arte22);
        text3 = requireView().findViewById(R.id.arte23);
        text4 = requireView().findViewById(R.id.arte24);

        text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text4.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        storageRef = FirebaseStorage.getInstance().getReference();

        img1 = requireView().findViewById(R.id.arte21img);
        obtenerImagenFirebase("artesania/bordado1.jpg", img1);
        img2 = requireView().findViewById(R.id.arte22img);
        obtenerImagenFirebase("artesania/bordado2.jpg", img2);
        img3 = requireView().findViewById(R.id.arte23img);
        obtenerImagenFirebase("artesania/bordado3.jpg", img3);

        //BOTON ATRAS

        Button atrasBtn2 = requireView().findViewById(R.id.arteAtras2);
        atrasBtn2.setOnClickListener(view1 -> {
            Fragment fragment = new artesaniaSelector();
            fragment.setArguments(args);
            cargarFragment(fragment);
        });

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