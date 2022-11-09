package com.example.tfg.dialogFragments;

import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.tfg.MainActivity;
import com.example.tfg.R;


public class ExitFragment extends DialogFragment{

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        Intent inicio = new Intent(getActivity(), MainActivity.class);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View exitView = inflater.inflate(R.layout.fragment_exit, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(exitView);

        Button si = exitView.findViewById(R.id.buttonSi);
        Button no = exitView.findViewById(R.id.buttonNo);

        si.setOnClickListener(view -> {
            int pId = android.os.Process.myPid();
            android.os.Process.killProcess(pId);
        });

        no.setOnClickListener(view -> dismiss());

        return builder.create();
    }

}