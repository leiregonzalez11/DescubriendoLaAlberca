package com.example.tfg.mapsFragments.sitiosdeinteres.iglesia;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.tfg.R;
import com.example.tfg.mapsFragments.sitiosdeinteres.iglesia.info.infomonu3Fragment;

public class iglesiaFragment extends DialogFragment implements View.OnClickListener {

    private final Bundle args = new Bundle();
    Animation slideAnimation;
    View plazaView;


    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        plazaView = inflater.inflate(R.layout.fragmentdialog_iglesia, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(plazaView);

        assert getArguments()!=null;
        String idioma = getArguments().getString("idioma");
        args.putString("idioma", idioma);

        setListeners(plazaView);

        ImageButton info = plazaView.findViewById(R.id.moninfo3);
        info.setOnClickListener(view -> {
            DialogFragment fragment = new infomonu3Fragment();
            fragment.setCancelable(false);
            fragment.show(getChildFragmentManager(),"plaza_fragment");
        });

        Button volver = plazaView.findViewById(R.id.buttonVolverIglesia);
        volver.setOnClickListener(view -> zoomOut());

        return builder.create();
    }

    private void setListeners(View plazaView) {

        ImageButton torre = plazaView.findViewById(R.id.torre);
        ImageButton carmen = plazaView.findViewById(R.id.retablovirgencarmen);
        ImageButton pedro = plazaView.findViewById(R.id.retablosanpedro);
        ImageButton sudor = plazaView.findViewById(R.id.retablocristosudor);
        ImageButton rosario = plazaView.findViewById(R.id.virgenrosario);
        ImageButton capillacentral = plazaView.findViewById(R.id.capillacentral);
        ImageButton salida1 = plazaView.findViewById(R.id.portico1);
        ImageButton salida2 = plazaView.findViewById(R.id.portico2);
        ImageButton pilab = plazaView.findViewById(R.id.pilabautismal);
        ImageButton pila1 = plazaView.findViewById(R.id.pilaagua1);
        ImageButton pila2 = plazaView.findViewById(R.id.pilaagua2);
        ImageButton espadana = plazaView.findViewById(R.id.espadana);
        ImageButton sacristia = plazaView.findViewById(R.id.sacristia);
        ImageButton dolores = plazaView.findViewById(R.id.capilladolores);
        ImageButton pulpito = plazaView.findViewById(R.id.pulpito);
        ImageButton ana = plazaView.findViewById(R.id.retablosantaana);
        ImageButton santoc = plazaView.findViewById(R.id.santocristo);
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

            case R.id.santocristo:
                String monumento = "retablocristobatallas";
                args.putString("retablo", monumento);
                args.putString("titulo", "Retablo del Santo Cristo");
                fragment = new retablosFragment();
                zoomIn(fragment, btn);
                break;
            case R.id.retablosanpedro:
                monumento = "retablosanpedro";
                args.putString("retablo", monumento);
                args.putString("titulo", "Retablo de San Pedro");
                fragment = new retablosFragment();
                zoomIn(fragment, btn);
                break;
            case R.id.retablosantaana:
                monumento = "retablosantaana";
                args.putString("retablo", monumento);
                args.putString("titulo", "Retablo de Santa Ana");
                fragment = new retablosFragment();
                zoomIn(fragment, btn);
                break;
            case R.id.pulpito:
                monumento = "pulpito";
                args.putString("monumento", monumento);
                args.putString("titulo", "Púlpito");
                break;
            case R.id.pilaagua1:
            case R.id.pilaagua2:
                monumento = "pilasaguabendita";
                args.putString("monumento", monumento);
                args.putString("titulo", "Pilas de agua bendita");
                fragment = new iglesiaMon1();
                zoomIn(fragment, btn);
                break;
            case R.id.pilabautismal:
                monumento = "pilabautismal";
                args.putString("monumento", monumento);
                args.putString("titulo", "Pila Bautismal");
                fragment = new iglesiaMon1();
                zoomIn(fragment, btn);
                break;
            case R.id.retablocristosudor:
                monumento = "retablocristosudor";
                args.putString("retablo", monumento);
                args.putString("titulo", "Retablo del Cristo del Sudor");
                //fragment = new retablosFragment();
                //zoomIn(fragment, btn);
                break;
            case R.id.capillacentral:
                monumento = "capillacentral";
                break;
            case R.id.espadana:
                monumento = "espadaña";
                args.putString("monumento", monumento);
                args.putString("titulo", "Espadaña");
                fragment = new iglesiaMon1();
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
                break;
            case R.id.torre:
                monumento = "torre";
                args.putString("monumento", monumento);
                args.putString("titulo", "Torre de la iglesia");
                break;
            case R.id.sacristia:
                monumento = "sacristia";
                args.putString("monumento", monumento);
                args.putString("titulo", "Sacristía");
                fragment = new iglesiaMon1();
                zoomIn(fragment, btn);
                break;
            case R.id.retablovirgencarmen:
                monumento = "retablovirgendelcarmen";
                args.putString("retablo", monumento);
                args.putString("titulo", "Retablo de la Virgen del Carmen");
                fragment = new retablosFragment();
                zoomIn(fragment, btn);
                break;
            case R.id.virgenrosario:
                monumento = "retablorosario";
                args.putString("retablo", monumento);
                args.putString("titulo", "Retablo de la Virgen del Rosario");
                fragment = new retablosFragment();
                zoomIn(fragment, btn);
                break;
        }

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
        plazaView.startAnimation(slideAnimation);

        new Handler().postDelayed(this::dismiss,900);
    }
}