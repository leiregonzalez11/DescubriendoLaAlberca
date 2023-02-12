package com.example.tfg.categoriasFragments.secundarias.gastronomia;

public class Receta {

    private String nombreReceta;
    private String descrReceta;
    private String ingredientes;
    private String pasos;

    public Receta (){}

    public String getNombreReceta() {
        return nombreReceta;
    }

    public String getDescrReceta(){
        return descrReceta;
    }

    public String [] getIngredientes() {
        return ingredientes.split(",");
    }

    public String getPasos() {
        return pasos.replaceAll("--", "\n");
    }

    public void setNombreReceta(String nombreReceta) {
        this.nombreReceta = nombreReceta;
    }

    public void setDescrReceta(String descrReceta) {
        this.descrReceta = descrReceta;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public void setPasos(String pasos) {
        this.pasos = pasos;
    }

}
