package com.example.tfg.categoriasFragments.secundarias.artesania;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import com.example.tfg.R;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import android.widget.AdapterView;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import android.annotation.SuppressLint;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import com.example.tfg.adapters.SpinnerAdapter;
import androidx.fragment.app.FragmentTransaction;
import com.example.tfg.navigationmenu.Categorias;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class trajesFemeninos extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private Bundle args;
    private GestorDB dbHelper;
    private ImageView img1, img2;
    private StorageReference storageRef;
    private TextView text1, text2, text3;
    private String idioma, categoria, nombreTraje;

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

        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        myToolbar.setNavigationOnClickListener(v -> {
            myToolbar.setNavigationIcon(null);
            Fragment fragment = new Categorias();
            cargarFragment(fragment);
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

        //BOTON SIGUIENTE y ATRAS

        Button atrasBtn2 = requireView().findViewById(R.id.arteAtras3);
        atrasBtn2.setOnClickListener(this);

        Button siguienteBtn = requireView().findViewById(R.id.artesiguiente3);
        siguienteBtn.setOnClickListener(this);


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
        cargarFragment(fragment);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        nombreTraje = determinarTraje((String) adapterView.getItemAtPosition(position));

        String [] datos = dbHelper.obtenerDatosTrajes(idioma, "trajes", categoria, 3, nombreTraje);

        if (nombreTraje.equalsIgnoreCase("vistas") & idioma.equalsIgnoreCase("en")){
            text1.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text2.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text3.setText(datos[2]);
        } else if (nombreTraje.equalsIgnoreCase("manteo") & idioma.equalsIgnoreCase("en")){
            text1.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text2.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text3.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        } else{
            text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        }

        storageRef = FirebaseStorage.getInstance().getReference();

        obtenerImagenFirebase("artesania/" + nombreTraje + "1.jpg", img1);
        obtenerImagenFirebase("artesania/" + nombreTraje + "2.jpg", img2);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }

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