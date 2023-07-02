package com.example.tfg.categorias.principal;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.navigationMenu.Categorias;
import com.example.tfg.otherFiles.adapters.SpinnerAdapter;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class fiestasInicio extends Fragment implements AdapterView.OnItemSelectedListener {

    private String idioma, nombreFiesta;
    private Spinner spinner;
    private Button upBtn;
    private GestorDB dbHelper;
    private StorageReference storageRef;

    private TextView text1,text2,text3,text4,text5,text6,text7,text8, focus, titulo;
    private ImageView img1,img2,img3,img4,img5;
    Bundle args;

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

        args = new Bundle();
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
            spinner = v.findViewById(R.id.spinnerFiestas);
            focus = v.findViewById(R.id.focusfiestas);
            titulo = v.findViewById(R.id.tituloF);
            text1 = v.findViewById(R.id.fiestas1);
            text2 = v.findViewById(R.id.fiestas2);
            text3 = v.findViewById(R.id.fiestas3);
            text4 = v.findViewById(R.id.fiestas4);
            text5 = v.findViewById(R.id.fiestas5);
            text6 = v.findViewById(R.id.fiestas6);
            text7 = v.findViewById(R.id.fiestas7);
            text8 = v.findViewById(R.id.fiestas8);
            upBtn = v.findViewById(R.id.upbtn);
            img1  = v.findViewById(R.id.fiestaimg1);
            img2  = v.findViewById(R.id.fiestaimg2);
            img3  = v.findViewById(R.id.fiestaimg3);
            img4  = v.findViewById(R.id.fiestaimg4);
            img5  = v.findViewById(R.id.fiestaimg5);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        dbHelper = GestorDB.getInstance(requireContext());
        storageRef = FirebaseStorage.getInstance().getReference();

        String [] fiestas = getResources().getStringArray(R.array.opcionesFiestas);
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(new SpinnerAdapter(requireContext(), R.layout.dropsownitemsimple, fiestas));
        spinner.setOnItemSelectedListener(this);

        upBtn.setOnClickListener(v -> {
            focus.clearFocus();
            focus.requestFocus();
        });

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        titulo.setText((String) adapterView.getItemAtPosition(position));
        nombreFiesta = determinarFiesta(position);
        
        if (position == 0 || position == 1){
            String[] datos = dbHelper.obtenerDatosFiestas(idioma, nombreFiesta, 2); //San Anton y San Sebastian

            text1.setVisibility(View.VISIBLE);
            text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text2.setVisibility(View.VISIBLE);
            text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text3.setVisibility(View.GONE);
            text4.setVisibility(View.GONE);
            text5.setVisibility(View.GONE);
            text6.setVisibility(View.GONE);
            text7.setVisibility(View.GONE);
            text8.setVisibility(View.GONE);

            img1.setVisibility(View.VISIBLE);
            img2.setVisibility(View.GONE);
            img3.setVisibility(View.GONE);
            img4.setVisibility(View.GONE);
            img5.setVisibility(View.GONE);

            obtenerImagenFirebase("categorias/fiestas/" + nombreFiesta + "1.png", img1);
            
        } else if (position == 3 || position == 6 || position == 9){

            if (position != 9){
                String[] datos = dbHelper.obtenerDatosFiestas(idioma, nombreFiesta, 4); //Carnavales y Día del Trago

                text1.setVisibility(View.VISIBLE);
                text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text2.setVisibility(View.VISIBLE);
                text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setVisibility(View.VISIBLE);
                text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text4.setVisibility(View.VISIBLE);
                text4.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text5.setVisibility(View.GONE);
                text6.setVisibility(View.GONE);
                text7.setVisibility(View.GONE);
                text8.setVisibility(View.GONE);
            } else {
                String[] datos = dbHelper.obtenerDatosFiestas(idioma, nombreFiesta, 5); //Santa Águeda

                text1.setVisibility(View.VISIBLE);
                text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text2.setVisibility(View.VISIBLE);
                text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setVisibility(View.VISIBLE);
                text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text4.setVisibility(View.VISIBLE);
                text4.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text5.setVisibility(View.VISIBLE);
                text5.setText(datos[4] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text6.setVisibility(View.GONE);
                text7.setVisibility(View.GONE);
                text8.setVisibility(View.GONE);
            }

            img1.setVisibility(View.VISIBLE);
            img2.setVisibility(View.VISIBLE);
            img3.setVisibility(View.VISIBLE);
            img4.setVisibility(View.GONE);
            img5.setVisibility(View.GONE);

            obtenerImagenFirebase("categorias/fiestas/" + nombreFiesta + "1.png", img1);
            obtenerImagenFirebase("categorias/fiestas/" + nombreFiesta + "2.png", img2);
            obtenerImagenFirebase("categorias/fiestas/" + nombreFiesta + "3.png", img3);
            
        } else if (position == 5 || position == 7 || position == 10){
            String[] datos = dbHelper.obtenerDatosFiestas(idioma, nombreFiesta, 3); //Majadas, Semana Santa y Corpus

            text1.setVisibility(View.VISIBLE);
            text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text2.setVisibility(View.VISIBLE);
            text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text3.setVisibility(View.VISIBLE);
            text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text4.setVisibility(View.GONE);
            text5.setVisibility(View.GONE);
            text6.setVisibility(View.GONE);
            text7.setVisibility(View.GONE);
            text8.setVisibility(View.GONE);

            img1.setVisibility(View.VISIBLE);
            img2.setVisibility(View.VISIBLE);
            img3.setVisibility(View.GONE);
            img4.setVisibility(View.GONE);
            img5.setVisibility(View.GONE);

            obtenerImagenFirebase("categorias/fiestas/" + nombreFiesta + "1.png", img1);
            obtenerImagenFirebase("categorias/fiestas/" + nombreFiesta + "2.png", img2);
            
            
        } else if (position == 4){

            String[] datos = dbHelper.obtenerDatosFiestas(idioma, nombreFiesta, 8); //Las Candelas

            text1.setVisibility(View.VISIBLE);
            text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text2.setVisibility(View.VISIBLE);
            text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text3.setVisibility(View.VISIBLE);
            text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text4.setVisibility(View.VISIBLE);
            text4.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text5.setVisibility(View.VISIBLE);
            text5.setText(datos[4] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text6.setVisibility(View.VISIBLE);
            text6.setText(datos[5] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text7.setVisibility(View.VISIBLE);
            text7.setText(datos[6] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text8.setVisibility(View.VISIBLE);
            text8.setText(datos[7] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

            img1.setVisibility(View.VISIBLE);
            img2.setVisibility(View.VISIBLE);
            img3.setVisibility(View.VISIBLE);
            img4.setVisibility(View.VISIBLE);
            img5.setVisibility(View.VISIBLE);

            obtenerImagenFirebase("categorias/fiestas/" + nombreFiesta + "1.png", img1);
            obtenerImagenFirebase("categorias/fiestas/" + nombreFiesta + "2.png", img2);
            obtenerImagenFirebase("categorias/fiestas/" + nombreFiesta + "3.png", img3);
            obtenerImagenFirebase("categorias/fiestas/" + nombreFiesta + "4.png", img4);
            obtenerImagenFirebase("categorias/fiestas/" + nombreFiesta + "5.png", img5);
            
        } else if (position == 2 || position == 8){ //Fiestas Patronales y Cristo del Sudor //TODO

            text1.setVisibility(View.GONE);
            text2.setVisibility(View.GONE);
            text3.setVisibility(View.GONE);
            text4.setVisibility(View.GONE);
            text5.setVisibility(View.GONE);
            text6.setVisibility(View.GONE);
            text7.setVisibility(View.GONE);
            text8.setVisibility(View.GONE);

            img1.setVisibility(View.GONE);
            img2.setVisibility(View.GONE);
            img3.setVisibility(View.GONE);
            img4.setVisibility(View.GONE);
            img5.setVisibility(View.GONE);
        }
        
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /** Método utilizado para conocer la ruta elegida por el usuario para obtener la información */
    private String determinarFiesta(int idFiesta) {

        switch (idFiesta){
            case 0:
                nombreFiesta = "sananton";
                break;
            case 1:
                nombreFiesta = "sansebastian";
                break;
            case 2:
                nombreFiesta = "fiestasagosto";
                break;
            case 3:
                nombreFiesta = "carnavales";
                break;
            case 4:
                nombreFiesta = "lascandelas";
                break;
            case 5:
                nombreFiesta = "semanasanta";
                break;
            case 6:
                nombreFiesta = "diadeltrago";
                break;
            case 7:
                nombreFiesta = "romeriamajadas";
                break;
            case 8:
                nombreFiesta = "cristosudor";
                break;
            case 9:
                nombreFiesta = "santaagueda";
                break;
            case 10:
                nombreFiesta = "corpus";
                break;
        }

        return nombreFiesta;
    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView btn){
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