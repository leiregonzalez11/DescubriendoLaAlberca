package com.example.tfg.categorias;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.tfg.categorias.arquitectura.ArquitecturaActivity;
import com.example.tfg.categorias.artesania.artesaniaActivity;
import com.example.tfg.inicio.MainActivity;
import com.example.tfg.R;
import com.example.tfg.ajustes.ajustesActivity;
import com.example.tfg.mapa.MapsActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class categoriasActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener {

    BottomNavigationView bottomNavigationView;
    protected Button btnhistoria, btnTrad, btnMonu, btnFiesta, btnGastro, btnAloj, btnRutas, btnOtros;
    ImageButton btnArte, btnArqui;
    String idioma, path;
    StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        Bundle extra = getIntent().getExtras();
        idioma = extra.getString("idioma");
        System.out.println("IDIOMAAAAA: " + idioma);

        storageRef = FirebaseStorage.getInstance().getReference();

        setBtnListeners();

        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewCategorias);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_categoria);
    }

    private void setBtnListeners() {

        //btnhistoria = findViewById(R.id.botonhistoria);
        //btnhistoria.setOnClickListener(this);

        btnArte = findViewById(R.id.botonartesania);
        path = "categorias/" + idioma + "/artesania-" + idioma + ".jpg";
        btnArte.setOnClickListener(this);
        obtenerImagenFirebase(path, btnArte);

        //btnTrad = findViewById(R.id.botontradiciones);
        //btnTrad.setOnClickListener(this);

        btnArqui = findViewById(R.id.botonarquitectura);
        path = "categorias/" + idioma + "/arquitectura-" + idioma + ".jpg";
        btnArqui.setOnClickListener(this);
        obtenerImagenFirebase(path, btnArqui);

        /*btnMonu = findViewById(R.id.botonmonumentos);
        btnMonu.setOnClickListener(this);

        btnFiesta = findViewById(R.id.botonfiestas);
        btnFiesta.setOnClickListener(this);

        btnGastro = findViewById(R.id.botongastronomia);
        btnGastro.setOnClickListener(this);

        btnAloj = findViewById(R.id.botonalojamientos);
        btnAloj.setOnClickListener(this);

        btnRutas = findViewById(R.id.botonruta);
        btnRutas.setOnClickListener(this);

        btnOtros = findViewById(R.id.botonotros);
        btnOtros.setOnClickListener(this);*/

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
                mapa.putExtra("idioma", idioma);
                startActivity(mapa);
                finish();
                return true;

            case R.id.navigation_categoria:
                return true;

            case R.id.navigation_ajustes:
                Intent intent = new Intent(this, ajustesActivity.class);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        //Cuando se presione el botón, realiza una acción aquí

        ImageButton btn = (ImageButton) view;

        switch (btn.getId()){

            //case R.id.botonhistoria:

            case R.id.botonartesania:
                Intent artesania = new Intent(this, artesaniaActivity.class);
                artesania.putExtra("idioma", idioma);
                startActivity(artesania);
                finish();
                break;

            //case R.id.botontradiciones:

            case R.id.botonarquitectura:
                Intent arquitectura = new Intent(this, ArquitecturaActivity.class);
                arquitectura.putExtra("idioma", idioma);
                startActivity(arquitectura);
                finish();
                break;

            // case R.id.botonmonumentos:

            //case R.id.botonfiestas:

            //case R.id.botongastronomia:

            //case R.id.botonalojamientos:

            //case R.id.botonruta:

            //case R.id.botonotros:

        }
    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageButton btn){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext()).load(uri).into(btn);
            }
        });
    }

}