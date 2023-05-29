package com.example.tfg.Maps.servicios;

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
    }

    public Servicio (){}

    public Servicio (String nombreServ, String localizacion, String telServ){
            this.nombreServ = nombreServ;
            this.locationServ = localizacion;
            this.telServ = telServ;
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

    public void setNombreServ(String nombreServ) {
        this.nombreServ = nombreServ;
    }

    public void setLocationServ(String locationServ) {
        this.locationServ = locationServ;
    }

    public void setTelServ(String telServ) {
        this.telServ = telServ;
    }

    public String getHorarioServ() {
        return horarioServ;
    }

    public void setHorarioServ(String horarioServ) {
        this.horarioServ = horarioServ;
    }

    public String getPrecioServ() {
        return precioServ;
    }

    public void setPrecioServ(String precioServ) {
        this.precioServ = precioServ;
    }
}
