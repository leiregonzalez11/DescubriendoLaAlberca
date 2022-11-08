package com.example.tfg.anterior.categorias.artesania;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class artesaniaActivity4 extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, OnClickListener {

    BottomNavigationView bottomNavigationView;
    StorageReference storageRef;
    ImageView img1, img2, img3;
    String idioma, categoria;

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artesania4);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        myToolbar.setTitleTextColor(R.color.white);

        Bundle extra = getIntent().getExtras();
        idioma = extra.getString("idioma");
        categoria = extra.getString("categoria");

        img1 = findViewById(R.id.arte41img);
        img2 = findViewById(R.id.arte42img);
        img3 = findViewById(R.id.arte43img);

        GestorDB dbHelper = new GestorDB(getApplicationContext());

        String [] datos = dbHelper.obtenerDescrInterfaz(idioma, "interfaz4", categoria, 4);

        TextView text1 = findViewById(R.id.arte41);
        TextView text2 = findViewById(R.id.arte42);
        TextView text3 = findViewById(R.id.arte43);
        TextView text4= findViewById(R.id.arte44);

        text1.setText(datos[0] + Html.fromHtml("<br>"));
        text2.setText(datos[1] + Html.fromHtml("<br>"));
        text3.setText(datos[2] + Html.fromHtml("<br>"));
        text4.setText(datos[3] + Html.fromHtml("<br>"));

        storageRef = FirebaseStorage.getInstance().getReference();
        obtenerImagenFirebase("artesania/hombre1.jpg", img1);
        obtenerImagenFirebase("artesania/hombre2.jpg", img2);
        obtenerImagenFirebase("artesania/hombre3.jpg", img3);

        //BOTON SIGUIENTE y ATRAS

        Button atrasBtn = findViewById(R.id.arte4atras);
        atrasBtn.setOnClickListener(this);

        Button atrasBtn2 = findViewById(R.id.arteAtras4);
        atrasBtn2.setOnClickListener(this);

        Button finBtn = findViewById(R.id.artefintraje);
        finBtn.setOnClickListener(this);

        //MENU
        bottomNavigationView = findViewById(R.id.navigationViewArte4);
        bottomNavigationView.setSelectedItemId(R.id.navigation_categoria);
        bottomNavigationView.setOnItemSelectedListener(this);

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

    @Override
    public void onBackPressed() {}

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(getApplicationContext()).load(uri).into(img));
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        //Cuando se presione el botón, realiza una acción aquí

        Button btn = (Button) view;

        switch (btn.getId()) {

            case R.id.arte4atras:
                Intent atras = new Intent(this, artesaniaActivity3.class);
                atras.putExtra("idioma", idioma);
                atras.putExtra("categoria", categoria);
                startActivity(atras);
                finish();
                break;

            case R.id.artefintraje:
                Intent atrasselector = new Intent(this, artesaniaSelectorActivity.class);
                atrasselector.putExtra("idioma", idioma);
                atrasselector.putExtra("categoria", categoria);
                startActivity(atrasselector);
                finish();
                break;

            case R.id.arteAtras4:
                Intent arquifin = new Intent(this, null);
                arquifin.putExtra("idioma", idioma);
                startActivity(arquifin);
                finish();
                break;
        }
    }
}