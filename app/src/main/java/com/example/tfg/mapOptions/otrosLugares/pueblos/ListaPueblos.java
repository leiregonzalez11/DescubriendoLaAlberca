package com.example.tfg.mapOptions.otrosLugares.pueblos;

import android.content.Context;
import com.example.tfg.GestorDB;
import java.util.LinkedList;

public class ListaPueblos {

    private final LinkedList<Pueblo> pueblos;

    public ListaPueblos (Context context, String idioma){
        try (GestorDB dbHelper = GestorDB.getInstance(context.getApplicationContext())) {
            pueblos = dbHelper.obtenerPueblos(idioma);
        }
    }

    public Pueblo buscarPueblo(String nombrepueblo){

        for (int i = 0; i < pueblos.size(); i++){
            Pueblo pueblo = pueblos.get(i);
            if (pueblo.getNombrePueblo().equalsIgnoreCase(nombrepueblo)){
                return pueblo;
            }
        }
        return null;
    }

    public String [] listaNombres(){

        String [] nombres = new String[pueblos.size()];
        for (int i = 0; i < pueblos.size(); i++){
            nombres[i] = pueblos.get(i).getNombrePueblo();
        }
        return nombres;
    }

}
