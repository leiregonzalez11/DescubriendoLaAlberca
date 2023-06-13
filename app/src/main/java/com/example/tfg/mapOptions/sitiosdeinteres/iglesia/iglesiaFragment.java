package com.example.tfg.mapOptions.sitiosdeinteres.iglesia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.DialogFragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.tfg.mapOptions.sitiosdeinteres.monumentos1;
import com.example.tfg.mapOptions.sitiosdeinteres.monumentos4;
import com.example.tfg.GestorDB;
import com.example.tfg.R;
import com.example.tfg.mapOptions.sitiosdeinteres.info.infomonu3Fragment;
import com.example.tfg.mapOptions.sitiosdeinteres.monumentos2;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class iglesiaFragment extends DialogFragment implements View.OnClickListener {

    private final Bundle args = new Bundle();
    Animation slideAnimation;
    String idioma;
    View iglesiaView;
    TextView text1, text2, text3, text4, pruebatexto;
    ImageButton ant, sig;
    ImageView img1, img2, img3;
    private StorageReference storageRef;


    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        iglesiaView = inflater.inflate(R.layout.fragmentdialog_iglesia, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(iglesiaView);

        assert getArguments()!=null;
        idioma = getArguments().getString("idioma");
        args.putString("idioma", idioma);

        setListenersBtnMapa();
        setTextAndPhotos();

        Button volver = iglesiaView.findViewById(R.id.buttonVolverIglesia);
        volver.setOnClickListener(view -> zoomOut());

        return builder.create();
    }

    @SuppressLint("SetTextI18n")
    private void setTextAndPhotos() {

        text1 = iglesiaView.findViewById(R.id.iglesia1text);
        text2 = iglesiaView.findViewById(R.id.iglesia2text);
        text3 = iglesiaView.findViewById(R.id.iglesia3text);
        text4 = iglesiaView.findViewById(R.id.iglesia4text);
        pruebatexto = iglesiaView.findViewById(R.id.pruebatextoi);

        img1 = iglesiaView.findViewById(R.id.iglesiafoto1);
        img2 = iglesiaView.findViewById(R.id.iglesiafoto2);
        img3 = iglesiaView.findViewById(R.id.iglesiafoto3);

        ant = iglesiaView.findViewById(R.id.ibtn);
        sig = iglesiaView.findViewById(R.id.ibtn2);

        try (GestorDB dbHelper = new GestorDB(getContext())) {
            storageRef = FirebaseStorage.getInstance().getReference();

            if (pruebatexto.getText().toString().equalsIgnoreCase("1")) {

                ant.setVisibility(View.GONE);
                sig.setVisibility(View.VISIBLE);

                String[] datos = dbHelper.obtenerInfoMonumentos(idioma, "iglesia-cat1", 4);

                text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                text4.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                obtenerImagenFirebase("mapas/monumentos/iglesia1.png", img1);
                obtenerImagenFirebase("mapas/monumentos/iglesia2.png", img2);
                obtenerImagenFirebase("mapas/monumentos/iglesia3.png", img3);
            }


            ant.setOnClickListener(view1 -> {
                pruebatexto.clearFocus();
                if (pruebatexto.getText().toString().equalsIgnoreCase("2")) {

                    ant.setVisibility(View.GONE);
                    sig.setVisibility(View.VISIBLE);

                    pruebatexto.setText("1");

                    String[] datos = dbHelper.obtenerInfoMonumentos(idioma, "iglesia-cat1", 4);

                    text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text4.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    pruebatexto.requestFocus();

                    obtenerImagenFirebase("mapas/monumentos/iglesia1.png", img1);
                    obtenerImagenFirebase("mapas/monumentos/iglesia2.png", img2);
                    obtenerImagenFirebase("mapas/monumentos/iglesia3.png", img3);

                }
            });

            sig.setOnClickListener(view1 -> {
                pruebatexto.clearFocus();
                if (pruebatexto.getText().toString().equalsIgnoreCase("1")) {

                    ant.setVisibility(View.VISIBLE);
                    sig.setVisibility(View.GONE);

                    pruebatexto.setText("2");

                    String[] datos = dbHelper.obtenerInfoMonumentos(idioma, "iglesia-cat2", 4);

                    text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    text4.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    pruebatexto.requestFocus();

                    obtenerImagenFirebase("mapas/monumentos/iglesia4.png", img1);
                    obtenerImagenFirebase("mapas/monumentos/iglesia5.png", img2);
                    obtenerImagenFirebase("mapas/monumentos/iglesia6.png", img3);

                }

            });
        }


    }

    private void setListenersBtnMapa() {

        ImageButton info = iglesiaView.findViewById(R.id.moninfo3);
        ImageButton torre = iglesiaView.findViewById(R.id.torre);
        ImageButton carmen = iglesiaView.findViewById(R.id.retablovirgencarmen);
        ImageButton pedro = iglesiaView.findViewById(R.id.retablosanpedro);
        ImageButton sudor = iglesiaView.findViewById(R.id.retablocristosudor);
        ImageButton rosario = iglesiaView.findViewById(R.id.virgenrosario);
        ImageButton capillacentral = iglesiaView.findViewById(R.id.capillacentral);
        ImageButton salida1 = iglesiaView.findViewById(R.id.portico1);
        ImageButton salida2 = iglesiaView.findViewById(R.id.portico2);
        ImageButton pilab = iglesiaView.findViewById(R.id.pilabautismal);
        ImageButton pila1 = iglesiaView.findViewById(R.id.pilaagua1);
        ImageButton pila2 = iglesiaView.findViewById(R.id.pilaagua2);
        ImageButton espadana = iglesiaView.findViewById(R.id.espadana);
        ImageButton sacristia = iglesiaView.findViewById(R.id.sacristia);
        ImageButton dolores = iglesiaView.findViewById(R.id.capilladolores);
        ImageButton pulpito = iglesiaView.findViewById(R.id.pulpito);
        ImageButton ana = iglesiaView.findViewById(R.id.retablosantaana);
        ImageButton santoc = iglesiaView.findViewById(R.id.santocristo);
        info.setOnClickListener(this);
        torre.setOnClickListener(this);
        carmen.setOnClickListener(this);
        pedro.setOnClickListener(this);
        sudor.setOnClickListener(this);
        rosario.setOnClickListener(this);
        capillacentral.setOnClickListener(this);
        salida1.setOnClickListener(this);
        salida2.setOnClickListener(this);
        pilab.setOnClickListener(this);
        pila1.setOnClickListener(this);
        pila2.setOnClickListener(this);
        espadana.setOnClickListener(this);
        sacristia.setOnClickListener(this);
        dolores.setOnClickListener(this);
        pulpito.setOnClickListener(this);
        ana.setOnClickListener(this);
        santoc.setOnClickListener(this);

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        ImageButton btn = (ImageButton) view;
        DialogFragment fragment;

        switch (btn.getId()) {

            case R.id.moninfo3:
                fragment = new infomonu3Fragment();
                fragment.setCancelable(false);
                fragment.show(getChildFragmentManager(),"iglesia_fragment");
                break;
            case R.id.santocristo:
                String monumento = "retablocristobatallas";
                args.putString("monumento", monumento);
                args.putString("titulo", "Retablo del Santo Cristo");
                fragment = new monumentos2();
                zoomIn(fragment, btn);
                break;
            case R.id.retablosanpedro:
                monumento = "retablosanpedro";
                args.putString("monumento", monumento);
                args.putString("titulo", "Retablo de San Pedro");
                fragment = new monumentos2();
                zoomIn(fragment, btn);
                break;
            case R.id.retablosantaana:
                monumento = "retablosantaana";
                args.putString("monumento", monumento);
                args.putString("titulo", "Retablo de Santa Ana");
                fragment = new monumentos2();
                zoomIn(fragment, btn);
                break;
            case R.id.pulpito:
                monumento = "pulpito";
                args.putString("monumento", monumento);
                args.putString("titulo", "Púlpito");
                fragment = new pulpito();
                zoomIn(fragment, btn);
                break;
            case R.id.pilaagua1:
            case R.id.pilaagua2:
                monumento = "pilasaguabendita";
                args.putString("monumento", monumento);
                args.putString("titulo", "Pilas de agua bendita");
                fragment = new monumentos1();
                zoomIn(fragment, btn);
                break;
            case R.id.pilabautismal:
                monumento = "pilabautismal";
                args.putString("monumento", monumento);
                args.putString("titulo", "Pila Bautismal");
                fragment = new monumentos1();
                zoomIn(fragment, btn);
                break;
            case R.id.retablocristosudor:
                monumento = "retablocristosudor";
                args.putString("monumento", monumento);
                args.putString("titulo", "Retablo del Cristo del Sudor");
                break;
            case R.id.capillacentral:
                monumento = "capillamayor";
                args.putString("monumento", monumento);
                args.putString("titulo", "Capilla Mayor");
                fragment = new monumentos4();
                zoomIn(fragment, btn);
                break;
            case R.id.espadana:
                monumento = "espadaña";
                args.putString("monumento", monumento);
                args.putString("titulo", "Espadaña");
                fragment = new monumentos1();
                zoomIn(fragment, btn);
                break;
            case R.id.portico1:
            case R.id.portico2:
                monumento = "porticos";
                args.putString("monumento", monumento);
                args.putString("titulo", "Pórticos de la iglesia");
                break;
            case R.id.capilladolores:
                monumento = "dolores";
                args.putString("monumento", monumento);
                args.putString("titulo", "Capilla de la Virgen de los Dolores");
                break;
            case R.id.torre:
                monumento = "torre";
                args.putString("monumento", monumento);
                args.putString("titulo", "Torre de la Iglesia");
                fragment = new torre();
                zoomIn(fragment, btn);
                break;
            case R.id.sacristia:
                monumento = "sacristia";
                args.putString("monumento", monumento);
                args.putString("titulo", "Sacristía");
                fragment = new monumentos1();
                zoomIn(fragment, btn);
                break;
            case R.id.retablovirgencarmen:
                monumento = "retablovirgendelcarmen";
                args.putString("monumento", monumento);
                args.putString("titulo", "Retablo de la Virgen del Carmen");
                fragment = new monumentos2();
                zoomIn(fragment, btn);
                break;
            case R.id.virgenrosario:
                monumento = "retablorosario";
                args.putString("monumento", monumento);
                args.putString("titulo", "Retablo de la Virgen del Rosario");
                fragment = new monumentos2();
                zoomIn(fragment, btn);
                break;
        }

    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }

    public void zoomIn (DialogFragment fragment, View view){
        slideAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_in2);
        view.startAnimation(slideAnimation);

        new Handler().postDelayed(() -> {
            fragment.setArguments(args);
            fragment.setCancelable(false);
            fragment.show(getChildFragmentManager(),"fragment");
        },900);
    }

    public void zoomOut (){
        slideAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_out);
        iglesiaView.startAnimation(slideAnimation);

        new Handler().postDelayed(this::dismiss,900);
    }

}