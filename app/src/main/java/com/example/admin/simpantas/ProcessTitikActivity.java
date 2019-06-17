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
    Button btnInputData,btnGetData;
    private int temp = 0;

    private DBHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_titik);
        db = new DBHelper(this);
        db.removeTitik();

        btnInputData = (Button) findViewById(R.id.btnInputData);
        btnGetData = (Button) findViewById(R.id.btnGetData);

        btnInputData.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            public void onClick(View v) {
                temp = 1;
                Intent i = new Intent(ProcessTitikActivity.this, InputTitikActivity.class);
                i.putExtra("temp menu", temp);
                startActivity(i);
            }
        });

        btnGetData.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                temp = 2;
                Intent i = new Intent(ProcessTitikActivity.this, InputTitikActivity.class);
                i.putExtra("temp menu", 2);
                startActivity(i);
            }
        });
    }

}
