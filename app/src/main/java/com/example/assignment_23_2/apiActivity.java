package com.example.assignment_23_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class apiActivity extends AppCompatActivity {

    /*
        My application is about giving information about sports, especially for iron players(Your guide in the GYM ).
        The information was taken from the API for this field of sports.
        As for the movies, I have attached them only for information that any user of this application can use only.
     */
    private final String[] itemList = {"Exercise","Calories Burned", "Movies"} ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this , android.R.layout.simple_list_item_1 , itemList);

        ListView listView =(ListView) findViewById(R.id.listV);
        listView.setAdapter(listAdapter);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    Intent intent = new Intent( apiActivity.this, ExerciseActivity.class  );
                    startActivity(intent);
                }
                else if(position == 1  ){
                    Intent intent = new Intent( apiActivity.this, CaloriesActivity.class  );
                    startActivity(intent);

                }else if(position == 2  ) {
                    Intent intent = new Intent(apiActivity.this, MoviesActivity.class);
                    startActivity(intent);
                }

            }
        };
        listView.setOnItemClickListener(itemClickListener);
    }


    public void btn_OnClick(View view) {
        Toast.makeText(apiActivity.this,"You have been logged out successfully",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent( apiActivity.this, MainActivity.class  );
        startActivity(intent);


    }
}