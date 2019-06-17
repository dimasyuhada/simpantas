package com.example.admin.simpantas;

public class TitikFilter {
    private double latitude, longitude;
    private int id,unixDateTime, unixDate;
    private String tanggal, kabupaten, kecamatan;

    public static final String TABLE_NAME = "Titikfilter";
    public static final String COLUMN_SID = "sid";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_UNIXDATE = "unixdate";
    public static final String COLUMN_UNIXDATETIME = "unixdatetime";
    public static final String COLUMN_TANGGAL = "tanggal";
    public static final String COLUMN_KABUPATEN = "kabupaten";
    public static final String COLUMN_KECAMATAN = "kecamatan";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_SID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_LATITUDE + " DOUBLE,"
                    + COLUMN_LONGITUDE + " DOUBLE,"
                    + COLUMN_UNIXDATE + " INTEGER,"
                    + COLUMN_UNIXDATETIME + " INTEGER,"
                    + COLUMN_TANGGAL + " TEXT,"
                    + COLUMN_KABUPATEN + " TEXT,"
                    + COLUMN_KECAMATAN + " TEXT"
                    + ")";

    public TitikFilter()
    {

    }

    public TitikFilter(double latitude, double longitude, int unixDate, int unixDateTime, String tanggal, String kabupaten, String kecamatan) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.unixDate = unixDate;
        this.unixDateTime = unixDateTime;
        this.tanggal = tanggal;
        this.kabupaten = kabupaten;
        this.kecamatan = kecamatan;
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

    public String getKabupaten() {
        return kabupaten;
    }

    public void setKabupaten(String kabupaten) {
        this.kabupaten = kabupaten;
    }

    public String getKecamatan() {
        return getKecamatan();
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }
}
