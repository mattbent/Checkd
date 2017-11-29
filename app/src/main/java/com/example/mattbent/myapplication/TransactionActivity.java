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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

//Read File Imports
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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

    //write file
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
    private float typesOfAccount(String accountType){
        //Read Tranctions History
        String input = readFromFile(this,accountType);
        Context context = this;
        String splitSl [] = input.split(";");
        String money;
        money = splitSl[0];
        money = money.replace("money=$","");
        float moneyDou = Float.parseFloat(money);
        return moneyDou;
    }
    //==============================================================================================
    private void trans()
    {
        float savingAccountMon = typesOfAccount("Saving1.txt");
        float checkingAccountMon = typesOfAccount("Checking1.txt");
        //get the spinner name for both spinners
        Spinner fromSpinner=(Spinner) findViewById(R.id.transaction_spinnerFrom);
        String fromText = fromSpinner.getSelectedItem().toString();
        Spinner toSpinner=(Spinner) findViewById(R.id.transaction_spinnerTo);
        String toText = toSpinner.getSelectedItem().toString();
        //get amount user put in
        EditText amount = findViewById(R.id.editAmount);
        String amountS = amount.getText().toString();
        float amountMon = Float.parseFloat(amountS);
        TextView textbox = (TextView) ((Activity)this).findViewById(R.id.txtMessage);

        if(fromText.equals("Checking Account")&&toText.equals("Saving Account"))
        {
            checkingAccountMon = checkingAccountMon - amountMon;
            if(checkingAccountMon > 0) {
                savingAccountMon = savingAccountMon + amountMon;
                String savingAccountString = readFromFile(this, "Saving1.txt");
                String splitSaving[] = savingAccountString.split(";");
                String savingAccountS = Float.toString(savingAccountMon);
                splitSaving[0] = "money=$" + savingAccountS + ";";
                StringBuilder sbSaving = new StringBuilder();
                for (int i = 0; i < splitSaving.length; i++) {
                    sbSaving.append(splitSaving[i] + ";").append('\n');
                }
                writeToFile(sbSaving.toString(), this, "Saving1.txt");

                //checkingAccountMon = checkingAccountMon - amountMon;
                String checkingAccountString = readFromFile(this, "Checking1.txt");
                String splitChecking[] = checkingAccountString.split(";");
                String checkingAccountS = Float.toString(checkingAccountMon);
                splitChecking[0] = "money=$" + checkingAccountS + ";";
                StringBuilder sbChecking = new StringBuilder();
                for (int i = 0; i < splitChecking.length; i++) {
                    sbChecking.append(splitChecking[i] + ";").append('\n');
                }
                writeToFile(sbChecking.toString(), this, "Checking1.txt");
                textbox.setText("Money has been Transfor Successfully");
            }
            else
            {
                textbox.setText("Can not transfer money, not enough money in the Checking Account");
            }
        }
        else if(fromText.equals("Saving Account")&&toText.equals("Checking Account"))
        {

            savingAccountMon = savingAccountMon - amountMon;
            if(savingAccountMon > 0) {
                String savingAccountString = readFromFile(this, "Saving1.txt");
                String splitSaving[] = savingAccountString.split(";");
                String savingAccountS = Float.toString(savingAccountMon);
                splitSaving[0] = "money=$" + savingAccountS + ";";
                StringBuilder sbSaving = new StringBuilder();
                for (int i = 0; i < splitSaving.length; i++) {
                    sbSaving.append(splitSaving[i] + ";").append('\n');
                }
                writeToFile(sbSaving.toString(), this, "Saving1.txt");

                checkingAccountMon = checkingAccountMon + amountMon;
                String checkingAccountString = readFromFile(this, "Checking1.txt");
                String splitChecking[] = checkingAccountString.split(";");
                String checkingAccountS = Float.toString(checkingAccountMon);
                splitChecking[0] = "money=$" + checkingAccountS + ";";
                StringBuilder sbChecking = new StringBuilder();
                for (int i = 0; i < splitChecking.length; i++) {
                    sbChecking.append(splitChecking[i] + ";").append('\n');
                }
                writeToFile(sbChecking.toString(), this, "Checking1.txt");
                textbox.setText("Money has been Transfor Successfully");
            }
            else
            {
                textbox.setText("Can not transfer money, not enough money in the Saving Account");
            }
        }
        else
        {
            textbox.setText("Can not Transfer money in the same account");
        }
    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.getMenu().getItem(1).setChecked(true);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        Button btn = (Button) findViewById(R.id.btm_comfirm);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trans();
            }
        });
    }

}
