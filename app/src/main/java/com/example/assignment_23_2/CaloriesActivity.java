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
import android.widget.EditText;
import android.widget.ListView;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
public class CaloriesActivity extends AppCompatActivity {

    private RequestQueue queue;
    private EditText txttitle12;
    private ListView lstTodos1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories);

        queue = Volley.newRequestQueue(this);
        txttitle12 = findViewById(R.id.txttitle12);
        lstTodos1 = findViewById(R.id.listV21);
    }

    //Here I wrote the code based on what was explained in the lecture, and I will not go beyond the scope of the lecture.
    public void btn_OnClick(View view) {
        String ac = txttitle12.getText().toString().trim();

        if (!ac.isEmpty()){
            String url = "https://api.api-ninjas.com/v1/caloriesburned?activity=";
            String api = "boaFyEf73Smtc4XRK3nHbUgKFBcSERvU50fkyATK";
            String str = url + ac + "&X-Api-Key=" + api;

            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, str,
                    null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    ArrayList<String> todos = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);

                            todos.add("Name: " + obj.getString("name") + "\n" +
                                    "Calories per hour: " + obj.getString("calories_per_hour") + "\n" +
                                    "Duration minutes: " + obj.getString("duration_minutes") + "\n" +
                                    "Total calories: " + obj.getString("total_calories") );



                        } catch (JSONException exception) {
                            Log.d("volley_error", exception.toString());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CaloriesActivity.this,
                                android.R.layout.simple_list_item_1, todos);
                        lstTodos1.setAdapter(adapter);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("volley_error", error.toString());
                }
            });

            queue.add(request);

        }
        else{
            Toast.makeText(CaloriesActivity.this,"You should write the name of activity first",Toast.LENGTH_SHORT).show();

        }
    }

 }




