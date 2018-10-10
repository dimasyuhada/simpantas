package com.example.admin.simpantas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ProcessTitikActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_titik);
        
        readTitik();
    }

    private List<Titik> titiks = new ArrayList<>();

    private void readTitik() {
        InputStream is = getResources().openRawResource(R.raw.tahun2017);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line = "";
        try{
            while((line = reader.readLine()) != null){
                //BAGI DATANYA DENGAN TANDA KOMA
                String[] tokens = line.split(",");

                //Baca CSV nya.
                Titik readTitik = new Titik();
                readTitik.setLatitude(Double.parseDouble(tokens[0]));
                readTitik.setLongitude(Double.parseDouble(tokens[1]));
                readTitik.setUnixDate(Integer.parseInt(tokens[2]));
                readTitik.setUnixTime(Integer.parseInt(tokens[3]));
                readTitik.setTanggal(tokens[4]);
                readTitik.setProvinsi(tokens[5]);
                readTitik.setKabupaten(tokens[6]);
                readTitik.setKecamatan(tokens[7]);
                readTitik.setDesa(tokens[8]);
                titiks.add(readTitik);

                Log.d("ProcessActivity","Just Created: "+readTitik);

            }
        } catch(IOException e){
            Log.wtf("ProcessActivity","Error Reading on the line " + line, e);
            e.printStackTrace();
        }
    }
}
