package com.example.admin.simpantas;

public class Transform {
    private int sid;
    private String item,tahun;

    public static final String TABLE_NAME = "Transform";
    public static final String COLUMN_SID = "sid";
    public static final String COLUMN_ITEM = "item";
    public static final String COLUMN_TAHUN = "tahun";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_SID + " INTEGER PRIMARY KEY,"
                    + COLUMN_ITEM+ " TEXT,"
                    + COLUMN_TAHUN + " TEXT"
                    + ")";

    public Transform()
    {

    }

    public Transform(int sid, String item, String tahun) {
        this.sid = sid;
        this.item = item;
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

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }
}
