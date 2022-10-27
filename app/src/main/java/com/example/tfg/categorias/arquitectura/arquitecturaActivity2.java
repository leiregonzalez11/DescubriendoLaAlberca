package com.example.tfg.categorias.arquitectura;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.ajustes.ajustesActivity;
import com.example.tfg.categorias.categoriasActivity;
import com.example.tfg.inicio.MainActivity;
import com.example.tfg.mapa.MapsActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class arquitecturaActivity2 extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, OnClickListener{

    BottomNavigationView bottomNavigationView;
    ImageView img1, img2, img3;
    TextView text1, text2, text3;
    StorageReference storageRef;
    String idioma, categoria;

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arquitectura2);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        myToolbar.setTitleTextColor(R.color.white);

        Bundle extra = getIntent().getExtras();
        idioma = extra.getString("idioma");
        categoria = extra.getString("categoria");

        GestorDB dbHelper = new GestorDB(getApplicationContext());

        String [] datos = dbHelper.obtenerDescrInterfaz(idioma, "interfaz2", categoria, 3);

        text1 = findViewById(R.id.arqui21);
        text2 = findViewById(R.id.arqui22);
        text3 = findViewById(R.id.arqui23);

        text1.setText(datos[0]+ Html.fromHtml("<br>"));
        text2.setText(datos[1]+ Html.fromHtml("<br>"));
        text3.setText(datos[2]+ Html.fromHtml("<br>"));

        storageRef = FirebaseStorage.getInstance().getReference();

        img1 = findViewById(R.id.arqui21img);
        img2 = findViewById(R.id.arqui22img);
        img3 = findViewById(R.id.arqui23img);
        obtenerImagenFirebase("arquitectura/exterior1.jpg", img1);
        //TODO: Cambiar la foto
        obtenerImagenFirebase("arquitectura/exterior3.jpeg", img2);
        obtenerImagenFirebase("arquitectura/exterior2.jpg", img3);

        //BOTON SIGUIENTE y ATRAS

        Button atrasBtn = findViewById(R.id.arquiatras2);
        atrasBtn.setOnClickListener(this);

        Button siguienteBtn = findViewById(R.id.arquisiguiente2);
        siguienteBtn.setOnClickListener(this);

        Button finBtn = findViewById(R.id.arquiAtras2);
        finBtn.setOnClickListener(this);

        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewArqui2);
        bottomNavigationView.setSelectedItemId(R.id.navigation_categoria);
        bottomNavigationView.setOnItemSelectedListener(this);

    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext()).load(uri).into(img);
            }
        });
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

        switch (btn.getId()){

            case R.id.arquiatras2:
                Intent atras = new Intent(this, ArquitecturaActivity.class);
                atras.putExtra("idioma", idioma);
                startActivity(atras);
                break;

            case R.id.arquisiguiente2:
                Intent arqui3 = new Intent(this, arquitecturaActivity3.class);
                arqui3.putExtra("idioma", idioma);
                arqui3.putExtra("categoria", categoria);
                startActivity(arqui3);
                break;

            case R.id.arquiAtras2:
                Intent arquifin = new Intent(this, categoriasActivity.class);
                arquifin.putExtra("idioma", idioma);
                startActivity(arquifin);
                finish();
                break;
        }
    }
}