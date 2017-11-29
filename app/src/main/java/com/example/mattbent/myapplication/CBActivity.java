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
    //==================================================================================================
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
    // Read File Return String
    private String readFromFile(Context context,String fileName) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(fileName);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return ret;
    }

    //==============================================================================================
    private void typesOfAccount(String accountType){
        //Read Tranctions History
        String input = readFromFile(this,accountType);
        Context context = this;
        String splitSl [] = input.split(";");
        String money;
        money = splitSl[0];
        money = money.replace("money=","");
        TextView viewAmount = (TextView) ((Activity)context).findViewById(R.id.textView);
        viewAmount.setText(money);

        StringBuilder sb = new StringBuilder();
        for(int i =1; i < splitSl.length; i++)
        {
            sb.append(splitSl[i]).append('\n');
        }
        TextView txtView = (TextView) ((Activity)context).findViewById(R.id.textView2);
        txtView.setText(sb.toString());
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
