package com.example.admin.simpantas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class InputTitikActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    String[] years={"2015","2016","2017","2018"};

    Button btnUpload;
    Spinner spinnerTahun;
    TextView tvFilename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_titik);
        spinnerTahun = (Spinner) findViewById(R.id.spinnerTahun);
        tvFilename = (TextView) findViewById(R.id.titleFile);
        btnUpload = (Button) findViewById(R.id.btnUpload);

        ArrayAdapter<String> aa = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,years);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTahun.setAdapter(aa);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //PROSES UPLOAD FILE CSV
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), years[position], Toast.LENGTH_LONG).show();
    }
}
