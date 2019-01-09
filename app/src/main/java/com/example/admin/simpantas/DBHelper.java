package com.example.admin.simpantas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "dbSimpantas";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Titik.CREATE_TABLE);
        db.execSQL(Hotspot.CREATE_TABLE);
        db.execSQL(Hotspot.CREATE_TABLE_UPDATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Titik.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Hotspot.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Hotspot.TABLE_NAME_UPDATE);
        onCreate(db);
    }

    //TITIK
    public boolean insertTitik(double latitude, double longitude, int unixDate, int unixDateTime, String tanggal, String provinsi, String kabupaten, String kecamatan, String desa) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Titik.COLUMN_LATITUDE, latitude);
        values.put(Titik.COLUMN_LONGITUDE, longitude);
        values.put(Titik.COLUMN_UNIXDATE, unixDate);
        values.put(Titik.COLUMN_UNIXDATETIME, unixDateTime);
        values.put(Titik.COLUMN_TANGGAL, tanggal);
        values.put(Titik.COLUMN_PROVINSI, provinsi);
        values.put(Titik.COLUMN_KABUPATEN, kabupaten);
        values.put(Titik.COLUMN_KECAMATAN, kecamatan);
        values.put(Titik.COLUMN_DESA, desa);

        // insert row
        long id = db.insert(Titik.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id != -1;
    }

    public List<Titik> getAllTitik() {
        List<Titik> titiks = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Titik.TABLE_NAME + " ORDER BY " +
                Titik.COLUMN_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Titik titik = new Titik();
                titik.setLatitude(cursor.getDouble(cursor.getColumnIndex(Titik.COLUMN_LATITUDE)));
                titik.setLongitude(cursor.getDouble(cursor.getColumnIndex(Titik.COLUMN_LONGITUDE)));
                titik.setUnixDate(cursor.getInt(cursor.getColumnIndex(Titik.COLUMN_UNIXDATE)));
                titik.setUnixDateTime(cursor.getInt(cursor.getColumnIndex(Titik.COLUMN_UNIXDATETIME)));
                titik.setTanggal(cursor.getString(cursor.getColumnIndex(Titik.COLUMN_TANGGAL)));
                titik.setProvinsi(cursor.getString(cursor.getColumnIndex(Titik.COLUMN_PROVINSI)));
                titik.setKabupaten(cursor.getString(cursor.getColumnIndex(Titik.COLUMN_KABUPATEN)));
                titik.setKecamatan(cursor.getString(cursor.getColumnIndex(Titik.COLUMN_KECAMATAN)));
                titik.setDesa(cursor.getString(cursor.getColumnIndex(Titik.COLUMN_DESA)));

                titiks.add(titik);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        // return notes list
        return titiks;
    }

    //HOTSPOT
    public boolean insertHotspot(double latitude, double longitude, int confidence, String kawasan, String tanggal, String kecamatan, String kabupaten, int temp) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Hotspot.COLUMN_LATITUDE, latitude);
        values.put(Hotspot.COLUMN_LONGITUDE, longitude);
        values.put(Hotspot.COLUMN_CONFIDENCE, confidence);
        values.put(Hotspot.COLUMN_KAWASAN, kawasan);
        values.put(Hotspot.COLUMN_TANGGAL, tanggal);
        values.put(Hotspot.COLUMN_KECAMATAN, kecamatan);
        values.put(Hotspot.COLUMN_KABUPATEN, kabupaten);
        values.put(Hotspot.COLUMN_TEMP, temp);

        // insert row
        long id = db.insert(Hotspot.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id != -1;
    }
    public List<Hotspot> getAllHotspot() {
        List<Hotspot> hotspots = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Hotspot.TABLE_NAME + " ORDER BY " +
                Hotspot.COLUMN_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Hotspot hs = new Hotspot();
                hs.setLatitude(cursor.getDouble(cursor.getColumnIndex(Hotspot.COLUMN_LATITUDE)));
                hs.setLongitude(cursor.getDouble(cursor.getColumnIndex(Hotspot.COLUMN_LONGITUDE)));
                hs.setConfidence(cursor.getInt(cursor.getColumnIndex(Hotspot.COLUMN_CONFIDENCE)));
                hs.setKawasan(cursor.getString(cursor.getColumnIndex(Hotspot.COLUMN_KAWASAN)));
                hs.setTanggal(cursor.getString(cursor.getColumnIndex(Hotspot.COLUMN_TANGGAL)));
                hs.setKecamatan(cursor.getString(cursor.getColumnIndex(Hotspot.COLUMN_KECAMATAN)));
                hs.setKabupaten(cursor.getString(cursor.getColumnIndex(Hotspot.COLUMN_KABUPATEN)));
                hs.setTemp(cursor.getInt(cursor.getColumnIndex(Hotspot.COLUMN_TEMP)));

                hotspots.add(hs);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        // return notes list
        return hotspots;
    }

    public void updateHotspot(){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + Hotspot.TABLE_NAME + " WHERE " +
                Hotspot.COLUMN_ID + " > 0";

        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst())
        {
            do{
                Hotspot hs = new Hotspot();
                if(cursor.getInt(cursor.getColumnIndex(Hotspot.COLUMN_ID))>0){
                    ContentValues values = new ContentValues();
                    values.put(Hotspot.COLUMN_TEMP, Hotspot.COLUMN_TEMP+1);
                    db.update(Hotspot.TABLE_NAME, values, Hotspot.COLUMN_ID + "= ?",new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(Hotspot.COLUMN_ID)))});
                }
            }
            while (cursor.moveToNext());
        }
        db.close();
    }

    public boolean insertHotspotUpdate(double latitude, double longitude, int confidence, String kawasan, String tanggal, String kecamatan, String kabupaten, int temp) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Hotspot.COLUMN_LATITUDE, latitude);
        values.put(Hotspot.COLUMN_LONGITUDE, longitude);
        values.put(Hotspot.COLUMN_CONFIDENCE, confidence);
        values.put(Hotspot.COLUMN_KAWASAN, kawasan);
        values.put(Hotspot.COLUMN_TANGGAL, tanggal);
        values.put(Hotspot.COLUMN_KECAMATAN, kecamatan);
        values.put(Hotspot.COLUMN_KABUPATEN, kabupaten);
        values.put(Hotspot.COLUMN_TEMP, temp);

        // insert row
        long id = db.insert(Hotspot.TABLE_NAME_UPDATE, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id != -1;
    }
    public List<Hotspot> getAllHotspotUpdate() {
        List<Hotspot> hotspots = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Hotspot.TABLE_NAME_UPDATE + " ORDER BY " +
                Hotspot.COLUMN_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Hotspot hs = new Hotspot();
                hs.setLatitude(cursor.getDouble(cursor.getColumnIndex(Hotspot.COLUMN_LATITUDE)));
                hs.setLongitude(cursor.getDouble(cursor.getColumnIndex(Hotspot.COLUMN_LONGITUDE)));
                hs.setConfidence(cursor.getInt(cursor.getColumnIndex(Hotspot.COLUMN_CONFIDENCE)));
                hs.setKawasan(cursor.getString(cursor.getColumnIndex(Hotspot.COLUMN_KAWASAN)));
                hs.setTanggal(cursor.getString(cursor.getColumnIndex(Hotspot.COLUMN_TANGGAL)));
                hs.setKecamatan(cursor.getString(cursor.getColumnIndex(Hotspot.COLUMN_KECAMATAN)));
                hs.setKabupaten(cursor.getString(cursor.getColumnIndex(Hotspot.COLUMN_KABUPATEN)));
                hs.setTemp(cursor.getInt(cursor.getColumnIndex(Hotspot.COLUMN_TEMP)));

                hotspots.add(hs);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        // return notes list
        return hotspots;
    }

    public List<Hotspot> getSelectedHotspot(int i) {
        List<Hotspot> hotspots = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Hotspot.TABLE_NAME + " WHERE " +
                Hotspot.COLUMN_TEMP + "=" + i;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Hotspot hs = new Hotspot();
                hs.setLatitude(cursor.getDouble(cursor.getColumnIndex(Hotspot.COLUMN_LATITUDE)));
                hs.setLongitude(cursor.getDouble(cursor.getColumnIndex(Hotspot.COLUMN_LONGITUDE)));
                hs.setConfidence(cursor.getInt(cursor.getColumnIndex(Hotspot.COLUMN_CONFIDENCE)));
                hs.setKawasan(cursor.getString(cursor.getColumnIndex(Hotspot.COLUMN_KAWASAN)));
                hs.setTanggal(cursor.getString(cursor.getColumnIndex(Hotspot.COLUMN_TANGGAL)));
                hs.setKecamatan(cursor.getString(cursor.getColumnIndex(Hotspot.COLUMN_KECAMATAN)));
                hs.setKabupaten(cursor.getString(cursor.getColumnIndex(Hotspot.COLUMN_KABUPATEN)));
                hs.setTemp(cursor.getInt(cursor.getColumnIndex(Hotspot.COLUMN_TEMP)));

                hotspots.add(hs);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        // return notes list
        return hotspots;
    }

    public void removeAll()
    {
        SQLiteDatabase db = this.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(Hotspot.TABLE_NAME, null, null);
        db.delete(Hotspot.TABLE_NAME_UPDATE, null, null);
    }


}
