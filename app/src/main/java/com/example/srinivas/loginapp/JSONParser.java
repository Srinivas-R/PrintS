package com.example.srinivas.loginapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Srinivas on 27-Mar-16.
 */
public class JSONParser {

    private static PrintJob JSONtoPrintJob(JSONObject job)
    {
        try {
            String aUSN = job.getString("USN");
            String jN = job.getString("JobName");
            String fP = "";
            int nop = job.getInt("NumberPages");
            int price = job.getInt("Price");
            boolean completed = job.getBoolean("Completed");
            return new PrintJob(aUSN,jN,fP,nop,price,completed);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static JSONObject PrintJobtoJSON(PrintJob printJob)
    {
        JSONObject output = new JSONObject();
        try {
            output.put("JobName",printJob.jobName);
            output.put("Price",printJob.price);
            output.put("NumberPages",printJob.numberOfPages);
            output.put("Completed",printJob.completed);
            output.put("USN",printJob.associatedUSN);
            output.put("userUSN",printJob.associatedUSN);

            return output;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONArray PrintJobstoJSON(PrintJob[] printJob,int n)
    {
        JSONObject temp = null;
        JSONArray JSONjobs = new JSONArray();
        if(printJob!=null)
        for(int i = 0;i < n;i++)
        {
            temp = PrintJobtoJSON(printJob[i]);
            JSONjobs.put(temp);
        }
        return JSONjobs;

    }

    public static PrintJob[] JSONtoPrintJobs(JSONArray jobs)
    {
        PrintJob[] myJobs = new PrintJob[10];
        if(jobs.length() >= 10)
            throw new ArrayIndexOutOfBoundsException("JSONArray size more than 10,cannot parse");
        for(int i = 0; i < jobs.length(); i++)
        {
            try {
                JSONObject tempJob = jobs.getJSONObject(i);
                PrintJob temp = JSONtoPrintJob(tempJob);
                myJobs[i] = temp;

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return myJobs;
    }

    public static User JSONtoUser(JSONObject parentObject)
    {
            if(parentObject == null || parentObject.length() == 0)
                return null;
            String name,USN,password,mobile;
            PrintJob[] myJobs;
            JSONArray jobs;
            int n;

            try {
                password = parentObject.getString("passwd");
                USN = parentObject.getString("USN");
                name = parentObject.getString("Name");
                mobile = parentObject.getString("Mobile");
                //jobs = parentObject.getJSONArray("PrintJobs");
                jobs = null;
                try {
                    jobs = parentObject.getJSONArray("PrintJobs");
                }catch (JSONException e){
                    e.printStackTrace();
                }
                if(jobs == null)
                    n = 0;
                else
                    n = jobs.length();
                if(n==0)
                    myJobs = null;
                else
                    myJobs = JSONtoPrintJobs(jobs);

                return new User(name,USN,password,mobile,myJobs,n);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;

    }

    public static JSONObject UsertoJSON(User user)
    {
        JSONObject output = new JSONObject();

        String name,USN,password,mobile;
        PrintJob[] myJobs;

        int numberOfJobs;

        name = user.name;
        USN = user.USN;
        password = user.password;
        mobile = user.mobile;
        myJobs = user.myJobs;
        numberOfJobs = user.numberOfJobs;

        JSONArray jobs = PrintJobstoJSON(myJobs,numberOfJobs);

        try
        {
            output.put("userId","Dummy");
            output.put("passwd",password);
            output.put("USN",USN);
            output.put("Name",name);
            output.put("Mobile",mobile);
            //output.put("numberOfPendingJobs",numberOfPendingJobs);
            output.put("PrintJobs",jobs);
            return  output;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;


    }
}
