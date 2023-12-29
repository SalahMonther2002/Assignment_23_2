package com.example.assignment_23_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    public static final String DATA = "DATA";
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private EditText txtuser;
    private EditText txtpass;
    private EditText txtdate;
    private EditText txtphone;
    private Button btnreg;

    protected static ArrayList<User> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupViews();
        setupSharedPrefs();

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtuserV = txtuser.getText().toString();
                String txtpassV = txtpass.getText().toString();
                String txtdateV = txtdate.getText().toString();
                String txtphoneV = txtphone.getText().toString();
                boolean isExist = false;

                if( !txtuserV.isEmpty() &&  !txtpassV.isEmpty() && !txtdateV.isEmpty() && !txtphoneV.isEmpty()){
                    Gson gson = new Gson();
                    String str = prefs.getString(DATA, "");
                    if(!str.equals("")) {
                        list =  gson.fromJson(str, new TypeToken<ArrayList<User>>(){}.getType());
                        for(int i=0; i< list.size();i++){
                            if(txtuserV.equals(list.get(i).getUsername())){
                                Toast.makeText(RegisterActivity.this,"You are trying to register with a user who is already registered!!",Toast.LENGTH_SHORT).show();
                                isExist=true;
                                break;
                            }
                        }
                        if(!isExist){
                            list.add(new User(txtuserV,txtpassV,txtdateV,txtphoneV));
                            Toast.makeText(RegisterActivity.this,"You have been registered successfully!!",Toast.LENGTH_SHORT).show();
                            txtuser.setText("");
                            txtpass.setText("");
                            txtdate.setText("");
                            txtphone.setText("");

                            String usersString = gson.toJson(list);

                            editor.putString(DATA, usersString);
                            editor.commit();
                        }


                    }else{
                        list.add(new User(txtuserV,txtpassV,txtdateV,txtphoneV));
                        Toast.makeText(RegisterActivity.this,"You have been registered successfully!!",Toast.LENGTH_SHORT).show();

                        txtuser.setText("");
                        txtpass.setText("");
                        txtdate.setText("");
                        txtphone.setText("");


                        String usersString = gson.toJson(list);

                        editor.putString(DATA, usersString);
                        editor.commit();
                    }


                }else{
                    Toast.makeText(RegisterActivity.this,"You are missed some fields!!",Toast.LENGTH_SHORT).show();
                }

            }
        });





    }

    private void setupViews() {
        txtuser = findViewById(R.id.txtuser);
        txtpass = findViewById(R.id.txtpass);
        txtdate = findViewById(R.id.txtdate);
        txtphone = findViewById(R.id.txtphone);
        btnreg = findViewById(R.id.btnreg);

    }

    private void setupSharedPrefs() {
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }

    /*If the user mistakenly exits the registration activity and has entered all his information but has not completed the registration process,
    it does not make sense for a good application not to save this information that the user entered.
    So I made some modifications to the life cycle call back method.
    The first cycle is when the activity turns off "ex: when user click on back btn", then onPause() onStop() onDestroy() may be called.
    onPause() method :This method must contain code that must run quickly, so
    the code to save the data locally must be in onStop().
     */
    @Override
    protected void onStop() {
        super.onStop();

        // Save entered user data

        editor.putString("username", txtuser.getText().toString());
        editor.putString("password", txtpass.getText().toString());
        editor.putString("date", txtdate.getText().toString());
        editor.putString("phone", txtphone.getText().toString());
        editor.commit();
    }
    /* When the user returns to the registration activity from which the user had gone,
    we said that it is logical that the data he entered is in the correct place, and this is correct.
    When activity is run,the onCreate() onStart() onResume() will be called.
     */
    @Override
    protected void onStart() {
        super.onStart();

        String username = prefs.getString("username", "");
        String password = prefs.getString("password", "");
        String date = prefs.getString("date", "");
        String phone = prefs.getString("phone", "");
        txtuser.setText(username);
        txtpass.setText(password);
        txtdate.setText(date);
        txtphone.setText(phone);
    }
}