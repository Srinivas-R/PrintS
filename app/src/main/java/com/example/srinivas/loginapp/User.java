package com.example.srinivas.loginapp;

/**
 * Created by Srinivas on 23-Mar-16.
 */
public class User {

    String name,USN,password,mobile;
    PrintJob myJobs[];
    int numberOfPendingJobs;

    public User(String n,String u,String p,String m,PrintJob[] MJ,int nOPJ)
    {
        name = n;
        USN = u;
        password = p;
        mobile = m;
        myJobs = MJ;
        numberOfPendingJobs = nOPJ;
    }

    public User(String u,String p)
    {
        name = "";
        USN = u;
        password = p;
        mobile = "";
        numberOfPendingJobs = 0;
        myJobs = null;
    }


}
