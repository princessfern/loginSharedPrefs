package com.example.app_with_shared_perference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    Button buttonForLogIn ;
    Button buttonForSignUp;
    EditText userNameInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MyDBhelper dBhelper=new MyDBhelper(this);

        ArrayList<UserModel> userModels;
        userModels = dBhelper.fetchUsers();
        for(int i=0;i<userModels.size();i++){
            Log.d("USER_TABLE_INFO", "USERNAME: " + userModels.get(i).userName + " PASSWORD: " + userModels.get(i).password);
        }

        buttonForLogIn = findViewById(R.id.buttonLoginActivity);
        buttonForLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Verification code.

                userNameInput = (EditText) findViewById(R.id.username);
                passwordInput = (EditText) findViewById(R.id.password);
                String username = userNameInput.getText().toString();
                String password = passwordInput.getText().toString();
                ArrayList<UserModel> userModels;
                userModels = dBhelper.fetchUsers();
                Boolean valid_login = false;
                for(int i=0;i<userModels.size();i++){
                    if(username==userModels.get(i).userName && password==userModels.get(i).password){
                        valid_login = true;
                        SharedPreferences pref = getSharedPreferences("login",MODE_PRIVATE);
                        /*
                         * We can only insert shared preference only though the editor.*/
                        Toast.makeText(LoginActivity.this,
                                "TEST", Toast.LENGTH_SHORT).show();

                        SharedPreferences.Editor editor = pref.edit();
                        //Set the flag value to be true.
                        editor.putBoolean("flag",true);
                        editor.apply();
                        Intent iHome = new Intent(LoginActivity.this,
                                HomeActivity.class);
                        Toast.makeText(LoginActivity.this,
                                "User logged in! ", Toast.LENGTH_SHORT).show();
                    }
                }
                if (valid_login==false){
                    Toast.makeText(LoginActivity.this,
                            "Invalid Login.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonForSignUp = findViewById(R.id.signup);
        buttonForSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(LoginActivity.this,
                        "TEST", Toast.LENGTH_SHORT).show();
                userNameInput = (EditText) findViewById(R.id.username);
                passwordInput = (EditText) findViewById(R.id.password);
                String username = userNameInput.getText().toString();
                String password = passwordInput.getText().toString();
                ArrayList<UserModel> userModels;
                userModels = dBhelper.fetchUsers();
                boolean matched = false;
                for(int i=0;i<userModels.size();i++){
                    if(username==userModels.get(i).userName) {
                        Toast.makeText(LoginActivity.this,
                                "Invalid Username. Please Try again.", Toast.LENGTH_SHORT).show();
                        matched = true;
                    }
                }
                if(matched == false){
                    dBhelper.addUser(username, password);
                    Toast.makeText(LoginActivity.this,
                            "User created! Click Login.", Toast.LENGTH_SHORT).show();
                    for(int i=0;i<userModels.size();i++){
                        Log.d("USER_TABLE_INFO", "USERNAME: " + userModels.get(i).userName + " PASSWORD: " + userModels.get(i).password);
                    }
                }
            }
        });
    }
}