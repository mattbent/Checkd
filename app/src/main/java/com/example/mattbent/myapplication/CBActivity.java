package com.example.mattbent.myapplication;

//GUI Imports
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

//Read File Imports
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.content.res.AssetManager;
import android.content.Context;

//Other Imports
import org.w3c.dom.Text;

import java.util.ArrayList;

public class CBActivity extends AppCompatActivity {

    private TextView mTextMessage;
    fileHandle loadInfo = new fileHandle();
    loadUserInfo load = new loadUserInfo();     //helper class that can load all the data that program need
    Context context = this;                     //represent context in current activity

    //==================================================================================================
    //Activity Changing
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
            Intent intent = new Intent(this, DepositActivity.class);
            startActivity(intent);
        }
    }
    //Navigation Bar
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
    //==============================================================================================
    //displayTransInfo: display entire page of information
    private void displayTransInfo(double money,String trans){

        String moneyFin = "$"+Double.toString(money);
        TextView viewAmount = (TextView) ((Activity)context).findViewById(R.id.textView);
        viewAmount.setText(moneyFin);


        TextView txtView = (TextView) ((Activity)context).findViewById(R.id.textView2);
        txtView.setText(trans);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        final Account user = new Account(load.getAccount(context),load.getPassword(context),load.getFingerprint(context)
                ,load.getSaving(context),load.getChecking(context));
        final String checkingTrans = load.getCheckingTrans(context);
        final String savingTrans = load.getSavingTrans(context);
        TextView txtUsername = (TextView)findViewById(R.id.txt_username);
        String userN = "Hello, "+user.getUsername();
        txtUsername.setText(userN);

        //Spinner
        Spinner mySpinner=(Spinner) findViewById(R.id.account_spinner);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(id == 0)
                {
                    //Saving Account
                    displayTransInfo(user.getSavings(),checkingTrans);
                }
                else if(id == 1)
                {
                    //Checking Account
                    displayTransInfo(user.getChecking(),savingTrans);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        TextView txtView = (TextView) ((Activity)context).findViewById(R.id.textView2);
        txtView.setMovementMethod(new ScrollingMovementMethod());
    }

}
