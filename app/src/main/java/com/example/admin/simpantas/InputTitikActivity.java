package com.example.admin.simpantas;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.siegmar.fastcsv.reader.CsvParser;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;


public class InputTitikActivity extends AppCompatActivity{

    private List<Titik> titiks = new ArrayList<>();

    String time ="",strTahun = "",strBulan = "";
//    ProgressDialog pDialog;
    TextView lblTitle;
    Button btnProcess, btnPattern, btnCari;
    Spinner spinnerTahun, spinnerBulan;
    File file;

    private DBHelper db;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_titik);

        lblTitle = (TextView) findViewById(R.id.titlePage);
        spinnerBulan = (Spinner) findViewById(R.id.spinnerBulan);
        spinnerTahun = (Spinner) findViewById(R.id.spinnerTahun);
        btnCari = (Button) findViewById(R.id.btnCari);
        btnProcess = (Button) findViewById(R.id.btnProses);
        btnPattern = (Button) findViewById(R.id.btnPattern);

        db = new DBHelper(this);
        Intent intent = getIntent();
        int valTemp = intent.getExtras().getInt("temp menu");
        Log.d("temp value",String.valueOf(valTemp));

        ProgressDialog pd = new ProgressDialog(InputTitikActivity.this);

        ArrayAdapter<CharSequence> aa = ArrayAdapter.createFromResource(this, R.array.arrayTahun, R.layout.support_simple_spinner_dropdown_item);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTahun.setAdapter(aa);
        ArrayAdapter<CharSequence> ab = ArrayAdapter.createFromResource(this, R.array.arrayBulan, R.layout.support_simple_spinner_dropdown_item);
        ab.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBulan.setAdapter(ab);

        if(valTemp==1){
            lblTitle.setText("INPUT BARU");
            btnCari.setVisibility(View.GONE);
            btnPattern.setVisibility(View.GONE);
        }else{
            lblTitle.setText("LIHAT SEKUENS");
            btnProcess.setVisibility(View.GONE);
            btnPattern.setVisibility(View.GONE);
        }

        btnProcess.setOnClickListener(v -> {
            file = new File(Environment.getExternalStorageDirectory()+"/dbSimpantas/",ab.getItem(spinnerBulan.getSelectedItemPosition())+""+aa.getItem(spinnerTahun.getSelectedItemPosition())+".csv");
            Log.d("FILE NAME",String.valueOf(file));
            if (file.exists()){
                strTahun = spinnerTahun.getSelectedItem().toString();
                strBulan = spinnerBulan.getSelectedItem().toString();
                btnProcess.setVisibility(View.GONE);

                pd.setMessage("Please Wait...");
                pd.setCancelable(false);
                pd.show();
//                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        processReadCSV(file);
                        pd.dismiss();
                    }
                }).start();
                btnPattern.setVisibility(View.VISIBLE);
            }else{
                Toast.makeText(InputTitikActivity.this, "file didn't exist. Check dataSimpantas folder again.", Toast.LENGTH_SHORT).show();
            }
        });
        btnPattern.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent titik = new Intent( InputTitikActivity.this, PolaSekuensActivity.class);
                titik.putExtra("tahunValue",aa.getItem(spinnerTahun.getSelectedItemPosition()));
                titik.putExtra("bulanValue",ab.getItem(spinnerBulan.getSelectedItemPosition()));
                titik.putExtra("state",1);
                startActivity(titik);
            }
        });

        btnCari.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent titik = new Intent( InputTitikActivity.this, SpadeResultActivity.class);
                titik.putExtra("tahunValue",aa.getItem(spinnerTahun.getSelectedItemPosition()));
                titik.putExtra("bulanValue",ab.getItem(spinnerBulan.getSelectedItemPosition()));
                titik.putExtra("state",2);
                startActivity(titik);
            }
        });
    }

    private void processReadCSV(File file) {
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("HH:mm:ss a");
        String strTime = "";
        CsvReader csvReader = new CsvReader();
        csvReader.setFieldSeparator(';');
        String line = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT){
            try{
                CsvParser csvParser = csvReader.parse(new BufferedReader(new FileReader(file.getAbsolutePath())));
                CsvRow row;
                while((row = csvParser.nextRow()) != null){
                    String[] tokens = row.getField(0).split(",");
                    //Baca CSV nya.
                    Titik readTitik = new Titik();
                    readTitik.setLatitude(Double.parseDouble(tokens[0]));
                    readTitik.setLongitude(Double.parseDouble(tokens[1]));

                    // CONVERT TIME
                    time = tokens[2];
                    boolean checkNoon = false;
                    if(tokens[2].substring(tokens[2].length()-2).equals("PM")) {
                        checkNoon = true;
                    }
                    time = tokens[2].substring(0,tokens[2].length()-3);
                    String[] waktu= time.split(":");
                    int kodejam= Integer.parseInt(waktu[0]);
                    if(checkNoon){
                        kodejam += 12;
                        if(kodejam == 24) kodejam = 0;
                    }
                    int kodemenit=Integer.parseInt(waktu[1]);
                    int kodedetik=Integer.parseInt(waktu[2]);

                    Log.d("InputActivity","GOT PARSE jam: "+kodejam+" menit: "+kodemenit+" detik: "+kodedetik);

                    // MAKE UNIXDATE
                    int cTahun = Integer.parseInt(tokens[3].substring(0,4));
                    int cBulan = Integer.parseInt(tokens[3].substring(4,6));
                    int cTanggal = Integer.parseInt(tokens[3].substring(6,8));
                    cTahun = cTahun-1900;
                    cBulan = cBulan-1;

                    Log.d("InputActivity","GOT PARSE VALUE: "+cTanggal+" "+cBulan+" "+cTahun+" ");

                    Date date = new Date(cTahun,cBulan,cTanggal);
                    long unixdate = date.getTime();
                    unixdate = unixdate/1000+25200;
                    int uDate = (int) unixdate;
                    readTitik.setUnixDate(uDate);

                    Log.d("InputActivity","UNIXDATE: "+uDate);

                    //MAKE UNIXDATETIME
                    String tmp = String.valueOf(cTanggal)+" "+String.valueOf(cBulan)+" "+String.valueOf(cTahun)+" "+String.valueOf(kodejam)+":"+String.valueOf(kodemenit)+":"+String.valueOf(kodedetik);
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy HH:mm:ss", Locale.ENGLISH);
                    Date dt = sdf.parse(tmp);
                    long unixdatetime = dt.getTime();
                    unixdatetime = unixdatetime/1000;
                    int uDateTime = (int) unixdatetime;
                    System.out.println("unixdatetime =" +unixdatetime);
                    readTitik.setUnixDate(uDateTime);
//                readTitik.setUnixDate(1);
//
//                readTitik.setTanggal("1");
                    if(tokens[4].isEmpty() || tokens[4].equals(" ")){
                        readTitik.setTanggal("NA");
                    }else{
                        readTitik.setTanggal(tokens[4]);
                    }
                    if(tokens[5].isEmpty() || tokens[5].equals(" ")){
                        readTitik.setProvinsi("NA");
                    }else{
                        readTitik.setProvinsi(tokens[5]);
                    }
                    if(tokens[6].isEmpty() || tokens[6].equals(" ")){
                        readTitik.setKabupaten("NA");
                    }else{
                        readTitik.setKabupaten(tokens[6]);
                    }
                    if(tokens[7].isEmpty() || tokens[7].equals(" ")){
                        readTitik.setKecamatan("NA");
                    }else{
                        readTitik.setKecamatan(tokens[7]);
                    }
                    titiks.add(readTitik);

                    boolean result = db.insertTitik(Double.parseDouble(tokens[0]),Double.parseDouble(tokens[1]),uDate,uDateTime,tokens[3],tokens[4],tokens[5],tokens[6],tokens[7],strBulan,strTahun);
                    if(result){
                        String msg = spinnerTahun.getSelectedItem().toString();
//                        Toast.makeText(InputTitikActivity.this, "Success!!", Toast.LENGTH_SHORT).show();
                    }else{
//                        Toast.makeText(InputTitikActivity.this, "Error!!", Toast.LENGTH_SHORT).show();
                    }
                    Log.d("InputActivity","Just Created: "+readTitik);
                }
            } catch(IOException e){
//                if (!this.isFinishing() && pDialog != null) {
//                    pDialog.dismiss();
//                }
                Log.d("InputActivity","Error Reading on the line " + line, e);
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }



}
