package com.example.tfg.EspacioDelViajero.restauracion;

import android.content.Context;

import com.example.tfg.GestorDB;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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


}
