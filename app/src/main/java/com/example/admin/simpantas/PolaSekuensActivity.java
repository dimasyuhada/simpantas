package com.example.admin.simpantas;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.pfv.spmf.algorithms.sequentialpatterns.clasp_AGP.idlists.creators.IdListCreator;
import ca.pfv.spmf.algorithms.sequentialpatterns.gsp_AGP.items.SequenceDatabase;
import ca.pfv.spmf.algorithms.sequentialpatterns.gsp_AGP.items.creators.AbstractionCreator;
import ca.pfv.spmf.algorithms.sequentialpatterns.gsp_AGP.items.creators.AbstractionCreator_Qualitative;
import ca.pfv.spmf.algorithms.sequentialpatterns.spade_spam_AGP.AlgoSPADE;
import ca.pfv.spmf.algorithms.sequentialpatterns.spade_spam_AGP.candidatePatternsGeneration.CandidateGenerator;
import ca.pfv.spmf.algorithms.sequentialpatterns.spade_spam_AGP.candidatePatternsGeneration.CandidateGenerator_Qualitative;
import ca.pfv.spmf.algorithms.sequentialpatterns.spade_spam_AGP.idLists.creators.IdListCreator_Bitmap;
import ca.pfv.spmf.algorithms.sequentialpatterns.spade_spam_AGP.idLists.creators.IdListCreator_FatBitmap;
import ca.pfv.spmf.algorithms.sequentialpatterns.spam.Candidate;
import ca.pfv.spmf.gui.plot.Plot;
import de.siegmar.fastcsv.reader.CsvParser;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;

public class PolaSekuensActivity extends AppCompatActivity{

    private DBHelper db;
    private ProgressDialog pDialog;
    private ListView lv;

    EditText inputMinsup;
    LinearLayout minsupView;
    Button bSpade,bMinSup;
    String tahunValue = "";
    String bulanValue = "";

