package com.example.tfg.espacioDelViajero.alojamiento;

import android.os.Parcel;
import android.os.Parcelable;

public class Alojamiento implements Parcelable {

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
        parcel.writeString(this.nombreAloj);
        parcel.writeDouble(this.puntAloj);
        parcel.writeString(this.locationAloj);
        parcel.writeString(this.telAloj);
    }

    public Alojamiento(){}

    public Alojamiento (String nombreAloj, String localizacion, String telAloj, Double puntAloj){
        this.nombreAloj = nombreAloj;
        this.locationAloj = localizacion;
        this.telAloj = telAloj;
        this.puntAloj = puntAloj;
    }

    protected Alojamiento(Parcel in) {
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

}
