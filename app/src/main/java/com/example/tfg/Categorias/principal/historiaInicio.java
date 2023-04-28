package com.example.tfg.Categorias.principal;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import android.annotation.SuppressLint;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.tfg.NavigationMenu.Categorias;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class historiaInicio extends Fragment {

    private ImageButton ant, sig;
    private ImageView img1, img2, img3, img4, img5;
    private String idioma;
    private StorageReference storageRef;
    private TextView text1, text2, text3, text4, text5, text6, pruebatexto;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static historiaInicio newInstance(Bundle args) {
        historiaInicio fragment = new historiaInicio();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public historiaInicio() {}

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

        Bundle args = new Bundle();

        if (getArguments() != null) {
            idioma = getArguments().getString("idioma");
        }

        args.putString("idioma", idioma);

    }

    /** El Fragment va a cargar su layout, el cual debemos especificar.
     Aquí se instanciarán los objetos que si son vistas */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_historia_inicio, container, false);
        if(v != null){
            pruebatexto = v.findViewById(R.id.pruebatextoh);
            text1 = v.findViewById(R.id.historia1);
            text2 = v.findViewById(R.id.historia2);
            text3 = v.findViewById(R.id.historia3);
            text4 = v.findViewById(R.id.historia4);
            text5 = v.findViewById(R.id.historia5);
            text6 = v.findViewById(R.id.historia6);
            ant = v.findViewById(R.id.historiabtn);
            sig = v.findViewById(R.id.historiabtn2);
            img1 = v.findViewById(R.id.histimg1);
            img2 = v.findViewById(R.id.histimg2);
            img3 = v.findViewById(R.id.histimg3);
            img4 = v.findViewById(R.id.histimg4);
            img5 = v.findViewById(R.id.histimg5);

        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        GestorDB dbHelper = new GestorDB(getContext());
        storageRef = FirebaseStorage.getInstance().getReference();

        if (pruebatexto.getText().toString().equalsIgnoreCase("1")){
            ant.setVisibility(View.GONE);
            sig.setVisibility(View.VISIBLE);
            String [] datos = dbHelper.obtenerInfoHist("cat1", idioma,6);
            text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text4.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text5.setText(datos[4] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text6.setText(datos[5] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            obtenerImagenFirebase("categorias/historia/historia1.png", img1);
            obtenerImagenFirebase("categorias/historia/historia2.png", img2);
            obtenerImagenFirebase("categorias/historia/historia5.png", img3);
            obtenerImagenFirebase("categorias/historia/historia3.png", img4);
            obtenerImagenFirebase("categorias/historia/historia4.png", img5);
        }


        ant.setOnClickListener(view1 -> {
            pruebatexto.clearFocus();
            if (pruebatexto.getText().toString().equalsIgnoreCase("2")){
                ant.setVisibility(View.GONE);
                sig.setVisibility(View.VISIBLE);
                pruebatexto.setText("1");

                String [] datos = dbHelper.obtenerInfoHist("cat1", idioma, 6);

                text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text4.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text5.setText(datos[4] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text6.setText(datos[5] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                pruebatexto.requestFocus();

                obtenerImagenFirebase("categorias/historia/historia1.png", img1);
                obtenerImagenFirebase("categorias/historia/historia2.png", img2);
                obtenerImagenFirebase("categorias/historia/historia5.png", img3);
                obtenerImagenFirebase("categorias/historia/historia3.png", img4);
                obtenerImagenFirebase("categorias/historia/historia4.png", img5);

            } else if (pruebatexto.getText().toString().equalsIgnoreCase("3")){
                pruebatexto.clearFocus();
                ant.setVisibility(View.VISIBLE);
                sig.setVisibility(View.VISIBLE);
                pruebatexto.setText("2");

                String [] datos = dbHelper.obtenerInfoHist("cat2", idioma,6);

                text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text4.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text5.setText(datos[4] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text6.setText(datos[5] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                pruebatexto.requestFocus();

                obtenerImagenFirebase("categorias/historia/historia6.png", img1);
                obtenerImagenFirebase("categorias/historia/historia7.png", img2);
                obtenerImagenFirebase("categorias/historia/historia8.png", img3);
                obtenerImagenFirebase("categorias/historia/historia9.png", img4);
                obtenerImagenFirebase("categorias/historia/historia10.png", img5);

            }
        });

        sig.setOnClickListener(view1 -> {
            pruebatexto.clearFocus();
            if (pruebatexto.getText().toString().equalsIgnoreCase("1")){
                ant.setVisibility(View.VISIBLE);
                sig.setVisibility(View.VISIBLE);
                pruebatexto.setText("2");

                String [] datos = dbHelper.obtenerInfoHist("cat2", idioma, 6);

                text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text4.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text5.setText(datos[4] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text6.setText(datos[5] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                pruebatexto.requestFocus();

                obtenerImagenFirebase("categorias/historia/historia6.png", img1);
                obtenerImagenFirebase("categorias/historia/historia7.png", img2);
                obtenerImagenFirebase("categorias/historia/historia8.png", img3);
                obtenerImagenFirebase("categorias/historia/historia9.png", img4);
                obtenerImagenFirebase("categorias/historia/historia10.png", img5);

            } else if (pruebatexto.getText().toString().equalsIgnoreCase("2")){
                pruebatexto.clearFocus();
                ant.setVisibility(View.VISIBLE);
                sig.setVisibility(View.GONE);
                pruebatexto.setText("3");

                String [] datos = dbHelper.obtenerInfoHist("cat3", idioma,5);

                text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text4.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text5.setText(datos[4] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text6.setText("");
                pruebatexto.requestFocus();

                obtenerImagenFirebase("categorias/historia/historia11.png", img1);
                obtenerImagenFirebase("categorias/historia/historia12.png", img2);
                obtenerImagenFirebase("categorias/historia/historia13.png", img3);
                obtenerImagenFirebase("categorias/historia/historia14.png", img4);
                obtenerImagenFirebase("categorias/historia/historia15.png", img5);

            }
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