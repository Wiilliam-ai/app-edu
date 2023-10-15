package com.example.eduapp.views.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.eduapp.R;

public class LoginViewActivity extends AppCompatActivity {
    private Button btnLinkRegister;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);
        linkControls();

        btnLinkRegister.setOnClickListener(v -> {
            intent = new Intent(this, RegisterViewActivity.class);
            startActivity(intent);
        });
    }

    private void linkControls(){
        btnLinkRegister = findViewById(R.id.btnLinkRegister);
    }
}