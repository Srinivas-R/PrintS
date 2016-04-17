package com.example.srinivas.loginapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class LoginScreen extends AppCompatActivity implements View.OnClickListener{

    Button bLogin,bRegister;
    EditText etUSN, etPassword;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        userLocalStore = new UserLocalStore(this);
        bLogin = (Button)findViewById(R.id.button);
        bRegister = (Button)findViewById(R.id.button2);
        etUSN = (EditText)findViewById(R.id.editText);
        etPassword = (EditText)findViewById(R.id.editText2);
        bRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.button:        //Login

                User user = new User(etUSN.getText().toString(),etPassword.getText().toString());

                //Send a user with the entered USN, password to the server
                ServerTask serverTask = new ServerTask(user,"http://localhost:3001/users",1,this);

                //This dummy string serves no purpose, I'm too lazy to change the function prototype
                String dummy = null;
                serverTask.execute(dummy);


                userLocalStore.storeUserData(user);
                userLocalStore.setUserLoggedIn(true);
                break;

            case R.id.button2:       //Register
                startActivity(new Intent(this,RegisterScreen.class));
                break;

        }

    }


}
