package com.example.tfg.categorias.principal;

import android.os.Bundle;
import com.example.tfg.R;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import android.widget.ImageView;
import android.widget.AdapterView;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import android.annotation.SuppressLint;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import com.example.tfg.otherFiles.adapters.SpinnerAdapter;
import androidx.fragment.app.FragmentTransaction;
import com.example.tfg.navigationMenu.Categorias;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class rutasInicio extends Fragment implements AdapterView.OnItemSelectedListener {

    private GestorDB dbHelper;
    private Spinner spinner;
    private ImageView img1, img2, img3;
    private StorageReference storageRef;
    private String idioma, nombreRuta;
    private TextView text2, text3, text4, text5, text6;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static rutasInicio newInstance(Bundle args) {
        rutasInicio fragment = new rutasInicio();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public rutasInicio() {}

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
    }

    /** El Fragment va a cargar su layout, el cual debemos especificar.
     Aquí se instanciarán los objetos que si son vistas */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_rutas, container, false);
        if(v != null){
            text2 = v.findViewById(R.id.rutas2);
            text3 = v.findViewById(R.id.rutas3);
            text4 = v.findViewById(R.id.rutas4);
            text5 = v.findViewById(R.id.rutas5);
            text6 = v.findViewById(R.id.rutas6);
            img1 = v.findViewById(R.id.rutasimg1);
            img2 = v.findViewById(R.id.rutasimg2);
            img3 = v.findViewById(R.id.rutasimg7);
            spinner = v.findViewById(R.id.spinnerRutas);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        dbHelper = GestorDB.getInstance(requireContext());
        storageRef = FirebaseStorage.getInstance().getReference();

        String [] rutas = getResources().getStringArray(R.array.rutas);
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(new SpinnerAdapter(requireContext(), R.layout.dropdownitemrutas, rutas));
        spinner.setOnItemSelectedListener(this);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        nombreRuta = determinarRuta((String) adapterView.getItemAtPosition(position));

        String[] datos = dbHelper.obtenerDatosRutas(idioma, nombreRuta);

        text2.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text3.setText(datos[1]);
        text4.setText(datos[2]);
        text5.setText(datos[4]);
        text6.setText(datos[3]);
        obtenerImagenFirebase("categorias/rutas/" + nombreRuta + "1.png", img1);
        obtenerImagenFirebase("categorias/rutas/" + nombreRuta + "2.png", img2);
        obtenerImagenFirebase("categorias/rutas/" + nombreRuta + "3.png", img3);
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