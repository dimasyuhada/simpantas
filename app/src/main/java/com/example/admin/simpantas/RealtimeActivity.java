package com.example.admin.simpantas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RealtimeActivity extends AppCompatActivity {

    private String TAG = RealtimeActivity.class.getSimpleName();
    private DBHelper db;
    private ProgressDialog pDialog;
    private ListView lv;
    private Button bMap;
    private int inputCount;
    private String recentDate = "";
    private TextView showDate;
    private List<Hotspot> todayHotspot = new ArrayList<>();

    private List<Hotspot> hpListDay1 = new ArrayList<>();
    private List<Hotspot> hpListDay2 = new ArrayList<>();
    private List<Hotspot> hpListDay3 = new ArrayList<>();
    private List<Hotspot> hpListDay4 = new ArrayList<>();

    // URL to get contacts JSON (CONTOH DARI ANDROIDHIVE)
    private static String url = "http://sipongi.menlhk.go.id/action/indohotspot";

    ArrayList<HashMap<String, String>> coordinateList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime);
        db = new DBHelper(this);

        coordinateList = new ArrayList<HashMap<String, String>>();
        lv = (ListView) findViewById(R.id.list);
        bMap = (Button) findViewById(R.id.btnMap);
        showDate = (TextView) findViewById(R.id.strDate);

        //CEK TANGGAL UNTUK PEMBAHARUAN DATA TITIK
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        recentDate = df.format(c);
        showDate.setText(recentDate);

