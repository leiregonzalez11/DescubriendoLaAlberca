package com.example.tfg.ajustesFragments.restauracion;

import android.content.Context;

import com.example.tfg.GestorDB;
import com.example.tfg.ajustesFragments.alojamiento.Alojamiento;
import com.example.tfg.ajustesFragments.alojamiento.ListaAlojamientos;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ListaEstablecimiento {

    private List<Establecimiento> establecimientos;
    private static ListaEstablecimiento miListaestablecimientos;
    private GestorDB dbHelper;

    private ListaEstablecimiento (){
        establecimientos = new LinkedList<>();
    }

public static ListaEstablecimiento getMiListaestablecimientos(){
        if (miListaestablecimientos == null){
            miListaestablecimientos = new ListaEstablecimiento();
        }
        return miListaestablecimientos;
    }

    public void setContext(Context context){
        dbHelper = new GestorDB(context);
    }

    public List<Establecimiento> getListaEstablecimientos (String categoriaEst, boolean ascdesc, String tipo){
        String query;
        if (!ascdesc){
            query = "SELECT * FROM restaurante WHERE categoriaRest = '" + categoriaEst + "';";
        } else {
            query = "SELECT * FROM restaurante WHERE categoriaRest = '" + categoriaEst + "' ORDER BY puntuacion " + tipo.toUpperCase() + ";";
        }
        establecimientos = dbHelper.obtenerestablecimientos(query);
        return establecimientos;
    }

    public List<String> getListaNombres(List<Establecimiento> est, String orden){

        List<String> lista = new LinkedList<>();
        for (int i = 0; i < est.size(); i++){
            lista.add(est.get(i).getNombreEstabl());
        }

        if (orden.equalsIgnoreCase("atoz")){
            organizedAlphabeticList(lista);
        } else if (orden.equalsIgnoreCase("ztoa")){
            organizedAlphabeticList(lista);
            Collections.reverse(lista);
        }

        return lista;
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


    public Establecimiento buscarEst(String nombreAloj, List<Establecimiento> est){
        for (int i = 0; i <est.size(); i++){
            Establecimiento estbl = est.get(i);
            if (estbl.getNombreEstabl().equalsIgnoreCase(nombreAloj)){
                return estbl;
            }
        }
        return null;
    }

}
