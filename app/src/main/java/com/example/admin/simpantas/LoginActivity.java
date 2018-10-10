package com.example.admin.simpantas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.inputUsername);
        etUsername = (EditText) findViewById(R.id.inputUsername);

        btnLogin = (Button) findViewById(R.id.btnSignIn);

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(etUsername!=null || etPassword!=null){
            Intent titik = new Intent( LoginActivity.this, MainActivity.class);
            startActivity(titik);
        }else{
            //DIALOGBOX
        }
    }
}
