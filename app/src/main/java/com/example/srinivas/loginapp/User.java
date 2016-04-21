package com.example.srinivas.loginapp;

/**
 * Created by Srinivas on 23-Mar-16.
 */
public class User {

    String name,USN,password,mobile;
    PrintJob myJobs[];
    int numberOfJobs;

    public User(String n,String u,String p,String m,PrintJob[] MJ,int nOPJ)
    {
        name = n;
        USN = u;
        password = p;
        mobile = m;
        myJobs = MJ;
        numberOfJobs = nOPJ;
    }

    public User(String u,String p)
    {
        name = "";
        USN = u;
        password = p;
        mobile = "";
        numberOfJobs = 0;
        myJobs = new PrintJob[10];
    }

    public void addPrintJob(PrintJob printJob)
    {
        if(numberOfJobs == 10)
            throw new ArrayIndexOutOfBoundsException("More than 10 print jobs");
        numberOfJobs++;
        if(numberOfJobs == 1)
            myJobs = new PrintJob[10];
        myJobs[numberOfJobs] = printJob;
    }

    public PrintJob getLatestPrintJob()
    {
        return myJobs[numberOfJobs];
    }

    public String[] getJobNames()
    {
        String[] output = new String[numberOfJobs];
        for(int i=0;i<numberOfJobs;i++)
        {
            output[i] = myJobs[i].jobName;
        }
        return output;
    }

    public PrintJob searchPrintJobs(String name)
    {
        for(int i=0;i<numberOfJobs;i++)
        {
            if(myJobs[i].jobName == name)
                return myJobs[i];
        }
        return null;
    }


}
