package com.example.tfg.categoriasFragments.secundarias.cultura.diccionario;

import android.content.Context;

import com.example.tfg.GestorDB;
import com.example.tfg.categoriasFragments.secundarias.gastronomia.Receta;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListaPalabras {

    private final LinkedList<Palabra> palabras;

    public ListaPalabras (Context context, String idioma){
        GestorDB dbHelper = new GestorDB(context);
        palabras = dbHelper.obtenerPalabras(idioma);
    }

    /* public void eliminarPalabra(String nombrePalabra){
        Palabra palabra = buscarPalabra(nombrePalabra);
        palabras.remove(palabra);
    }*/

    public Palabra buscarPalabra(String nombrePalabra){
        for (int i = 0; i< palabras.size(); i++){
            Palabra palabra = palabras.get(i);
            if (palabra.getNombrePalabra().equalsIgnoreCase(nombrePalabra)){
                return palabra;
            }
        }
        return null;
    }

    public List<String> obtenerListaPalabras(String letra){

        List<String> lista = new ArrayList<>();

        for (int i = 0; i< palabras.size(); i++){
            Palabra palabra = palabras.get(i);
            if (palabra.getCategoriaPalabra().equalsIgnoreCase(letra)){
                lista.add(palabra.getNombrePalabra());
            }
        }
        return lista;
    }

    public int obtenerNumeroPalabras(){
        return palabras.size();
    }

    public void imprimirPalabras() {
        for (int i = 0; i < palabras.size(); i++){
            Palabra palabra = palabras.get(i);
            System.out.println("PALABRA: " + palabra.getNombrePalabra());
        }
    }




}
