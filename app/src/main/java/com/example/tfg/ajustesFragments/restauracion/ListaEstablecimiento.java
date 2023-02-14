package com.example.tfg.ajustesFragments.restauracion;

import android.content.Context;

import com.example.tfg.GestorDB;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListaEstablecimiento {

    private final LinkedList<Establecimiento> establecimientos;

    public ListaEstablecimiento (Context context){
        GestorDB dbHelper = new GestorDB(context);
        establecimientos = dbHelper.obtenerestablecimientos();
    }

    public List<String> getListaBares(){

        List<String> lista = new ArrayList<>();
        for (int i = 0; i < establecimientos.size(); i++){
            if (establecimientos.get(i).getCatEstabl().equalsIgnoreCase("bar")){
                lista.add(establecimientos.get(i).getNombreEstabl());
            }
        }
        return lista;
    }

    public List<String> getListaRest(){

        List<String> lista = new ArrayList<>();
        for (int i = 0; i < establecimientos.size(); i++){
            if (establecimientos.get(i).getCatEstabl().equalsIgnoreCase("restaurante")){
                lista.add(establecimientos.get(i).getNombreEstabl());
            }
        }
        return lista;
    }

    public Establecimiento buscarEst(String nombrereceta){

        for (int i = 0; i < establecimientos.size(); i++){
            Establecimiento est = establecimientos.get(i);
            if (est.getNombreEstabl().equalsIgnoreCase(nombrereceta)){
                return est;
            }
        }
        return null;
    }

    public int obtenerNumeroestablecimientos(){
        return establecimientos.size();
    }

    public void imprimirestablecimientos() {
        for (int i = 0; i < establecimientos.size(); i++){
            System.out.println("RECETA: " + establecimientos.get(i).getNombreEstabl());
        }
    }
}
