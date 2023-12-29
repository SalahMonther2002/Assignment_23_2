package com.example.assignment_23_2;

import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CarsActivity extends AppCompatActivity {
    private RequestQueue queue;
    private ListView lstTodos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars);
        queue = Volley.newRequestQueue(this);
        lstTodos = findViewById(R.id.listV2);
    }

    //Here I wrote the code based on what was explained in the lecture, and I will not go beyond the scope of the lecture.

    public void btn_OnClick(View view) {

        String url = "https://raw.githubusercontent.com/vega/vega/main/docs/data/cars.json?fbclid=IwAR1u8_K4rgWpnLkwObugLWrwVYhut-FzKcfHIcVGPDJIbxRyKV9lfuoSAvg";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<String> todos = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        todos.add("Name: "+ obj.getString("Name")+"\n"+
                                  "Horsepower: " + obj.getString("Horsepower") + "\n"+
                                  "Year: "  +  obj.getString("Year") + "\n"+
                                  "Origin: " + obj.getString("Origin") + " .");
                    }catch(JSONException exception){
                        Log.d("volley_error", exception.toString());
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CarsActivity.this,
                        android.R.layout.simple_list_item_1, todos);
                lstTodos.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("volley_error", error.toString());
            }
        });

        queue.add(request);


    }


}