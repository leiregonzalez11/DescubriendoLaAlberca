package com.example.tfg.EspacioDelViajero.restauracion;

import android.os.Parcel;
import android.os.Parcelable;

public class Establecimiento implements Parcelable {
    
    private String catEstabl;
    private String nombreEstabl;
    private double puntEstabl;
    private String locationEstabl;
    private String telEstabl;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.catEstabl);
        parcel.writeString(this.nombreEstabl);
        parcel.writeDouble(this.puntEstabl);
        parcel.writeString(this.locationEstabl);
        parcel.writeString(this.telEstabl);
    }

    public Establecimiento (){}

    protected Establecimiento(Parcel in) {
        this.catEstabl = in.readString();
        this.nombreEstabl = in.readString();
        this.puntEstabl = in.readDouble();
        this.locationEstabl = in.readString();
        this.telEstabl = in.readString();
    }

    public static final Parcelable.Creator<Establecimiento> CREATOR = new Parcelable.Creator<Establecimiento>() {
        @Override
        public Establecimiento createFromParcel(Parcel source) {
            return new Establecimiento(source);
        }

        @Override
        public Establecimiento[] newArray(int size) {
            return new Establecimiento[size];
        }
    };

    public String getCatEstabl() {
        return catEstabl;
    }

    public String getNombreEstabl() {
        return nombreEstabl;
    }

    public double getPuntEstabl() {
        return puntEstabl;
    }

    public String getLocationEstabl() {
        return locationEstabl;
    }

    public String getTelEstabl() {
        return telEstabl;
    }

    public void setCatEstabl(String catEstabl) {
        this.catEstabl = catEstabl;
    }

    public void setNombreEstabl(String nombreEstabl) {
        this.nombreEstabl = nombreEstabl;
    }

    public void setPuntEstabl(double puntEstabl) {
        this.puntEstabl = puntEstabl;
    }

    public void setLocationEstabl(String locationEstabl) {
        this.locationEstabl = locationEstabl;
    }

    public void setTelEstabl(String telEstabl) {
        this.telEstabl = telEstabl;
    }
}
