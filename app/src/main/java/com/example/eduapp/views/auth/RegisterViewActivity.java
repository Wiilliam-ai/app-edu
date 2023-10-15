package com.example.eduapp.views.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.eduapp.R;
import com.example.eduapp.interfaces.Defaults;

public class RegisterViewActivity extends AppCompatActivity implements Defaults {
    private Button btnLinkSession;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_view);
        linkControls();

        btnLinkSession.setOnClickListener(v -> {
            intent = new Intent(this, LoginViewActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void linkControls() {
        btnLinkSession = findViewById(R.id.btnLinkSession);
    }
}