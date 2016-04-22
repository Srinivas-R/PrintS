package com.example.srinivas.loginapp;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.transform.stream.StreamResult;

/**
 * Created by Srinivas on 27-Mar-16.
 */

//Contains static method to take URL as param and return JSONArray
public class ServerConnection extends Application {

    //Each method will request from seperate tables

    //First method sends a user with USN and password, receives JSON object with full User details
    public static JSONObject loadUserFromDatabase(User user,String Address) throws IOException
    {

        JSONObject jsonUser = new JSONObject();
        JSONObject receivedUser = null;
        JSONArray tempreceivedUser = null;

        HttpURLConnection conn = null;
        BufferedReader streamReader = null;


        try {
            jsonUser.put("USN",user.USN);
            jsonUser.put("passwd",user.password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            URL url = new URL(Address);

            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("POST");
            conn.connect();

            OutputStream os = conn.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os,"UTF-8");
            osw.write(jsonUser.toString());

            osw.flush();
            osw.close();

            InputStream is = conn.getInputStream();
            streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuffer responseStrBuffer = new StringBuffer();

            String inputStr = "";
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuffer.append(inputStr);

            if(responseStrBuffer.length()>0)
            {
                tempreceivedUser = new JSONArray(responseStrBuffer.toString());
                receivedUser = tempreceivedUser.getJSONObject(0);
            }
            else
                receivedUser = JSONParser.UsertoJSON(new User("Error","DummyPassword"));

        } catch (java.net.SocketTimeoutException e) {
            e.printStackTrace();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {

            if(conn != null)
            conn.disconnect();

            if(streamReader != null)
            streamReader.close();

        }
        return receivedUser;
    }

    //Second method sends a user, receives JSON array containing PrintJobs[]
    public static JSONArray loadPrintJobsFromDatabase(User user,String Address) throws IOException
    {

        JSONObject JSONuser = JSONParser.UsertoJSON(user);
        JSONArray jsonArray = null;
        HttpURLConnection conn = null;
        BufferedReader streamReader = null;

        try {
            URL url = new URL(Address);

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.connect();

            //SEND USER FOR WHICH JOBS MUST ACCESSED
            OutputStream os = conn.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os,"UTF-8");
            osw.write(JSONuser.toString());

            osw.flush();
            osw.close();

            InputStream is = conn.getInputStream();
            streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuffer responseStrBuffer = new StringBuffer();

            String inputStr = "";
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuffer.append(inputStr);

            jsonArray = new JSONArray(responseStrBuffer.toString());

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {

            if(conn != null)
                conn.disconnect();

            if(streamReader != null)
                streamReader.close();

        }

        return jsonArray;
    }

    public static JSONObject sendPrintJobToDatabase(User user,String Address) throws IOException
    {
        JSONObject jsonPrintJob = JSONParser.PrintJobtoJSON(user.getLatestPrintJob());
        JSONObject response = null;
        BufferedReader streamReader = null;
        HttpURLConnection conn =  null;

        try {
            URL url = new URL(Address);

            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("POST");
            conn.connect();

            OutputStream os = conn.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os,"UTF-8");
            osw.write(jsonPrintJob.toString());

            osw.flush();
            osw.close();

            InputStream is = conn.getInputStream();
            streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuffer responseStrBuffer = new StringBuffer();

            String inputStr = "";
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuffer.append(inputStr);

            if(responseStrBuffer.length()>0)
            {
                response = new JSONObject(responseStrBuffer.toString());
            }
            else
            {
                response = new JSONObject();
                response.put("Success",false);
            }



        } catch (java.net.SocketTimeoutException e) {
            e.printStackTrace();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {

            if(conn != null)
                conn.disconnect();

            if(streamReader != null)
                streamReader.close();

        }
        return response;


    }

    public static JSONObject sendUserToDatabase(User user,String Address) throws IOException
    {
        JSONObject jsonUser = JSONParser.UsertoJSON(user);
        HttpURLConnection conn = null;
        JSONObject receivedUser = null;
        BufferedReader streamReader = null;

        try
        {
            URL url = new URL(Address);
            conn = (HttpURLConnection)url.openConnection();

            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.connect();

            OutputStream os = conn.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os,"UTF-8");
            osw.write(jsonUser.toString());

            osw.flush();
            osw.close();

            InputStream is = conn.getInputStream();
            streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuffer responseStrBuffer = new StringBuffer();

            String inputStr = "";
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuffer.append(inputStr);

            if(responseStrBuffer.length()>0)
                receivedUser = new JSONObject(responseStrBuffer.toString());
            else
                receivedUser = JSONParser.UsertoJSON(new User("Error","DummyPassword"));

        } catch (java.net.SocketTimeoutException e){
            e.printStackTrace();
        } catch (IOException | JSONException e){
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        } finally {
            if(conn != null)
                conn.disconnect();

            if(streamReader != null)
                streamReader.close();

        }
        return receivedUser;

    }

}
