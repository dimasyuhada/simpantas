package com.example.admin.simpantas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class PolaSekuensActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    String[] years={"2015","2016","2017","2018"};
    String[] support={"1","2","3","4"};

    Button btnOk;
    Spinner spinnerTahun, spinnerSupport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pola_sekuens);

        spinnerTahun = (Spinner) findViewById(R.id.spinnerTahun);
        spinnerSupport = (Spinner) findViewById(R.id.spinnerSupp);
        btnOk = (Button) findViewById(R.id.btnOk);

        spinnerTahun.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        ArrayAdapter<String> aa = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,years);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTahun.setAdapter(aa);

        spinnerSupport.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        ArrayAdapter<String> bb = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,support);
        bb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTahun.setAdapter(bb);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //PROSES POLA TITIK PANAS DISINI
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
