package com.example.tfg.Categorias.secundarias.cultura.diccionario;

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
import android.widget.TextView;
import com.example.tfg.R;

public class definicionpalabra extends DialogFragment {

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View infoView = inflater.inflate(R.layout.fragment_definicionpalabra, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(infoView);

        assert getArguments()!=null;
        String palabraElegida = getArguments().getString("palabra");
        String idioma = getArguments().getString("idioma");
        Palabra palabra = getArguments().getParcelable("palabra");

        TextView palabratitulo = infoView.findViewById(R.id.palabratitulo);
        TextView definicion = infoView.findViewById(R.id.definicionpalabra);

        palabratitulo.setText(palabra.getNombrePalabra());
        definicion.setText(palabra.getDefinicionpalabra() + HtmlCompat.fromHtml("<br>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        Button volver = infoView.findViewById(R.id.buttonVolverDef);
        volver.setOnClickListener(view -> dismiss());

        return builder.create();
    }


}