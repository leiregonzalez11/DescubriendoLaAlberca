package com.example.tfg.mapsFragments.otrosLugares.pueblos;

public class Pueblo {

    private String nombrePueblo;
    private String descrPueblo;
    private double latitud;
    private double longitud;
    private String kmDesdeLA;
    private String fiestamayor;

    public Pueblo (){}


    public String getNombrePueblo() {
        return nombrePueblo;
    }

    public String getDescrPueblo() {
        return descrPueblo;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public String getKmDesdeLA() {
        return kmDesdeLA;
    }

    public String getFiestamayor() {
        return fiestamayor;
    }

    public void setNombrePueblo(String nombrePueblo) {
        this.nombrePueblo = nombrePueblo;
    }

    public void setDescrPueblo(String descrPueblo) {
        this.descrPueblo = descrPueblo;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public void setKmDesdeLA(String kmDesdeLA) {
        this.kmDesdeLA = kmDesdeLA;
    }

    public void setFiestamayor(String fiestamayor) {
        this.fiestamayor = fiestamayor;
    }
}
