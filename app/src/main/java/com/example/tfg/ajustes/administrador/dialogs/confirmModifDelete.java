package com.example.tfg.ajustes.administrador.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.telecom.TelecomManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfg.R;
import com.example.tfg.ajustes.administrador.administrador_inicio;

public class confirmModifDelete extends DialogFragment {

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View view = inflater.inflate(R.layout.fragment_confirmdelete, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view);

        assert getArguments()!=null;
        String opcionElegida = getArguments().getString("choose");

        TextView titulo = view.findViewById(R.id.tituloConfirm);

        if (opcionElegida.equals("modif")){
            titulo.setText(getResources().getString(R.string.tituloconfirmmod));
        } else{
            titulo.setText(getResources().getString(R.string.tituloconfirmdel));
        }

        Button si = view.findViewById(R.id.buttonSiMD);
        Button no = view.findViewById(R.id.buttonNoMD);

        si.setOnClickListener(v -> {
            if (opcionElegida.equals("modif")){
                Toast.makeText(getContext(), getResources().getString(R.string.modifok), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), getResources().getString(R.string.deleteok), Toast.LENGTH_SHORT).show();
            }
            Fragment fragment = administrador_inicio.newInstance();
            cargarFragment(fragment);
        });

        no.setOnClickListener(v -> dismiss());

        return builder.create();
    }

    private void cargarFragment(Fragment fragment){
        // Obtenemos el administrador de fragmentos a través de la actividad
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        // Definimos una transacción
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // Remplazamos el contenido principal por el fragmento
        fragmentTransaction.replace(R.id.relativelayout, fragment);
        fragmentTransaction.addToBackStack(null);
        // Cambiamos el fragment en la interfaz
        fragmentTransaction.commit();
    }
}