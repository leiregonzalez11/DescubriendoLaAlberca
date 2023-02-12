package com.example.tfg.categoriasFragments.secundarias.cultura.diccionario;

public class Palabra {

    private String categoriaPalabra;
    private String nombrepalabra;
    private String definicionpalabra;

    public Palabra (){}

    public String getCategoriaPalabra() {
        return categoriaPalabra;
    }

    public String getNombrePalabra() {
        return nombrepalabra;
    }

    public String getDefinicionpalabra() {
        return definicionpalabra;
    }

    public void setCategoriaPalabra(String categoriaPalabra) {
        this.categoriaPalabra = categoriaPalabra;
    }

    public void setNombrepalabra(String nombrepalabra) {
        this.nombrepalabra = nombrepalabra;
    }

    public void setDefinicionpalabra(String definicionpalabra) {
        this.definicionpalabra = definicionpalabra;
    }
}
