package com.example.eduapp.views.others;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.eduapp.R;
import com.google.android.material.appbar.MaterialToolbar;

public class PlaysActivity extends AppCompatActivity {
    private MaterialToolbar topAppBar;
    private Intent intent;
    private Button btnIrMaravillas,btnMathGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plays);
        topAppBar = findViewById(R.id.topAppBar);
        btnIrMaravillas = findViewById(R.id.btnIrMaravillas);
        btnMathGame = findViewById(R.id.btnGameMath);

        topAppBar.setNavigationOnClickListener(v -> {
            intent = new Intent(this, MenuViewsActivity.class);
            startActivity(intent);
            finish();
        });

        btnMathGame.setOnClickListener(v -> {
            intent = new Intent(this, PlayMathActivity.class);
            startActivity(intent);
        });
        btnIrMaravillas.setOnClickListener(v -> {
            intent = new Intent(this, Play_Mar_Activity.class);
            startActivity(intent);
        });

    }
}