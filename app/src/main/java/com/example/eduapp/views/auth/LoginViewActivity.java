package com.example.eduapp.views.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.eduapp.R;
import com.example.eduapp.api.Back;
import com.example.eduapp.databases.DatabaseHelper;
import com.example.eduapp.views.others.MenuViewsActivity;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class LoginViewActivity extends AppCompatActivity {
    TextInputEditText txtInpCorreo,txtInpPassword;
    private Button btnLinkRegister,btnLoginApp;
    private Intent intent;
    private RequestQueue queue;
    Back back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);
        linkControls();

        btnLinkRegister.setOnClickListener(v -> {
            intent = new Intent(this, RegisterViewActivity.class);
            startActivity(intent);
            finish();
        });

        btnLoginApp.setOnClickListener(v -> {
            String email = getTextInput(txtInpCorreo);
            String password = getTextInput(txtInpPassword);
            if (isValid(email, password)) {
                sendLoginUser(email,password);
            }
        });
    }

    private void linkControls(){
        queue = Volley.newRequestQueue(this);
        back = new Back();

        btnLinkRegister = findViewById(R.id.btnLinkRegister);
        btnLoginApp = findViewById(R.id.btnRegisterApp);
        txtInpCorreo = findViewById(R.id.txtInpCorreo);
        txtInpPassword = findViewById(R.id.txtInpPassword);
    }

    private String getTextInput (TextInputEditText inputText){
        return inputText.getText().toString().trim();
    }

    private boolean isValid(String email,String password) {
        if (email.equals("") || password.equals("") ){
            Toast.makeText(this, "El complete los campos", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void sendLoginUser(String email,String password){
        String url = back.getURL_API() + "/auth/login";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", email);
            jsonBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                response -> {
                    boolean status = response.optBoolean("status");

                    if (status){
                        String token = response.optString("token");
                        String fullName = response.optString("fullName");
                        String correo = response.optString("correo");
                        DatabaseHelper dbHelper = new DatabaseHelper(this);

                        boolean isInserted = dbHelper.insertUser(fullName, correo, token);

                        if (isInserted) {
                            cleanTextAll();
                            intent = new Intent(this, MenuViewsActivity.class);
                            startActivity(intent);
                        }
                    }
                },
                error -> {
                    if (error.networkResponse != null) {
                        try {
                            String errorMessage = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                            JSONObject errorJson = new JSONObject(errorMessage);
                            String errorMsg = errorJson.getString("msg");
                            Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();
                            cleanTextAll();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Error en la solicitud", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsonObjectRequest);
    }

    private void cleanTextAll() {
        txtInpCorreo.setText("");
        txtInpPassword.setText("");
    }
}