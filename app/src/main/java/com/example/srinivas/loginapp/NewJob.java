package com.example.srinivas.loginapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

public class NewJob extends AppCompatActivity implements View.OnClickListener{

    Button bSelect,bSubmit;
    EditText etJobName,etNOP;
    TextView tvFilePath;
    FileChooser fileChooser;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_job);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bSubmit = (Button)findViewById(R.id.button8);
        bSelect = (Button)findViewById(R.id.button7);
        etJobName = (EditText)findViewById(R.id.editText7);
        etNOP = (EditText)findViewById(R.id.editText8);
        tvFilePath = (TextView)findViewById(R.id.textView3);
        fileChooser = new FileChooser(NewJob.this).setFileListener(new FileChooser.FileSelectedListener() {
            @Override
            public void fileSelected(File file) {
                tvFilePath.setText(file.getAbsolutePath());
            }
        });
        userLocalStore = new UserLocalStore(this);

        bSubmit.setOnClickListener(this);
        bSelect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button7:                  //Choose File
                fileChooser.showDialog();
                break;

            case R.id.button8:                  //Submit
                PrintJob printJob = new PrintJob(userLocalStore.getLoggedInUser().USN,
                        etJobName.getText().toString(),tvFilePath.toString(),
                        Integer.parseInt(etNOP.getText().toString()),
                        Integer.parseInt(etNOP.getText().toString()),
                        false);
                User temp = userLocalStore.getLoggedInUser();
                temp.addPrintJob(printJob);
                Toast.makeText(this, "Sending PrintJob", Toast.LENGTH_SHORT);
                ServerTask serverTask = new ServerTask(temp,"http://25.51.242.195:3001/jobs/create",3,NewJob.this);
                String dummy = null;
                serverTask.execute(dummy);
        }

    }
}
