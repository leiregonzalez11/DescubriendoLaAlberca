package com.example.tfg.Maps.otrosLugares.penaFrancia;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
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
    private String idioma;
    private final Bundle args = new Bundle();
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


        args.putString("idioma", idioma);


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
                String[] datos = dbHelper.obtenerInfoLugares(idioma, "miradordesantodomingo","peñadefrancia", 2);
                text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setText("");

                //Imagenes
                obtenerImagenFirebase("mapas/otros/penafrancia/miradorsantodomingo.png", img1);
                obtenerImagenFirebase("mapas/otros/penafrancia/miradorsantodomingo2.png", img2);

                btnExtra.setVisibility(View.GONE);
                break;

            case "lablanca":

                titulo.setText("Capilla de la Blanca");

                //Texto
                datos = dbHelper.obtenerInfoLugares(idioma, "capilladelablanca", "peñadefrancia", 3);
                text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

                //Imagenes
                obtenerImagenFirebase("mapas/otros/penafrancia/capilladelablanca1.png", img1);
                obtenerImagenFirebase("mapas/otros/penafrancia/capilladelablanca2.png", img2);

                btnExtra.setVisibility(View.GONE);
                break;

            case "sanandresexterior":

                titulo.setText("Capilla exterior de San Andrés");

                //Texto
                datos = dbHelper.obtenerInfoLugares(idioma, "capillaexteriordesanandrés","peñadefrancia", 2);
                text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setText("");

                //Imagenes
                obtenerImagenFirebase("mapas/otros/penafrancia/capillasanandres.png", img1);
                obtenerImagenFirebase("mapas/otros/penafrancia/capillasanandres2.png", img2);

                btnExtra.setVisibility(View.GONE);
                break;

            case "santocristoexterior":

                titulo.setText("Capilla exterior del Santo Cristo");

                //Texto
                datos = dbHelper.obtenerInfoLugares(idioma, "capillaexteriordelsantocristo", "peñadefrancia", 2);
                text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setText("");

                //Imagenes
                obtenerImagenFirebase("mapas/otros/penafrancia/capillasantocristo1.png", img1);
                obtenerImagenFirebase("mapas/otros/penafrancia/capillasantocristo2.png", img2);

                btnExtra.setVisibility(View.GONE);
                break;

            case "balconsantiago":

                titulo.setText("Balcón de Santiago");

                //Texto
                datos = dbHelper.obtenerInfoLugares(idioma, "balcóndesantiago","peñadefrancia", 3);
                text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

                //Imagenes
                obtenerImagenFirebase("mapas/otros/penafrancia/balconsantiago1.png", img1);
                obtenerImagenFirebase("mapas/otros/penafrancia/balconsantiago2.png", img2);

                btnExtra.setVisibility(View.VISIBLE);
                btnExtra.setText("  El Salto del Niño  ");
                btnExtra.setOnClickListener(v -> {
                    DialogFragment saltoFragment = new saltoDelNinoFragment();
                    saltoFragment.setArguments(args);
                    saltoFragment.setCancelable(false);
                    saltoFragment.show(getChildFragmentManager(),"salto_fragment");
                });
                break;

            case "pozoverde":

                titulo.setText("El Pozo Verde");

                //Texto
                datos = dbHelper.obtenerInfoLugares(idioma, "pozoverde","peñadefrancia", 2);
                text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setText("");

                //Imagenes
                obtenerImagenFirebase("mapas/otros/penafrancia/pozoverde1.png", img1);
                obtenerImagenFirebase("mapas/otros/penafrancia/pozoverde2.png", img2);

                btnExtra.setVisibility(View.GONE);
                break;

            case "navedcha":

                titulo.setText("Nave Derecha");

                String [] datos3 = dbHelper.obtenerInfoLugares(idioma, "navederecha","peñadefrancia", 3);
                if (idioma.equalsIgnoreCase("es")){
                    img1.setImageResource(R.drawable.planoiglesiadchaes);
                } else if (idioma.equalsIgnoreCase("en")){
                    img1.setImageResource(R.drawable.planoiglesiadchaen);
                } else{
                    img1.setImageResource(R.drawable.planoiglesiadchaeu);
                }

                //Texto
                text1.setText(datos3[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text2.setText(datos3[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setText(datos3[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

                //Imagenes
                obtenerImagenFirebase("mapas/otros/penafrancia/navederecha.png", img2);

                btnExtra.setVisibility(View.GONE);
                break;

            case "coropeña":

                titulo.setText("Coro de la Iglesia");

                String [] datos1 = dbHelper.obtenerInfoLugares(idioma, "coro","peñadefrancia", 1);

                if (idioma.equalsIgnoreCase("es")){
                    img1.setImageResource(R.drawable.planoiglesiacoroes);
                } else if (idioma.equalsIgnoreCase("en")){
                    img1.setImageResource(R.drawable.planoiglesiacoroen);
                } else{
                    img1.setImageResource(R.drawable.planoiglesiacoroeu);
                }

                //Texto
                text1.setText(datos1[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text2.setText("");
                text3.setText("");

                //Imagenes
                obtenerImagenFirebase("mapas/otros/penafrancia/coro.png", img2);

                btnExtra.setVisibility(View.GONE);
                break;

            case "altarmayor":

                titulo.setText("Altar Mayor");

                String [] datos4 = dbHelper.obtenerInfoLugares(idioma, "altarmayor","peñadefrancia", 2);

                if (idioma.equalsIgnoreCase("es")){
                    img1.setImageResource(R.drawable.planoiglesiacentroes);
                } else if (idioma.equalsIgnoreCase("en")){
                    img1.setImageResource(R.drawable.planoiglesiacentroen);
                } else{
                    img1.setImageResource(R.drawable.planoiglesiacentroeu);
                }

                //Texto
                text1.setText(datos4[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text2.setText(datos4[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setText("");

                //Imagenes
                obtenerImagenFirebase("mapas/otros/penafrancia/altarmayor.png", img2);

                btnExtra.setVisibility(View.GONE);
                break;
        }

    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }

}