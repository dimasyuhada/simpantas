package com.example.admin.simpantas;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    final String PREFNAME = "MyPrefsFile";
    public static final String value = "key";
    SharedPreferences sharedPreferences;

    Button inputButton, visualButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputButton = (Button) findViewById(R.id.button);
        visualButton = (Button) findViewById(R.id.button3);

        inputButton.setOnClickListener(this);
        visualButton.setOnClickListener(this);

        //BUAT CEK APAKAH INSTALL PERTAMA
        //makeFolder();
    }

    private void makeFolder() {
        //KALO INSTALL PERTAMA, BUAT DITEKTORI UNTUK NYIMPEN FILE YANG AKAN DIPERGUNAKAN
        String folder_simpantas = "dbSimpantas";
        File directory = new File(Environment.getExternalStorageDirectory(),folder_simpantas);
        if(!directory.exists()){
            directory.mkdir();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button:
                Intent titik = new Intent( MainActivity.this, InputTitikActivity.class);
                startActivity(titik);
            break;
            case R.id.button3:
                Intent realtime = new Intent( MainActivity.this, RealtimeActivity.class);
                startActivity(realtime);
            break;
        }
    }
}
