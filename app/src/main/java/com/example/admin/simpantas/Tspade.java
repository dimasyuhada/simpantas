package com.example.admin.simpantas;

public class Tspade {
    private int id;
    private String unixdatetime,bulan,tahun;

    public static final String TABLE_NAME = "Tspade";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_UNIXDATETIME = "unixdatetime";
    public static final String COLUMN_BULAN = "bulan";
    public static final String COLUMN_TAHUN = "tahun";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_UNIXDATETIME+ " TEXT,"
                    + COLUMN_BULAN + " TEXT,"
                    + COLUMN_TAHUN + " TEXT"
                    + ")";

    public Tspade()
    {

    }

    public Tspade(int id, String unixdatetime, String bulan, String tahun) {
        this.id = id;
        this.unixdatetime = unixdatetime;
        this.tahun = tahun;
    }

    public int getId() {
        return id;
    }

    public void setId(int sid) {
        this.id = id;
    }

    public String getUnixdatetime() {
        return unixdatetime;
    }

    public void setUnixdatetime(String item) {
        this.unixdatetime = unixdatetime;
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
