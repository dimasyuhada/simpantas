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

    Button inputButton, sekuensButton, visualButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputButton = (Button) findViewById(R.id.button);
        sekuensButton = (Button) findViewById(R.id.buttonX);
        visualButton = (Button) findViewById(R.id.button3);

        inputButton.setOnClickListener(this);
        visualButton.setOnClickListener(this);

        //BUAT CEK APAKAH INSTALL PERTAMA
        checkInit();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void checkInit() {
        //KALO INSTALL PERTAMA, BUAT DITEKTORI UNTUK NYIMPEN FILE YANG AKAN DIPERGUNAKAN
        File directory = new File(Environment.getExternalStorageDirectory()+"/dataHotspot");
        if(!directory.exists()){
            directory.mkdir();
        }
        File newDirectory = new File("/testFolder");
        newDirectory.mkdirs();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button:
                Intent titik = new Intent( MainActivity.this, InputTitikActivity.class);
                startActivity(titik);
            break;
            case R.id.buttonX:
                Intent visual = new Intent( MainActivity.this, VisualisasiActivity.class);
                startActivity(visual);
            break;
            case R.id.button3:
                Intent realtime = new Intent( MainActivity.this, ProcessTitikActivity.class);
                startActivity(realtime);
                break;
        }
    }
}
