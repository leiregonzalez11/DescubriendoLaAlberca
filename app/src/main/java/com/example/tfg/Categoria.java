package com.example.tfg;

public class Categoria {

    private String nombre;
    private int idDrawable;

    public Categoria(String nombre, int idDrawable){
        this.nombre = nombre;
        this.idDrawable = idDrawable;
    }

    public Categoria (String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return nombre;
    }

    public int getIdDrawable(){
        return idDrawable;
    }

    public int getId(){
        return nombre.hashCode();
    }

    public static Categoria[] categorias = {
            new Categoria("Tradición"),
            new Categoria("Arquitectura"),
            new Categoria("Monumentos"),
            new Categoria("Gastronomia"),
            new Categoria("Alojamientos"),
            new Categoria("Arte"),
            new Categoria("Rutas"),
            new Categoria("Otros")
    };

    /**
     * Obtiene item basado en su identificador
     *
     * @param id identificador
     * @return Categoria
     */
    public static Categoria getItem(int id) {
        for (Categoria item : categorias) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}
