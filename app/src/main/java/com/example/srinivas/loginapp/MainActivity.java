package com.example.srinivas.loginapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button bLogout,bNewJob,bMyJobs;
    TextView tvName,tvUSN;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TESTING
        startActivity(new Intent(this, MyJobs.class));
        userLocalStore = new UserLocalStore(this);
        bLogout = (Button)findViewById(R.id.button3);
        bNewJob = (Button)findViewById(R.id.button5);
        bMyJobs = (Button)findViewById(R.id.button6);
        tvName  = (TextView)findViewById(R.id.textView);
        tvUSN  = (TextView)findViewById(R.id.textView2);

        bLogout.setOnClickListener(this);
        bNewJob.setOnClickListener(this);
        bMyJobs.setOnClickListener(this);

        bNewJob.setEnabled(false);
        bMyJobs.setEnabled(false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(authenticate()==true)
            allow();
        else
            startActivity(new Intent(MainActivity.this,LoginScreen.class));


    }

    private void allow()
    {
        User user = userLocalStore.getLoggedInUser();
        tvName.setText(user.name);
        tvUSN.setText(user.USN);
        bNewJob.setEnabled(true);
        bMyJobs.setEnabled(true);

    }

    private boolean authenticate()
    {
        return userLocalStore.getUserLoggedIn();
    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.button3:      //Logout
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);
                startActivity(new Intent(this,LoginScreen.class));
                break;

            case R.id.button5:      //New Job
                startActivity(new Intent(this,NewJob.class));
                break;

            case R.id.button6:      //MyJobs
                startActivity(new Intent(this,MyJobs.class));
                break;

        }

    }
}
