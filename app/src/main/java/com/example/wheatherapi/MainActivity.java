package com.example.wheatherapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.Normalizer;

public class MainActivity extends AppCompatActivity {
    EditText city;
    Button search;
    TextView max,min,humidity;
    RequestQueue queue;

    String url = "https://openweathermap.org/data/2.5/weather?q=";
    String key = "&appid=b6907d289e10d714a6e88b30761fae22";
    String getCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        city = findViewById(R.id.city);
        search = findViewById(R.id.search);
        max = findViewById(R.id.max);
        min = findViewById(R.id.min);
        humidity = findViewById(R.id.humidity);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCity = city.getText().toString();
                loadDatafromUrl(getCity);


            }
        });

    }
    private void loadDatafromUrl(String cityname) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url+cityname+key, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject jsonObject = null;

                        try {
                            jsonObject = response.getJSONObject("main");

                            DecimalFormat df = new DecimalFormat("#.00");
                            double maxtemp = jsonObject.getDouble("temp_max");
                            String maximum = String.valueOf(df.format(maxtemp) + " °C");
                            double mintemp = jsonObject.getDouble("temp_min");
                            String minimum = String.valueOf(df.format(mintemp) + " °C");
                            double hum = jsonObject.getDouble("humidity");
                            String humidit = String.valueOf(df.format(hum) + " %");
                            max.setText(maximum);
                            min.setText(minimum);
                            humidity.setText(humidit);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }
    }
