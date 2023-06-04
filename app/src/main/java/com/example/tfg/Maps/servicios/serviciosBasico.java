package com.example.tfg.Maps.servicios;

import static android.service.controls.ControlsProviderService.TAG;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.tfg.EspacioDelViajero.restauracion.Establecimiento;
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

public class serviciosBasico extends DialogFragment {

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

        final View infoView = inflater.inflate(R.layout.fragment_servicios_basico, null);
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
                        Object datos = dataMap.get(key);
                        HashMap userData = (HashMap) datos;
                        assert userData != null;
                        servicio = new Servicio((String) key, (String) userData.get("ubicacion"),
                                (String) userData.get("telefono"));
                        ps.add(servicio);
                    }

                    TextView text1 = infoView.findViewById(R.id.nombreServ);
                    TextView tel = infoView.findViewById(R.id.telServ2);
                    TextView ubi = infoView.findViewById(R.id.ubiServ2);

                    servicio = buscarServ(serv, ps);

                    //Titulo
                    assert servicio != null;
                    text1.setText(titulo);

                    String telefono = servicio.getTelServ();

                    ubi.setText(servicio.getLocationServ());

                    if (!telefono.equals("No Disponible")) {
                        SpannableString telsubrayado = new SpannableString(telefono);
                        telsubrayado.setSpan(new UnderlineSpan(), 0, telsubrayado.length(), 0);
                        tel.setText(telsubrayado);
                        tel.setOnClickListener(v -> {
                            Uri number = Uri.parse("tel:" + tel); // Creamos una uri con el número de telefono
                            Intent dial = new Intent(Intent.ACTION_DIAL, number); // Creamos una llamada al Intent de llamadas
                            startActivity(dial); // Ejecutamos el Intent
                        });
                    } else{
                        tel.setText(telefono);
                    }

                    ImageView img = infoView.findViewById(R.id.imgServ);

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

        Button back = infoView.findViewById(R.id.buttonVolverServ);
        back.setOnClickListener(view -> dismiss());

        return builder.create();
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