package com.example.tfg.otherFiles.dialogFragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.tfg.R;
public class whatToDo_Admin extends DialogFragment implements View.OnClickListener {

    Button anadir, modificar, eliminar;

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
                Toast.makeText(getContext(), "Has pulsado: AÑADIR", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnadminmodificar:
                Toast.makeText(getContext(), "Has pulsado: MODIFICAR", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnadmineliminar:
                Toast.makeText(getContext(), "Has pulsado: ELIMINAR", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}