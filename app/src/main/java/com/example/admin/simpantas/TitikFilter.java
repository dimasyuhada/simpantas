package com.example.admin.simpantas;

public class TitikFilter {
    private double latitude, longitude;
    private int id,unixDateTime, unixDate;
    private String tanggal,tahun;

    public static final String TABLE_NAME = "Titikfilter";
    public static final String COLUMN_SID = "sid";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_UNIXDATE = "unixdate";
    public static final String COLUMN_UNIXDATETIME = "unixdatetime";
    public static final String COLUMN_TANGGAL = "tanggal";
    public static final String COLUMN_TAHUN = "tahun";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_SID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_ID+ " INTEGER,"
                    + COLUMN_LATITUDE + " DOUBLE,"
                    + COLUMN_LONGITUDE + " DOUBLE,"
                    + COLUMN_UNIXDATE + " INTEGER,"
                    + COLUMN_UNIXDATETIME + " INTEGER,"
                    + COLUMN_TANGGAL + " TEXT,"
                    + COLUMN_TAHUN + " TEXT"
                    + ")";

    public TitikFilter()
    {

    }

    public TitikFilter(int id, double latitude, double longitude, int unixDate, int unixDateTime, String tanggal, String tahun) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.unixDate = unixDate;
        this.unixDateTime = unixDateTime;
        this.tanggal = tanggal;
        this.tahun = tahun;
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

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }
}
