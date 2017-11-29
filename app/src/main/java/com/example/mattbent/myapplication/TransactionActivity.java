package com.example.mattbent.myapplication;


//GUI Imports
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

//Read File Imports
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.res.AssetManager;
import android.content.Context;

public class TransactionActivity extends AppCompatActivity {

    private TextView mTextMessage;
    public void changeActivity(String choice) {
        if(choice.equals("a")) {
            Intent intent = new Intent(this, CBActivity.class);
            startActivity(intent);
        }
        else if(choice.equals("b")){
            Intent intent = new Intent(this, TransactionActivity.class);
            startActivity(intent);
        }
        else if(choice.equals("c")){

        }
    }

    //Read Files
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
                if(line.contains("money"))
                {

                }
                else {
                    sb.append(line).append('\n');
                }
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
    //Read Files
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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_balance:
                    changeActivity("a");
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_transfer:
                    changeActivity("b");
                    //mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    changeActivity("c");
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };


    private double typesOfAccount(String accountType){
        //Read Amount
        ArrayList list = readFileArrayList(accountType);
        String splitS[];
        String money;
        money = list.get(0).toString();
        splitS = money.split("=");
        money = splitS[1];
        money=money.replace("$","");
        double moneyNum = Double.parseDouble(money);
        return moneyNum;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        double savingAccountMon = typesOfAccount("Saving1.txt");
        double checkingAccountMon = typesOfAccount("Checking1.txt");

        Button btn = (Button) findViewById(R.id.btm_comfirm);

    }

}
