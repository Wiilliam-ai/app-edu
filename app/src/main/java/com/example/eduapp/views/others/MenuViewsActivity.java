package com.example.eduapp.views.others;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.eduapp.R;
import com.example.eduapp.interfaces.Defaults;

public class MenuViewsActivity extends AppCompatActivity implements Defaults {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_views);
    }

    @Override
    public void linkControls() {

    }
}