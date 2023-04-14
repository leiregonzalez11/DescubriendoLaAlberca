package com.example.tfg.EspacioDelViajero.alojamiento;

import android.os.Parcel;
import android.os.Parcelable;

public class Alojamiento implements Parcelable {


    private String catAloj;
    private String nombreAloj;
    private double puntAloj;
    private String locationAloj;
    private String telAloj;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.catAloj);
        parcel.writeString(this.nombreAloj);
        parcel.writeDouble(this.puntAloj);
        parcel.writeString(this.locationAloj);
        parcel.writeString(this.telAloj);
    }

    public Alojamiento(){}

    protected Alojamiento(Parcel in) {
        this.catAloj = in.readString();
        this.nombreAloj = in.readString();
        this.puntAloj = in.readDouble();
        this.locationAloj = in.readString();
        this.telAloj = in.readString();
    }

    public static final Parcelable.Creator<Alojamiento> CREATOR = new Parcelable.Creator<Alojamiento>() {
        @Override
        public Alojamiento createFromParcel(Parcel source) {
            return new Alojamiento(source);
        }

        @Override
        public Alojamiento[] newArray(int size) {
            return new Alojamiento[size];
        }
    };

    public String getCatAloj() {
        return catAloj;
    }

    public String getNombreAloj() {
        return nombreAloj;
    }

    public double getPuntAloj() {
        return puntAloj;
    }

    public String getLocationAloj() {
        return locationAloj;
    }

    public String getTelAloj() {
        return telAloj;
    }

    public void setCatAloj(String catAloj) {
        this.catAloj = catAloj;
    }

    public void setNombreAloj(String nombreAloj) {
        this.nombreAloj = nombreAloj;
    }

    public void setPuntAloj(double puntAloj) {
        this.puntAloj = puntAloj;
    }

    public void setLocationAloj(String locationAloj) {
        this.locationAloj = locationAloj;
    }

    public void setTelAloj(String telAloj) {
        this.telAloj = telAloj;
    }
}
