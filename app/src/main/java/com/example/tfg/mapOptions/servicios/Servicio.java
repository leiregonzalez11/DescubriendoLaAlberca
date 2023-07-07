package com.example.tfg.mapOptions.servicios;

import android.os.Parcel;
import android.os.Parcelable;

public class Servicio implements Parcelable {

    private String nombreServ;
    private String locationServ;
    private String telServ;
    private String horarioServ;
    private String precioServ;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.nombreServ);
        parcel.writeString(this.locationServ);
        parcel.writeString(this.telServ);
        parcel.writeString(this.horarioServ);
        parcel.writeString(this.precioServ);
    }

    public Servicio (){}

    public Servicio (String nombreServ, String localizacion, String telServ){
            this.nombreServ = nombreServ;
            this.locationServ = localizacion;
            this.telServ = telServ;
    }

    public Servicio (String nombreServ, String localizacion, String horario, String precio){
        this.nombreServ = nombreServ;
        this.locationServ = localizacion;
        this.horarioServ = horario;
        this.precioServ = precio;
    }

    protected Servicio(Parcel in) {
        this.nombreServ = in.readString();
        this.locationServ = in.readString();
        this.telServ = in.readString();
    }

    public static final Parcelable.Creator<Servicio> CREATOR = new Parcelable.Creator<Servicio>() {
        @Override
        public Servicio createFromParcel(Parcel source) {
            return new Servicio(source);
        }

        @Override
        public Servicio[] newArray(int size) {
            return new Servicio[size];
        }
    };

    public String getNombreServ() {
        return nombreServ;
    }

    public String getLocationServ() {
        return locationServ;
    }

    public String getTelServ() {
        return telServ;
    }

    public String getHorarioServ() {
        return horarioServ;
    }

    public String getPrecioServ() {
        return precioServ;
    }
}
