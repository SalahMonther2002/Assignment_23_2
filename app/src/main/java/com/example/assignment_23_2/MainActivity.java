package com.example.assignment_23_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    public static final String NAME = "NAME";
    public static final String PASS = "PASS";
    public static final String FLAG = "FLAG";
    private boolean flag = false;
    private EditText txtuser;
    private EditText txtpass;
    private CheckBox chk;
    private Button btnlog;
    private Button btnreg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        setupSharedPrefs();
        checkPrefs();

        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtuserV = txtuser.getText().toString();
                String txtpassV = txtpass.getText().toString();
                boolean isExist =false;
                if( !txtuserV.isEmpty() &  !txtpassV.isEmpty()){
                    Gson gson = new Gson();
                    String str = prefs.getString(RegisterActivity.DATA, "");
                    if(!str.equals("")) {
                        RegisterActivity.list =  gson.fromJson(str, new TypeToken<ArrayList<User>>(){}.getType());
                        for(int i=0; i< RegisterActivity.list.size();i++){
                            if(txtuserV.equals(RegisterActivity.list.get(i).getUsername()) & txtpassV.equals(RegisterActivity.list.get(i).getPassword()) ){
                              if(chk.isChecked()){
                                    editor.putString(NAME, txtuserV);
                                    editor.putString(PASS, txtpassV);
                                    editor.putBoolean(FLAG, true);
                                    editor.apply();
                                }


                            Toast.makeText(MainActivity.this,"You have been logged in successfully",Toast.LENGTH_SHORT).show();

                            isExist=true;
                            break;
                            }
                        }
                        if(isExist){
                            Intent intent = new Intent( MainActivity.this, apiActivity.class  );
                            startActivity(intent);
                        }else{
                            Toast.makeText(MainActivity.this,"You do not have an account in this Username and Password to log in !!",Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(MainActivity.this,"You do not have an account in this Username and Password to login !!",Toast.LENGTH_SHORT).show();

                    }



                }else{
                Toast.makeText(MainActivity.this,"You are missed some fields!!",Toast.LENGTH_SHORT).show();
            }


            }
        });

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MainActivity.this, RegisterActivity.class  );
                startActivity(intent);
            }
        });

    }

    private void setupViews() {
        txtuser = findViewById(R.id.txtuser);
        txtpass = findViewById(R.id.txtpass);
        chk = findViewById(R.id.chk);
        btnlog = findViewById(R.id.btnlog);
        btnreg = findViewById(R.id.btnreg);

    }
    private void setupSharedPrefs() {
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }

    private void checkPrefs() {
        flag = prefs.getBoolean(FLAG, false);

        if(flag){
            String name = prefs.getString(NAME, "");
            String password = prefs.getString(PASS, "");
            txtuser.setText(name);
            txtpass.setText(password);
            chk.setChecked(true);
        }
    }



    public void btnLoginOnClick(View view) {
        String txtuserV = txtuser.getText().toString();
        String txtpassV = txtpass.getText().toString();
        if (chk.isChecked()) {
            if (!flag) {
                editor.putString(NAME, txtuserV);
                editor.putString(PASS, txtpassV);
                editor.putBoolean(FLAG, true);
                editor.apply();
            }
        }

    }



}