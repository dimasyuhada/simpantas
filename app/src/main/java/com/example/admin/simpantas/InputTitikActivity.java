package com.example.admin.simpantas;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class InputTitikActivity extends AppCompatActivity{

    private List<Titik> titiks = new ArrayList<>();
    private static final String FORMAT_TIME = "hh:mm:ss a";

    Button btnProcess;
    Spinner spinnerTahun;
    Spinner spinnerBulan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_titik);
        spinnerTahun = (Spinner) findViewById(R.id.spinnerTahun);
        spinnerBulan = (Spinner) findViewById(R.id.spinnerBulan);
        btnProcess = (Button) findViewById(R.id.btnProcess);


        ArrayAdapter<CharSequence> aa = ArrayAdapter.createFromResource(this, R.array.arrayTahun, R.layout.support_simple_spinner_dropdown_item);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTahun.setAdapter(aa);

        ArrayAdapter<CharSequence> ab = ArrayAdapter.createFromResource(this, R.array.arrayBulan, R.layout.support_simple_spinner_dropdown_item);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBulan.setAdapter(ab);

        btnProcess.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                processReadCSV();

            }
        });
    }

    private void processReadCSV() {
//        String pickTahun = spinnerTahun.getSelectedItem().toString();
//        System.out.println(pickTahun);
//        try {
//            String csvFileString = this.getApplicationInfo().dataDir+pickTahun+".csv";
//            File csvFile = new File(csvFileString);
//            CSVReader reader = new CSVReader(new FileReader(pickTahun+".csv"));
//            String [] nextLine;
//            while ((nextLine = reader.readNext()) != null){
//                System.out.println(nextLine[0] + nextLine[1] + "dll..");
//            }
//
//        } catch (java.io.IOException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "FILE NYA GA KETEMU GBLG!!", Toast.LENGTH_SHORT).show();
//        }

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_TIME);

        InputStream is = getResources().openRawResource(R.raw.tahun2017);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line = "";
        try{

            reader.readLine();
            while((line = reader.readLine()) != null){
                //BAGI DATANYA DENGAN TANDA KOMA
                String[] tokens = line.split(",");

                //Baca CSV nya.
                Titik readTitik = new Titik();
                readTitik.setLatitude(Double.
                        parseDouble(tokens[0]));
                readTitik.setLongitude(Double.parseDouble(tokens[1]));
                readTitik.setUnixTime(100);
                readTitik.setTanggal(tokens[3]);
                readTitik.setProvinsi(tokens[4]);
                readTitik.setKabupaten(tokens[5]);
                readTitik.setKecamatan(tokens[6]);
                readTitik.setDesa(tokens[7]);
                titiks.add(readTitik);

                Log.d("InputActivity","Just Created: "+readTitik);

            }
        } catch(IOException e){
            Log.wtf("InputActivity","Error Reading on the line " + line, e);
            e.printStackTrace();
        }
    }
}
