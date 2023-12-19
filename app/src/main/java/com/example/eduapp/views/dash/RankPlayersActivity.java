package com.example.eduapp.views.dash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.eduapp.R;
import com.example.eduapp.model.Player;
import com.example.eduapp.ui.PlayerAdapter;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class RankPlayersActivity extends AppCompatActivity {
    private RecyclerView rcvPlayers;
    private List<Player> playerList;
    private PlayerAdapter playerAdapter;
    private MaterialToolbar topAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_players);
        rcvPlayers = findViewById(R.id.rcvRankPlayers);
        topAppBar = findViewById(R.id.topAppBarRank);
        rcvPlayers.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        topAppBar.setNavigationOnClickListener(v -> {
            finish();
        });
        getListPlayers();
    }

    private void getListPlayers() {
        playerList = new ArrayList<>();
        playerList.add(new Player("https://cdn3.iconfinder.com/data/icons/avatars-round-flat/33/man5-512.png","William","95 pts"));
        playerList.add(new Player("https://cdn3.iconfinder.com/data/icons/avatars-round-flat/33/man5-512.png","Jesus","45 pts"));
        playerList.add(new Player("https://cdn3.iconfinder.com/data/icons/avatars-round-flat/33/man5-512.png","Maria","40 pts"));
        playerAdapter = new PlayerAdapter(playerList,this);
        rcvPlayers.setAdapter(playerAdapter);
    }
}