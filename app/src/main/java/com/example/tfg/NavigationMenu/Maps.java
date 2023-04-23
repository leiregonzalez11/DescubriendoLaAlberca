package com.example.tfg.NavigationMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tfg.R;
import com.example.tfg.OtherFiles.Adapters.SpinnerAdapter;
import com.example.tfg.Maps.parking.parkingFragment;
import com.example.tfg.Maps.otrosLugares.otrosLugaresInicio;
import com.example.tfg.Maps.parking.senalParkingFragment;
import com.example.tfg.Maps.sitiosdeinteres.ermitasFragment;
import com.example.tfg.Maps.sitiosdeinteres.iglesia.iglesiamapaFragment;
import com.example.tfg.Maps.sitiosdeinteres.info.infoMonuFragment;
import com.example.tfg.Maps.sitiosdeinteres.monumentos1;
import com.example.tfg.Maps.sitiosdeinteres.monumentos2;
import com.example.tfg.Maps.sitiosdeinteres.plaza.plazamapaFragment;

public class Maps extends Fragment implements AdapterView.OnItemSelectedListener {

    private TextView text;
    private String idioma;
    private ImageView img1;
    private Spinner spinner;
    private ImageButton btnminfo;
    private Bundle args;
    Animation slideAnimation;
    private String [] opcionesSpinner;
    private Button btnp1,btnp2,btnp3,btnp4,btnp5, btnparking;
    private ImageButton btnm1,btnm2,btnm3,btnm4,btnm5,btnm6,btnm7,btnm8,btnm9,btnm10,btnm11,btnm12;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static Maps newInstance() {
        return new Maps();
    }

    /** Required empty public constructor */
    public Maps(){}

    /** El Fragment ha sido creado */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(null);
        TextView name = myToolbar.findViewById(R.id.name);
        name.setText(R.string.mapa);
        name.setTextSize(20);

