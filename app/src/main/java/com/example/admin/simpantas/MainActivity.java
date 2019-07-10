package com.example.admin.simpantas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    final String PREFNAME = "MyPrefsFile";
    public static final String value = "key";
    SharedPreferences permissionStatus;

    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;

    Button inputButton, visualButton, aboutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputButton = (Button) findViewById(R.id.button);
        visualButton = (Button) findViewById(R.id.button3);
        aboutButton = (Button) findViewById(R.id.button5);
        inputButton.setOnClickListener(this);
        visualButton.setOnClickListener(this);
        aboutButton.setOnClickListener(this);

        permissionStatus = getSharedPreferences("permissionStatus",MODE_PRIVATE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button:
                Intent titik = new Intent( MainActivity.this, ProcessTitikActivity.class);
                startActivity(titik);
            break;
            case R.id.button3:
                Intent realtime = new Intent( MainActivity.this, RealtimeActivity.class);
                startActivity(realtime);
            break;
            case R.id.button5:
                Intent about = new Intent( MainActivity.this, AboutActivity.class);
                startActivity(about);
                break;
        }
    }
}
