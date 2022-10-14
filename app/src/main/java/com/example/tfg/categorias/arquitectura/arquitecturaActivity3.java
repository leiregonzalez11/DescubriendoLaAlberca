package com.example.tfg.categorias.arquitectura;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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

public class arquitecturaActivity3 extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, OnClickListener{

    BottomNavigationView bottomNavigationView;
    StorageReference storageRef;
    ImageView img1, img2, img3, img4, img5;
    TextView text1, text2, text3, text4, text5;
    String idioma, categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arquitectura3);

        Bundle extra = getIntent().getExtras();
        idioma = extra.getString("idioma");
        categoria = extra.getString("categoria");

        GestorDB dbHelper = new GestorDB(getApplicationContext());

        String [] datos = dbHelper.obtenerDescrInterfaz(idioma, "interfaz3", categoria, 5);

        text1 = findViewById(R.id.arqui31);
        text2 = findViewById(R.id.arqui32);
        text3 = findViewById(R.id.arqui33);
        text4 = findViewById(R.id.arqui34);
        text5 = findViewById(R.id.arqui35);

        text1.setText(datos[0]);
        text2.setText(datos[1]);
        text3.setText(datos[2]);
        text4.setText(datos[3]);
        text5.setText(datos[4]);

        storageRef = FirebaseStorage.getInstance().getReference();

        img1 = findViewById(R.id.arqui31img);
        img2 = findViewById(R.id.arqui32img);
        img3 = findViewById(R.id.arqui33img);
        img4 = findViewById(R.id.arqui34img);
        img5 = findViewById(R.id.arqui35img);
        obtenerImagenFirebase("arquitectura/interior1.jpg", img1);
        obtenerImagenFirebase("arquitectura/interior5.jpg", img2);
        obtenerImagenFirebase("arquitectura/interior2.jpg", img3);
        obtenerImagenFirebase("arquitectura/interior4.jpg", img4);
        obtenerImagenFirebase("arquitectura/interior3.jpg", img5);

        //BOTON SIGUIENTE y ATRAS

        Button atrasBtn = findViewById(R.id.arquiatras3);
        atrasBtn.setOnClickListener(this);

        Button siguienteBtn = findViewById(R.id.arquisiguiente3);
        siguienteBtn.setOnClickListener(this);

        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewArqui3);
        bottomNavigationView.setSelectedItemId(R.id.navigation_categoria);
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

    @Override
    public void onBackPressed() {}

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        //Cuando se presione el botón, realiza una acción aquí

        Button btn = (Button) view;

        switch (btn.getId()){

            case R.id.arquiatras3:
                Intent atras = new Intent(this, arquitecturaActivity2.class);
                atras.putExtra("idioma", idioma);
                atras.putExtra("categoria", categoria);
                startActivity(atras);
                break;

            case R.id.arquisiguiente3:
                Intent arqui4 = new Intent(this, arquitecturaActivity4.class);
                arqui4.putExtra("idioma", idioma);
                arqui4.putExtra("categoria", categoria);
                startActivity(arqui4);
                break;
        }
    }
}