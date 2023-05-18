package com.example.tfg.Categorias.secundarias.cultura.diccionario;

import android.content.Context;

import com.example.tfg.GestorDB;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ListaPalabras {

    private LinkedList<Palabra> palabras1;

    public ListaPalabras (Context context, String letra, String idioma){
        GestorDB dbHelper = new GestorDB(context);
        for (int i = 0; i< palabras1.size(); i++){
            System.out.println("PALABRA NOMBRE " + i + " " + palabras1.get(i).getNombrePalabra());
        }
        //palabras = dbHelper.obtenerPalabras(idioma);
    }

    public ListaPalabras (LinkedList<Palabra> lista){
        this.palabras1 = lista;
    }



    public void setListaPalabras(LinkedList<Palabra> listaPalabras){
        palabras1 = listaPalabras;
    }

    public List<String> obtenerListaPalabras(){

        List<String> lista = new ArrayList<>();
        for (int i = 0; i< palabras1.size(); i++){
            Palabra palabra = palabras1.get(i);
            System.out.println("PALABRA NOMBRE " + i + " " + palabras1.get(i).getNombrePalabra());
            System.out.println("PALABRA NOMBRE2 " + i + " " + palabra.getNombrePalabra());
            lista.add(palabra.getNombrePalabra());
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
