package com.example.srinivas.loginapp;
import java.io.File;

/**
 * Created by Srinivas on 23-Mar-16.
 */
public class PrintJob {

    File f;
    String associatedUSN;
    String jobName,localFilePath;
    int price,numberOfPages;
    boolean completed;

    public PrintJob()
    {
        f = null;
        associatedUSN = null;
        jobName = "";
        localFilePath = "";
        price = 0;
        numberOfPages = 0;
        completed = true;
    }

    public PrintJob(String aUSN,String jN,String fP,int noP,int price,boolean completed)
    {
        associatedUSN = aUSN;
        jobName = jN;
        localFilePath = fP;
        numberOfPages = noP;
        //f = new File(localFilePath);
        f = null;
        this.completed = completed;
        this.price = price;
        /*if(!f.exists())
        {
            localFilePath = null;
            throw new IllegalArgumentException("Invalid path to file, PrintJob constructor");
        }*/

    }



}
