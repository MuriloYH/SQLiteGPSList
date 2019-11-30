package br.com.bossini.sqlitegpslist;

import java.io.Serializable;

public class Lugar implements Serializable {

    private int id;
    private double latitude;
    private double longitude;

    public Lugar(double latitude, double longitude) {
        setLatitude(latitude);
        setLongitude(longitude);
    }

    public Lugar(int id, double latitude, double longitude) {
        setId(id);
        setLatitude(latitude);
        setLongitude(longitude);
    }

    public Lugar(){

    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Latitude: " + latitude + ", Longitude: " + longitude;
    }

}
