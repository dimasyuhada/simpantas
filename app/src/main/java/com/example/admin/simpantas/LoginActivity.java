package com.example.admin.simpantas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class LoginActivity extends AppCompatActivity {

    EditText _username, _password;
    Button _login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _username = (EditText) findViewById(R.id.inputUsername);
        _password = (EditText) findViewById(R.id.inputPassword);
        _login = (Button) findViewById(R.id.btnLogin);

        _login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String txUsername = _username.getText().toString();
                String txPassword = _password.getText().toString();

                if(txUsername.isEmpty()){
                    _username.setError("Username tidak boleh kosong!");
                } else if(!txUsername.equals("Admin")){
                    _username.setError("Username Salah!");
                } else if(txPassword.isEmpty()){
                    _password.setError("Password tidak boleh kosong!");
                } else if(!txPassword.equals("Admin")){
                    _password.setError("Password Salah!");
                }else{
                    Intent masuk = new Intent( LoginActivity.this, MainActivity.class);
                    startActivity(masuk);
                }
            }
        });

    }
}
