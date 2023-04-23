package com.example.tfg.Categorias.secundarias.artesania;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import com.example.tfg.R;

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
import com.example.tfg.OtherFiles.Adapters.SpinnerAdapter;
import androidx.fragment.app.FragmentTransaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.example.tfg.Categorias.principal.artesaniaInicio;


public class trajesFemeninos extends Fragment implements AdapterView.OnItemSelectedListener{

    private Bundle args;
    private GestorDB dbHelper;
    private ImageView img1, img2;
    private StorageReference storageRef;
    private TextView text1, text2, text3;
    private String idioma, categoria, nombreTraje;
    private LinearLayout layout;
    private Spinner spinner;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static trajesFemeninos newInstance(Bundle args) {
        trajesFemeninos fragment = new trajesFemeninos();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public trajesFemeninos() {}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        TextView name = myToolbar.findViewById(R.id.name);
        name.setText(R.string.artesaniamayus);
        name.setTextSize(20);
        myToolbar.setNavigationOnClickListener(v -> {
            myToolbar.setNavigationIcon(null);
            Fragment fragment = artesaniaInicio.newInstance(args);
            cargarFragment(fragment);
        });

        args = new Bundle();

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
        View v = inflater.inflate(R.layout.fragment_trajes_femeninos, container, false);
        if (v != null){
            layout = v.findViewById(R.id.layoutFotoArte1);
            text1 = v.findViewById(R.id.arte31);
            text2 = v.findViewById(R.id.arte32);
            text3 = v.findViewById(R.id.arte33);
            img1 = v.findViewById(R.id.arte31img);
            img2 = v.findViewById(R.id.arte32img);
            spinner = v.findViewById(R.id.spinner);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        dbHelper = new GestorDB(getContext());

        //Spinner
        String [] trajes = getResources().getStringArray(R.array.trajes_serranos);
        spinner.setAdapter(new SpinnerAdapter(getContext(), R.layout.dropdownitemartesania, trajes));
        spinner.setOnItemSelectedListener(this);

    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        nombreTraje = determinarTraje((String) adapterView.getItemAtPosition(position));

        String [] datos = dbHelper.obtenerDatosTrajes(idioma, "trajes",3, nombreTraje);

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

        obtenerImagenFirebase("categorias/artesania/" + nombreTraje + "1.png", img1);
        obtenerImagenFirebase("categorias/artesania/" + nombreTraje + "2.png", img2);

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

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(layout.getLayoutParams());

        if(idtraje.toLowerCase().contains("sayas")){
            nombreTraje = "sayas";
            lp.topMargin=-130;
            lp.bottomMargin=-20;

        } else if (idtraje.toLowerCase().contains("ventioseno")){
            nombreTraje = "ventioseno";
            lp.bottomMargin=80;
            lp.topMargin=-25;

        } else if (idtraje.toLowerCase().contains("vistas")){
            nombreTraje = "vistas";
            lp.bottomMargin=80;
            lp.topMargin=-25;

        } else if (idtraje.toLowerCase().contains("zagalejo")){
            nombreTraje = "zagalejo";
            lp.bottomMargin=80;
            lp.topMargin=-25;

        } else if (idtraje.toLowerCase().contains("manteo")){
            nombreTraje = "manteo";
            lp.bottomMargin=80;
            lp.topMargin=-25;
        }

        layout.setLayoutParams(lp);
        System.out.println("TOP: " + lp.topMargin);
        System.out.println("BOTTOM: " + lp.bottomMargin);

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