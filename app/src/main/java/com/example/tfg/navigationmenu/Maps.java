package com.example.tfg.navigationmenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfg.R;
import com.example.tfg.adapters.SpinnerAdapter;
import com.example.tfg.ajustesFragments.FormularioDeContacto;
import com.example.tfg.ajustesFragments.Idiomas;
import com.example.tfg.dialogFragments.ExitFragment;
import com.example.tfg.dialogFragments.parkingFragment;
import com.example.tfg.mapsFragments.otrosLugares.otrosLugaresInicio;
import com.example.tfg.mapsFragments.otrosLugares.penaDeFrancia;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class Maps extends Fragment implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private TextView text;
    private ImageView img1;
    private String idioma;
    private Bundle args, argsMenu;
    private String [] opcionesSpinner;
    private Button btnp1,btnp2,btnp3,btnp4,btnp5,btnp6,btnp7,btnp8,btnp9,btnp10,btnp11;

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
        setHasOptionsMenu(true);
        args = new Bundle();
        argsMenu = new Bundle(); //Argumentos para el menu de opciones
        argsMenu.putString("iu", "maps");
    }

    /** El Fragment va a cargar su layout, el cual debemos especificar.
     Aquí se instanciarán los objetos que si son vistas */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_maps, container, false);
        if(v != null){
            img1 = v.findViewById(R.id.mapa);
            spinner = v.findViewById(R.id.spinnerMapa);
            text = v.findViewById(R.id.textomaps);
            btnp1 = v.findViewById(R.id.btnp1);
            btnp2 = v.findViewById(R.id.btnp2);
            btnp3 = v.findViewById(R.id.btnp3);
            btnp4 = v.findViewById(R.id.btnp4);
            btnp5 = v.findViewById(R.id.btnp5);
            btnp6 = v.findViewById(R.id.btnp6);
            btnp7 = v.findViewById(R.id.btnp7);
            btnp8 = v.findViewById(R.id.btnp8);
            btnp9 = v.findViewById(R.id.btnp9);
            btnp10 = v.findViewById(R.id.btnp10);
            btnp11 = v.findViewById(R.id.btnp11);
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
            img1.setImageResource(R.drawable.planolaalbercamonumentos);
            setVisibilityP(false);
        }

        else if (selecteditem.equalsIgnoreCase(opcionesSpinner[1])){ //Opcion seleccionada: Servicios
            img1.setImageResource(R.drawable.planolaalbercaservicios);
            setVisibilityP(false);
        }

        else if (selecteditem.equalsIgnoreCase(opcionesSpinner[3])){
            setVisibilityP(false);
            args.putString("idioma", idioma);
            Fragment fragment = otrosLugaresInicio.newInstance(args);
            cargarFragment(fragment);
        }

    }

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menuusuario, menu);
    }

    @SuppressLint({"NonConstantResourceId", "ShowToast"})
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.menu_contacto:
                //Creamos el fragmento
                fragment = FormularioDeContacto.newInstance(argsMenu);
                cargarFragment(fragment);
                break;

            case R.id.menu_idioma:
                //Creamos el fragmento
                fragment = Idiomas.newInstance(argsMenu);
                cargarFragment(fragment);
                break;

            case R.id.menu_acercade:
                //Creamos el fragmento
                //fragment = Idiomas.newInstance(args);
                break;

            default:
                return super.onOptionsItemSelected(menuItem);
        }

        return true;
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
            case "Pulsa sobre los iconos para más información":
                idioma = "es";
                break;
            case "Sakatu ikonoen gainean informazio gehiagorako":
                idioma = "eu";
                break;
            case "Click on the icons for more information":
                idioma = "en";
                break;
        }

        return idioma;
    }

    public void setBtnParking(){

        setVisibilityP(true);
        btnp1.setOnClickListener(view -> parkingOnClick("eras1"));
        btnp2.setOnClickListener(view -> parkingOnClick("ermitaeras"));
        btnp3.setOnClickListener(view -> parkingOnClick("eras2"));
        btnp4.setOnClickListener(view -> parkingOnClick("eras3"));
        btnp5.setOnClickListener(view -> parkingOnClick("sanantonio"));
        btnp6.setOnClickListener(view -> parkingOnClick("afueras"));
        btnp7.setOnClickListener(view -> parkingOnClick("centromedico"));
        btnp8.setOnClickListener(view -> parkingOnClick("carreteranueva"));
        btnp9.setOnClickListener(view -> parkingOnClick("puente"));
        btnp10.setOnClickListener(view -> parkingOnClick("elcastillo"));
        btnp11.setOnClickListener(view -> parkingOnClick("barrera"));

    }

    public void setVisibilityP(boolean activar){

        if (activar){
            btnp1.setVisibility(View.VISIBLE);
            btnp2.setVisibility(View.VISIBLE);
            btnp3.setVisibility(View.VISIBLE);
            btnp4.setVisibility(View.VISIBLE);
            btnp5.setVisibility(View.VISIBLE);
            btnp6.setVisibility(View.VISIBLE);
            btnp7.setVisibility(View.VISIBLE);
            btnp8.setVisibility(View.VISIBLE);
            btnp9.setVisibility(View.VISIBLE);
            btnp10.setVisibility(View.VISIBLE);
            btnp11.setVisibility(View.VISIBLE);
        }

        else{
            btnp1.setVisibility(View.INVISIBLE);
            btnp2.setVisibility(View.INVISIBLE);
            btnp3.setVisibility(View.INVISIBLE);
            btnp4.setVisibility(View.INVISIBLE);
            btnp5.setVisibility(View.INVISIBLE);
            btnp6.setVisibility(View.INVISIBLE);
            btnp7.setVisibility(View.INVISIBLE);
            btnp8.setVisibility(View.INVISIBLE);
            btnp9.setVisibility(View.INVISIBLE);
            btnp10.setVisibility(View.INVISIBLE);
            btnp11.setVisibility(View.INVISIBLE);
        }

    }

    public void parkingOnClick(String parking){
        DialogFragment parkingFragment = new parkingFragment();
        args.putString("parking", parking);
        parkingFragment.setArguments(args);
        parkingFragment.setCancelable(false);
        parkingFragment.show(getChildFragmentManager(),"parking_fragment");

    }
}