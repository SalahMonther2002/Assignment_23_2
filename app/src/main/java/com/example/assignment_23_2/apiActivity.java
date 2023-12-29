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

    /* Regarding cars, I have read information that may be of interest to the user,
    the power of the car in terms of speed, and where this car was made. In my opinion, this is information of interest to the user.
    /* The same applies to movies, where there is information that may interest the user.
    * */
    private final String[] itemList = {"Cars","Movies"} ;

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
                    Intent intent = new Intent( apiActivity.this, CarsActivity.class  );
                    startActivity(intent);
                }
                else if(position == 1  ){
                    Intent intent = new Intent( apiActivity.this, MoviesActivity.class  );
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