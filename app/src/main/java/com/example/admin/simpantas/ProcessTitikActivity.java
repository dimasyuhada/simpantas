package com.example.admin.simpantas;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ProcessTitikActivity extends AppCompatActivity implements Serializable{

    private String TAG = ProcessTitikActivity.class.getSimpleName();

    Button btnInput,btnVisual;
    private TextView strCount;
    private int inputCount;
    private DBHelper db;
//    private List<Hotspot> hpListDB = new ArrayList<>();
    private List<Hotspot> hpListView = new ArrayList<>();
    private List<Hotspot> hpListDay1 = new ArrayList<>();
    private List<Hotspot> hpListDay2 = new ArrayList<>();
    private List<Hotspot> hpListDay3 = new ArrayList<>();
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_titik);
        db = new DBHelper(this);
        db.removeAll();

        btnInput = (Button) findViewById(R.id.btnInput);
        btnVisual = (Button) findViewById(R.id.btnVisualisasi);
        strCount = (TextView) findViewById(R.id.resultInput);

        btnVisual.setVisibility(View.GONE);
        strCount.setVisibility(View.GONE);

        btnInput.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            public void onClick(View v) {
                new GetQuery().execute();
            }
        });

        btnVisual.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                processQuerySequence();

            }
        });
    }

    private void inputDummy(double lat, double lng, int conf, String kws, String tgl, String kec, String kab, int tmp) {
        boolean result = db.insertHotspot(lat,lng,conf,kws,tgl,kec,kab,tmp);
        if(result){
            Log.d("Input Hotspot","Success: "+result);
            inputCount += 1;
        }else{
            Log.d("Input Hotspot","Error: "+result);
        }
    }

    private void inputDummyUpdate(double lat, double lng, int conf, String kws, String tgl, String kec, String kab, int tmp) {
        boolean result = db.insertHotspotUpdate(lat,lng,conf,kws,tgl,kec,kab,tmp);
        if(result){
            Log.d("Input Hotspot BARU","Success: "+result);
            inputCount += 1;
        }else{
            Log.d("Input Hotspot BARU","Error: "+result);
        }
    }

    private void processQuerySequence() {
        //hpListDB.addAll(db.getAllHotspot());
        Intent i = new Intent(ProcessTitikActivity.this, VisualisasiActivity.class);
        i.putExtra("resultObject", (Serializable) hpListView);
        startActivity(i);
    }


    private class GetQuery extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ProcessTitikActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // DUMMY HARI 0
            inputDummyUpdate(0.465,101.436,80,"Pekanbaru","18 Desember 2018","Bukit Raya","Kota Pekanbaru",0);
            inputDummyUpdate(0.54,101.44,70,"Pekanbaru","18 Desember 2018","Lima Puluh","Kota Pekanbaru",0);
            inputDummyUpdate(0.616,101.321,85,"Pekanbaru","18 Desember 2018","Rumbai","Kota Pekanbaru",0);
            inputDummyUpdate(0.513,101.451,72,"Pekanbaru","18 Desember 2018","Sail","Kota Pekanbaru",0);
            inputDummyUpdate(0.461,101.355,50,"Pekanbaru","18 Desember 2018","Tampan","Kota Pekanbaru",0);

            // DUMMY HARI 1
            inputDummy(0.465,101.436,80,"Pekanbaru","19 Desember 2018","Bukit Raya","Kota Pekanbaru",1);
            inputDummy(0.6,101.437,85,"Pekanbaru","19 Desember 2018","Rumbai Pesisir","Kota Pekanbaru",1);
            inputDummy(0.536,101.429,90,"Pekanbaru","19 Desember 2018","Senapelan","Kota Pekanbaru",1);
            inputDummy(0.461,101.355,50,"Pekanbaru","19 Desember 2018","Tampan","Kota Pekanbaru",1);
            inputDummy(0.513,101.451,72,"Pekanbaru","20 Desember 2018","Sail","Kota Pekanbaru",2);

            // DUMMY HARI 2
            inputDummy(0.465,101.436,80,"Pekanbaru","20 Desember 2018","Bukit Raya","Kota Pekanbaru",2);
            inputDummy(0.54,101.44,70,"Pekanbaru","20 Desember 2018","Lima Puluh","Kota Pekanbaru",2);
            inputDummy(0.616,101.321,85,"Pekanbaru","20 Desember 2018","Rumbai","Kota Pekanbaru",2);
            inputDummy(0.536,101.429,50,"Pekanbaru","20 Desember 2018","Senapelan","Kota Pekanbaru",2);

            // DUMMY HARI 3
            inputDummy(0.465,101.436,80,"Pekanbaru","21 Desember 2018","Bukit Raya","Kota Pekanbaru",3);
            inputDummy(0.54,101.44,70,"Pekanbaru","21 Desember 2018","Lima Puluh","Kota Pekanbaru",3);
            inputDummy(0.616,101.321,85,"Pekanbaru","21 Desember 2018","Rumbai","Kota Pekanbaru",3);
            inputDummy(0.513,101.451,72,"Pekanbaru","21 Desember 2018","Sail","Kota Pekanbaru",3);
            inputDummy(0.536,101.429,50,"Pekanbaru","21 Desember 2018","Senapelan","Kota Pekanbaru",3);

            try{
//                hpListDB.addAll(db.getAllHotspot());
//                for(int i=0; i<hpListView.size(); i++){
//                    for (int j=0;j<hpListDB.size(); j++){
//                        if((hpListView.get(i).getLatitude() == hpListDB.get(j).getLatitude()) && (hpListView.get(i).getLongitude() == hpListDB.get(j).getLongitude())){
//                             if (hpListDB.get(j).getTemp() == 1){
//                                 hpListView.get(i).setTemp(1);
//                                 break;
//                             }
//                             else if (hpListDB.get(j).getTemp() == 2){
//                                 hpListView.get(i).setTemp(2);
//                                 break;
//                             }
//                             else if (hpListDB.get(j).getTemp() == 3){
//                                 hpListView.get(i).setTemp(3);
//                                 break;
//                             }
//                        }
//                    }
//                }
                hpListView.addAll(db.getAllHotspotUpdate());
                hpListDay1.addAll(db.getSelectedHotspot(1));
                hpListDay2.addAll(db.getSelectedHotspot(2));
                hpListDay3.addAll(db.getSelectedHotspot(3));
                for(int i=0; i<hpListView.size(); i++) {
                    for(int j=0; j<hpListDay1.size(); j++){
                        if ((Double.compare(hpListView.get(i).getLatitude(),hpListDay1.get(j).getLatitude())==0) && (Double.compare(hpListView.get(i).getLongitude(),hpListDay1.get(j).getLongitude())==0)){
                            hpListView.get(i).setTemp(1);
                            for(int k=0; k<hpListDay2.size(); k++){
                                if ((Double.compare(hpListView.get(i).getLatitude(),hpListDay2.get(k).getLatitude())==0) && (Double.compare(hpListView.get(i).getLongitude(),hpListDay2.get(k).getLongitude())==0)){
                                    hpListView.get(i).setTemp(2);
                                    for(int l=0; l<hpListDay3.size(); l++){
                                        if ((Double.compare(hpListView.get(i).getLatitude(),hpListDay3.get(l).getLatitude())==0) && (Double.compare(hpListView.get(i).getLongitude(),hpListDay3.get(l).getLongitude())==0)){
                                            hpListView.get(i).setTemp(3);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }catch (Exception e){
                Log.e(TAG, "Query error : "+ e.getMessage());
                Toast.makeText(getApplicationContext(), "Query error : "+ e.getMessage(), Toast.LENGTH_LONG).show();
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
            strCount.setText("Jumlah Row bertambah : "+ inputCount);
            strCount.setVisibility(View.VISIBLE);
            btnVisual.setVisibility(View.VISIBLE);

        }

    }
}
