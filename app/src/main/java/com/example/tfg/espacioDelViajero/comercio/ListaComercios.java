package com.example.tfg.espacioDelViajero.comercio;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public interface ListaComercios {

    static List<String> obtenerListaNombresComercio(List<Comercio> est, String orden){

        List<String> lista = new LinkedList<>();
        for (int i = 0; i < est.size(); i++){
            lista.add(est.get(i).getNombreComercio());
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

    static void organizedAlphabeticList(List<String> list) {
        list.sort(new Comparator<String>() {
            final Collator collator = Collator.getInstance();

            public int compare(String o1, String o2) {
                return collator.compare(o1, o2);
            }
        });
    }

    static Comercio buscarComercio(String nombreCom, List<Comercio> com){
        for (int i = 0; i < com.size(); i++){
            Comercio comr = com.get(i);
            if (comr.getNombreComercio().equalsIgnoreCase(nombreCom)){
                return comr;
            }
        }
        return null;
    }


}
