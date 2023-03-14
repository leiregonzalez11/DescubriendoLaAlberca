package com.example.tfg.dialogFragments;

import androidx.fragment.app.DialogFragment;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import com.example.tfg.R;


public class ordenarFragment extends DialogFragment{

    RadioButton atoz, ztoa, asc, desc;

    /** Creamos la interfaz que utilizaremos para pasar datos a la interfaz */
    public interface ordenarDialogListener {
        void onClickRadioButton(String ordenar);
    }

    // Se crea el objeto de la interfaz
    private ordenarDialogListener listener;
    String ordenar;

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View view = inflater.inflate(R.layout.fragment_ordenar, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view);

        assert getArguments() != null;
        String orden = getArguments().getString("ordenar");
        String origen = getArguments().getString("origen");

        atoz = view.findViewById(R.id.radio_atoz);
        ztoa = view.findViewById(R.id.radio_ztoa);
        asc = view.findViewById(R.id.radio_asc);
        desc = view.findViewById(R.id.radio_desc);

        if (origen.equalsIgnoreCase("alojamiento") ||
                origen.equalsIgnoreCase("establecimiento")){
            asc.setVisibility(View.VISIBLE);
            desc.setVisibility(View.VISIBLE);
        } else{
            asc.setVisibility(View.GONE);
            desc.setVisibility(View.GONE);
        }

        setRadioButton(orden);

        //Cada vez que se pulse un radio button la interfaz se ejecutará
        atoz.setOnClickListener(v -> {
            ordenar = "atoz";
            atoz.setChecked(true);
            if (listener != null) {
                listener.onClickRadioButton(ordenar);
            }
            dismiss();
        });

        ztoa.setOnClickListener(v -> {
            ordenar = "ztoa";
            ztoa.setChecked(true);
            if (listener != null) {
                listener.onClickRadioButton(ordenar);
            }
            dismiss();
        });

        asc.setOnClickListener(v -> {
            ordenar = "asc";
            asc.setChecked(true);
            if (listener != null) {
                listener.onClickRadioButton(ordenar);
            }
            dismiss();
        });

        desc.setOnClickListener(v -> {
            ordenar = "desc";
            desc.setChecked(true);
            if (listener != null) {
                listener.onClickRadioButton(ordenar);
            }
            dismiss();
        });

        return builder.create();
    }

    private void setRadioButton(String orden) {

        if (orden.equalsIgnoreCase("atoz")){
            atoz.setChecked(true);
        } else if (orden.equalsIgnoreCase("ztoa")){
            ztoa.setChecked(true);
        } else if (orden.equalsIgnoreCase("asc")){
            asc.setChecked(true);
        } else{
            desc.setChecked(true);
        }
    }

    // A través de este método se implementa la interfaz en el fragmento,
    // teniendo en cuenta que al implementar la interfaz ésta se inicializa.
    public void setOnClickButtonListener(ordenarDialogListener listener) {
        this.listener = listener;
    }

}