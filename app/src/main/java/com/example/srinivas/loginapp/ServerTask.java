package com.example.srinivas.loginapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
public class ServerTask extends AsyncTask<Void,String,String> {

    private UserLocalStore userLocalStore;
    private ProgressDialog progressDialog;
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
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        userLocalStore = new UserLocalStore(context);

    }

    @Override
    protected String doInBackground(Void... params) {
        try {

            if(type == 1)
            {
                JSONObject jsonObject = ServerConnection.loadUserFromDatabase(user,url);
                //Parse into user object, store in obtainedUser
                obtainedUser = JSONParser.JSONtoUser(jsonObject);
                if(jsonObject!=null)
                return jsonObject.toString();
            }
            else if(type == 2)
            {
                JSONArray jsonArray = ServerConnection.loadPrintJobsFromDatabase(user,url);
                //Parse into PrintJob object, store in obtainedPrintJobs array
                obtainedPrintJobs = JSONParser.JSONtoPrintJobs(jsonArray);
                return jsonArray.toString();
            }
            else if(type == 3)
            {
                ServerConnection.sendPrintJobToDatabase(user,url);
                return new String("PrintJobSent");
            }
            else
            {
                ServerConnection.sendUserToDatabase(user,url);
                return new String("UserSent");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
        if(type == 1)
        {
            if(s != null)
            {
                Toast.makeText(context,"Username : " + obtainedUser.name,Toast.LENGTH_LONG).show();
                userLocalStore.storeUserData(obtainedUser);
                userLocalStore.setUserLoggedIn(true);
                context.startActivity(new Intent(context,MainActivity.class));
            }
            else
                Toast.makeText(context,"No data received",Toast.LENGTH_LONG).show();

        }
        else if(type == 2)
        {
            if(s.length() > 0)
                Toast.makeText(context,"PrintJobs received",Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context,"No PrintJobs received",Toast.LENGTH_LONG).show();
        }
        else if(type == 3)
        {
            if(s == "PrintJobSent")
                Toast.makeText(context,"PrintJobs Sent",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context,"PrintJobs not Sent",Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(s == "UserSent")
                Toast.makeText(context,"User Sent",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context,"User not sent",Toast.LENGTH_SHORT).show();
        }
    }
}
