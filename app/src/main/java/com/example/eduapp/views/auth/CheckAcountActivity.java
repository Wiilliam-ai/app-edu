package com.example.eduapp.views.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.eduapp.R;
import com.example.eduapp.api.Back;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class CheckAcountActivity extends AppCompatActivity {
    private EditText edtToken;
    private Button btnValidarToken;
    private RequestQueue queue;
    private Intent intent;
    Back back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_acount);
        queue = Volley.newRequestQueue(this);
        back = new Back();

        edtToken = findViewById(R.id.edtToken);
        btnValidarToken = findViewById(R.id.btnValidarToken);

        btnValidarToken.setOnClickListener(v -> {
            String token = edtToken.getText().toString().trim();

            if (token.length() <= 5){
                Toast.makeText(this, "Ingrese un token valido", Toast.LENGTH_SHORT).show();
            }else {
                sendToken(token);
            }
        });
    }

    private void sendToken(String token) {
        String url = back.getURL_API() + "/auth/check";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                response -> {
                    boolean status = response.optBoolean("status");
                    String msg = response.optString("msg");
                    if (status){
                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                        intent = new Intent(this, LoginViewActivity.class);
                        startActivity(intent);
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
                });
        queue.add(jsonObjectRequest);
    }
}