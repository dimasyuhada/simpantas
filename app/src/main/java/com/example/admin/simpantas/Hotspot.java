package com.example.admin.simpantas;

import java.io.Serializable;

public class Hotspot implements Serializable{
    private double latitude, longitude;
    private int  confidence, temp;
    private String kawasan,tanggal, kecamatan, kabupaten;

    public static final String TABLE_NAME_UPDATE = "HotspotUpdate";
    public static final String TABLE_NAME = "Hotspot";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_CONFIDENCE = "confidence";
    public static final String COLUMN_KAWASAN = "kawasan";
    public static final String COLUMN_TANGGAL = "tanggal";
    public static final String COLUMN_KECAMATAN = "kecamatan";
    public static final String COLUMN_KABUPATEN = "kabupaten";
    public static final String COLUMN_TEMP = "temp";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_LATITUDE + " DOUBLE,"
                    + COLUMN_LONGITUDE + " DOUBLE,"
                    + COLUMN_CONFIDENCE + " INTEGER,"
                    + COLUMN_KAWASAN + " INTEGER,"
                    + COLUMN_TANGGAL + " TEXT,"
                    + COLUMN_KECAMATAN + " TEXT,"
                    + COLUMN_KABUPATEN+ " TEXT,"
                    + COLUMN_TEMP+ " INTEGER"
                    + ")";

    public static final String CREATE_TABLE_UPDATE =
            "CREATE TABLE " + TABLE_NAME_UPDATE + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_LATITUDE + " DOUBLE,"
                    + COLUMN_LONGITUDE + " DOUBLE,"
                    + COLUMN_CONFIDENCE + " INTEGER,"
                    + COLUMN_KAWASAN + " INTEGER,"
                    + COLUMN_TANGGAL + " TEXT,"
                    + COLUMN_KECAMATAN + " TEXT,"
                    + COLUMN_KABUPATEN+ " TEXT,"
                    + COLUMN_TEMP+ " INTEGER"
                    + ")";

    public Hotspot()
    {

    }

    public Hotspot(double latitude, double longitude, int confidence,  String kawasan, String tanggal, String kecamatan, String kabupaten, int temp) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.confidence = confidence;
        this.kawasan = kawasan;
        this.tanggal = tanggal;
        this.kecamatan = kecamatan;
        this.kabupaten = kabupaten;
        this.temp = temp;
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

    public int getConfidence() {
        return confidence;
    }

    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }

    public String getKawasan() {
        return kawasan;
    }

    public void setKawasan(String kawasan) {
        this.kawasan = kawasan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getKabupaten() {
        return kabupaten;
    }

    public void setKabupaten(String kabupaten) {
        this.kabupaten = kabupaten;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }
}
