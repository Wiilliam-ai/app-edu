package com.example.eduapp.views.others;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.eduapp.R;
import com.example.eduapp.interfaces.Defaults;
import com.example.eduapp.views.dash.ProfileActivity;
import com.example.eduapp.views.dash.RankPlayersActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuViewsActivity extends AppCompatActivity implements Defaults {
    private BottomNavigationView bottom_nav;
    private Intent intent;
    private CardView cardRank,cardVideoShort;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_views);
        bottom_nav = findViewById(R.id.botton_nav);
        cardRank = findViewById(R.id.cardRank);
        cardVideoShort = findViewById(R.id.cardVideoShort);

        cardRank.setOnClickListener(v -> {
            intent = new Intent(this, RankPlayersActivity.class);
            startActivity(intent);
        });

        cardVideoShort.setOnClickListener(v -> {
            intent = new Intent(this, RankPlayersActivity.class);
            startActivity(intent);
        });


        bottom_nav.setOnItemSelectedListener(item -> {
            String title = item.getTitle().toString();

            switch (title){
                case "Niveles":
                    intent = new Intent(this, PlaysActivity.class);
                    startActivity(intent);
                    break;
                case "Logros":
                    Toast.makeText(this, "Hola", Toast.LENGTH_SHORT).show();
                    break;
                case "Perfil":
                    intent = new Intent(this, ProfileActivity.class);
                    startActivity(intent);
                    break;
                case "Mas":
                    Toast.makeText(this, "Hola", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        });
    }

    @Override
    public void linkControls() {
    }
}