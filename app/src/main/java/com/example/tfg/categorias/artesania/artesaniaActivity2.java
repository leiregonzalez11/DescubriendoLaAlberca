package com.example.tfg.categorias.artesania;

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

public class artesaniaActivity2 extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, OnClickListener{

    BottomNavigationView bottomNavigationView;
    String idioma, categoria;
    ImageView img1, img2, img3;
    TextView text1, text2, text3, text4;
    StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artesania2);

        Bundle extra = getIntent().getExtras();
        idioma = extra.getString("idioma");
        categoria = extra.getString("categoria");

        GestorDB dbHelper = new GestorDB(getApplicationContext());

        //OBTENEMOS LOS TEXTOS Y LAS IMAGENES DE LA INTERFAZ

        String [] datos = dbHelper.obtenerDatosInterfaz(idioma, "interfaz2", categoria, 4);

        text1 = findViewById(R.id.arte21);
        text1.setText(datos[0]);

        text2 = findViewById(R.id.arte22);
        text2.setText(datos[1]);

        text3 = findViewById(R.id.arte23);
        text3.setText(datos[2]);

        text4 = findViewById(R.id.arte24);
        text4.setText(datos[3]);

        storageRef = FirebaseStorage.getInstance().getReference();

        img1 = findViewById(R.id.arte21img);
        obtenerImagenFirebase("artesania/bordado1.jpg", img1);
        img2 = findViewById(R.id.arte22img);
        obtenerImagenFirebase("artesania/bordado2.jpg", img2);
        img3 = findViewById(R.id.arte23img);
        obtenerImagenFirebase("artesania/bordado3.jpg", img3);

        //BOTON SIGUIENTE y ATRAS

        Button atrasBtn = findViewById(R.id.arteatras2);
        atrasBtn.setOnClickListener(this);

        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewArte2);
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

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        //Cuando se presione el botón, realiza una acción aquí

        Button btn = (Button) view;

        switch (btn.getId()){

            case R.id.arteatras2:
                Intent atras = new Intent(this, artesaniaSelectorActivity.class);
                atras.putExtra("idioma", idioma);
                atras.putExtra("categoria", categoria);
                startActivity(atras);
                break;

            case R.id.artefin1:
                Intent cat = new Intent(this, categoriasActivity.class);
                cat.putExtra("idioma", idioma);
                startActivity(cat);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {}

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
}