package com.example.mattbent.myapplication;

//GUI imports
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//Read File Imports
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.content.res.AssetManager;
import android.content.Context;

//Other imports
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //==============================================================================================
    //Read Files(Using arraylist)
    private ArrayList readFileArrayList(String fileName) {
        Context context = this;
        AssetManager am = context.getAssets();
        try {
            InputStream is = am.open(fileName);
            ArrayList list = new ArrayList();
            list = convertStreamToArrayList(is);
            return list;
        }catch (IOException e) {
            System.out.println("cant find the file");
        }
        return null;
    }
    //Convert stream to ArrayList
    private ArrayList convertStreamToArrayList(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ArrayList list = new ArrayList();
        String line = null;

        try {
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    //Read Files(using String)
    private String readFileString(String fileName) {
        Context context = this;
        AssetManager am = context.getAssets();
        try {
            InputStream is = am.open(fileName);
            String  response = convertStreamToString(is);
            return response;
        }catch (IOException e) {
            System.out.println("cant find the file");
        }
        return null;
    }
    //Convert stream to String
    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    //==============================================================================================
    //write the file into internal storage
    private void writeToFile(String data,Context context,String fileName) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    //==============================================================================================
    public void login(){
        Intent intent = new Intent(this, CBActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.btn_login);

        //store saving and checking into internal storage
        String readSaving;
        String readChecking;

        readSaving = readFileString("Saving1.txt");
        readChecking = readFileString("Checking1.txt");
        writeToFile(readSaving,this,"Saving1.txt");
        writeToFile(readChecking,this,"Checking1.txt");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //read file password and account name
                ArrayList list = readFileArrayList("Account1.txt");
                String splitS[];
                String splitS2[];
                String account = list.get(0).toString();
                splitS = account.split("=");
                account = splitS[1];
                String password = list.get(1).toString();
                splitS2 = password.split("=");
                password = splitS2[1];

                //read user input
                EditText accountN =findViewById(R.id.input_email);
                EditText passwordN =findViewById(R.id.input_password);
                String accountName = accountN.getText().toString();
                String passwordName = passwordN.getText().toString();
                login();
                //determine is the account name and password match with the file
               if(account.equals(accountName)&&password.equals(passwordName))
                {
                    login();
                }
                else{
                   Button btn = (Button) findViewById(R.id.btn_login);
                    btn.setText("Try Again");
               }


                /*for(int a =0; a < list.size();a++){
                    String ha = list.get(a).toString();
                   Log.d("myInfoTag",ha);
                }*/

            }
        });

    }

}
