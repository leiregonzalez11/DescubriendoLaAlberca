package com.example.tfg.espacioDelViajero.alojamiento;

import static android.service.controls.ControlsProviderService.TAG;

import android.os.Build;
import android.util.Log;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public interface ListaAlojamiento {

    public static List<String> getListaNombresAlojamiento(LinkedList<Alojamiento> est, String orden){

        if (orden.equalsIgnoreCase("asc")){
            organizedPuntuacionAscList(est);

        } else if (orden.equalsIgnoreCase("desc")){
            organizedPuntuacionAscList(est);
            Collections.reverse(est);
        }

        List<String> lista = new LinkedList<>();
        for (int i = 0; i < Objects.requireNonNull(est).size(); i++){
            lista.add(est.get(i).getNombreAloj());
        }

        if (orden.equalsIgnoreCase("atoz")){
            organizedAlphabeticList(lista);
        } else if (orden.equalsIgnoreCase("ztoa")){
            organizedAlphabeticList(lista);
            Collections.reverse(lista);
        }

        return lista;

    }

    static Alojamiento buscarAloj(String nombreAloj, List<Alojamiento> aloj){
        for (int i = 0; i <aloj.size(); i++){
            Alojamiento aloja = aloj.get(i);
            if (aloja.getNombreAloj().equalsIgnoreCase(nombreAloj)){
                return aloja;
            }
        }
        return null;
    }

    static void organizedPuntuacionAscList (List<Alojamiento> est){

        boolean ordenada = false;
        List<Alojamiento> listaOrdenada = new LinkedList<>();

        while (!ordenada){
            int i=0;
            while (i< est.size()){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    Log.d(TAG, "EST SIZE : " + est.size() + " ");
                }
                Alojamiento actual = est.get(i);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    Log.d(TAG, "ACTUAL : " + i + " " + actual.getNombreAloj() + " PUNTUACION: " + actual.getPuntAloj());
                }
                if (!listaOrdenada.contains(actual)) {
                    for (int j = 0; j < est.size(); j++) {
                        Alojamiento comp = est.get(j);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            Log.d(TAG, "COMPARADOR : " + j + " " + comp.getNombreAloj() + " PUNTUACION: " + comp.getPuntAloj());
                        }
                        if (!listaOrdenada.contains(comp)){
                            if (actual.getPuntAloj() >= comp.getPuntAloj()){
                                actual = comp;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                    Log.d(TAG, "TEMPORAL : " + j + " " + actual.getNombreAloj() + " PUNTUACION: " + actual.getPuntAloj());
                                }
                            }
                        }
                    }
                }
                if (!listaOrdenada.contains(actual)){
                    listaOrdenada.add(actual);
                }

                if (listaOrdenada.size() == est.size()){
                    ordenada = true;
                }
                i++;
            }
        }

        System.out.println("LISTA ORDENADA FINAL");
        for (Alojamiento estab: listaOrdenada) {
            System.out.println("Alojamiento: "+ estab.getNombreAloj() + " PUNTUACION: "+ estab.getPuntAloj());
        }

    }

    //Utilizando la Clase Collator que actúa como comparadora de cadena para solucionar el error de las tildes
    static void organizedAlphabeticList(List<String> list) {
        list.sort(new Comparator<String>() {
            final Collator collator = Collator.getInstance();

            public int compare(String o1, String o2) {
                return collator.compare(o1, o2);
            }
        });
    }
}
