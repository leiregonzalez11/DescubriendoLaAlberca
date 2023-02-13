package com.example.tfg.mapsFragments.otrosLugares.pueblos;

import android.content.Context;
import com.example.tfg.GestorDB;
import java.util.LinkedList;

public class ListaPueblos {

    private final LinkedList<Pueblo> pueblos;

    public ListaPueblos (Context context, String idioma){
        GestorDB dbHelper = new GestorDB(context);
       pueblos = dbHelper.obtenerPueblos(idioma);
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

    public int obtenerNumeroPueblos(){
        return pueblos.size();
    }

    public void imprimirPueblos() {
        for (int i = 0; i < pueblos.size(); i++){
            Pueblo pueblo = pueblos.get(i);
            System.out.println("PUEBLO: " + pueblo.getNombrePueblo());
        }
    }
}
