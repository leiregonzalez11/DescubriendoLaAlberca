package com.example.tfg.ajustesFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.tfg.R;
import com.example.tfg.navigationmenu.Ajustes;
import com.example.tfg.navigationmenu.Categorias;
import com.example.tfg.navigationmenu.Inicio;
import com.example.tfg.navigationmenu.Maps;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Objects;

public class FormularioDeContacto extends Fragment implements View.OnClickListener {

    private boolean enviado;
    private EditText asuntoET;
    private Toolbar myToolbar;
    private Fragment fragment;
    private Button siguienteBtn;
    private TextInputEditText mensajeET;
    private String asunto, asuntoet, iu;

    /**
     * Utilizaremos este Factory Method para crear una nueva instancia
     * de este fragmento utilizando los parámetros dados.
     * @return Una nueva instancia del Fragment.
     */
    public static FormularioDeContacto newInstance(Bundle args) {
        FormularioDeContacto fragment = new FormularioDeContacto();
        if (args != null){
            fragment.setArguments(args);
        }
        return fragment;
    }

    /** Required empty public constructor */
    public FormularioDeContacto() {}

    /** El Fragment ha sido creado.
     * Aqui fijamos los parámetros que tengan que ver con el Activity. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        iu = requireArguments().getString("iu");
        myToolbar = requireActivity().findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_circle_arrow_left_solid);
        myToolbar.setNavigationOnClickListener(v -> start(iu));
    }

    /** El Fragment va a cargar su layout, el cual debemos especificar.
     Aquí se instanciarán los objetos que si son vistas */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_form, container, false);
        if(v != null){
            asuntoET= v.findViewById(R.id.asunto);
            mensajeET = v.findViewById(R.id.mensaje);
            siguienteBtn = v.findViewById(R.id.btnEnviar);
        }
        return v;
    }

    /** La vista de layout ha sido creada y ya está disponible
     Aquí fijaremos todos los parámetros de nuestras vistas **/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        asuntoet = asuntoET.getHint().toString().trim();
        asunto = getResources().getString(R.string.asuntoet);

        asuntoET.setOnClickListener(v -> {
            if (asuntoet.equals(asunto)){
                asuntoET.setHint("");
            } else if (asuntoet.equals("")) {
                asuntoET.setHint(asunto);
            }
        });

        //Botón Enviar
        siguienteBtn.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        //Cuando se presione el botón, realiza una acción aquí
        Button btn = (Button) view;
        if (btn.getId() == R.id.btnEnviar) {
            //Toast.makeText(contactoActivity.this, "Has pulsado Enviar", Toast.LENGTH_LONG).show();
            if (validarDatos()){
                enviado = enviarEmail();
                if (enviado){
                    asuntoET.setText("");
                    mensajeET.setText("");
                }
            }
        }
    }

    @Override
    public void onResume() {
        if (enviado){
            Toast.makeText(getContext(), "Email Enviado", Toast.LENGTH_LONG).show();
            start(iu);
        }
        super.onResume();
    }

    private void start(String iu) {
        myToolbar.setNavigationIcon(null);
        //Determinamos el fragment de retorno y creamos el Fragment
        switch (iu) {
            case "inicio":
                fragment = Inicio.newInstance();
                break;
            case "categorias":
                fragment = Categorias.newInstance();
                break;
            case "ajustes":
                fragment = Ajustes.newInstance();
                break;
            case "maps":
                fragment = Maps.newInstance();
                break;
        }

        assert !(fragment == null);
        cargarFragment(fragment);
    }

    @SuppressLint("IntentReset")
    private boolean enviarEmail(){

        enviado = false;

        String[] TO = {"leiregonzalezlopez99@gmail.com"};
        String[] CC = {"leiregonz11@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        String mensajeet = Objects.requireNonNull(mensajeET.getText()).toString();
        asuntoet = asuntoET.getText().toString();

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, asuntoet);
        emailIntent.putExtra(Intent.EXTRA_TEXT, mensajeet);

        try {
            startActivity(Intent.createChooser(emailIntent, "Enviando Email..."));
            Log.i("termina envio de email...", "");
            //Toast.makeText(getContext(), "Email Enviado", Toast.LENGTH_LONG).show();
            enviado = true;

        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "No se ha podido enviar el mensaje", Toast.LENGTH_SHORT).show();
        }

        return enviado;
    }

    public boolean validarDatos() {

        boolean valido = true;

        //Validamos los datos introducidos
        String asunto = asuntoET.getText().toString();
        String mensaje = Objects.requireNonNull(mensajeET.getText()).toString();

        if (asunto.equals("") && mensaje.equals("")) { //Si el asunto y el mensaje están vacíos
            Toast.makeText(getContext(), "No puede haber campos vacíos. Revise el formulario.", Toast.LENGTH_SHORT).show();
            asuntoET.setHint(getResources().getString(R.string.asuntoet));
            valido = false;
        } else if (asunto.equals("¿Cuál es el motivo de tu mensaje?") || asunto.equals("")) { //Si el asunto está vacío
            Toast.makeText(getContext(), getString(R.string.asuntoVacio), Toast.LENGTH_SHORT).show();
            asuntoET.setHint(getResources().getString(R.string.asuntoet));
            valido = false;
        } else if (mensaje.equals("")){ //Si el mensaje está vacío
            Toast.makeText(getContext(), getString(R.string.mensajeVacio), Toast.LENGTH_SHORT).show();
            mensajeET.setText("");
            valido = false;
        }

        return valido;
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