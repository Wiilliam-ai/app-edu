package com.example.eduapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.eduapp.views.auth.LoginViewActivity;
import com.example.eduapp.views.auth.RegisterViewActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnLogin,btnRegister;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linkControls();

        btnLogin.setOnClickListener(v -> {
            intent = new Intent(this, LoginViewActivity.class);
            startActivity(intent);
        });

        btnRegister.setOnClickListener(v -> {
            intent = new Intent(this, RegisterViewActivity.class);
            startActivity(intent);
        });
    }

    private void linkControls(){
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
    }
}