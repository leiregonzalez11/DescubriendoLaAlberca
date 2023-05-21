package com.example.tfg.Maps.otrosLugares.penaFrancia;

import static android.service.controls.ControlsProviderService.TAG;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.tfg.EspacioDelViajero.alojamiento.alojamientoFragment;
import com.example.tfg.OtherFiles.Adapters.listViewAdapter;
import com.example.tfg.OtherFiles.DialogFragments.ordenarFragment;
import com.example.tfg.R;
import com.example.tfg.EspacioDelViajero.alojamiento.Alojamiento;
import com.example.tfg.EspacioDelViajero.alojamiento.ListaAlojamientos;
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

public class hospederiaFragment extends DialogFragment {

    private StorageReference storageRef;
    private Alojamiento alojamiento;

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View infoView = inflater.inflate(R.layout.fragmentdialog_hospederia, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(infoView);

        assert getArguments() != null;
        //Instancia a la base de datos
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        //apuntamos al nodo que queremos leer
        DatabaseReference myRef = fdb.getReference().child("alojamiento").child("hospederia");

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
                    HashMap dataMap = (HashMap) dataSnapshot.getValue();
                    assert dataMap != null;
                    for (Object key: dataMap.keySet()){
                        Object datos = dataMap.get(key);
                        HashMap userData = (HashMap) datos;
                        assert userData != null;
                        alojamiento = new Alojamiento((String) key, (String) userData.get("ubicacion"),
                                (String) userData.get("telefono"), (Double) userData.get("puntuación"));
                    }

                    TextView text1 = infoView.findViewById(R.id.nombreHosp);
                    TextView tel = infoView.findViewById(R.id.telHosp2);
                    TextView ubi = infoView.findViewById(R.id.ubiHosp2);
                    RatingBar ratingBar = infoView.findViewById(R.id.ratingBarHosp);

                    //Titulo
                    assert alojamiento != null;
                    text1.setText(alojamiento.getNombreAloj());

                    //Datos informativos y ubicación
                    ratingBar.setRating((float) alojamiento.getPuntAloj());

                    String telefono = alojamiento.getTelAloj();

                    SpannableString websubrayado = new SpannableString(alojamiento.getLocationAloj());
                    websubrayado.setSpan(new UnderlineSpan(), 0, websubrayado.length(), 0);

                    ubi.setText(websubrayado);
                    ubi.setOnClickListener(v -> {
                        Uri uri = Uri.parse("https://" + alojamiento.getLocationAloj()); // Creamos una uri con el numero de telefono
                        Intent dial = new Intent(Intent.ACTION_VIEW, uri); // Creamos una llamada al Intent de llamadas
                        startActivity(dial); // Ejecutamos el Intent
                    });

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
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        ImageView img = infoView.findViewById(R.id.imgHosp);

        //Imagen
        storageRef = FirebaseStorage.getInstance().getReference();
        obtenerImagenFirebase(img);

        Button back = infoView.findViewById(R.id.buttonVolverHosp);
        back.setOnClickListener(view -> dismiss());

        return builder.create();
    }

    /** Método utilizado para obtener la imagen de Firebase Storage */
    private void obtenerImagenFirebase(ImageView img){
        StorageReference pathReference = storageRef.child("mapas/otros/penafrancia/hospederia.png");
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(requireContext()).load(uri).into(img));
    }
}