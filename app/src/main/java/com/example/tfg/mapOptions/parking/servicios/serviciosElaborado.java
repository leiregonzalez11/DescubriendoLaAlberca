package com.example.tfg.mapOptions.parking.servicios;

import static android.service.controls.ControlsProviderService.TAG;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tfg.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class serviciosElaborado extends DialogFragment {

    private StorageReference storageRef;
    private Servicio servicio;

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View infoView = inflater.inflate(R.layout.fragment_servicios_elaborado, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(infoView);

        assert getArguments() != null;
        String titulo = getArguments().getString("titulo");
        String catServicio = getArguments().getString("catServicio");
        String serv = getArguments().getString("servicio");

        //Instancia a la base de datos
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        //apuntamos al nodo que queremos leer
        DatabaseReference myRef = fdb.getReference().child("servicios").child(catServicio);

        //Agregamos un ValueEventListener para que los cambios que se hagan en la base de datos
        //se reflejen en la aplicacion
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {

            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()){
                    LinkedList<Servicio> ps = new LinkedList<>();
                    HashMap dataMap = (HashMap) dataSnapshot.getValue();
                    assert dataMap != null;
                    for (Object key: dataMap.keySet()){
                        ps.add(crearServicioElaborado(key, dataMap));
                    }

                    TextView text1 = infoView.findViewById(R.id.nombreServE);
                    TextView price = infoView.findViewById(R.id.priceServE2);
                    TextView horario = infoView.findViewById(R.id.clockServE2);
                    TextView ubi = infoView.findViewById(R.id.ubiServE2);

                    servicio = buscarServ(serv, ps);

                    //Titulo
                    assert servicio != null;
                    text1.setText(titulo);

                    ubi.setText(servicio.getLocationServ());
                    price.setText(servicio.getPrecioServ().replaceAll("--", "\n"));
                    horario.setText(servicio.getHorarioServ().replaceAll("--", "\n"));

                    ImageView img = infoView.findViewById(R.id.imgServE);

                    //Imagen
                    storageRef = FirebaseStorage.getInstance().getReference();
                    obtenerImagenFirebase(img);

                }
            }

            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        Button back = infoView.findViewById(R.id.buttonVolverServE);
        back.setOnClickListener(view -> dismiss());

        return builder.create();
    }

    private Servicio crearServicioElaborado(Object key, HashMap dataMap) {
        Object datos = dataMap.get(key);
        HashMap userData = (HashMap) datos;
        assert userData != null;
        servicio = new Servicio((String) key, (String) userData.get("ubicacion"),
                (String) userData.get("horario"), (String) userData.get("precio"));
        return servicio;
    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(ImageView img){
        StorageReference pathReference = storageRef.child("mapas/servicios/" + servicio.getNombreServ() + ".png");
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }

    public Servicio buscarServ(String nombre, List<Servicio> serv){
        for (int i = 0; i <serv.size(); i++){
            Servicio estbl = serv.get(i);
            if (estbl.getNombreServ().equalsIgnoreCase(nombre)){
                return estbl;
            }
        }
        return null;
    }

}