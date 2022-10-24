package com.example.tfg.ajustes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tfg.R;
import com.example.tfg.categorias.categoriasActivity;
import com.example.tfg.inicio.MainActivity;
import com.example.tfg.mapa.MapsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;

public class contactoActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener {

    BottomNavigationView bottomNavigationView;
    EditText emailET, asuntoET;
    TextInputEditText mensajeET;
    boolean enviado;
    String asunto, mensaje, idioma, asuntoet, mensajeet;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        myToolbar.setTitleTextColor(R.color.white);

        Bundle extra = getIntent().getExtras();
        idioma = extra.getString("idioma");

        asuntoET= findViewById(R.id.asunto);
        asuntoet = asuntoET.getHint().toString().trim();
        mensajeET = findViewById(R.id.mensaje);

        System.out.println("ASUNTOOO " + asuntoet);

        asunto = getResources().getString(R.string.asuntoet);

        System.out.println("ASUNTOOO " + asunto);


        asuntoET.setOnClickListener(view -> {
            if (asuntoet.equals(asunto)){
                asuntoET.setHint("");
            }
        });

       //Botón Enviar

        Button siguienteBtn = findViewById(R.id.btnEnviar);
        siguienteBtn.setOnClickListener(this);

        Button atrasBtn = findViewById(R.id.btnAtrasContacto);
        atrasBtn.setOnClickListener(this);

        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewCont);
        bottomNavigationView.setSelectedItemId(R.id.navigation_ajustes);
        bottomNavigationView.setOnItemSelectedListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.navigation_inicio:
                Intent inicio = new Intent(this, MainActivity.class);
                startActivity(inicio);
                finish();
                return true;

            case R.id.navigation_mapa:
                Intent mapa = new Intent(this, MapsActivity.class);
                mapa.putExtra("idioma",idioma);
                startActivity(mapa);
                finish();
                return true;

            case R.id.navigation_categoria:
                Intent categorias = new Intent(this, categoriasActivity.class);
                categorias.putExtra("idioma",idioma);
                startActivity(categorias);
                finish();
                return true;

            case R.id.navigation_ajustes:
                Intent ajustes = new Intent(this, ajustesActivity.class);
                ajustes.putExtra("idioma", idioma);
                startActivity(ajustes);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed() {}

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
        } else if (btn.getId() == R.id.btnAtrasContacto){
            Intent intent = new Intent(this, ajustesActivity.class);
            intent.putExtra("idioma", idioma);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onRestart() {
        if (enviado){
            Toast.makeText(contactoActivity.this, "Email Enviado", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, ajustesActivity.class);
            intent.putExtra("idioma", idioma);
            startActivity(intent);
            finish();
        }
        super.onRestart();
    }



    @SuppressLint("IntentReset")
    private boolean enviarEmail(){

        enviado = false;

        String[] TO = {"leiregonzalezlopez99@gmail.com"};
        String[] CC = {"leiregonz11@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        mensajeet = mensajeET.getText().toString();
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
            //Toast.makeText(contactoActivity.this, "Email Enviado", Toast.LENGTH_LONG).show();
            enviado = true;

        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(contactoActivity.this, "No se ha podido enviar el mensaje", Toast.LENGTH_SHORT).show();
        }

        return enviado;
    }


    public boolean validarDatos() {

        boolean valido = true;

        //Validamos el asunto
        EditText textAsunto = findViewById(R.id.asunto);
        String asunto = textAsunto.getHint().toString();
        if (asunto.equals("")) { //Si el email está vacío
            Toast.makeText(getApplicationContext(), getString(R.string.asuntoVacio), Toast.LENGTH_SHORT).show();
            textAsunto.setText("");
            valido = false;
        }

        //Validamos el mensaje
        EditText textMensaje = findViewById(R.id.mensaje);
        String mensaje = textMensaje.getText().toString();
        if (mensaje.equals("")) { //Si el email está vacío
            Toast.makeText(getApplicationContext(), getString(R.string.mensajeVacio), Toast.LENGTH_SHORT).show();
            textMensaje.setText("");
            valido = false;
        }

        return valido;
    }




}

