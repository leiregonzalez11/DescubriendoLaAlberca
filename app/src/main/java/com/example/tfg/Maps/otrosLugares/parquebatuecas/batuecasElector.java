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

        Button casaparque = batView.findViewById(R.id.btncasaparque);
        Button chorro = batView.findViewById(R.id.btnchorro);
        Button pinturas = batView.findViewById(R.id.btnpinturas);
        Button monasterio = batView.findViewById(R.id.btnmonasterio);
        casaparque.setOnClickListener(this);
        chorro.setOnClickListener(this);
        pinturas.setOnClickListener(this);
        monasterio.setOnClickListener(this);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        Button btn = (Button) view;

        switch (btn.getId()) {
            case R.id.btncasaparque:
                lugar = "casaparque";
                DialogFragment f1 = new casaParqueymirador();
                args.putString("button", "casaparque");
                f1.setArguments(args);
                f1.setCancelable(false);
                f1.show(getChildFragmentManager(),"casaparque");
                break;
            case R.id.btnchorro:
                Fragment f2 = elchorro.newInstance(args);
                cargarFragment(f2);
                break;
            case R.id.btnmonasterio:
                Fragment f3 = monasterioBatuecas.newInstance(args);
                cargarFragment(f3);
                break;
            case R.id.btnpinturas:
                DialogFragment f4 = new ermitaspinturasmonasterio();
                args.putString("button", "pinturas");
                f4.setArguments(args);
                f4.setCancelable(false);
                f4.show(getChildFragmentManager(),"batuecasermitasypinturas");
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