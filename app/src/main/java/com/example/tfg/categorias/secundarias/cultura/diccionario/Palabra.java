package com.example.tfg.categorias.secundarias.cultura.diccionario;

import android.os.Parcel;
import android.os.Parcelable;

public class Palabra implements Parcelable {

    private String nombrepalabra;
    private String definicionpalabraEs;
    private String definicionpalabraEu;
    private String definicionpalabraEn;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.nombrepalabra);
        parcel.writeString(this.definicionpalabraEs);
        parcel.writeString(this.definicionpalabraEu);
        parcel.writeString(this.definicionpalabraEn);
    }

    public Palabra (){}

    public Palabra (String nombrepalabra, String definicionpalabraEs, String definicionpalabraEu, String definicionpalabraEn){
        this.nombrepalabra = nombrepalabra;
        this.definicionpalabraEs = definicionpalabraEs;
        this.definicionpalabraEu = definicionpalabraEu;
        this.definicionpalabraEn = definicionpalabraEn;
    }

    protected Palabra(Parcel in) {
        this.nombrepalabra = in.readString();
        this.definicionpalabraEs = in.readString();
        this.definicionpalabraEn = in.readString();
        this.definicionpalabraEu = in.readString();
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

    public String getNombrePalabra() {
        return nombrepalabra;
    }

    public String getDefinicionpalabraEs() {
        return definicionpalabraEs;
    }

    public String getDefinicionpalabraEn() {
        return definicionpalabraEn;
    }

    public String getDefinicionpalabraEu() {
        return definicionpalabraEu;
    }

}
