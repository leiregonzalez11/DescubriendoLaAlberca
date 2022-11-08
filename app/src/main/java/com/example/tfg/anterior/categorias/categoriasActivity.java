package com.example.tfg.anterior.categorias;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tfg.anterior.categorias.arquitectura.ArquitecturaActivity;
import com.example.tfg.anterior.categorias.artesania.artesaniaActivity;
import com.example.tfg.anterior.categorias.gastronomia.gastronomiaActivity;
import com.example.tfg.anterior.categorias.otros.otrosActivity;
import com.example.tfg.anterior.categorias.rutas.rutasActivity;
import com.example.tfg.anterior.categorias.tradiciones.tradicionesActivity;
import com.example.tfg.anterior.inicio.MainActivity;
import com.example.tfg.R;
import com.example.tfg.anterior.ajustes.ajustesActivity;
import com.example.tfg.anterior.mapa.MapsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class categoriasActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener {

    BottomNavigationView bottomNavigationView;
    protected ImageButton btnhistoria, btnTrad, btnMonu, btnFiesta, btnGastro, btnPers, btnRutas, btnOtros, btnArte, btnArqui;
    String idioma, path;
    StorageReference storageRef;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        myToolbar.setTitleTextColor(R.color.white);

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

    @SuppressLint("WrongViewCast")
    private void setBtnListeners() {

        btnhistoria = findViewById(R.id.botonhistoria);
        path = "categorias/" + idioma + "/historia-" + idioma + ".jpg";
        btnhistoria.setOnClickListener(this);
        obtenerImagenFirebase(path, btnhistoria);

        btnArte = findViewById(R.id.botonartesania);
        path = "categorias/" + idioma + "/artesania-" + idioma + ".jpg";
        btnArte.setOnClickListener(this);
        obtenerImagenFirebase(path, btnArte);

        btnTrad = findViewById(R.id.botontradiciones);
        path = "categorias/" + idioma + "/tradicion-" + idioma + ".jpg";
        btnTrad.setOnClickListener(this);
        obtenerImagenFirebase(path, btnTrad);

        btnArqui = findViewById(R.id.botonarquitectura);
        path = "categorias/" + idioma + "/arquitectura-" + idioma + ".jpg";
        btnArqui.setOnClickListener(this);
        obtenerImagenFirebase(path, btnArqui);

        btnMonu = findViewById(R.id.botonmonumentos);
        path = "categorias/" + idioma + "/monumentos-" + idioma + ".jpg";
        btnMonu.setOnClickListener(this);
        obtenerImagenFirebase(path, btnMonu);

        btnFiesta = findViewById(R.id.botonfiestas);
        path = "categorias/" + idioma + "/fiestas-" + idioma + ".jpg";
        btnFiesta.setOnClickListener(this);
        obtenerImagenFirebase(path, btnFiesta);

        btnGastro = findViewById(R.id.botongastronomia);
        path = "categorias/" + idioma + "/gastronomia-" + idioma + ".jpg";
        btnGastro.setOnClickListener(this);
        obtenerImagenFirebase(path, btnGastro);

        btnPers = findViewById(R.id.botonpersonajes);
        //TODO: Cambiar la ruta
        path = "categorias/" + idioma + "/alojamientos-" + idioma + ".jpg";
        btnPers.setOnClickListener(this);
        obtenerImagenFirebase(path, btnPers);

        btnRutas = findViewById(R.id.botonruta);
        path = "categorias/" + idioma + "/rutas-" + idioma + ".jpg";
        btnRutas.setOnClickListener(this);
        obtenerImagenFirebase(path, btnRutas);

        btnOtros = findViewById(R.id.botonotros);
        path = "categorias/" + idioma + "/otros-" + idioma + ".jpg";
        btnOtros.setOnClickListener(this);
        obtenerImagenFirebase(path, btnOtros);

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
                Intent ajustes = new Intent(this, ajustesActivity.class);
                ajustes.putExtra("idioma", idioma);
                startActivity(ajustes);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @SuppressLint({"NonConstantResourceId", "ShowToast"})
    public void onClick(View view) {
        //Cuando se presione el botón, realiza una acción aquí

        ImageButton btn = (ImageButton) view;

        switch (btn.getId()){

            case R.id.botonhistoria:
                Toast.makeText(categoriasActivity.this, "Has pulsado Historia", Toast.LENGTH_LONG);
                break;

            case R.id.botonartesania:
                Intent artesania = new Intent(this, artesaniaActivity.class);
                artesania.putExtra("idioma", idioma);
                startActivity(artesania);
                finish();
                break;

            case R.id.botontradiciones:
                Intent tradiciones = new Intent(this, tradicionesActivity.class);
                tradiciones.putExtra("idioma", idioma);
                startActivity(tradiciones);
                finish();
                break;

            case R.id.botonarquitectura:
                Intent arquitectura = new Intent(this, ArquitecturaActivity.class);
                arquitectura.putExtra("idioma", idioma);
                startActivity(arquitectura);
                finish();
                break;

            case R.id.botonmonumentos:
                Toast.makeText(categoriasActivity.this, "Has pulsado Monumentos", Toast.LENGTH_LONG);
                break;

            case R.id.botonfiestas:
                Toast.makeText(categoriasActivity.this, "Has pulsado Fiestas", Toast.LENGTH_LONG);
                break;

            case R.id.botongastronomia:
                Intent gastronomia = new Intent(this, gastronomiaActivity.class);
                gastronomia.putExtra("idioma", idioma);
                startActivity(gastronomia);
                finish();
                break;

            case R.id.botonpersonajes:
                Toast.makeText(categoriasActivity.this, "Has pulsado Cultura", Toast.LENGTH_LONG);
                break;

            case R.id.botonruta:
                Intent rutas = new Intent(this, rutasActivity.class);
                rutas.putExtra("idioma", idioma);
                startActivity(rutas);
                finish();
                break;

            case R.id.botonotros:
                Intent otros = new Intent(this, otrosActivity.class);
                otros.putExtra("idioma", idioma);
                startActivity(otros);
                finish();
                break;

        }
    }

    @Override
    public void onBackPressed() {}

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageButton btn){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(getApplicationContext()).load(uri).into(btn));
    }

}