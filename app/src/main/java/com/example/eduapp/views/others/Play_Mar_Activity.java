package com.example.eduapp.views.others;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.eduapp.R;
import com.example.eduapp.api.Back;
import com.example.eduapp.databases.DatabaseHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Play_Mar_Activity extends AppCompatActivity {
    TextView puntos, contador, vidas, txtcorrecto, txtincorrecto;
    ImageView imagen;
    EditText txtedit;
    Button btnconfirmar;
    private DatabaseHelper dbHelper;
    private RequestQueue queue;
    Back back;
    String[] nombre_pers = {"gran muralla china",
            "chichén itzá",
            "petra",
            "machu picchu",
            "cristo redentor",
            "coliseo romano",
            "taj mahal"};
    String[] nombre_pers2 = {"gran_muralla_china",
            "chichén_itzá",
            "petra",
            "machu_picchu",
            "cristo_redentor",
            "coliseo_romano",
            "taj_mahal"};
    String[] imagen_pers = {"img1", "img2", "img3", "img4", "img5", "img6", "img7"};
    int puntoI = 0;

    ArrayList<String> nombresArr = new ArrayList<String>(Arrays.asList(nombre_pers));
    ArrayList<String> nombresArr2 = new ArrayList<String>(Arrays.asList(nombre_pers2));
    ArrayList<String> imagenArr = new ArrayList<String>(Arrays.asList(imagen_pers));

    int vidasI = 3;
    int numerogenerado = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_mar);

        dbHelper = new DatabaseHelper(this);
        queue = Volley.newRequestQueue(this);
        back = new Back();

        txtcorrecto = (TextView) findViewById(R.id.txtcorrecto);
        txtincorrecto = (TextView) findViewById(R.id.txtincorrecto);

        puntos = (TextView) findViewById(R.id.Puntos);
        vidas = (TextView) findViewById(R.id.vidas);
        contador = (TextView) findViewById(R.id.Cuenta);
        imagen = (ImageView) findViewById(R.id.imagen);
        txtedit = (EditText) findViewById(R.id.txtedit);
        btnconfirmar = (Button) findViewById(R.id.btnconfirmar);

        //buscamos numero aleatorio
        Random rand = new Random();
        int randInt = rand.nextInt(nombresArr.size());
        establecer_imagen(randInt);
        numerogenerado = randInt;
        btnconfirmar.setOnClickListener(v -> {
            String textoconfirmar = txtedit.getText().toString();
            if (textoconfirmar.equalsIgnoreCase(nombre_pers[numerogenerado]) || textoconfirmar.equalsIgnoreCase(nombre_pers2[numerogenerado])) {
                txtcorrecto.setVisibility(View.VISIBLE);
                if (puntoI < 3){
                    puntoI = puntoI + 1;
                    puntos.setText("Puntos: " + puntoI);
                    esperar1();
                }else {
                    switch (vidasI){
                        case 3 : setPoints(30,1); break;
                        case 2 : setPoints(20,1); break;
                        case 1 : setPoints(10,1); break;
                    }
                }
                
            } else {
                txtincorrecto.setVisibility(View.VISIBLE);
                vidasI = puntoI - 1;
                vidas.setText("Vidas: " + vidasI);
                esperar2();
            }
            
            if (vidasI == 0) {
                Toast.makeText(this, "Derrota", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void esperar2() {
        new CountDownTimer(2000, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                btnconfirmar.setVisibility(View.GONE);
            }

            @Override
            public void onFinish() {
                btnconfirmar.setVisibility(View.VISIBLE);
                txtincorrecto.setVisibility(View.INVISIBLE);
                txtedit.setText("");
                txtedit.setHint("Ingrese el personaje");
            }
        }.start();
    }

    void esperar1() {
        new CountDownTimer(4000, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                contador.setText("" + (millisUntilFinished / 1000 + 1));
                btnconfirmar.setVisibility(View.GONE);
            }

            @Override
            public void onFinish() {
                btnconfirmar.setVisibility(View.VISIBLE);

                //borramos esa imagen para no tenerla en cuenta en un proximo juego
                nombresArr.remove(numerogenerado);
                nombresArr2.remove(numerogenerado);
                imagenArr.remove(numerogenerado);
                contador.setText("");
                //generamos un nuevo numero aleatorio
                GenerarImagenaletoria();

                txtcorrecto.setVisibility(View.INVISIBLE);
                txtedit.setText("");
                txtedit.setHint("Ingrese el personaje");
            }
        }.start();
    }

    void establecer_imagen(int numero) {
        if (imagen_pers[numero] != null) {
            int id = getResources().getIdentifier(imagen_pers[numero], "drawable", getPackageName());
            imagen.setImageResource(id);
        }
    }

    public void GenerarImagenaletoria() {
        Random rand = new Random();
        int randInt = rand.nextInt(nombresArr.size());
        establecer_imagen(randInt);
        numerogenerado = randInt;
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
                            Toast.makeText(this, "Victory", Toast.LENGTH_SHORT).show();
                            finish();
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