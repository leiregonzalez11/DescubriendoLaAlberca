package com.example.tfg.mapsFragments.otrosLugares.penaFrancia;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class monumentopenaFragment extends DialogFragment {

    private Button btnExtra;
    private GestorDB dbHelper;
    private ImageView img1, img2;
    private String idioma, categoria;
    private Bundle args = new Bundle();
    private StorageReference storageRef;
    private TextView text1, text2, text3, titulo;

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View infoView = inflater.inflate(R.layout.fragment_monumentopena, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(infoView);

        dbHelper = new GestorDB(getContext());
        storageRef = FirebaseStorage.getInstance().getReference();

        assert getArguments() != null;
        String monumento = getArguments().getString("monumento");
        idioma = getArguments().getString("idioma");
        categoria = getArguments().getString("categoria");

        args.putString("idioma", idioma);
        args.putString("categoria", categoria);

        titulo = infoView.findViewById(R.id.monumentospenatitulo);
        text1 = infoView.findViewById(R.id.monumentostext1);
        text2 = infoView.findViewById(R.id.monumentostext2);
        text3 = infoView.findViewById(R.id.monumentostext3);
        img1 = infoView.findViewById(R.id.imagemon1);
        img2 = infoView.findViewById(R.id.imagemon2);
        btnExtra = infoView.findViewById(R.id.buttonmonumentos);

        titulo.setText(monumento);

        Button volver = infoView.findViewById(R.id.buttonVolverMon);
        volver.setOnClickListener(view -> dismiss());

        setText(monumento);

        return builder.create();
    }

    @SuppressLint("SetTextI18n")
    private void setText(String monumento) {

        switch (monumento){
            case "santodomingo":

                titulo.setText("Mirador de Santo Domingo");

                //Texto
                String[] datos = dbHelper.obtenerInfoPena(idioma, "miradordesantodomingo", categoria, "peñadefrancia", 1);
                text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text2.setText("");
                text3.setText("");

                //Imagenes
                img1.setVisibility(View.VISIBLE);
                obtenerImagenFirebase("otros/penafrancia/miradorsantodomingo.png", img1);

                btnExtra.setVisibility(View.GONE);
                break;

            case "lablanca":

                titulo.setText("Capilla de la Blanca");

                //Texto
                datos = dbHelper.obtenerInfoPena(idioma, "capilladelablanca", categoria, "peñadefrancia", 3);
                System.out.println("MONUMENTO: " + datos[0]);
                System.out.println("MONUMENTO: " + datos[1]);
                System.out.println("MONUMENTO: " + datos[2]);
                text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

                //Imagenes
                obtenerImagenFirebase("otros/penafrancia/capilladelablanca1.png", img1);
                obtenerImagenFirebase("otros/penafrancia/capilladelablanca2.png", img2);

                btnExtra.setVisibility(View.GONE);
                break;

            case "sanandresexterior":

                titulo.setText("Capilla exterior de San Andrés");

                //Texto
                datos = dbHelper.obtenerInfoPena(idioma, "capillaexteriordesanandrés", categoria, "peñadefrancia", 2);
                text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setText("");

                //Imagenes
                obtenerImagenFirebase("otros/penafrancia/capillasanandres.png", img1);
                obtenerImagenFirebase("otros/penafrancia/capillasanandres2.png", img2);

                btnExtra.setVisibility(View.GONE);
                break;

            case "santocristoexterior":

                titulo.setText("Capilla exterior del Santo Cristo");

                //Texto
                datos = dbHelper.obtenerInfoPena(idioma, "capillaexteriordelsantocristo", categoria, "peñadefrancia", 2);
                text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text1.requestFocus();
                text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setText("");

                //Imagenes
                obtenerImagenFirebase("otros/penafrancia/capillasantocristo1.png", img1);
                obtenerImagenFirebase("otros/penafrancia/capillasantocristo2.png", img2);

                btnExtra.setVisibility(View.GONE);
                break;

            case "balconsantiago":

                titulo.setText("Balcón de Santiago");

                //Texto
                datos = dbHelper.obtenerInfoPena(idioma, "balcóndesantiago", categoria, "peñadefrancia", 3);
                text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

                //Imagenes
                //obtenerImagenFirebase("otros/penafrancia/balcondesantiago.png", img1);

                btnExtra.setVisibility(View.VISIBLE);
                btnExtra.setText("  El Salto del Niño  ");
                btnExtra.setOnClickListener(view1 -> {
                    DialogFragment saltoFragment = new saltoDelNinoFragment();
                    saltoFragment.setArguments(args);
                    saltoFragment.setCancelable(false);
                    saltoFragment.show(getChildFragmentManager(),"salto_fragment");
                });
                break;
        }

    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }

}