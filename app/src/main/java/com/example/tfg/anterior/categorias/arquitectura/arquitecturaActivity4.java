package com.example.tfg.anterior.categorias.arquitectura;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;


public class arquitecturaActivity4 extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, OnClickListener {

    BottomNavigationView bottomNavigationView;
    String idioma, categoria;
    private StorageReference storageRef;
    private ImageView img1, img2;

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arquitectura4);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        myToolbar.setTitleTextColor(R.color.white);

        /*Obtenemos los datos agregados desde la actividad anterior...*/
        Bundle extra = getIntent().getExtras();
        idioma = extra.getString("idioma");
        categoria = extra.getString("categoria");

        GestorDB dbHelper = new GestorDB(getApplicationContext());

        String [] datos = dbHelper.obtenerDescrInterfaz(idioma, "inscripciones", categoria, 3);

        TextView text1 = findViewById(R.id.arqui41);
        TextView text2 = findViewById(R.id.arqui42);
        TextView text3 = findViewById(R.id.arqui43);

        text1.setText(datos[0] + Html.fromHtml("<br>"));
        text2.setText(datos[1] + Html.fromHtml("<br>"));
        text3.setText(datos[2] + Html.fromHtml("<br>"));

        storageRef = FirebaseStorage.getInstance().getReference();

        img1 = findViewById(R.id.arqui41img);
        img2 = findViewById(R.id.arqui42img);

        obtenerImagenFirebase("arquitectura/inscripciones2.jpg", img1);
        obtenerImagenFirebase("arquitectura/inscripciones3.jpg", img2);


        //BOTON SIGUIENTE y ATRAS
        Button atrasBtnCat = findViewById(R.id.arquiAtras4);
        atrasBtnCat.setOnClickListener(this);

        Button atrasBtn = findViewById(R.id.arquiatras4);
        atrasBtn.setOnClickListener(this);

        Button finBtn = findViewById(R.id.arquisiguiente4);
        finBtn.setOnClickListener(this);


        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewArqui4);
        bottomNavigationView.setSelectedItemId(R.id.navigation_categoria);
        bottomNavigationView.setOnItemSelectedListener(this);

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

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.navigation_inicio:
                Intent inicio = new Intent(this, null);
                startActivity(inicio);
                finish();
                return true;

            case R.id.navigation_mapa:
                Intent mapa = new Intent(this, null);
                mapa.putExtra("idioma",idioma);
                startActivity(mapa);
                finish();
                return true;

            case R.id.navigation_categoria:
                Intent categorias = new Intent(this, null);
                categorias.putExtra("idioma",idioma);
                startActivity(categorias);
                finish();
                return true;

            case R.id.navigation_ajustes:
                Intent ajustes = new Intent(this, null);
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


        switch (btn.getId()) {

            case R.id.arquiatras4:
                Intent atras = new Intent(this, arquitecturaActivity3.class);
                atras.putExtra("idioma", idioma);
                atras.putExtra("categoria", categoria);
                startActivity(atras);
                finish();
                break;

            case R.id.arquisiguiente4:
                Intent arqui5 = new Intent(this, arquitecturaActivity5.class);
                arqui5.putExtra("idioma", idioma);
                arqui5.putExtra("categoria", categoria);
                startActivity(arqui5);
                finish();
                break;

            case R.id.arquiAtras4:
                Intent arquifin = new Intent(this, null);
                arquifin.putExtra("idioma", idioma);
                startActivity(arquifin);
                finish();
                break;
        }
    }
}