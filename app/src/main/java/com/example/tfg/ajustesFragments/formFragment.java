package com.example.tfg.ajustesFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.tfg.R;
import com.example.tfg.adapters.SpinnerAdapter;
import com.example.tfg.adapters.tabAdapter;
import com.example.tfg.adapters.tabAdapterComer;
import com.example.tfg.anterior.ajustes.ajustesActivity;
import com.example.tfg.anterior.ajustes.contactoActivity;
import com.example.tfg.navigationmenu.FragmentAjustes;
import com.example.tfg.navigationmenu.FragmentInicio;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;


public class formFragment extends Fragment implements View.OnClickListener {

    EditText asuntoET;
    TextInputEditText mensajeET;
    boolean enviado;
    String asunto, asuntoet, mensajeet;

    public formFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        //Toolbar
        Toolbar myToolbar = requireView().findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(myToolbar);
        Objects.requireNonNull(Objects.requireNonNull((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowTitleEnabled(false);
        myToolbar.setTitleTextColor(R.color.white);

        asuntoET= requireView().findViewById(R.id.asunto);
        asuntoet = asuntoET.getHint().toString().trim();
        mensajeET = requireView().findViewById(R.id.mensaje);

        asunto = getResources().getString(R.string.asuntoet);

        asuntoET.setOnClickListener(view -> {
            if (asuntoet.equals(asunto)){
                asuntoET.setHint("");
            } else if (asuntoet.equals("")) {
                asuntoET.setHint(asunto);
            }
        });

        //Botón Enviar

        Button siguienteBtn = requireView().findViewById(R.id.btnEnviar);
        siguienteBtn.setOnClickListener(this);

        Button atrasBtn = requireView().findViewById(R.id.btnAtrasForm);
        atrasBtn.setOnClickListener(this);

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
        } else if (btn.getId() == R.id.btnAtrasForm){

            //Creamos el Fragment
            Fragment fragment = new FragmentAjustes();

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

    @Override
    public void onResume() {
        if (enviado){
            Toast.makeText(getContext(), "Email Enviado", Toast.LENGTH_LONG).show();

            //Creamos el Fragment
            Fragment fragment = new FragmentAjustes();

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
        super.onResume();
    }



    @SuppressLint("IntentReset")
    private boolean enviarEmail(){

        enviado = false;

        String[] TO = {"leiregonzalezlopez99@gmail.com"};
        String[] CC = {"leiregonz11@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        mensajeet = Objects.requireNonNull(mensajeET.getText()).toString();
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

        EditText textAsunto = requireView().findViewById(R.id.asunto);
        EditText textMensaje = requireView().findViewById(R.id.mensaje);
        String asunto = textAsunto.getText().toString();
        String mensaje = textMensaje.getText().toString();

        if (asunto.equals("") && mensaje.equals("")) { //Si el asunto y el mensaje están vacíos
            Toast.makeText(getContext(), "No puede haber campos vacíos. Revise el formulario.", Toast.LENGTH_SHORT).show();
            textAsunto.setHint(getResources().getString(R.string.asuntoet));
            valido = false;
        } else if (asunto.equals("¿Cuál es el motivo de tu mensaje?") || asunto.equals("")) { //Si el asunto está vacío
            Toast.makeText(getContext(), getString(R.string.asuntoVacio), Toast.LENGTH_SHORT).show();
            textAsunto.setHint(getResources().getString(R.string.asuntoet));
            valido = false;
        } else if (mensaje.equals("")){ //Si el mensaje está vacío
            Toast.makeText(getContext(), getString(R.string.mensajeVacio), Toast.LENGTH_SHORT).show();
            textMensaje.setText("");
            valido = false;
        }

        return valido;
    }

}