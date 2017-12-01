package com.example.mattbent.myapplication;

//GUI imports
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;
import android.view.MenuItem;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

//Read File Imports
import android.content.Context;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.content.res.AssetManager;

//Other imports
import java.util.ArrayList;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    Context context = this;                     //represent context in current activity
    fileHandle loadInfo = new fileHandle();     //create a new constructor, helper function fileHandle
    loadUserInfo load = new loadUserInfo();     //helper class that can load all the data that program need

    //login(): allow user login and change to next activity
    public void login(){
        //Account user = new Account(account,password,fingerprint,saving,checking);
        Account user = new Account(load.getAccount(context),load.getPassword(context),load.getFingerprint(context)
                ,load.getSaving(context),load.getChecking(context));

        //read user input
        EditText accountN =findViewById(R.id.input_email);
        EditText passwordN =findViewById(R.id.input_password);
        String accountName = accountN.getText().toString();
        String passwordName = passwordN.getText().toString();

        Intent intent1 = new Intent(this, CBActivity.class);
        startActivity(intent1);

        //determine is the account name and password match with the file
        if(user.getUsername().equals(accountName)&&user.getPassword().equals(passwordName))
        {
            Intent intent = new Intent(this, CBActivity.class);
            startActivity(intent);
        }
        else{
            Button btn = (Button) findViewById(R.id.btn_login);
            btn.setText("Try Again");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.btn_login);

        //store saving and checking into internal storage
        String readAccount = loadInfo.readFileString(context,"Account1.txt");
        String readSaving = loadInfo.readFileString(context,"Saving1.txt");
        String readChecking = loadInfo.readFileString(context,"Checking1.txt");
        loadInfo.writeToFile(readAccount,context,"Account1.txt");
        loadInfo.writeToFile(readSaving,context,"Saving1.txt");
        loadInfo.writeToFile(readChecking,context,"Checking1.txt");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

}
