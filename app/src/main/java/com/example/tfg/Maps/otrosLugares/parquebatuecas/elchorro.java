package com.example.tfg.Maps.otrosLugares.parquebatuecas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.example.tfg.Maps.otrosLugares.batuecas;
import com.example.tfg.NavigationMenu.Categorias;
import com.example.tfg.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link elchorro#newInstance} factory method to
 * create an instance of this fragment.
 */
public class elchorro extends Fragment {

    private ImageView img4, img2, img3;
    private ImageButton img1;
    private Bundle args = new Bundle();
    private StorageReference storageRef;
    private String idioma;
    private TextView text1, text2, text3, text4, text5, text6, text7, tp1, tp2, tp3, tp4;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static elchorro newInstance(Bundle args) {
        elchorro fragment = new elchorro();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public elchorro() {}

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
        View v =  inflater.inflate(R.layout.fragment_elchorro, container, false);
        if(v != null){
            text1 = v.findViewById(R.id.chorro1);
            text2 = v.findViewById(R.id.chorro2);
            text3 = v.findViewById(R.id.chorro3);
            text4 = v.findViewById(R.id.chorro4);
            text5 = v.findViewById(R.id.chorro5);
            text6 = v.findViewById(R.id.chorro6);
            text7 = v.findViewById(R.id.chorro7);
            tp1 = v.findViewById(R.id.chorrop1);
            tp2 = v.findViewById(R.id.chorrop2);
            tp3 = v.findViewById(R.id.chorrop3);
            tp4 = v.findViewById(R.id.chorrop4);
            img1 = v.findViewById(R.id.chorroimg1);
            img2 = v.findViewById(R.id.chorroimg2);
            img3 = v.findViewById(R.id.chorroimg3);
            img4 = v.findViewById(R.id.chorroimg4);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        GestorDB dbHelper = new GestorDB(requireContext());
        storageRef = FirebaseStorage.getInstance().getReference();

        String[] datosRuta = dbHelper.obtenerDatosRutas(idioma, "chorro");

        text1.setText(datosRuta[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        tp1.setText(datosRuta[1]);
        tp2.setText(datosRuta[2]);
        tp3.setText(datosRuta[4]);
        tp4.setText(datosRuta[3]);

        obtenerImagenFirebase("mapas/otros/batuecas/chorro1.png", img1);
        img1.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://es.wikiloc.com/rutas-senderismo/senda-del-chorro-de-las-batuecas-en-sierra-de-francia-salamanca-29632544"); // Creamos una uri
            Intent dial = new Intent(Intent.ACTION_VIEW, uri); // Creamos una llamada al Intent de Web
            startActivity(dial); // Ejecutamos el Intent
        });

        String[] datos = dbHelper.obtenerInfoLugares(idioma, "chorro","batuecas", 6);

        text2.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text3.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text4.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text5.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text6.setText(datos[4] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text7.setText(datos[5] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        //Imagenes

        storageRef = FirebaseStorage.getInstance().getReference();

        obtenerImagenFirebase("mapas/otros/batuecas/chorro2.png", img2);
        obtenerImagenFirebase("mapas/otros/batuecas/chorro3.png", img3);
        obtenerImagenFirebase("mapas/otros/batuecas/chorro4.png", img4);

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