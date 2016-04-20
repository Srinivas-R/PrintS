package com.example.srinivas.loginapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginScreen extends AppCompatActivity implements View.OnClickListener{

    Button bLogin,bRegister;
    EditText etUSN, etPassword;
    UserLocalStore userLocalStore;
    TextView textView;

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
        bLogin.setOnClickListener(this);
        bRegister.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.button:        //Login
                //etUSN.setText("Doign soemthing");
                User user = new User(etUSN.getText().toString(),etPassword.getText().toString());
                Toast.makeText(LoginScreen.this, "Doing something", Toast.LENGTH_SHORT).show();

                //Send a user with the entered USN, password to the server
                ServerTask serverTask = new ServerTask(user,"http://192.168.43.95:3001/login",1,LoginScreen.this);

                //This dummy string serves no purpose, I'm too lazy to change the function prototype
                String dummy = null;
                serverTask.execute(dummy);
                user = serverTask.getObtainedUser();
                userLocalStore.storeUserData(user);
                userLocalStore.setUserLoggedIn(true);

                break;

            case R.id.button2:       //Register
                startActivity(new Intent(this,RegisterScreen.class));
                break;

        }

    }


}
