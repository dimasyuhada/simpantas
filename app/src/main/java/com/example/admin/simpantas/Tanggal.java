package com.example.admin.simpantas;

public class Tanggal {
    private int id;
    private String tanggal;

    public static final String TABLE_NAME = "Tanggal";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TANGGAL = "tgl";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_TANGGAL+ " TEXT"
                    + ")";

    public Tanggal()
    {

    }

    public Tanggal(int id, String tanggal) {
        this.id = id;
        this.tanggal = tanggal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
