package com.example.srinivas.loginapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterScreen extends AppCompatActivity implements View.OnClickListener{

    Button bRegister;
    EditText etName,etUSN,etMobile, etPassword;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        */
        userLocalStore = new UserLocalStore(this);
        bRegister = (Button)findViewById(R.id.button4);
        etName = (EditText)findViewById(R.id.editText3);
        etUSN = (EditText)findViewById(R.id.editText4);
        etMobile = (EditText)findViewById(R.id.editText5);
        etPassword = (EditText)findViewById(R.id.editText6);

        bRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.button4:             //Register
                String name = etName.getText().toString();
                String USN = etUSN.getText().toString();
                String password = etPassword.getText().toString();
                String mobile = etMobile.getText().toString();
                User user = new User(name,USN,password,mobile,null,0);

                ServerTask serverTask = new ServerTask(user,"http://25.51.242.195:3001/login",4,RegisterScreen.this);
                serverTask.execute();
                break;
        }

    }



}
