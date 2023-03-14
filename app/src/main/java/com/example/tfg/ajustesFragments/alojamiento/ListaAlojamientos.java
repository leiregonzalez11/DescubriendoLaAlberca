package com.example.tfg.ajustesFragments.alojamiento;

import android.content.Context;

import com.example.tfg.GestorDB;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class ListaAlojamientos {

    private List<Alojamiento> alojamientos;
    private static ListaAlojamientos miListaAlojamientos;
    private String query;
    private GestorDB dbHelper;

    private ListaAlojamientos (){
        alojamientos = new LinkedList<>();
    }

    public static ListaAlojamientos getMiListaAlojamientos(){
        if (miListaAlojamientos == null){
            miListaAlojamientos = new ListaAlojamientos();
        }
        return miListaAlojamientos;
    }

    public void setContext(Context context){
        dbHelper = new GestorDB(context);
    }
    

    public List<String> getListaNombres(List<Alojamiento> aloj, String orden){

        List<String> lista = new LinkedList<>();
        for (int i = 0; i < aloj.size(); i++){
            lista.add(aloj.get(i).getNombreAloj());
        }

        if (orden.equalsIgnoreCase("atoz")){
            organizedAlphabeticList(lista);
        } else if (orden.equalsIgnoreCase("ztoa")){
            organizedAlphabeticList(lista);
            Collections.reverse(lista);
        }

        return lista;
    }

    public Alojamiento buscarAloj(String nombreAloj, List<Alojamiento> aloj){
        for (int i = 0; i <aloj.size(); i++){
            Alojamiento est = aloj.get(i);
            if (est.getNombreAloj().equalsIgnoreCase(nombreAloj)){
                return est;
            }
        }
        return null;
    }

    public List<Alojamiento> getListaAlojamientos (String categoriaAloj, boolean ascdesc, String tipo){
        if (!ascdesc){
            query = "SELECT * FROM alojamiento WHERE categoriaAloj = '" + categoriaAloj + "';";
        } else {
            query = "SELECT * FROM alojamiento WHERE categoriaAloj = '" + categoriaAloj + "' ORDER BY puntuacion " + tipo.toUpperCase() + ";";
        }
        alojamientos = dbHelper.obteneralojamientos(query);
        return alojamientos;
    }


    public List<Alojamiento> getHospederia () {
        String query = "SELECT * FROM alojamiento WHERE categoriaAloj = 'hospederia';";
        alojamientos = dbHelper.obteneralojamientos(query);
        return alojamientos;
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
