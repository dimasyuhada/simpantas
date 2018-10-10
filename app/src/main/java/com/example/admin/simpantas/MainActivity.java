package com.example.admin.simpantas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button inputButton, sekuensButton, visualButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputButton = (Button) findViewById(R.id.button);
        sekuensButton = (Button) findViewById(R.id.button2);
        visualButton = (Button) findViewById(R.id.button3);

        inputButton.setOnClickListener(this);
        sekuensButton.setOnClickListener(this);
        visualButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button:
                Intent titik = new Intent( MainActivity.this, InputTitikActivity.class);
                startActivity(titik);
            break;
            case R.id.button2:
                Intent pola = new Intent( MainActivity.this, PolaSekuensActivity.class);
                startActivity(pola);
            break;
            case R.id.button3:
                Intent visual = new Intent( MainActivity.this, VisualisasiActivity.class);
                startActivity(visual);
            break;
        }
    }
}
