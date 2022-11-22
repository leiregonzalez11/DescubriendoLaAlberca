package com.example.tfg.categoriasFragments.secundarias.gastronomia;

import android.annotation.SuppressLint;
import android.os.Bundle;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.adapters.SpinnerAdapter;
import com.example.tfg.categoriasFragments.principal.gastronomiaInicio;
import com.example.tfg.navigationmenu.Categorias;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class recetasTipicas extends Fragment {

    Bundle args;
    GestorDB dbHelper;
    String [] recetas;
    LinearLayout ingLayout;
    StorageReference storageRef;
    TextView tituloreceta, descrReceta, pasos2;
    ImageView img7, img8, img9, img10, img11, img12;
    String categoria, idioma, nombreReceta, tipoRecetas;
    TextView ing1, ing2, ing3, ing4, ing5, ing6, ing7, ing8, ing9, ing10, ing11, ing12;

    public recetasTipicas() {
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
        return inflater.inflate(R.layout.fragment_recetas_tipicas, container, false);
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

        dbHelper = new GestorDB(requireContext());
        storageRef = FirebaseStorage.getInstance().getReference();

        Spinner spinner1 = requireView().findViewById(R.id.spinnerTipo);
        Spinner spinner2 = requireView().findViewById(R.id.spinnerRecetas);


        String [] tipo = getResources().getStringArray(R.array.tipo);
        spinner1.setAdapter(new SpinnerAdapter(requireContext(), R.layout.dropsownitemsimple, tipo));

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
               tipoRecetas = (String) adapterView.getItemAtPosition(position);
               if (tipoRecetas.equalsIgnoreCase(tipo[0])){
                   recetas = getResources().getStringArray(R.array.recetasDulces);
               } else {
                   recetas = getResources().getStringArray(R.array.recetasSaladas);
               }

               spinner2.setAdapter(new SpinnerAdapter(requireContext(), R.layout.dropdownitemrecetas, recetas));

           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });

        getTextAndImages();

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                //Toast.makeText(getContext(), "Has pulsado " + (String) adapterView.getItemAtPosition(position), Toast.LENGTH_LONG).show();

                nombreReceta = (String) adapterView.getItemAtPosition(position);
                if (!nombreReceta.equalsIgnoreCase("turuletes") && !nombreReceta.equalsIgnoreCase("floretas")){
                    String nombreRecetaBBDD = nombreReceta.toLowerCase().replaceAll(" ", "");
                    Receta receta = dbHelper.obtenerReceta(idioma, nombreRecetaBBDD, categoria);
                    receta.setNombreReceta(nombreReceta);
                    setTextAndImages(receta);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button atrasBtn = requireView().findViewById(R.id.recetasAtras);
        atrasBtn.setOnClickListener(v -> {
            Fragment fragment = new gastronomiaInicio();
            fragment.setArguments(args);
            cargarFragment(fragment);
        });
    }

    private void getTextAndImages(){

        tituloreceta = requireView().findViewById(R.id.tituloreceta);
        descrReceta = requireView().findViewById(R.id.recetasTextDescr);

        //Ingredientes
        ing1 = requireView().findViewById(R.id.txt1);
        ing2 = requireView().findViewById(R.id.txt2);
        ing3 = requireView().findViewById(R.id.txt3);
        ing4 = requireView().findViewById(R.id.txt4);
        ing5 = requireView().findViewById(R.id.txt5);
        ing6 = requireView().findViewById(R.id.txt6);
        ing7 = requireView().findViewById(R.id.txt7);
        ing8 = requireView().findViewById(R.id.txt8);
        ing9 = requireView().findViewById(R.id.txt9);
        ing10 = requireView().findViewById(R.id.txt10);
        ing11 = requireView().findViewById(R.id.txt11);
        ing12 = requireView().findViewById(R.id.txt12);

        //Iconos ingredientes
        img7 = requireView().findViewById(R.id.img7);
        img8 = requireView().findViewById(R.id.img8);
        img9 = requireView().findViewById(R.id.img9);
        img10 = requireView().findViewById(R.id.img10);
        img11 = requireView().findViewById(R.id.img11);
        img12 = requireView().findViewById(R.id.img12);

        //Pasos
        pasos2 = requireView().findViewById(R.id.recetasTextPasos2);

        //Layout
        ingLayout = requireView().findViewById(R.id.ingredientes);

    }

    @SuppressLint("SetTextI18n")
    private void setTextAndImages(Receta receta){

        tituloreceta.setText(receta.getNombreReceta());
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