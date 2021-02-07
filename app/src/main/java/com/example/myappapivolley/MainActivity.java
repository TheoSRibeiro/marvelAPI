package com.example.myappapivolley;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn_research;
    Button btn_researchID;
    ListView lv_comics;
    TextView et_dataInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assinatura do layout para controle do layout
        btn_research = findViewById(R.id.btn_research);
        btn_researchID = findViewById(R.id.btn_researchID);
        lv_comics = findViewById(R.id.lv_comics);
        et_dataInput = findViewById(R.id.et_dataInput);

        final ComicsDataService comicsDataService = new ComicsDataService(MainActivity.this);

        //listener para o botão
        btn_research.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                comicsDataService.getComics(new ComicsDataService.VolleyResponseLinster() { //Passa como parametro o VolleyResponseLinster() como callback
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, Collections.singletonList(response.toString()));
                        lv_comics.setAdapter(arrayAdapter);
                    }
                });
            }
        });

        btn_researchID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                comicsDataService.getComicsForecastByID(et_dataInput.getText().toString(), new ComicsDataService.ForecastByIDResponse() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(List<ComicsModel> comicsModels) {
                        //Toast.makeText(MainActivity.this, comicsModel.toString(), Toast.LENGTH_SHORT).show();

                        //ArrayAdapter para mostrar a lista de comicsModels com todas as informações
                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, comicsModels);
                        lv_comics.setAdapter(arrayAdapter);
                    }
                });

            }
        });
    }

}