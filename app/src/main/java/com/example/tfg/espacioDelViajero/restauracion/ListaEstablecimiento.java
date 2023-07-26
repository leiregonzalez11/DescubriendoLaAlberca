package com.example.tfg.espacioDelViajero.restauracion;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public interface ListaEstablecimiento {

    static List<String> getListaNombresEstablecimiento(List<Establecimiento> est, String orden){

        if (orden.equalsIgnoreCase("asc")){
            est = organizedPuntuacionAscList(est);

        } else if (orden.equalsIgnoreCase("desc")){
            est = organizedPuntuacionAscList(est);
            Collections.reverse(est);
        }

        List<String> lista = new LinkedList<>();
        for (int i = 0; i < Objects.requireNonNull(est).size(); i++){
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
   static void organizedAlphabeticList(List<String> list) {
        list.sort(new Comparator<String>() {
            final Collator collator = Collator.getInstance();

            public int compare(String o1, String o2) {
                return collator.compare(o1, o2);
            }
        });
    }

    static List<Establecimiento> organizedPuntuacionAscList (List<Establecimiento> est){

        boolean ordenada = false;
        List<Establecimiento> listaOrdenada = new LinkedList<>();

        while (!ordenada){
            int i=0;
            while (i< est.size()){
                Establecimiento actual = est.get(i);
                if (!listaOrdenada.contains(actual)) {
                    for (int j = 0; j < est.size(); j++) {
                        Establecimiento comp = est.get(j);
                        if (!listaOrdenada.contains(comp)){
                            if (actual.getPuntEstabl() >= comp.getPuntEstabl()){
                                actual = comp;
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

        return listaOrdenada;
    }


    static Establecimiento buscarEst(String nombreAloj, List<Establecimiento> est){
        for (int i = 0; i <est.size(); i++){
            Establecimiento estbl = est.get(i);
            if (estbl.getNombreEstabl().equalsIgnoreCase(nombreAloj)){
                return estbl;
            }
        }
        return null;
    }
}
