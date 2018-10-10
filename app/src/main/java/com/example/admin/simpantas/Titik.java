package com.example.admin.simpantas;

public class Titik {
    private double latitude, longitude;
    private Integer unixDate, unixTime;
    private String tanggal,provinsi, kabupaten, kecamatan, desa;

    public Titik() {
        this.latitude = latitude;
        this.longitude = longitude;
        this.unixDate = unixDate;
        this.unixTime = unixTime;
        this.tanggal = tanggal;
        this.provinsi = provinsi;
        this.kabupaten = kabupaten;
        this.kecamatan = kecamatan;
        this.desa = desa;
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

    public Integer getUnixDate() {
        return unixDate;
    }

    public void setUnixDate(Integer unixDate) {
        this.unixDate = unixDate;
    }

    public Integer getUnixTime() {
        return unixTime;
    }

    public void setUnixTime(Integer unixTime) {
        this.unixTime = unixTime;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKabupaten() {
        return kabupaten;
    }

    public void setKabupaten(String kabupaten) {
        this.kabupaten = kabupaten;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getDesa() {
        return desa;
    }

    public void setDesa(String desa) {
        this.desa = desa;
    }
}
