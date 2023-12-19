package com.example.eduapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.eduapp.api.Back;
import com.example.eduapp.databases.DatabaseHelper;
import com.example.eduapp.views.auth.LoginViewActivity;
import com.example.eduapp.views.auth.RegisterViewActivity;
import com.example.eduapp.views.others.MenuViewsActivity;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private Button btnLogin,btnRegister;
    private ProgressBar progressBarInitial;
    private Intent intent;
    private RequestQueue queue;
    Back back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linkControls();

        btnLogin.setVisibility(View.GONE);
        btnRegister.setVisibility(View.GONE);

        if (!dbHelper.isTableEmpty()) {
            String firstUserToken = dbHelper.getFirstUserToken();
            if (firstUserToken != null) {
                String url = back.getURL_API() + "/auth/profile";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                        response -> {
                            boolean status = response.optBoolean("status");

                            if (status){
                                intent = new Intent(this, MenuViewsActivity.class);
                                startActivity(intent);
                            }
                        },
                        error -> {
                        }) {
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Authorization", "Bearer " + firstUserToken);
                        return headers;
                    }
                };
                queue.add(jsonObjectRequest);
            }
        } else {
            btnRegister.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.VISIBLE);
            progressBarInitial.setVisibility(View.GONE);
        }

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
        progressBarInitial = findViewById(R.id.progressBarInitial);

        dbHelper = new DatabaseHelper(this);
        queue = Volley.newRequestQueue(this);
        back = new Back();
    }
}