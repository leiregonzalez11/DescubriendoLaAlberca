package com.example.tfg.categoriasFragments.principal;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.adapters.SpinnerAdapter;
import com.example.tfg.navigationmenu.Categorias;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class rutasInicio extends Fragment implements AdapterView.OnItemSelectedListener {

    private GestorDB dbHelper;
    private String idioma, categoria, nombreRuta;
    private ImageView img1, img2, img3;
    private TextView text2, text3, text4, text5, text6;
    private StorageReference storageRef;

    public rutasInicio() {
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rutas, container, false);
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

        dbHelper = new GestorDB(requireContext());
        storageRef = FirebaseStorage.getInstance().getReference();

        text2 = requireView().findViewById(R.id.rutas2);
        text3 = requireView().findViewById(R.id.rutas3);
        text4 = requireView().findViewById(R.id.rutas4);
        text5 = requireView().findViewById(R.id.rutas5);
        text6 = requireView().findViewById(R.id.rutas6);

        img1 = requireView().findViewById(R.id.rutasimg1);
        img2 = requireView().findViewById(R.id.rutasimg2);
        img3 = requireView().findViewById(R.id.rutasimg7);

        Spinner spinner = requireView().findViewById(R.id.spinnerRutas);
        String [] rutas = getResources().getStringArray(R.array.rutas);
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(new SpinnerAdapter(requireContext(), R.layout.dropdownitemrutas, rutas));
        spinner.setOnItemSelectedListener(this);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        nombreRuta = determinarRuta((String) adapterView.getItemAtPosition(position));

        String[] datos = dbHelper.obtenerDatosRutas(idioma, nombreRuta, categoria);

        System.out.println("RUTAAAAA " + nombreRuta);
        for (int i = 0; i < datos.length; i++){
            System.out.println("RUTAAAAA " + i + datos[i]);
        }

        text2.setText(datos[0] + Html.fromHtml("<br>"));
        text3.setText(datos[1]);
        text4.setText(datos[2]);
        text5.setText(datos[4]);
        text6.setText(datos[3]);
        obtenerImagenFirebase("rutas/" + nombreRuta + "1.jpg", img1);
        obtenerImagenFirebase("rutas/" + nombreRuta + "2.jpg", img2);
        obtenerImagenFirebase("rutas/" + nombreRuta + "3.jpg", img3);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    /** Método utilizado para conocer la ruta elegida por el usuario para obtener la información */
    private String determinarRuta(String idRuta) {

        if(idRuta.toLowerCase().contains("francia")){
            nombreRuta = "peñadefrancia";
        } else if (idRuta.toLowerCase().contains("raíces")){
            nombreRuta = "raices";
        } else if (idRuta.toLowerCase().contains("torrita")){
            nombreRuta = "torrita";
        }else if (idRuta.toLowerCase().contains("huevo")){
            nombreRuta = "peñadelhuevo";
        }else if (idRuta.toLowerCase().contains("mogarraz")){
            nombreRuta = "mogarraz";
        }else if (idRuta.toLowerCase().contains("martin")){
            nombreRuta = "sanmartin";
        }

        return nombreRuta;
    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }

}