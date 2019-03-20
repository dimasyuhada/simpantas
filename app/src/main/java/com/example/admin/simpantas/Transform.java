package com.example.admin.simpantas;

public class Transform {
    private int sid;
    private String item,bulan,tahun;

    public static final String TABLE_NAME = "Transform";
    public static final String COLUMN_SID = "sid";
    public static final String COLUMN_ITEM = "item";
    public static final String COLUMN_BULAN = "bulan";
    public static final String COLUMN_TAHUN = "tahun";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_SID + " INTEGER,"
                    + COLUMN_ITEM+ " TEXT,"
                    + COLUMN_BULAN+ " TEXT,"
                    + COLUMN_TAHUN + " TEXT"
                    + ")";

    public Transform()
    {

    }

    public Transform(int sid, String item, String bulan, String tahun) {
        this.sid = sid;
        this.item = item;
        this.bulan = bulan;
        this.tahun = tahun;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
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
