package com.example.assignment_23_2;

import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;
import android.preference.PreferenceManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ExerciseActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private RequestQueue queue;
    private EditText txttitle1;
    private TextView txtRes1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        setupSharedPrefs();
        queue = Volley.newRequestQueue(this);//This is to organize the work of the all request,
                                                   // as this work is the function of the volley.
        txttitle1 = findViewById(R.id.txttitle1);
        txtRes1 = findViewById(R.id.txtRes1);
    }

    //Here I wrote the code based on what was explained in the lecture, and I will not go beyond the scope of the lecture.
    public void btn_OnClick(View view) {
        String ex = txttitle1.getText().toString().trim();
        txtRes1.setText("");
        if (!ex.isEmpty()){
            String url = "https://api.api-ninjas.com/v1/exercises?muscle=";
            String api = "boaFyEf73Smtc4XRK3nHbUgKFBcSERvU50fkyATK";
            String str = url + ex + "&X-Api-Key=" + api;
            //The result of the web service call is an array of json object, so I should receive it correctly
            //the first parameter is : the type of this request
            //the second parameter is : the URL
            //the third parameter is : null value
            //the fourth parameter is : Before I start explaining it, look at lines 57 and 58. This will be executed internally on another thread,
            //and when the work is finished by this thread, the results will be returned to the first main thread.
            // Question: How can I know that the work has finished? The answer is when you define the call back method called
            // onResponse() and this method will be executed only when the other thread finishes working and returns the results.
            // The first thread receives these results in the onResponse method.
            //Note: The result will be ready and parsed and will be in the form of a JSONArray. When you enter onResponse, you can do whatever you want with this data.
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, str,
                    null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);

                                txtRes1.setText("Name: " + obj.getString("name") + "\n" +
                                        "Type: " + obj.getString("type") + "\n" +
                                        "Muscle: " + obj.getString("muscle") + "\n" +
                                        "Equipment: " + obj.getString("equipment") + "\n" +
                                        "Difficulty: " + obj.getString("difficulty") + "\n" +
                                        "Instructions: " + obj.getString("instructions") + "\n" );



                        } catch (JSONException exception) {
                            Log.d("volley_error", exception.toString());
                        }
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("volley_error", error.toString());
                }
            });

            queue.add(request);//When you prepare the request and callbackMethod, just put it on the queue and run this request.
                              // Then the work begins according to the sequence that we explained.

        }
        else{
            Toast.makeText(ExerciseActivity.this,"You should write the name of muscle first",Toast.LENGTH_SHORT).show();

        }
    }

    private void setupSharedPrefs() {
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Save entered user data
        editor.putString("tt1",txttitle1.getText().toString());
        editor.putString("result1", txtRes1.getText().toString());
        editor.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();


        String result = prefs.getString("result1", "");
        String tt = prefs.getString("tt1", "");

        txttitle1.setText(tt);
        txtRes1.setText(result);


    }
}