package com.example.tfg.categoriasFragments.secundarias.cultura.diccionario;

import android.content.Context;

import com.example.tfg.GestorDB;
import com.example.tfg.categoriasFragments.secundarias.gastronomia.Receta;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ListaPalabras {

    private final LinkedList<Palabra> palabras;

    public ListaPalabras (Context context, String idioma){
        GestorDB dbHelper = new GestorDB(context);
        palabras = dbHelper.obtenerPalabras(idioma);
    }

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
        return organizedAlphabeticList(lista);
    }

    //Utilizando la Clase Collator que actúa como comparadora de cadena para solucionar el error de las tildes
    public static List<String> organizedAlphabeticList(List<String> list) {
        list.sort(new Comparator<String>() {
            final Collator collator = Collator.getInstance();

            public int compare(String o1, String o2) {
                return collator.compare(o1, o2);
            }
        });
        return list;
    }


}
