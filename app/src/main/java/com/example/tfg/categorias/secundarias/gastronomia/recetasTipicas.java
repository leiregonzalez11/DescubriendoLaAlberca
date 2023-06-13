package com.example.tfg.categorias.secundarias.gastronomia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tfg.R;
import com.example.tfg.otherFiles.adapters.SpinnerAdapter;
import com.example.tfg.categorias.principal.gastronomiaInicio;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class recetasTipicas extends Fragment {

    private Bundle args;
    private Button atrasBtn;
    private String[] recetasS;
    private ListaRecetas recetas;
    private LinearLayout ingLayout;
    private StorageReference storageRef;
    private Spinner spinner1, spinner2;
    private TextView tituloreceta, descrReceta, pasos2, introrecetas;
    private ImageView img7, img8, img9, img10, img11, img12, foto;
    private String idioma, nombreReceta, tipoRecetas, nombreRecetaBBDD;
    private TextView ing1, ing2, ing3, ing4, ing5, ing6, ing7, ing8, ing9, ing10, ing11, ing12;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static recetasTipicas newInstance(Bundle args) {
        recetasTipicas fragment = new recetasTipicas();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public recetasTipicas() {}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        args = new Bundle();

        if (getArguments() != null) {
            idioma = getArguments().getString("idioma");
        }

        args.putString("idioma", idioma);

        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        TextView name = myToolbar.findViewById(R.id.name);
        name.setText(R.string.gastronomiamayus);
        name.setTextSize(20);
        myToolbar.setNavigationOnClickListener(v -> {
            myToolbar.setNavigationIcon(null);
            Fragment fragment = gastronomiaInicio.newInstance(args);
            cargarFragment(fragment);
        });

    }

    /** El Fragment va a cargar su layout, el cual debemos especificar.
     Aquí se instanciarán los objetos que si son vistas */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recetas_tipicas, container, false);
        if (v != null){
            spinner1 = v.findViewById(R.id.spinnerTipo);
            spinner2 = v.findViewById(R.id.spinnerRecetas);
            getTextAndImages(v);
            atrasBtn = v.findViewById(R.id.recetasAtras);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recetas = new ListaRecetas(requireContext(), idioma);
        recetas.imprimirRecetas();

        storageRef = FirebaseStorage.getInstance().getReference();

        String [] tipo = getResources().getStringArray(R.array.tipo);
        spinner1.setAdapter(new SpinnerAdapter(requireContext(), R.layout.dropsownitemsimple, tipo));

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
               tipoRecetas = (String) adapterView.getItemAtPosition(position);
               if (tipoRecetas.equalsIgnoreCase(tipo[0])){
                   recetasS = getResources().getStringArray(R.array.recetasDulces);
               } else {
                   recetasS = getResources().getStringArray(R.array.recetasSaladas);
               }

               spinner2.setAdapter(new SpinnerAdapter(requireContext(), R.layout.dropdownitemrecetas, recetasS));

           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                nombreReceta = (String) adapterView.getItemAtPosition(position);
                nombreRecetaBBDD = nombreReceta.toLowerCase().replaceAll(" ", "");
                setTextAndImages();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        atrasBtn.setOnClickListener(v -> {
            introrecetas.clearFocus();
            introrecetas.requestFocus();
        });
    }

    private void getTextAndImages(View v){

        introrecetas = v.findViewById(R.id.recetasTextIntro);
        tituloreceta = v.findViewById(R.id.tituloreceta);
        descrReceta = v.findViewById(R.id.recetasTextDescr);

        //Ingredientes
        ing1 = v.findViewById(R.id.txt1);
        ing2 = v.findViewById(R.id.txt2);
        ing3 = v.findViewById(R.id.txt3);
        ing4 = v.findViewById(R.id.txt4);
        ing5 = v.findViewById(R.id.txt5);
        ing6 = v.findViewById(R.id.txt6);
        ing7 = v.findViewById(R.id.txt7);
        ing8 = v.findViewById(R.id.txt8);
        ing9 = v.findViewById(R.id.txt9);
        ing10 = v.findViewById(R.id.txt10);
        ing11 = v.findViewById(R.id.txt11);
        ing12 = v.findViewById(R.id.txt12);

        //Iconos ingredientes
        img7 = v.findViewById(R.id.img7);
        img8 = v.findViewById(R.id.img8);
        img9 = v.findViewById(R.id.img9);
        img10 = v.findViewById(R.id.img10);
        img11 = v.findViewById(R.id.img11);
        img12 = v.findViewById(R.id.img12);

        //Imagen receta
        foto = v.findViewById(R.id.imgReceta2);

        //Pasos
        pasos2 = v.findViewById(R.id.recetasTextPasos2);

        //Layout
        ingLayout = v.findViewById(R.id.ingredientes);

    }

    @SuppressLint("SetTextI18n")
    private void setTextAndImages(){

        Receta receta = recetas.buscarReceta(nombreRecetaBBDD);

        tituloreceta.setText(nombreReceta);
        descrReceta.setText(receta.getDescrReceta() + "\n");
        pasos2.setText(receta.getPasos());

        ing1.setText(receta.getIngredientes()[0]);
        ing2.setText(receta.getIngredientes()[1]);
        ing3.setText(receta.getIngredientes()[2]);
        ing4.setText(receta.getIngredientes()[3]);
        ing5.setText(receta.getIngredientes()[4]);
        ing6.setText(receta.getIngredientes()[5]);

        if (receta.getIngredientes().length == 6){

            //Ingredientes
            ing7.setText("");
            ing8.setText("");
            ing9.setText("");
            ing10.setText("");
            ing11.setText("");
            ing12.setText("");

            //Iconos
            img7.setImageResource(0);
            img8.setImageResource(0);
            img9.setImageResource(0);
            img10.setImageResource(0);
            img11.setImageResource(0);
            img12.setImageResource(0);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ingLayout.getLayoutParams());
            lp.bottomMargin = -40;
            ingLayout.setLayoutParams(lp);

        } else if (receta.getIngredientes().length == 7){

            //Ingredientes
            ing7.setText(receta.getIngredientes()[6]);
            ing8.setText("");
            ing9.setText("");
            ing10.setText("");
            ing11.setText("");
            ing12.setText("");

            //Iconos
            img7.setImageResource(R.drawable.arrow_right_foreground);
            img8.setImageResource(0);
            img9.setImageResource(0);
            img10.setImageResource(0);
            img11.setImageResource(0);
            img12.setImageResource(0);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ingLayout.getLayoutParams());
            lp.bottomMargin = -30;
            ingLayout.setLayoutParams(lp);

        } else if (receta.getIngredientes().length == 8){

            //Ingredientes
            ing7.setText(receta.getIngredientes()[6]);
            ing8.setText(receta.getIngredientes()[7]);
            ing9.setText("");
            ing10.setText("");
            ing11.setText("");
            ing12.setText("");

            //Iconos
            img7.setImageResource(R.drawable.arrow_right_foreground);
            img8.setImageResource(R.drawable.arrow_right_foreground);
            img9.setImageResource(0);
            img10.setImageResource(0);
            img11.setImageResource(0);
            img12.setImageResource(0);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ingLayout.getLayoutParams());
            lp.bottomMargin = -30;
            ingLayout.setLayoutParams(lp);

        } else if (receta.getIngredientes().length == 9){

            //Ingredientes
            ing7.setText(receta.getIngredientes()[6]);
            ing8.setText(receta.getIngredientes()[7]);
            ing9.setText(receta.getIngredientes()[8]);
            ing10.setText("");
            ing11.setText("");
            ing12.setText("");

            //Iconos
            img7.setImageResource(R.drawable.arrow_right_foreground);
            img8.setImageResource(R.drawable.arrow_right_foreground);
            img9.setImageResource(R.drawable.arrow_right_foreground);
            img10.setImageResource(0);
            img11.setImageResource(0);
            img12.setImageResource(0);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ingLayout.getLayoutParams());
            lp.bottomMargin = -20;
            ingLayout.setLayoutParams(lp);

        } else if (receta.getIngredientes().length == 10){

            //Ingredientes
            ing7.setText(receta.getIngredientes()[6]);
            ing8.setText(receta.getIngredientes()[7]);
            ing9.setText(receta.getIngredientes()[8]);
            ing10.setText(receta.getIngredientes()[9]);
            ing11.setText("");
            ing12.setText("");

            //Iconos
            img7.setImageResource(R.drawable.arrow_right_foreground);
            img8.setImageResource(R.drawable.arrow_right_foreground);
            img9.setImageResource(R.drawable.arrow_right_foreground);
            img10.setImageResource(R.drawable.arrow_right_foreground);
            img11.setImageResource(0);
            img12.setImageResource(0);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ingLayout.getLayoutParams());
            lp.bottomMargin = -20;
            ingLayout.setLayoutParams(lp);

        } else if (receta.getIngredientes().length == 11){

            //Ingredientes
            ing7.setText(receta.getIngredientes()[6]);
            ing8.setText(receta.getIngredientes()[7]);
            ing9.setText(receta.getIngredientes()[8]);
            ing10.setText(receta.getIngredientes()[9]);
            ing11.setText(receta.getIngredientes()[10]);

            ing12.setText("");
            //Iconos
            img7.setImageResource(R.drawable.arrow_right_foreground);
            img8.setImageResource(R.drawable.arrow_right_foreground);
            img9.setImageResource(R.drawable.arrow_right_foreground);
            img10.setImageResource(R.drawable.arrow_right_foreground);
            img11.setImageResource(R.drawable.arrow_right_foreground);
            img12.setImageResource(0);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ingLayout.getLayoutParams());
            lp.bottomMargin = 50;
            ingLayout.setLayoutParams(lp);

        } else if (receta.getIngredientes().length == 12){
            //Ingredientes
            ing7.setText(receta.getIngredientes()[6]);
            ing8.setText(receta.getIngredientes()[7]);
            ing9.setText(receta.getIngredientes()[8]);
            ing10.setText(receta.getIngredientes()[9]);
            ing11.setText(receta.getIngredientes()[10]);
            ing12.setText(receta.getIngredientes()[11]);
            //Iconos
            img7.setImageResource(R.drawable.arrow_right_foreground);
            img8.setImageResource(R.drawable.arrow_right_foreground);
            img9.setImageResource(R.drawable.arrow_right_foreground);
            img10.setImageResource(R.drawable.arrow_right_foreground);
            img11.setImageResource(R.drawable.arrow_right_foreground);
            img12.setImageResource(R.drawable.arrow_right_foreground);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ingLayout.getLayoutParams());
            lp.bottomMargin = 30;
            ingLayout.setLayoutParams(lp);
        }

        storageRef = FirebaseStorage.getInstance().getReference();
        obtenerImagenFirebase("categorias/gastronomia/" + nombreRecetaBBDD + ".png", foto);

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