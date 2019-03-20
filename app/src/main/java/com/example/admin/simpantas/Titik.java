package com.example.admin.simpantas;

public class Titik {
    private double latitude, longitude;
    private int unixDateTime, unixDate;
    private String tanggal,provinsi, kabupaten, kecamatan, desa, bulan, tahun;

    public static final String TABLE_NAME = "Titik";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_UNIXDATE = "unixdate";
    public static final String COLUMN_UNIXDATETIME = "unixdatetime";
    public static final String COLUMN_TANGGAL = "tanggal";
    public static final String COLUMN_PROVINSI = "provinsi";
    public static final String COLUMN_KABUPATEN = "kabupaten";
    public static final String COLUMN_KECAMATAN = "kecamatan";
    public static final String COLUMN_DESA = "desa";
    public static final String COLUMN_BULAN = "bulan";
    public static final String COLUMN_TAHUN = "tahun";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_LATITUDE + " DOUBLE,"
                    + COLUMN_LONGITUDE + " DOUBLE,"
                    + COLUMN_UNIXDATE + " INTEGER,"
                    + COLUMN_UNIXDATETIME + " INTEGER,"
                    + COLUMN_TANGGAL + " TEXT,"
                    + COLUMN_PROVINSI + " TEXT,"
                    + COLUMN_KABUPATEN + " TEXT,"
                    + COLUMN_KECAMATAN + " TEXT,"
                    + COLUMN_DESA + " TEXT,"
                    + COLUMN_BULAN + " TEXT,"
                    + COLUMN_TAHUN + " TEXT"
                    + ")";

    public Titik()
    {

    }

    public Titik(double latitude, double longitude, int unixDate, int unixDateTime, String tanggal, String provinsi, String kabupaten, String kecamatan, String desa, String bulan, String tahun) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.unixDate = unixDate;
        this.unixDateTime = unixDateTime;
        this.tanggal = tanggal;
        this.provinsi = provinsi;
        this.kabupaten = kabupaten;
        this.kecamatan = kecamatan;
        this.desa = desa;
        this.bulan = bulan;
        this.tahun = tahun;
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

    public int getUnixDate() {
        return unixDate;
    }

    public void setUnixDate(int unixDate) {
        this.unixDate = unixDate;
    }

    public int getUnixDateTime() {
        return unixDateTime;
    }

    public void setUnixDateTime(int unixDateTime) {
        this.unixDateTime = unixDateTime;
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

    public String getBulan() {
        return bulan;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }
}
