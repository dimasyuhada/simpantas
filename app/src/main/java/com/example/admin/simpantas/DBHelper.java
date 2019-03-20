package com.example.admin.simpantas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
        db.execSQL(TitikFilter.CREATE_TABLE);
        db.execSQL(Transform.CREATE_TABLE);
        db.execSQL(Tanggal.CREATE_TABLE);
        db.execSQL(Tspade.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Titik.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Hotspot.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Hotspot.TABLE_NAME_UPDATE);
        db.execSQL("DROP TABLE IF EXISTS " + TitikFilter.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Transform.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Tanggal.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Tspade.TABLE_NAME);
        onCreate(db);
    }
    // CHECK AND UPDATE TANGGAL
    public boolean initTanggal(String tgl) {
        boolean checkDate = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT * FROM " + Tanggal.TABLE_NAME;

        Cursor cursor = db.rawQuery(count, null);
        if (cursor!=null){
            cursor.moveToFirst();
            if (cursor.getCount() ==0) {
                ContentValues values = new ContentValues();
                values.put(Tanggal.COLUMN_TANGGAL, tgl);
                db.insert(Tanggal.TABLE_NAME, null, values);
            }else{
                String dateDb = cursor.getString(cursor.getColumnIndex(Tanggal.COLUMN_TANGGAL));
                if (!dateDb.equals(tgl))
                {
                    db.delete(Tanggal.TABLE_NAME, null, null);
                    ContentValues values = new ContentValues();
                    values.put(Tanggal.COLUMN_TANGGAL, tgl);
                    db.insert(Tanggal.TABLE_NAME, null, values);
                    checkDate = true;
                }
            }
        }
        db.close();
        return checkDate;
    }

    //RETRACTED TABLE
    public void removeTitik()
    {
        SQLiteDatabase db = this.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(Titik.TABLE_NAME, null, null);
        db.delete(TitikFilter.TABLE_NAME, null, null);
        db.delete(Transform.TABLE_NAME, null, null);
    }
    public void removeHotspotUpdate()
    {
        SQLiteDatabase db = this.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(Hotspot.TABLE_NAME_UPDATE, null, null);
    }
    public void removeTransform()
    {
        SQLiteDatabase db = this.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(Transform.TABLE_NAME, null, null);
    }

    public void removeTspade()
    {
        SQLiteDatabase db = this.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(Tspade.TABLE_NAME, null, null);
    }

    //TITIK
    public boolean insertTitik(double latitude, double longitude, int unixDate, int unixDateTime, String tanggal, String provinsi, String kabupaten, String kecamatan, String desa, String bulan, String tahun) {
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
        values.put(Titik.COLUMN_BULAN, bulan);
        values.put(Titik.COLUMN_TAHUN, tahun);

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

    //TITIKFILTER
    public  ArrayList<HashMap<String, String>> getTitikForFilter(String bulanValue, String tahunValue){
        ArrayList<HashMap<String, String>> titikFilter = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT A."+Titik.COLUMN_LATITUDE+",A."+Titik.COLUMN_LONGITUDE+",A."+Titik.COLUMN_UNIXDATE+",A."+Titik.COLUMN_UNIXDATETIME+",A."+Titik.COLUMN_TANGGAL+","+Titik.COLUMN_ID+",A."+Titik.COLUMN_BULAN+",A."+Titik.COLUMN_TAHUN+" FROM "+Titik.TABLE_NAME+
                " A JOIN (SELECT count(*) as count,latitude,longitude,unixdate,unixdatetime,bulan,tahun from "+Titik.TABLE_NAME+" WHERE bulan ='"+bulanValue+"' AND tahun ="+tahunValue+" GROUP BY latitude,longitude having count >=2) B on A."+Titik.COLUMN_LATITUDE+"=B."+Titik.COLUMN_LATITUDE+" AND A."+Titik.COLUMN_LONGITUDE+"=B."+Titik.COLUMN_LONGITUDE+" AND A."+Titik.COLUMN_BULAN+"=B."+Titik.COLUMN_BULAN+" AND A."+Titik.COLUMN_TAHUN+"=B."+Titik.COLUMN_TAHUN+
                " ORDER BY A."+Titik.COLUMN_LATITUDE+",A."+Titik.COLUMN_LONGITUDE+",A."+Titik.COLUMN_UNIXDATE+",A."+Titik.COLUMN_UNIXDATETIME ;
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String,String> tf = new HashMap<>();
                tf.put("latitude",cursor.getString(cursor.getColumnIndex(Titik.COLUMN_LATITUDE)));
                tf.put("longitude",cursor.getString(cursor.getColumnIndex(Titik.COLUMN_LONGITUDE)));
                tf.put("unixdate",cursor.getString(cursor.getColumnIndex(Titik.COLUMN_UNIXDATE)));
                tf.put("unixdatetime",cursor.getString(cursor.getColumnIndex(Titik.COLUMN_UNIXDATETIME)));
                tf.put("tanggal",cursor.getString(cursor.getColumnIndex(Titik.COLUMN_TANGGAL)));
                titikFilter.add(tf);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();

        return titikFilter;
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
        String selectQuery = "SELECT * FROM " + Hotspot.TABLE_NAME + " WHERE " +
                Hotspot.COLUMN_ID + " > 0";

        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst())
        {
            do{
                Hotspot hs = new Hotspot();
                if(cursor.getInt(cursor.getColumnIndex(Hotspot.COLUMN_ID))>0){
                    ContentValues values = new ContentValues();
                    values.put(Hotspot.COLUMN_TEMP, cursor.getInt(cursor.getColumnIndex(Hotspot.COLUMN_TEMP))+1);
                    db.update(Hotspot.TABLE_NAME, values, Hotspot.COLUMN_ID + "= ?",new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(Hotspot.COLUMN_ID)))});
                }
            }
            while (cursor.moveToNext());
        }
        db.close();
        Log.d("Test Flow", "MASUK SINI GA 2");
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

    public void insertHotspotUpdateWithArray(HashMap<String, String> hs) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        double latitude = 0; double longitude = 0;
        int confidence = 0; String strConfidence = "";

        latitude = Double.parseDouble(hs.get("latitude"));
        longitude = Double.parseDouble(hs.get("longitude"));

        strConfidence = hs.get("confidence").substring(0,hs.get("confidence").length()-1);
        confidence = Integer.parseInt(strConfidence);

        ContentValues values = new ContentValues();
        values.put(Hotspot.COLUMN_LATITUDE, latitude);
        values.put(Hotspot.COLUMN_LONGITUDE, longitude);
        values.put(Hotspot.COLUMN_CONFIDENCE, confidence);
        values.put(Hotspot.COLUMN_KAWASAN, hs.get("kawasan"));
        values.put(Hotspot.COLUMN_TANGGAL, hs.get("tanggal"));
        values.put(Hotspot.COLUMN_KECAMATAN, hs.get("kecamatan"));
        values.put(Hotspot.COLUMN_KABUPATEN, hs.get("kabupatenKota"));
        values.put(Hotspot.COLUMN_PROVINSI, hs.get("provinsi"));
        values.put(Hotspot.COLUMN_TEMP, 0);

        // insert row
        db.insert(Hotspot.TABLE_NAME_UPDATE, null, values);

        // close db connection
        db.close();
    }

    public List<Hotspot> getAllHotspotUpdate() {
        List<Hotspot> hotspots = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + Hotspot.TABLE_NAME_UPDATE + " ORDER BY " +
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
                hs.setProvinsi(cursor.getString(cursor.getColumnIndex(Hotspot.COLUMN_PROVINSI)));
                hs.setTemp(cursor.getInt(cursor.getColumnIndex(Hotspot.COLUMN_TEMP)));
                hotspots.add(hs);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        // return notes list
        Log.d("Get : ",""+ hotspots);
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

    public void migrateHotspotData(List<Hotspot> hs){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (Hotspot hotspot : hs){
            values.put(Hotspot.COLUMN_LATITUDE, hotspot.getLatitude());
            values.put(Hotspot.COLUMN_LONGITUDE, hotspot.getLongitude());
            values.put(Hotspot.COLUMN_CONFIDENCE, hotspot.getConfidence());
            values.put(Hotspot.COLUMN_KAWASAN, hotspot.getKawasan());
            values.put(Hotspot.COLUMN_TANGGAL, hotspot.getTanggal());
            values.put(Hotspot.COLUMN_KECAMATAN, hotspot.getKecamatan());
            values.put(Hotspot.COLUMN_KABUPATEN, hotspot.getKabupaten());
            values.put(Hotspot.COLUMN_PROVINSI, hotspot.getProvinsi());
            values.put(Hotspot.COLUMN_TEMP, 1);
            Log.d("Added ",""+ values);
            db.insert(Hotspot.TABLE_NAME, null, values);
        }
        db.close();
        Log.d("Test Flow", "MASUK SINI GA 3");
    }

    //TRANSFORM
    public boolean insertTransform(int sid, String item, String bulan, String tahun) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Transform.COLUMN_SID, sid);
        values.put(Transform.COLUMN_ITEM, item);
        values.put(Transform.COLUMN_BULAN, bulan);
        values.put(Transform.COLUMN_TAHUN, tahun);

        // insert row
        long id = db.insert(Transform.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id != -1;
    }

    public boolean exportTransform() {
        boolean statsInput = true;
        File file = null;
        File exportFile = new File(Environment.getExternalStorageDirectory()+"/dbSimpantas","transform");
        if (!exportFile.exists())
        {
            exportFile.mkdirs();
        }
        file = new File(exportFile,"transform-data.csv");
        try{
            file.createNewFile();
            CSVWriter csvWriter = new CSVWriter(new FileWriter(file));
            SQLiteDatabase db = this.getReadableDatabase();
            String selectQuery = "SELECT * FROM " + Transform.TABLE_NAME;
            Cursor c = db.rawQuery(selectQuery,null);
            while(c.moveToNext()){
                String rowData = c.getString(1);
                rowData = rowData.replaceAll("\"","");
                String arrStr[] = {rowData};
                csvWriter.writeNext(arrStr,false);
                Log.d("VALUE INPUTNYA ", Arrays.toString(arrStr));
            }
            csvWriter.close();
            c.close();

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Export Transform", e.getMessage(), e);
            statsInput = false;
        }
        return statsInput;
    }



    //TSPADE
    public void insertTspade(HashMap<String, String> ts) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Tspade.COLUMN_UNIXDATETIME, ts.get("unixdatetime"));
        values.put(Tspade.COLUMN_BULAN, ts.get("month"));
        values.put(Tspade.COLUMN_TAHUN, ts.get("year"));

        // insert row
        db.insert(Tspade.TABLE_NAME, null, values);

        // close db connection
        db.close();
    }

    public List<Tspade> getTspadeByDate(String m, String y) {
        List<Tspade> spades = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Tspade.TABLE_NAME + " WHERE " +
                Tspade.COLUMN_BULAN + "=" + m + " AND " + Tspade.COLUMN_TAHUN + "=" + y;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Tspade t = new Tspade();
                t.setUnixdatetime(cursor.getString(cursor.getColumnIndex(Tspade.COLUMN_UNIXDATETIME)));
                t.setBulan(cursor.getString(cursor.getColumnIndex(Tspade.COLUMN_BULAN)));
                t.setTahun(cursor.getString(cursor.getColumnIndex(Tspade.COLUMN_TAHUN)));
                spades.add(t);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        // return notes list
        return spades;
    }



}
