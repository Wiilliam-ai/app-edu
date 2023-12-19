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
import com.example.eduapp.interfaces.Defaults;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class RegisterViewActivity extends AppCompatActivity implements Defaults {
    TextInputEditText txtInpFullName,txtInpEmail,txtInpPass,txtInpPass2;
    private Button btnLinkSession,btnRegisterApp;
    private Intent intent;
    private RequestQueue queue;
    Back back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_view);
        linkControls();

        btnRegisterApp.setOnClickListener(v -> {
            String name = getTextInput(txtInpFullName);
            String email = getTextInput(txtInpEmail);
            String password = getTextInput(txtInpPass);
            String passwordRepeat = getTextInput(txtInpPass2);

            if (isValid(name,email, password, passwordRepeat)) {
                sendRegister(name,email,password);
            }
        });

        btnLinkSession.setOnClickListener(v -> {
            intent = new Intent(this, LoginViewActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void linkControls() {

        queue = Volley.newRequestQueue(this);
        back = new Back();

        btnLinkSession = findViewById(R.id.btnLinkSession);
        btnRegisterApp = findViewById(R.id.btnRegisterApp);
        txtInpFullName = findViewById(R.id.txtInpFullName);
        txtInpEmail = findViewById(R.id.txtInpEmail);
        txtInpPass = findViewById(R.id.txtInpPass);
        txtInpPass2 = findViewById(R.id.txtInpPass2);
    }

    private String getTextInput (TextInputEditText inputText){
        return inputText.getText().toString().trim();
    }

    private boolean isValid(String fullName,String email, String password, String passwordRepeat) {
        if (fullName.equals("")){
            Toast.makeText(this, "El email es requerido", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Ingrese un email válido", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.isEmpty() || password.length() < 6) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(passwordRepeat)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void sendRegister(String name,String email,String password){
        String url = back.getURL_API() + "/auth/register";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("fullName", name);
            jsonBody.put("email", email);
            jsonBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                response -> {
                    boolean status = response.optBoolean("status");
                    String msg = response.optString("msg");
                    if (status){
                        cleanTextAll();
                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                        intent = new Intent(this, CheckAcountActivity.class);
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

    private void cleanTextAll () {
        txtInpFullName.setText("");
        txtInpEmail.setText("");
        txtInpPass.setText("");
        txtInpPass2.setText("");
    }
}