package com.example.admin.simpantas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

public class SpadeResultTextActivity extends AppCompatActivity implements Serializable{

    TextView summaryProcess;
    Button btnVisual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spade_result_text);

        summaryProcess = (TextView) findViewById(R.id.sumProcess);
        btnVisual = (Button) findViewById(R.id.btnVis);
        btnVisual.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            public void onClick(View v) {

            }
        });
    }
}