        args = new Bundle();
        Bundle argsMenu = new Bundle(); //Argumentos para el menu de opciones
        argsMenu.putString("iu", "maps");
    }

    /** El Fragment va a cargar su layout, el cual debemos especificar.
     Aquí se instanciarán los objetos que si son vistas */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_maps, container, false);
        if(v != null){
            img1 = v.findViewById(R.id.mapa);
            spinner = v.findViewById(R.id.spinnerMapa);
            text = v.findViewById(R.id.textomaps);
            btnparking = v.findViewById(R.id.btnparking);
            btnp1 = v.findViewById(R.id.btnp1);
            btnp2 = v.findViewById(R.id.btnp2);
            btnp3 = v.findViewById(R.id.btnp3);
            btnp4 = v.findViewById(R.id.btnp4);
            btnp5 = v.findViewById(R.id.btnp5);
            btnm1 = v.findViewById(R.id.btnm1);
            btnm2 = v.findViewById(R.id.btnm2);
            btnm3 = v.findViewById(R.id.btnm3);
            btnm4 = v.findViewById(R.id.btnm4);
            btnm5 = v.findViewById(R.id.btnm5);
            btnm6 = v.findViewById(R.id.btnm6);
            btnm7 = v.findViewById(R.id.btnm7);
            btnm8 = v.findViewById(R.id.btnm8);
            btnm9 = v.findViewById(R.id.btnm9);
            btnm10 = v.findViewById(R.id.btnm10);
            btnm11 = v.findViewById(R.id.btnm11);
            btnm12 = v.findViewById(R.id.btnm12);
            btnminfo = v.findViewById(R.id.moninfo);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        opcionesSpinner = getResources().getStringArray(R.array.opcionesmapa);
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(new SpinnerAdapter(requireContext(), R.layout.dropsownitemsimple2, opcionesSpinner));
        spinner.setOnItemSelectedListener(this);

        idioma = determinarIdioma();
        args.putString("idioma", idioma);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String selecteditem = adapterView.getSelectedItem().toString();

        if (selecteditem.equalsIgnoreCase(opcionesSpinner[0])){ //Opcion seleccionada: Parking

            setBtnParking();
            setVisibilityP(true);
            setVisibilityM(false);

            if (idioma.equalsIgnoreCase("es")) {
                img1.setImageResource(R.drawable.planolaalbercaparkinges);
            }

            else if (idioma.equalsIgnoreCase("en")){
                img1.setImageResource(R.drawable.planolaalbercaparkingen);
            }

            else if (idioma.equalsIgnoreCase("eu")){
                img1.setImageResource(R.drawable.planolaalbercaparkingeu);
            }
        }

        else if (selecteditem.equalsIgnoreCase(opcionesSpinner[2])){ //Opcion seleccionada: Sitios de interés
            img1.setImageResource(R.drawable.planolaalbercabasico);
            setBtnMonumentos();
            setVisibilityP(false);
            setVisibilityM(true);
        }

        else if (selecteditem.equalsIgnoreCase(opcionesSpinner[1])){ //Opcion seleccionada: Servicios
            img1.setImageResource(R.drawable.planolaalbercaservicios);
            setVisibilityP(false);
            setVisibilityM(false);
        }

        else if (selecteditem.equalsIgnoreCase(opcionesSpinner[3])){
            setVisibilityP(false);
            setVisibilityP(false);
            args.putString("idioma", idioma);
            Fragment fragment = otrosLugaresInicio.newInstance(args);
            cargarFragment(fragment);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

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

    /** Método utilizado para obtener el idioma actual de la app */
    public String determinarIdioma() {

        String idioma = null;
        String texto = text.getText().toString();

        switch (texto) {
            case "Pulsa sobre los iconos para más info.":
                idioma = "es";
                break;
            case "Sakatu ikonoen gainean info gehiagorako.":
                idioma = "eu";
                break;
            case "Click on the icons for more info.":
                idioma = "en";
                break;
        }

        return idioma;
    }

    /** Métodos Mapa Parking*/

    public void setBtnParking(){

        btnp1.setOnClickListener(view -> parkingOnClick("eras1"));
        btnp2.setOnClickListener(view -> parkingOnClick("caravanas"));
        btnp3.setOnClickListener(view -> parkingOnClick("eras2"));
        btnp4.setOnClickListener(view -> parkingOnClick("sanantonio"));
        btnp5.setOnClickListener(view -> parkingOnClick("afueras"));
        btnparking.setOnClickListener(view -> parkingOnClick("señal"));

    }

    public void setVisibilityP(boolean activar){

        if (activar){
            btnp1.setVisibility(View.VISIBLE);
            btnp2.setVisibility(View.VISIBLE);
            btnp3.setVisibility(View.VISIBLE);
            btnp4.setVisibility(View.VISIBLE);
            btnp5.setVisibility(View.VISIBLE);
            btnparking.setVisibility(View.VISIBLE);
        }

        else{
            btnp1.setVisibility(View.INVISIBLE);
            btnp2.setVisibility(View.INVISIBLE);
            btnp3.setVisibility(View.INVISIBLE);
            btnp4.setVisibility(View.INVISIBLE);
            btnp5.setVisibility(View.INVISIBLE);
            btnparking.setVisibility(View.INVISIBLE);
        }

    }

    public void parkingOnClick(String parking){

        if (!parking.equalsIgnoreCase("señal")){
            DialogFragment parkingFragment = new parkingFragment();
            args.putString("parking", parking);

            if ( parking.equalsIgnoreCase("eras2")){
                args.putInt("aparcar", 66);
            } else if (parking.equalsIgnoreCase("sanantonio") || parking.equalsIgnoreCase("afueras") || parking.equalsIgnoreCase("caravanas")){
                args.putInt("aparcar", 100);
            } else{
                args.putInt("aparcar", 33);
            }

            parkingFragment.setArguments(args);
            parkingFragment.setCancelable(false);
            parkingFragment.show(getChildFragmentManager(),"parking_fragment");

        } else{
            DialogFragment parkingFragment = new senalParkingFragment();
            args.putString("idioma", idioma);
            parkingFragment.setArguments(args);
            parkingFragment.setCancelable(false);
            parkingFragment.show(getChildFragmentManager(),"parkingSeñal_fragment");
        }
    }

    /** Métodos Mapa Monumentos*/

    public void setBtnMonumentos(){

        btnm1.setOnClickListener(view -> monumentosOnClick("iglesia", btnm1));
        btnm2.setOnClickListener(view -> monumentosOnClick("plazamayor", btnm2));
        btnm3.setOnClickListener(view -> monumentosOnClick("barrioelcastillo", btnm3));
        btnm4.setOnClickListener(view -> monumentosOnClick("miradorsantafe", btnm4));
        btnm5.setOnClickListener(view -> monumentosOnClick("antiguohospicio", btnm5));
        btnm6.setOnClickListener(view -> monumentosOnClick("bustomauricelegendre", btnm6));
        btnm7.setOnClickListener(view -> monumentosOnClick("lapuente", btnm7));
        btnm8.setOnClickListener(view -> monumentosOnClick("lasespeñitas", btnm8));
        btnm9.setOnClickListener(view -> monumentosOnClick("ermitadesanblas", btnm9));
        btnm10.setOnClickListener(view -> monumentosOnClick("plazasanantonio", btnm10));
        btnm11.setOnClickListener(view -> monumentosOnClick("ermitadelhumilladero", btnm11));
        btnm12.setOnClickListener(view -> monumentosOnClick("ermitasanantonio", btnm12));
        btnminfo.setOnClickListener(view -> monumentosOnClick("infomonumentos", btnminfo));

    }

    public void setVisibilityM(boolean activar){

        if (activar){
            btnm1.setVisibility(View.VISIBLE);
            btnm2.setVisibility(View.VISIBLE);
            btnm3.setVisibility(View.VISIBLE);
            btnm4.setVisibility(View.VISIBLE);
            btnm5.setVisibility(View.VISIBLE);
            btnm6.setVisibility(View.VISIBLE);
            btnm7.setVisibility(View.VISIBLE);
            btnm8.setVisibility(View.VISIBLE);
            btnm9.setVisibility(View.VISIBLE);
            btnm10.setVisibility(View.VISIBLE);
            btnm11.setVisibility(View.VISIBLE);
            btnm12.setVisibility(View.VISIBLE);
            btnminfo.setVisibility(View.VISIBLE);
        }

        else{
            btnm1.setVisibility(View.INVISIBLE);
            btnm2.setVisibility(View.INVISIBLE);
            btnm3.setVisibility(View.INVISIBLE);
            btnm4.setVisibility(View.INVISIBLE);
            btnm5.setVisibility(View.INVISIBLE);
            btnm6.setVisibility(View.INVISIBLE);
            btnm7.setVisibility(View.INVISIBLE);
            btnm8.setVisibility(View.INVISIBLE);
            btnm9.setVisibility(View.INVISIBLE);
            btnm10.setVisibility(View.INVISIBLE);
            btnm11.setVisibility(View.INVISIBLE);
            btnm12.setVisibility(View.INVISIBLE);
            btnminfo.setVisibility(View.INVISIBLE);
        }

    }

    public void monumentosOnClick(String monumento, View btn){

        args.putString("idioma", idioma);
        DialogFragment fragment;
        if (monumento.contains("ermita")){
            fragment = new ermitasFragment();
            args.putString("ermita", monumento);
            zoomIn(fragment, btn);
        } else if (monumento.equalsIgnoreCase("iglesia")){
            fragment = new iglesiamapaFragment();
            zoomIn(fragment, btn);
        } else if (monumento.equalsIgnoreCase("plazamayor")){
            fragment = new plazamapaFragment();
            zoomIn(fragment, btn);
        } else if (monumento.equalsIgnoreCase("antiguohospicio")){
            fragment = new monumentos2();
            args.putString("monumento", monumento);
            args.putString("titulo", "Antiguo hospicio de los Carmelitas");
            zoomIn(fragment, btn);
        } else if (monumento.equalsIgnoreCase("plazasanantonio")){
            fragment = new monumentos1();
            args.putString("monumento", monumento);
            args.putString("titulo", "Plaza San Antonio");
            zoomIn(fragment, btn);
        } else{
            fragment = new infoMonuFragment();
            fragment.setCancelable(false);
            fragment.show(getChildFragmentManager(),"plaza_fragment");
        }

    }

    public void zoomIn (DialogFragment fragment, View view){
        slideAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_in2);
        view.startAnimation(slideAnimation);

        new Handler().postDelayed(() -> {
            fragment.setArguments(args);
            fragment.setCancelable(false);
            fragment.show(getChildFragmentManager(),"fragment");
        },900);
    }

}