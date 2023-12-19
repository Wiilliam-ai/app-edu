package com.example.eduapp.views.dash;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.eduapp.MainActivity;
import com.example.eduapp.R;
import com.example.eduapp.databases.DatabaseHelper;
import com.google.android.material.appbar.MaterialToolbar;

public class ProfileActivity extends AppCompatActivity {
    private MaterialToolbar topAppBar;
    private Button btnCloseSession;
    // En tu actividad o en donde desees eliminar todos los registros de la tabla
    DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        topAppBar = findViewById(R.id.topBarProfile);
        btnCloseSession = findViewById(R.id.btnCloseSession);
        dbHelper = new DatabaseHelper(this);
        btnCloseSession.setOnClickListener(v -> {
            closeSession();
        });

        topAppBar.setNavigationOnClickListener(v -> {
            finish();
        });
    }

    private void closeSession() {
        new AlertDialog.Builder(this)
                .setMessage("¿Desea cerrar sesion?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    boolean recordsDeleted = dbHelper.deleteAllUsers();
                    if (recordsDeleted) {
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }
}