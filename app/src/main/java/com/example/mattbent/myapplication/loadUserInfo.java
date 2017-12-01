package com.example.mattbent.myapplication;

import android.content.Context;
import android.util.Log;

public class loadUserInfo {
    public loadUserInfo()
    {

    }

    fileHandle loadInfo = new fileHandle();     //create a new constructor, helper function fileHandle

    public String getAccount(Context context)
    {
        String input = loadInfo.readFromFile(context,"Account1.txt");
        String splitS[] =input.split(";");
        String output = splitS[0].replace("account=","");
        return output;
    }

    public String getPassword(Context context)
    {
        String input = loadInfo.readFromFile(context,"Account1.txt");
        String splitS[] =input.split(";");
        String output = splitS[1].replace("passowrd=","");
        return output;
    }

    public String getFingerprint(Context context)
    {
        String input = loadInfo.readFromFile(context,"Account1.txt");
        String splitS[] =input.split(";");
        String output = splitS[2].replace("fingerprint=","");
        return output;
    }

    public double getSaving(Context context)
    {
        String input = loadInfo.readFromFile(context,"Saving1.txt");
        String splitS[] =input.split(";");
        String output = splitS[0].replace("money=","");
        double outputNum = Double.parseDouble(output);
        return outputNum;
    }

    public double getChecking(Context context)
    {
        String input = loadInfo.readFromFile(context,"Checking1.txt");
        String splitS[] =input.split(";");
        String output = splitS[0].replace("money=","");
        double outputNum = Double.parseDouble(output);
        return outputNum;
    }

    public String getSavingTrans(Context context)
    {
        String input = loadInfo.readFromFile(context,"Saving1.txt");
        String splitS[] =input.split(";");
        StringBuilder sb = new StringBuilder();
        for(int i =1; i < splitS.length; i++)
        {
            sb.append(splitS[i]).append('\n');
        }
        return sb.toString();
    }

    public String getCheckingTrans(Context context)
    {
        String input = loadInfo.readFromFile(context,"Checking1.txt");
        String splitS[] =input.split(";");
        StringBuilder sb = new StringBuilder();
        for(int i =1; i < splitS.length; i++)
        {
            sb.append(splitS[i]).append('\n');
        }
        return sb.toString();
    }

}
