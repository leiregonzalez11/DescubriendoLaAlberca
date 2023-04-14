package com.example.tfg.Categorias.secundarias.cultura.diccionario;

import android.os.Parcel;
import android.os.Parcelable;

public class Palabra implements Parcelable {

    private String categoriaPalabra;
    private String nombrepalabra;
    private String definicionpalabra;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.categoriaPalabra);
        parcel.writeString(this.nombrepalabra);
        parcel.writeString(this.definicionpalabra);
    }

    public Palabra (){}

    protected Palabra(Parcel in) {
        this.categoriaPalabra = in.readString();
        this.nombrepalabra = in.readString();
        this.definicionpalabra = in.readString();
    }

    public static final Parcelable.Creator<Palabra> CREATOR = new Parcelable.Creator<Palabra>() {
        @Override
        public Palabra createFromParcel(Parcel source) {
            return new Palabra(source);
        }

        @Override
        public Palabra[] newArray(int size) {
            return new Palabra[size];
        }
    };

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
