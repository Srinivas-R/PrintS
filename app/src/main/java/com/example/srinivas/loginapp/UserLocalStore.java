package com.example.srinivas.loginapp;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Srinivas on 23-Mar-16.
 */
public class UserLocalStore {

    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context)
    {
        userLocalDatabase = context.getSharedPreferences(SP_NAME,0);
    }

    public void storeUserData(User user)
    {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("Name",user.name);
        spEditor.putString("USN",user.USN);
        spEditor.putString("Password",user.password);
        spEditor.putString("Mobile",user.mobile);
        spEditor.putInt("NumberOfPendingJobs", user.numberOfPendingJobs);
        spEditor.putString("MyJobs", JSONParser.PrintJobstoJSON(user.myJobs, user.numberOfPendingJobs).toString());
        spEditor.commit();

    }

    public User getLoggedInUser()
    {
        String name = userLocalDatabase.getString("Name", "");
        String USN = userLocalDatabase.getString("USN","");
        String password = userLocalDatabase.getString("Password","");
        String mobile = userLocalDatabase.getString("Mobile","");
        int numberOfPendingPages = userLocalDatabase.getInt("NumberOfPendingJobs", 0);
        String myJobs = userLocalDatabase.getString("MyJobs", "");
        PrintJob[] printJobs = null;
        try {
            printJobs = JSONParser.JSONtoPrintJobs(new JSONArray(myJobs));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        User temp = new User(name,USN,password,mobile,printJobs,numberOfPendingPages);
        return temp;

    }

    public void setUserLoggedIn(boolean loggedIn)
    {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn",loggedIn);
        spEditor.commit();
    }

    public boolean getUserLoggedIn()
    {
        if(userLocalDatabase.getBoolean("loggedIn",false) == true)
            return true;
        else
            return false;
    }

    public void clearUserData()
    {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();

    }



}