//        recentDate = "14-Jun-1994";
        boolean newDate = db.initTanggal(recentDate);
        if(newDate) {
            migrateData();
        }else{
            db.removeHotspotUpdate();
        }
        new GetHotspot().execute();
        bMap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                processSequence();
                Intent i = new Intent(RealtimeActivity.this, VisualisasiActivity.class);
                int x = 2;
                i.putExtra("resultObject", (Serializable) todayHotspot);
                i.putExtra("menuIdentifier",x);
                startActivity(i);
            }
        });

    }

    private void migrateData() {
        //AMBIL SEMUA DATA PADA TABLE HOTSPOT UPDATE TERAKHIR
        todayHotspot.addAll(db.getAllHotspotUpdate());
        Log.e("List Today : ",todayHotspot.toString());
        // UPDATE TEMP PADA TABLE HOTSPOT
        db.updateHotspot();
        // PINDAHIN ISI DARI TABLE HOTSPOTUPDATE KE TABLE HOTSPOT
        db.migrateHotspotData(todayHotspot);
        // HAPUS DATA PADA TABLE HOTSPOTUPDATE
        db.removeHotspotUpdate();
    }

    private class GetHotspot extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(RealtimeActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            JsonParser jp = new JsonParser();

            //MAKING REQUEST
            String jsonStr = jp.getJsonData("http://sipongi.menlhk.go.id/action/indohotspot");

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null){
                bMap.setVisibility(View.VISIBLE);
                try{
                    JSONObject jsonObject = new JSONObject(jsonStr);

                    JSONObject dataTitik = jsonObject.getJSONObject("data");
                    JSONObject dataUpdate = dataTitik.getJSONObject("lastupdate");
                    JSONArray dataHotspot = dataTitik.getJSONArray("hotspot");

                    if(dataHotspot!=null){
                        for (int i=0;i<dataHotspot.length() ; i++) {
                            JSONArray val = dataHotspot.getJSONArray(i);
                            String lat = val.getString(0);
                            String ltd = val.getString(1);
                            String info = val.getString(2);

                            DecimalFormat df = new DecimalFormat("#.000");
                            Double dLat = Double.parseDouble(lat);
                            Double dLtd = Double.parseDouble(ltd);
                            df.setRoundingMode(RoundingMode.CEILING);
                            dLat = Double.parseDouble(df.format(dLat));
                            dLtd = Double.parseDouble(df.format(dLtd));

                            lat = Double.toString(dLat);
                            ltd = Double.toString(dLtd);

                            Log.d("Round Result","lat : "+lat+" ltd : "+ltd);

                            String[] replaceInformation = cleanInfo(info);

                            //Log.d(TAG, "VALUE INFORMASI: "+replaceInformation);

                            HashMap<String, String> koordinat = new HashMap<>();

                            koordinat.put("latitude", lat);
                            koordinat.put("longitude", ltd);
                            if(replaceInformation[1]==null){
                                koordinat.put("tanggal", "NA");
                            }else{
                                koordinat.put("tanggal", replaceInformation[1]);
                            }
                            if(replaceInformation[4].equals("")){
                                koordinat.put("confidence", "NA");
                            }else{
                                koordinat.put("confidence", replaceInformation[4]);
                            }
                            if(replaceInformation[5].equals("")){
                                koordinat.put("kawasan", "NA");
                            }else{
                                koordinat.put("kawasan", replaceInformation[5]);
                            }
                            if(replaceInformation[6].equals("")){
                                koordinat.put("desa", "NA");
                            }else{
                                koordinat.put("desa", replaceInformation[6]);
                            }
                            if(replaceInformation[7].equals("")){
                                koordinat.put("kecamatan", "NA");
                            }else{
                                koordinat.put("kecamatan", replaceInformation[7]);
                            }
                            if(replaceInformation[8].equals("")){
                                koordinat.put("kabupatenKota", "NA");
                            }else{
                                koordinat.put("kabupatenKota", replaceInformation[8]);
                            }
                            if(replaceInformation[9].equals("")){
                                koordinat.put("provinsi", "NA");
                            }else{
                                koordinat.put("provinsi", replaceInformation[9]);
                            }
                            // adding contact to contact list
                            coordinateList.add(koordinat);
                            db.insertHotspotUpdateWithArray(koordinat);
                        }
                        for (int x=0;x<coordinateList.size();x++){
                            coordinateList.get(x).put("latitude","Latitude : "+coordinateList.get(x).get("latitude"));
                            coordinateList.get(x).put("longitude","Longitude : "+coordinateList.get(x).get("longitude"));
                            coordinateList.get(x).put("tanggal","Tanggal (yyyymmdd): "+coordinateList.get(x).get("tanggal"));
                            coordinateList.get(x).put("confidence","Confidence : "+coordinateList.get(x).get("confidence"));
                            coordinateList.get(x).put("kawasan","Kawasan : "+coordinateList.get(x).get("kawasan"));
                            coordinateList.get(x).put("desa","Desa : "+coordinateList.get(x).get("desa"));
                            coordinateList.get(x).put("kecamatan","Kecamatan : "+coordinateList.get(x).get("kecamatan"));
                            coordinateList.get(x).put("kabupatenKota","Kabupaten/Kota : "+coordinateList.get(x).get("kabupatenKota"));
                            coordinateList.get(x).put("provinsi","Provinsi : "+coordinateList.get(x).get("provinsi"));
                        }

                    }else{
                        bMap.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),
                                "Tidak Ada Hotspot Hari Ini",
                                Toast.LENGTH_LONG)
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    RealtimeActivity.this, coordinateList,
                    R.layout.list_item, new String[]{"latitude", "longitude", "tanggal", "confidence", "kawasan", "desa", "kecamatan", "kabupatenKota", "provinsi"}, new int[]{R.id.latitude,
                    R.id.longitude, R.id.informasiTanggal, R.id.confidence, R.id.kawasan, R.id.desa, R.id.kecamatan, R.id.kabupatenKota, R.id.provinsi});

            lv.setAdapter(adapter);
        }
    }

    private String[] cleanInfo(String inf){
        String replaceInfo=inf.replaceAll("<td>","");
        replaceInfo=replaceInfo.replaceAll("</td>","");
        replaceInfo=replaceInfo.replaceAll("<b>","");
        replaceInfo=replaceInfo.replaceAll("</b>","");
        replaceInfo=replaceInfo.replaceAll("<tr>","");
        replaceInfo=replaceInfo.replaceAll("</tr>","");
        replaceInfo=replaceInfo.replaceAll("<td>","");
        replaceInfo=replaceInfo.replaceAll("</td>","");
        replaceInfo=replaceInfo.replaceAll("<strong>","");
        replaceInfo=replaceInfo.replaceAll("</strong>","");
        replaceInfo=replaceInfo.replaceAll("<table style='background-color:transparent'>","");
        replaceInfo=replaceInfo.replaceAll("<div>","");
        replaceInfo=replaceInfo.replaceAll("</div>","");
        replaceInfo=replaceInfo.replaceAll("<div>","");
        replaceInfo=replaceInfo.replaceAll("</div>","");
        replaceInfo=replaceInfo.replaceAll("<u>","");
        replaceInfo=replaceInfo.replaceAll("</u>","");
        replaceInfo=replaceInfo.replaceAll("</table>","");
        replaceInfo=replaceInfo.replaceAll("</span>","");
        replaceInfo=replaceInfo.replaceAll("<div style='color:#000'>","");
        replaceInfo=replaceInfo.replaceAll("<div style='color:#000'>NOAA19 ","");
        replaceInfo=replaceInfo.replaceAll("<div style='color:#000'>TERRA ","");
        replaceInfo=replaceInfo.replaceAll("<div style='color:#000'>AQUA ","");
        replaceInfo=replaceInfo.replaceAll("\"","");
        replaceInfo=replaceInfo.replaceAll("<td width=2px align=center>","");
        replaceInfo=replaceInfo.replaceAll("<td width=20px align=center>","");
        replaceInfo=replaceInfo.replaceAll("<span class=balloon-podes style=cursor:pointer;>","");
        replaceInfo=replaceInfo.replaceAll("ASMC","");
        replaceInfo=replaceInfo.replaceAll("LAPAN","");
        replaceInfo=replaceInfo.replaceAll("AQUA","");
        replaceInfo=replaceInfo.replaceAll("TERRA","");
        replaceInfo=replaceInfo.replaceAll("NPP","");
        replaceInfo=replaceInfo.replaceAll("Tanggal","");
        replaceInfo=replaceInfo.replaceAll("Latitude","");
        replaceInfo=replaceInfo.replaceAll("Longitude","");
        replaceInfo=replaceInfo.replaceAll("Confidence","");
        replaceInfo=replaceInfo.replaceAll("Kawasan","");
        replaceInfo=replaceInfo.replaceAll("Desa","");
        replaceInfo=replaceInfo.replaceAll("Kecamatan","");
        replaceInfo=replaceInfo.replaceAll("Kota/Kabupaten","");
        replaceInfo=replaceInfo.replaceAll("Provinsi","");


        String[] valInfo = replaceInfo.split("  : ");
        String val1 = valInfo[1];
        String val2 = valInfo[2];
        String val3 = valInfo[3];
        String val4 = valInfo[4];
        String val5 = valInfo[5];
        String val6 = valInfo[6];
        String val7 = valInfo[7];
        String val8 = valInfo[8];
        String val9 = valInfo[9];

        Log.d(TAG,"HASIL SPLIT :"+val1+","+val2+","+val3+","+val4+","+val5+","+val6+","+val7+","+val8+","+val9);
        return valInfo;
    }

    private void processSequence() {
        todayHotspot.addAll(db.getAllHotspotUpdate());
        hpListDay1.addAll(db.getSelectedHotspot(1));
        hpListDay2.addAll(db.getSelectedHotspot(2));
        hpListDay3.addAll(db.getSelectedHotspot(3));
        hpListDay4.addAll(db.getSelectedHotspot(4));
        for(int i=0; i<todayHotspot.size(); i++) {
            for(int j=0; j<hpListDay1.size(); j++){
                if ((Double.compare(todayHotspot.get(i).getLatitude(),hpListDay1.get(j).getLatitude())==0) && (Double.compare(todayHotspot.get(i).getLongitude(),hpListDay1.get(j).getLongitude())==0)){
                    todayHotspot.get(i).setTemp(1);
                    for(int k=0; k<hpListDay2.size(); k++){
                        if ((Double.compare(todayHotspot.get(i).getLatitude(),hpListDay2.get(k).getLatitude())==0) && (Double.compare(todayHotspot.get(i).getLongitude(),hpListDay2.get(k).getLongitude())==0)){
                            todayHotspot.get(i).setTemp(2);
                            for(int l=0; l<hpListDay3.size(); l++){
                                if ((Double.compare(todayHotspot.get(i).getLatitude(),hpListDay3.get(l).getLatitude())==0) && (Double.compare(todayHotspot.get(i).getLongitude(),hpListDay3.get(l).getLongitude())==0)){
                                    todayHotspot.get(i).setTemp(3);
                                    for(int m=0; m<hpListDay4.size(); m++){
                                        if ((Double.compare(todayHotspot.get(i).getLatitude(),hpListDay4.get(m).getLatitude())==0) && (Double.compare(todayHotspot.get(i).getLongitude(),hpListDay4.get(m).getLongitude())==0)){
                                            todayHotspot.get(i).setTemp(4);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

