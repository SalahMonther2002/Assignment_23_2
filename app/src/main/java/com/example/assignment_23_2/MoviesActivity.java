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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.preference.PreferenceManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
public class MoviesActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private RequestQueue queue;
    private EditText txttitle;
    private TextView txtRes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        setupSharedPrefs();
        queue = Volley.newRequestQueue(this);
        txttitle = findViewById(R.id.txttitle);
        txtRes = findViewById(R.id.txtRes);
    }

    //Here I wrote the code based on what was explained in the lecture, and I will not go beyond the scope of the lecture.
    public void btn_OnClick(View view) {
        String movie = txttitle.getText().toString().trim();
        txtRes.setText("");
        if (!movie.isEmpty()){
            String url = "https://gitlab.com/gvanderput/gerard-movie-filtering/-/raw/master/data/movies.json?fbclid=IwAR3axi9XTCMoxJPwHrS8yyJ4dIGTOFFgI3kde8KFf8wLeBTcSSc4jBzmUkQ";


        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        if (obj.getString("title").equals(movie)) {
                            txtRes.setText("Title: " + obj.getString("title") + "\n" +
                                    "Year: " + obj.getString("year") + "\n" +
                                    "votes: " + obj.getString("votes") + "\n" +
                                    "Description: " + obj.getString("description"));
                            break;
                        }

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

        queue.add(request);

    }
    else{
            Toast.makeText(MoviesActivity.this,"You should write the title first",Toast.LENGTH_SHORT).show();

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
        editor.putString("tt",txttitle.getText().toString());
        editor.putString("result", txtRes.getText().toString());
        editor.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();


        String result = prefs.getString("result", "");
        String tt = prefs.getString("tt", "");

        txttitle.setText(tt);
        txtRes.setText(result);


    }
}