package com.example.tfg.ajustesFragments.alojamiento;

import android.content.Context;

import com.example.tfg.GestorDB;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListaAlojamientos {

    private final LinkedList<Alojamiento> alojamientos;

    public ListaAlojamientos (Context context){
        GestorDB dbHelper = new GestorDB(context);
        alojamientos = dbHelper.obteneralojamientos();
    }

    public List<String> getListaHoteles(){

        List<String> lista = new ArrayList<>();
        for (int i = 0; i < alojamientos.size(); i++){
            if (alojamientos.get(i).getCatAloj().equalsIgnoreCase("hotel")){
                lista.add(alojamientos.get(i).getNombreAloj());
            }
        }
        return lista;
    }

    public List<String> getListaCasas(){

        List<String> lista = new ArrayList<>();
        for (int i = 0; i < alojamientos.size(); i++){
            if (alojamientos.get(i).getCatAloj().equalsIgnoreCase("casarural")){
                lista.add(alojamientos.get(i).getNombreAloj());
            }
        }
        return lista;
    }

    public List<String> getListaApartamentos(){

        List<String> lista = new ArrayList<>();
        for (int i = 0; i < alojamientos.size(); i++){
            if (alojamientos.get(i).getCatAloj().equalsIgnoreCase("apartamento")){
                lista.add(alojamientos.get(i).getNombreAloj());
            }
        }
        return lista;
    }

    public Alojamiento buscarAloj(String nombreAloj){

        for (int i = 0; i < alojamientos.size(); i++){
            Alojamiento est = alojamientos.get(i);
            if (est.getNombreAloj().equalsIgnoreCase(nombreAloj)){
                return est;
            }
        }
        return null;
    }

    public int obtenerNumeroAlojamientos(){
        return alojamientos.size();
    }

    public void imprimiralojamientos() {
        for (int i = 0; i < alojamientos.size(); i++){
            System.out.println("RECETA: " + alojamientos.get(i).getNombreAloj());
        }
    }
    
}
