package com.example.tfg.EspacioDelViajero.comercio;

import android.os.Parcel;
import android.os.Parcelable;

public class Comercio implements Parcelable{

    private String catComercio;
    private String nombreComercio;
    private String locationComercio;
    private String telComercio;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.catComercio);
        parcel.writeString(this.nombreComercio);
        parcel.writeString(this.locationComercio);
        parcel.writeString(this.telComercio);
    }

    public Comercio (){}

    protected Comercio(Parcel in) {
        this.catComercio = in.readString();
        this.nombreComercio = in.readString();
        this.locationComercio = in.readString();
        this.telComercio = in.readString();
    }



    public static final Parcelable.Creator<Comercio> CREATOR = new Parcelable.Creator<Comercio>() {
        @Override
        public Comercio createFromParcel(Parcel source) {
            return new Comercio(source);
        }

        @Override
        public Comercio[] newArray(int size) {
            return new Comercio[size];
        }
    };


    public String getCatComercio() {
        return catComercio;
    }

    public void setCatComercio(String catComercio) {
        this.catComercio = catComercio;
    }

    public String getNombreComercio() {
        return nombreComercio;
    }

    public void setNombreComercio(String nombreComercio) {
        this.nombreComercio = nombreComercio;
    }

    public String getLocationComercio() {
        return locationComercio;
    }

    public void setLocationComercio(String locationComercio) {
        this.locationComercio = locationComercio;
    }

    public String getTelComercio() {
        return telComercio;
    }

    public void setTelComercio(String telComercio) {
        this.telComercio = telComercio;
    }
}