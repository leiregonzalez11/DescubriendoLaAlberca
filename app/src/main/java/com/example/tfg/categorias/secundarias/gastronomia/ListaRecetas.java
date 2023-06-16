package com.example.tfg.categorias.secundarias.gastronomia;

import android.content.Context;

import com.example.tfg.GestorDB;

import java.util.LinkedList;

public class ListaRecetas {

    private final LinkedList<Receta> recetas;

    public ListaRecetas (Context context, String idioma){
        try (GestorDB dbHelper = GestorDB.getInstance(context.getApplicationContext())) {
            recetas = dbHelper.obtenerRecetas(idioma);
        }
    }

    public Receta buscarReceta(String nombrereceta){

        for (int i = 0; i < recetas.size(); i++){
            Receta receta = recetas.get(i);
            if (receta.getNombreReceta().equalsIgnoreCase(nombrereceta)){
                return receta;
            }
        }
        return null;
    }

    public void imprimirRecetas() {
        for (int i = 0; i < recetas.size(); i++){
            Receta receta = recetas.get(i);
            System.out.println("RECETA: " + receta.getNombreReceta());
        }
    }
}
