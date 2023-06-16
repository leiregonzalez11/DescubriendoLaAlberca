package com.example.tfg.mapOptions.otrosLugares.penaFrancia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
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

public class conventoFragment extends DialogFragment {

    private Button btnExtra;
    private GestorDB dbHelper;
    private ImageView img1, img2;
    private String idioma;
    private final Bundle args = new Bundle();
    private StorageReference storageRef;
    private TextView text1, text2, text3, text4, text5;

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View infoView = inflater.inflate(R.layout.fragment_convento, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(infoView);

        dbHelper = GestorDB.getInstance(requireContext());
        storageRef = FirebaseStorage.getInstance().getReference();

        assert getArguments() != null;
        idioma = getArguments().getString("idioma");

        args.putString("idioma", idioma);

        TextView titulo = infoView.findViewById(R.id.tituloconvento);
        text1 = infoView.findViewById(R.id.santuariotext1);
        text2 = infoView.findViewById(R.id.santuariotext2);
        text3 = infoView.findViewById(R.id.santuariotext3);
        text4 = infoView.findViewById(R.id.santuariotext4);
        text5 = infoView.findViewById(R.id.santuariotext5);
        img1 = infoView.findViewById(R.id.imageconv1);
        img2 = infoView.findViewById(R.id.imageconv2);
        btnExtra = infoView.findViewById(R.id.buttoncasabaja);

        titulo.setText("El Convento");

        Button volver = infoView.findViewById(R.id.buttonVolverConv);
        volver.setOnClickListener(view -> dismiss());

        setText();

        return builder.create();
    }

    @SuppressLint("SetTextI18n")
    private void setText() {

        /* Texto */
        String [] datos = dbHelper.obtenerInfoLugares(idioma, "convento", "peñadefrancia", 5);
        text1.setText(datos[0] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text2.setText(datos[1] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text3.setText(datos[2] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text4.setText(datos[3] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        text5.setText(datos[4] + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        /* Imágenes */
        obtenerImagenFirebase("mapas/otros/penafrancia/convento1.png", img1);
        obtenerImagenFirebase("mapas/otros/penafrancia/convento2.png", img2);

        /* Botón La Casa Baja */
        btnExtra.setVisibility(View.VISIBLE);
        btnExtra.setText("   La Casa Baja   ");
        btnExtra.setOnClickListener(view1 -> {
            DialogFragment casaFragment = new lacasabajaFragment();
            casaFragment.setArguments(args);
            casaFragment.setCancelable(false);
            casaFragment.show(getChildFragmentManager(),"casa_fragment");
        });

    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(String path, ImageView img){
        StorageReference pathReference = storageRef.child(path);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }
}