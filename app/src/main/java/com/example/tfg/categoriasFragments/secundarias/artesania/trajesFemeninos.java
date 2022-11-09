package com.example.tfg.categoriasFragments.secundarias.artesania;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.adapters.SpinnerAdapter;
import com.example.tfg.navigationmenu.Categorias;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class trajesFemeninos extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    Bundle args;
    GestorDB dbHelper;
    ImageView img1, img2;
    TextView text1, text2, text3;
    StorageReference storageRef;
    String idioma, categoria, nombreTraje;

    public trajesFemeninos() {
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
        return inflater.inflate(R.layout.fragment_trajes_femeninos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbarPrueba);
        myToolbar.setNavigationIcon(R.drawable.arrow_back);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myToolbar.setNavigationIcon(null);
                Fragment fragment = new Categorias();

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
        });

        dbHelper = new GestorDB(getContext());

        text1 = requireView().findViewById(R.id.arte31);
        text2 = requireView().findViewById(R.id.arte32);
        text3 = requireView().findViewById(R.id.arte33);

        img1 = requireView().findViewById(R.id.arte31img);
        img2 = requireView().findViewById(R.id.arte32img);

        //Spinner

        Spinner spinner = requireView().findViewById(R.id.spinner);
        String [] trajes = getResources().getStringArray(R.array.trajes_serranos);
        spinner.setAdapter(new SpinnerAdapter(getContext(), R.layout.dropdownitemartesania, trajes));
        spinner.setOnItemSelectedListener(this);

        storageRef = FirebaseStorage.getInstance().getReference();

        //BOTON SIGUIENTE y ATRAS

        Button atrasBtn2 = requireView().findViewById(R.id.arteAtras3);
        atrasBtn2.setOnClickListener(this);

        Button siguienteBtn = requireView().findViewById(R.id.artesiguiente3);
        siguienteBtn.setOnClickListener(this);


    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        //Cuando se presione el botón, realiza una acción aquí

        Button btn = (Button) view;
        Fragment fragment = null;

        switch (btn.getId()){

            case R.id.arteAtras3:
                fragment = new artesaniaSelector();
                break;

            case R.id.artesiguiente3:
                fragment = new trajeMasculino();
                break;

        }

        assert fragment != null;
        fragment.setArguments(args);

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

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        nombreTraje = determinarTraje((String) adapterView.getItemAtPosition(position));

        String [] datos = dbHelper.obtenerDatosTrajes(idioma, "trajes", categoria, 3, nombreTraje);

        if (nombreTraje.equalsIgnoreCase("vistas") & idioma.equalsIgnoreCase("en")){
            text1.setText(datos[1] + Html.fromHtml("<br>"));
            text2.setText(datos[0] + Html.fromHtml("<br>"));
            text3.setText(datos[2]);
        } else if (nombreTraje.equalsIgnoreCase("manteo") & idioma.equalsIgnoreCase("en")){
            text1.setText(datos[2] + Html.fromHtml("<br>"));
            text2.setText(datos[0] + Html.fromHtml("<br>"));
            text3.setText(datos[1] + Html.fromHtml("<br>"));
        } else{
            text1.setText(datos[0] + Html.fromHtml("<br>"));
            text2.setText(datos[1] + Html.fromHtml("<br>"));
            text3.setText(datos[2] + Html.fromHtml("<br>"));
        }
        obtenerImagenFirebase("artesania/" + nombreTraje + "1.jpg", img1);
        obtenerImagenFirebase("artesania/" + nombreTraje + "2.jpg", img2);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private String determinarTraje(String idtraje) {

        if(idtraje.toLowerCase().contains("sayas")){
            nombreTraje = "sayas";

            //Ajustamos las imagenes al texto
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(img1.getLayoutParams());
            lp.topMargin = -180;
            lp.rightMargin = 40;
            lp.leftMargin = 40;
            lp.bottomMargin = -180;
            img1.setLayoutParams(lp);


        } else if (idtraje.toLowerCase().contains("ventioseno")){
            nombreTraje = "ventioseno";//Ajustamos las imagenes al texto

            //Ajustamos las imagenes al texto
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(img1.getLayoutParams());
            lp.topMargin = 0;
            lp.rightMargin = 40;
            lp.leftMargin = 40;
            lp.bottomMargin = 0;
            img1.setLayoutParams(lp);

        } else if (idtraje.toLowerCase().contains("vistas")){
            nombreTraje = "vistas";

            //Ajustamos las imagenes al texto
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(img1.getLayoutParams());
            lp.topMargin = 0;
            lp.rightMargin = 40;
            lp.leftMargin = 40;
            lp.bottomMargin = 0;
            img1.setLayoutParams(lp);

        } else if (idtraje.toLowerCase().contains("zagalejo")){
            nombreTraje = "zagalejo";

            //Ajustamos las imagenes al texto
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(img1.getLayoutParams());
            lp.topMargin = 0;
            lp.rightMargin = 40;
            lp.leftMargin = 40;
            lp.bottomMargin = 0;
            img1.setLayoutParams(lp);


        } else if (idtraje.toLowerCase().contains("manteo")){
            nombreTraje = "manteo";

            //Ajustamos las imagenes al texto
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(img1.getLayoutParams());
            lp.topMargin = 0;
            lp.rightMargin = 40;
            lp.leftMargin = 40;
            lp.bottomMargin = 0;
            img1.setLayoutParams(lp);

        }

        return nombreTraje;
    }
}