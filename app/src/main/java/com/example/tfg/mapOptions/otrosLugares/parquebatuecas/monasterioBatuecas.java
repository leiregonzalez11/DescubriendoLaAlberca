package com.example.tfg.mapOptions.otrosLugares.parquebatuecas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.tfg.mapOptions.otrosLugares.batuecas;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class monasterioBatuecas extends Fragment implements View.OnClickListener {

    private ImageView img1, img2;
    private final Bundle args = new Bundle();
    private StorageReference storageRef;
    private String idioma;
    private Button volver, ermitas, historia, mirador;
    private TextView text1, text2;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static monasterioBatuecas newInstance(Bundle args) {
        monasterioBatuecas fragment = new monasterioBatuecas();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public monasterioBatuecas() {}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            idioma = getArguments().getString("idioma");
        }

        args.putString("idioma", idioma);

        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        TextView name = myToolbar.findViewById(R.id.name);
        name.setText(R.string.batuecas);
        name.setTextSize(20);
        myToolbar.setNavigationOnClickListener(view12 -> {
            myToolbar.setNavigationIcon(null);
            Fragment fragment = batuecas.newInstance(args);
            cargarFragment(fragment);
        });

    }

    /** El Fragment va a cargar su layout, el cual debemos especificar.
     Aquí se instanciarán los objetos que si son vistas */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_monasteriobatuecas, container, false);
        if(v != null){
            text1 = v.findViewById(R.id.monasterioinfotext1);
            text2 = v.findViewById(R.id.monasterioinfotext2);
            img1 = v.findViewById(R.id.monasterioimg1);
            img2 = v.findViewById(R.id.monasterioimg2);
            volver = v.findViewById(R.id.buttonVolverMonasterio);
            ermitas = v.findViewById(R.id.btnermitasmon);
            historia = v.findViewById(R.id.btnhistoriamon);
            mirador = v.findViewById(R.id.btmiradormon);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        String[] datos;
        try (GestorDB dbHelper = GestorDB.getInstance(requireContext())) {
            storageRef = FirebaseStorage.getInstance().getReference();

            datos = dbHelper.obtenerInfoLugares(idioma, "monasteriointro", "batuecas", 2);
        }

        text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        //Imagenes

        storageRef = FirebaseStorage.getInstance().getReference();

        obtenerImagenFirebase("mapas/otros/batuecas/monasteriointro1.png", img1);
        obtenerImagenFirebase("mapas/otros/batuecas/monasteriointro2.png", img2);

        setListeners();

    }

    private void setListeners() {

        volver.setOnClickListener(this);
        ermitas.setOnClickListener(this);
        mirador.setOnClickListener(this);
        historia.setOnClickListener(this);

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

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Button btn = (Button) view;

        switch (btn.getId()) {
            case R.id.btnermitasmon:
                DialogFragment f1 = new ermitaspinturasmonasterio();
                args.putString("button", "ermitas");
                f1.setArguments(args);
                f1.setCancelable(false);
                f1.show(getChildFragmentManager(),"batuecasermitasypinturas");
                break;
            case R.id.btmiradormon:
                DialogFragment f2 = new casaParqueymirador();
                args.putString("button", "mirador");
                f2.setArguments(args);
                f2.setCancelable(false);
                f2.show(getChildFragmentManager(),"batuecasermitasypinturas");
                break;
            case R.id.btnhistoriamon:
                DialogFragment f3 = new ermitaspinturasmonasterio();
                args.putString("button", "monasterio");
                f3.setArguments(args);
                f3.setCancelable(false);
                f3.show(getChildFragmentManager(),"batuecasermitasypinturas");
                break;
            case R.id.buttonVolverMonasterio:
                Fragment fragment = batuecas.newInstance(args);
                cargarFragment(fragment);
                break;
        }
    }
}