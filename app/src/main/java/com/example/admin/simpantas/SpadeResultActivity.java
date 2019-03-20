package com.example.admin.simpantas;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SpadeResultActivity extends AppCompatActivity {

    ArrayList<HashMap<String, String>> spadeList;

    private DBHelper db;
    private ProgressDialog pDialog;
    ListView lv;
    TextView showDate;
    Button resultMap;

    String tahunValue = "";
    String bulanValue = "";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spade_result);
        db = new DBHelper(this);

        lv = (ListView) findViewById(R.id.spadeList);
        showDate = (TextView) findViewById(R.id.strDateResult);
        resultMap = (Button) findViewById(R.id.btnResultMap);
        spadeList = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        tahunValue = bundle.getString("thnVal");
        bulanValue = bundle.getString("blnVal");
        Log.d("BULAN BERAPA",bulanValue);
        Log.d("TAHUN BERAPA",tahunValue);
        showDate.setText("Result Spade : "+bulanValue+" "+tahunValue);

        new GetSpadeResult().execute();

        resultMap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                processVisualization();
            }
        });

    }

    private class GetSpadeResult extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(SpadeResultActivity.this);
            pDialog.setMessage("Getting Result...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            File file = new File(Environment.getExternalStorageDirectory()+"/dbSimpantas/transform/","hasil-transform-data.csv");
            CSVReader reader = null;
            try {
                reader = new CSVReader(new FileReader(file.getAbsolutePath()));
                String[] nextLine;
                while ((nextLine = reader.readNext()) != null) {
                    // nextLine[] is an array of values from the line
                    HashMap<String, String> spades = new HashMap<>();
                    spades.put("unixdatetime",nextLine[0]);
                    Log.d("RESULT TERLIHAT? ",nextLine[0]);

                    spades.put("month",bulanValue);
                    spades.put("year",tahunValue);
                    spadeList.add(spades);
                    db.insertTspade(spades);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
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
                    SpadeResultActivity.this, spadeList,
                    R.layout.list_spade, new String[]{"unixdatetime"}, new int[]{R.id.spade_list});
            lv.setAdapter(adapter);
        }
    }

    public void processVisualization(){

    }


}
