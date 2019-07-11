package com.example.admin.simpantas;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Objects;

public class TvFrequent implements Serializable{
    private int id;
    private double latitude,longitude;
    private String unix1,unix2,unix3,unix4,tanggal1,tanggal2,tanggal3,tanggal4,kabupaten,kecamatan;

    public static final String TABLE_NAME = "Tvfrequent";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_KABUPATEN = "kabupaten";
    public static final String COLUMN_KECAMATAN = "kecamatan";
    public static final String COLUMN_UNIX1 = "unix1";
    public static final String COLUMN_UNIX2 = "unix2";
    public static final String COLUMN_UNIX3 = "unix3";
    public static final String COLUMN_UNIX4 = "unix4";
    public static final String COLUMN_TANGGAL1 = "tanggal1";
    public static final String COLUMN_TANGGAL2 = "tanggal2";
    public static final String COLUMN_TANGGAL3 = "tanggal3";
    public static final String COLUMN_TANGGAL4 = "tanggal4";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_LATITUDE+ " DOUBLE,"
                    + COLUMN_LONGITUDE+ " DOUBLE,"
                    + COLUMN_KABUPATEN+ " TEXT,"
                    + COLUMN_KECAMATAN+ " TEXT,"
                    + COLUMN_UNIX1+ " TEXT,"
                    + COLUMN_UNIX2+ " TEXT,"
                    + COLUMN_UNIX3+ " TEXT,"
                    + COLUMN_UNIX4+ " TEXT,"
                    + COLUMN_TANGGAL1+ " TEXT,"
                    + COLUMN_TANGGAL2+ " TEXT,"
                    + COLUMN_TANGGAL3+ " TEXT,"
                    + COLUMN_TANGGAL4+ " TEXT"
                    + ")";

    public TvFrequent()
    {

    }

    public TvFrequent(int id, double latitude, double longitude, String kabupaten, String kecamatan, String unix1, String unix2, String unix3, String unix4, String tanggal1, String tanggal2, String tanggal3, String tanggal4) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.kabupaten = kabupaten;
        this.kecamatan = kecamatan;
        this.unix1 = unix1;
        this.unix2 = unix2;
        this.unix3 = unix3;
        this.unix4 = unix4;
        this.tanggal1 = tanggal1;
        this.tanggal2 = tanggal2;
        this.tanggal3 = tanggal3;
        this.tanggal4 = tanggal4;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTanggal1() {
        return tanggal1;
    }

    public void setTanggal1(String tanggal1) {
        this.tanggal1 = tanggal1;
    }

    public String getTanggal2() {
        return tanggal2;
    }

    public void setTanggal2(String tanggal2) {
        this.tanggal2 = tanggal2;
    }

    public String getTanggal3() {
        return tanggal3;
    }

    public void setTanggal3(String tanggal3) {
        this.tanggal3 = tanggal3;
    }

    public String getTanggal4() {
        return tanggal4;
    }

    public void setTanggal4(String tanggal4) {
        this.tanggal4 = tanggal4;
    }

    public String getUnix1() {
        return unix1;
    }

    public void setUnix1(String unix1) {
        this.unix1 = unix1;
    }

    public String getUnix2() {
        return unix2;
    }

    public void setUnix2(String unix2) {
        this.unix2 = unix2;
    }

    public String getUnix3() {
        return unix3;
    }

    public void setUnix3(String unix3) {
        this.unix3 = unix3;
    }

    public String getUnix4() {
        return unix4;
    }

    public void setUnix4(String unix4) {
        this.unix4 = unix4;
    }



}
