package com.example.tfg.ajustes.administrador;

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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.tfg.R;
public class whatToDo_Admin extends DialogFragment implements View.OnClickListener {

    Button anadir, modificar, eliminar;
    String opcionElegida;
    Bundle args;
    Fragment fragment;

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View view = inflater.inflate(R.layout.fragment_whattodo__admin, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view);

        assert getArguments() != null;
        String origen = getArguments().getString("origen");

        args = new Bundle();
        args.putString("origen", origen);


        anadir = view.findViewById(R.id.btnadminanadir);
        modificar = view.findViewById(R.id.btnadminmodificar);
        eliminar = view.findViewById(R.id.btnadmineliminar);

        anadir.setOnClickListener(this);
        modificar.setOnClickListener(this);
        eliminar.setOnClickListener(this);

        if (origen.equals("servicios")){
            anadir.setVisibility(View.GONE);
            modificar.setVisibility(View.VISIBLE);
            eliminar.setVisibility(View.GONE);
        } else if (origen.equals("diccionario")){
            anadir.setVisibility(View.VISIBLE);
            eliminar.setVisibility(View.VISIBLE);
            modificar.setVisibility(View.GONE);
        } else {
            anadir.setVisibility(View.VISIBLE);
            eliminar.setVisibility(View.VISIBLE);
            modificar.setVisibility(View.VISIBLE);
        }

        return builder.create();
    }

    @Override
    @SuppressLint({"NonConstantResourceId", "ShowToast"})
    public void onClick(View v) {

        Button btn = (Button) v;

        switch (btn.getId()) {
            case R.id.btnadminanadir:
                opcionElegida = "add";
                args.putString("choose", opcionElegida);
                Toast.makeText(getContext(), "Has pulsado: AÑADIR", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnadminmodificar:
                opcionElegida = "modif";
                args.putString("choose", opcionElegida);
                fragment = elegirOpcionModDel.newInstance(args);
                cargarFragment(fragment);
                break;
            case R.id.btnadmineliminar:
                opcionElegida = "del";
                args.putString("choose", opcionElegida);
                fragment = elegirOpcionModDel.newInstance(args);
                cargarFragment(fragment);
                break;

        }
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