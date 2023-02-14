package com.example.tfg.ajustesFragments.comercio;

import android.content.Context;

import com.example.tfg.GestorDB;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListaComercio {

    private final LinkedList<Comercio> comercios;

    public ListaComercio (Context context){
        GestorDB dbHelper = new GestorDB(context);
        comercios = dbHelper.obtenercomercios();
    }

    public List<String> getListaArte(){

        List<String> lista = new ArrayList<>();
        for (int i = 0; i < comercios.size(); i++){
            if (comercios.get(i).getCatComercio().equalsIgnoreCase("artesania")){
                lista.add(comercios.get(i).getNombreComercio());
            }
        }
        return lista;
    }

    public List<String> getListaAlim(){

        List<String> lista = new ArrayList<>();
        for (int i = 0; i < comercios.size(); i++){
            if (comercios.get(i).getCatComercio().equalsIgnoreCase("alimentacion")){
                lista.add(comercios.get(i).getNombreComercio());
            }
        }
        return lista;
    }
    

    public Comercio buscarComercio(String nombreComercio){

        for (int i = 0; i < comercios.size(); i++){
            Comercio comercio = comercios.get(i);
            if (comercio.getNombreComercio().equalsIgnoreCase(nombreComercio)){
                return comercio;
            }
        }
        return null;
    }

    public int obtenerNumeroComercios(){
        return comercios.size();
    }

    public void imprimirComercios() {
        for (int i = 0; i < comercios.size(); i++){
            System.out.println("RECETA: " + comercios.get(i).getNombreComercio());
        }
    }
    
    
    
}
