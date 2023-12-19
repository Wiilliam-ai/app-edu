package com.example.eduapp.views.others;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.eduapp.R;
import com.example.eduapp.api.Back;
import com.example.eduapp.databases.DatabaseHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlayMathResultActivity extends AppCompatActivity {
    TextView textResult;
    int great;
    private DatabaseHelper dbHelper;
    private RequestQueue queue;
    Back back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_math_result);

        great = getIntent().getIntExtra("RA", 0);
        textResult = findViewById(R.id.textResult);
        if (great <= 3){
            textResult.setText("You answered "+ great + " / 10 \n Puntos ganados:"+ 10);
            setPoints(10,2);
        } else if (great >= 4 && great <= 7) {
            textResult.setText("You answered "+ great + " / 10 \n Puntos ganados:"+ 25);
            setPoints(25,2);
        }else{
            textResult.setText("You answered "+ great + " / 10 \n Puntos ganados:"+ 35);
            setPoints(35,2);
        }
    }
    private void setPoints(int points,int idGame){
        if (!dbHelper.isTableEmpty()) {
            String url = back.getURL_API() + "/game/up_game";
            String firstUserToken = dbHelper.getFirstUserToken();
            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("points", points);
                jsonBody.put("idGame", idGame);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                    response -> {
                        boolean status = response.optBoolean("status");

                        if (status){
                            Handler handler = new Handler();
                            handler.postDelayed(this::finish, 3000);
                        }
                    },
                    error -> {
                        if (error.networkResponse != null) {
                            try {
                                String errorMessage = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                                JSONObject errorJson = new JSONObject(errorMessage);
                                String errorMsg = errorJson.getString("msg");
                                Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Error en la solicitud", Toast.LENGTH_SHORT).show();
                        }
                    }){
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + firstUserToken);
                    return headers;
                }
            };;
            queue.add(jsonObjectRequest);
        }
    }
}