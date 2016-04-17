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

import org.json.JSONArray;

import java.io.File;

public class NewJob extends AppCompatActivity implements View.OnClickListener{

    Button bSelect,bSubmit;
    EditText etJobName,etNOP;
    TextView tvFilePath;
    FileChooser fileChooser;
    ServerConnection serverConnection;

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

        }

    }
}