    ArrayList<HashMap<String, String>> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pola_sekuens);
        db = new DBHelper(this);
        db.removeTransform();
        userList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list_pola);
        minsupView = (LinearLayout) findViewById(R.id.minsupView);
        minsupView.setVisibility(LinearLayout.GONE);

        inputMinsup = (EditText) findViewById(R.id.txMinsup);
        bSpade = (Button) findViewById(R.id.btnSpade);
        bMinSup = (Button) findViewById(R.id.btnInputMinsup);

        Bundle bundle = getIntent().getExtras();
        tahunValue = bundle.getString("tahunValue");
        bulanValue = bundle.getString("bulanValue");
        Log.d("BULAN BERAPA",bulanValue);
        Log.d("TAHUN BERAPA",tahunValue);

        new GetFilter().execute();

        bSpade.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                doTransform();
                minsupView.setVisibility(LinearLayout.VISIBLE);
            }
        });
        bMinSup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    String strMinsup = inputMinsup.getText().toString();
                    float dMinsup = Float.parseFloat(strMinsup);
                    doSpade(dMinsup,tahunValue);

                    Intent spade = new Intent( PolaSekuensActivity.this, SpadeResultActivity.class);
                    spade.putExtra("blnVal",bulanValue);
                    spade.putExtra("thnVal",tahunValue);
                    startActivity(spade);

                } catch (IOException e) {
                    Log.d("Error SPADE : ",e.getMessage());
                    e.printStackTrace();
                } catch (NumberFormatException ignored){
                    Log.d("Error Min Support Input",ignored.getMessage());
                    ignored.printStackTrace();
                }
            }
        });
    }



    private class GetFilter extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(PolaSekuensActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userList = db.getTitikForFilter(bulanValue,tahunValue);
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
                    PolaSekuensActivity.this, userList,
                    R.layout.list_titikpanasfilter, new String[]{"latitude", "longitude", "unixdate", "unixdatetime", "tanggal"}, new int[]{R.id.latitude,
                    R.id.longitude, R.id.unixdate, R.id.unixdatetime, R.id.tanggal});

            lv.setAdapter(adapter);
        }
    }

    private void doTransform(){
        boolean result;
        String strItem, strTahun = "";
        String initLat = "";
        String initLtd = "";
        String initUnixDate = "";
        String initResult = "";
        int tempSid=1,i = 0;
        for(i=0; i< userList.size();i++){
            HashMap<String,String> tempArray = userList.get(i);
            if(i==0){
                initLat = tempArray.get("latitude");
                initLtd = tempArray.get("longitude");
                initUnixDate = tempArray.get("unixdate");
                initResult = tempArray.get("unixdatetime");
            }else{
                if(initLat.equals(tempArray.get("latitude")) && initLtd.equals(tempArray.get("longitude"))){
                    if(!initUnixDate.equals(tempArray.get("unixdate"))){
                        initResult += " -1";
                    }
                    initResult += " "+tempArray.get("unixdatetime");
                }else{
                    String[] resultSplit = initResult.split("\\s+");
                    ArrayList<String> dateArr = new ArrayList<>();
                    boolean isMinus = true;
                    for(int j=0; j<resultSplit.length; j++){
                        if(!resultSplit[j].equals("-1")){
                            dateArr.add(resultSplit[j]);
                            isMinus = false;
                        }
                    }
                    boolean isInsert = true;
                    if(!isMinus){
                        Integer subs = Integer.valueOf(dateArr.get(0)) - Integer.valueOf(dateArr.get(1));
                        if (subs > -25200){
                            isInsert = false;
                        }
                    }
                    if(isInsert){
                        initResult += " -1 -2";
                        result = db.insertTransform(tempSid++,initResult,bulanValue,tahunValue);
                    }
                    initResult = tempArray.get("unixdatetime");
                    initLat = tempArray.get("latitude");
                    initLtd = tempArray.get("longitude");
                    initUnixDate = tempArray.get("unixdate");
                }
            }
        }
        initResult += " -1 -2";
        result = db.insertTransform(tempSid++,initResult,bulanValue,tahunValue);
        //EXPORT TRANSFORM DATA
        boolean initExport = db.exportTransform();
        if (result && initExport){
            Toast.makeText(PolaSekuensActivity.this, "Success!!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(PolaSekuensActivity.this, "Error!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void doSpade(double mSupport, String tahunValue) throws IOException {
        boolean keepPatterns = true;
        boolean verbose = false;
        boolean dfs = true;
        boolean outputSequenceIdentifiers = false;

        ca.pfv.spmf.algorithms.sequentialpatterns.spade_spam_AGP.dataStructures.creators.AbstractionCreator abstractionCreator =
                ca.pfv.spmf.algorithms.sequentialpatterns.spade_spam_AGP.dataStructures.creators.AbstractionCreator_Qualitative.getInstance();

        ca.pfv.spmf.algorithms.sequentialpatterns.spade_spam_AGP.idLists.creators.IdListCreator idListCreator =
                IdListCreator_Bitmap.getInstance();

        CandidateGenerator candidateGenerator = CandidateGenerator_Qualitative.getInstance();

        ca.pfv.spmf.algorithms.sequentialpatterns.spade_spam_AGP.dataStructures.database.SequenceDatabase sequenceDatabase =
                new ca.pfv.spmf.algorithms.sequentialpatterns.spade_spam_AGP.dataStructures.database.SequenceDatabase(abstractionCreator,idListCreator);

        sequenceDatabase.loadFile(Environment.getExternalStorageDirectory()+"/dbSimpantas/transform/transform-data.csv",mSupport);
        Log.d("Spade :",sequenceDatabase.toString());

        AlgoSPADE algorithm = new AlgoSPADE(mSupport,dfs,abstractionCreator);

        algorithm.runAlgorithm(sequenceDatabase,candidateGenerator,keepPatterns,verbose,Environment.getExternalStorageDirectory()+"/dbSimpantas/transform/hasil-transform-data.csv",outputSequenceIdentifiers);
        Log.d("Minimum Support :",String.valueOf(mSupport));
        System.out.print(algorithm.getNumberOfFrequentPatterns()+ " frequent patterns.");

        System.out.print(algorithm.printStatistics());
    }

}
