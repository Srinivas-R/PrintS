package com.example.srinivas.loginapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Srinivas on 03-Apr-16.
 */
public class ServerTask extends AsyncTask<String,String,String> {

    private String url;
    private User user;
    private User obtainedUser;
    private PrintJob[] obtainedPrintJobs;
    private Context context;
    private int type;                              //1 - Get User, 2 - Get Array of PrintJobs

    public User getObtainedUser()
    {
        return obtainedUser;
    }


    ServerTask(User u,String URL,int Type,Context context)
    {
        this.url = URL;
        this.type = Type;
        this.user = u;
        this.obtainedUser = null;
        this.obtainedPrintJobs = null;
        this.context = context;

    }

    @Override
    protected String doInBackground(String... params) {
        try {

            if(type == 1)
            {
                JSONObject jsonObject = ServerConnection.loadUserFromDatabase(user,url,context);
                //Parse into user object, store in obtainedUser
                obtainedUser = JSONParser.JSONtoUser(jsonObject);
                return jsonObject.toString();
            }
            else
            {
                JSONArray jsonArray = ServerConnection.loadPrintJobsFromDatabase(user,url,context);
                //Parse into PrintJob object, store in obtainedPrintJobs array
                obtainedPrintJobs = JSONParser.JSONtoPrintJobs(jsonArray);
                return jsonArray.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    protected void onPostExecute(String s) {
        if(type == 1)
        {
            try {
                if(s.length() > 0)
                {
                    JSONObject temp = null;
                    temp = new JSONObject(s);
                    User tempUser = JSONParser.JSONtoUser(temp);
                    Toast.makeText(context,"Username : " + tempUser.name,Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(context,"No data received",Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
            if(s.length() > 0)
                Toast.makeText(context,"PrintJobs received",Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context,"No PrintJobs received",Toast.LENGTH_LONG).show();
        }
    }
}
