package com.example.srinivas.loginapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class MyJobs extends AppCompatActivity{

    UserLocalStore userLocalStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_jobs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        populateListView();
        registerClick();
    }

    private void populateListView() {

        //Create list of items to show
        User temp = userLocalStore.getLoggedInUser();
        String[] jobNames = temp.getJobNames();
        //String[] jobNames = {"Srinivas","Karthik","This shit is Working"};
        //Create Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                jobNames);

        //Show in listView
        ListView list = (ListView)findViewById(R.id.listView);
        list.setAdapter(adapter);
    }

    private void registerClick()
    {
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                String jobName = textView.getText().toString();
                PrintJob job = userLocalStore.getLoggedInUser().searchPrintJobs(jobName);
                if(job == null)
                    Toast.makeText(MyJobs.this,"Error, Searched and found null job",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MyJobs.this,
                            "Number of Pages : " + job.numberOfPages + "\nCompleted : " + job.completed,
                            Toast.LENGTH_LONG).show();
            }
        });


    }


}
