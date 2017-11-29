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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.content.res.AssetManager;
import android.content.Context;

//Other Imports
import java.util.ArrayList;

public class CBActivity extends AppCompatActivity {

    private TextView mTextMessage;

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

    private void typesOfAccount(String accountType){
        //Read Tranctions History
        String input = readFileString(accountType);
        Context context = this;
        TextView txtView = (TextView) ((Activity)context).findViewById(R.id.textView2);
        txtView.setText(input);
        //Read Amount
        ArrayList list = readFileArrayList(accountType);
        String splitS[];
        String money;
        money = list.get(0).toString();
        splitS = money.split("=");
        money = splitS[1];
        Context context2 = this;
        TextView viewAmount = (TextView) ((Activity)context2).findViewById(R.id.textView);
        viewAmount.setText(money);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Spinner
        Spinner mySpinner=(Spinner) findViewById(R.id.account_spinner);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(id == 0)
                {
                    //Saving Account
                    typesOfAccount("Checking1.txt");
                }
                else if(id == 1)
                {
                    //Checking Account
                    typesOfAccount("Saving1.txt");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        Context context = this;
        TextView txtView = (TextView) ((Activity)context).findViewById(R.id.textView2);
        txtView.setMovementMethod(new ScrollingMovementMethod());
    }

}
