package com.example.tfg.Maps.otrosLugares.parquebatuecas;

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
import android.widget.Button;
import android.widget.Toast;

import com.example.tfg.Categorias.secundarias.artesania.trajesFemeninos;
import com.example.tfg.R;

public class batuecasElector extends DialogFragment implements View.OnClickListener {

    View batView;
    String lugar;
    private final Bundle args = new Bundle();

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        batView = inflater.inflate(R.layout.fragment_batuecas_elector, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(batView);

        assert getArguments() != null;
        String idioma = getArguments().getString("idioma");
        args.putString("idioma", idioma);

        setListeners(batView);

        Button volver = batView.findViewById(R.id.buttonvolverbatuecas);
        volver.setOnClickListener(view -> dismiss());

        return builder.create();
    }

    private void setListeners(View batView) {
        
        Button ermitas = batView.findViewById(R.id.btnermitas);
        Button casaparque = batView.findViewById(R.id.btncasaparque);
        Button chorro = batView.findViewById(R.id.btnchorro);
        Button pinturas = batView.findViewById(R.id.btnpinturas);
        Button monasterio = batView.findViewById(R.id.btnmonasterio);
        ermitas.setOnClickListener(this);
        casaparque.setOnClickListener(this);
        chorro.setOnClickListener(this);
        pinturas.setOnClickListener(this);
        monasterio.setOnClickListener(this);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        Button btn = (Button) view;
        DialogFragment fragment;

        switch (btn.getId()) {
            case R.id.btnermitas:
                lugar = "ermitas";
                break;
            case R.id.btncasaparque:
                lugar = "casaparque";
                break;
            case R.id.btnchorro:
                lugar = "chorro";
                Fragment f = elchorro.newInstance(args);
                cargarFragment(f);
                break;
            case R.id.btnmonasterio:
                lugar = "monasteriointro";
                break;
            case R.id.btnpinturas:
                lugar = "pinturas";
                break;
        }
        Toast.makeText(getContext(), "No disponible en este momento", Toast.LENGTH_LONG).show();
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