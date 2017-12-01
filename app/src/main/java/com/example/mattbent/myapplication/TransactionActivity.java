package com.example.mattbent.myapplication;


//GUI Imports
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

//Read File Imports

import android.content.Context;

public class TransactionActivity extends AppCompatActivity {

    Context context = this;                     //represent context in current activity
    fileHandle loadInfo = new fileHandle();     //create a new constructor, helper function fileHandle
    loadUserInfo load = new loadUserInfo();     //helper class that can load all the data that program need
    private TextView mTextMessage;

    //==============================================================================================
    //Acitivity Changing
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
    private void trans(Account user)
    {
        TextView textbox = (TextView) ((Activity)this).findViewById(R.id.txtMessage); //message box at the bot
        double savingAccountMon = user.getSavings()*100;
        double checkingAccountMon = user.getChecking()*100;
        //get the spinner name for both spinners
        Spinner fromSpinner=(Spinner) findViewById(R.id.transaction_spinnerFrom);
        String fromText = fromSpinner.getSelectedItem().toString();
        Spinner toSpinner=(Spinner) findViewById(R.id.transaction_spinnerTo);
        String toText = toSpinner.getSelectedItem().toString();
        //get amount user put in
        EditText amount = findViewById(R.id.editAmount);
        String amountS = amount.getText().toString();
        //prevent app crash when user dont enter anything
        if(amountS.matches(""))
        {
            textbox.setText("You did not enter any Amount!!");
            return;
        }
        double amountMon = Double.parseDouble(amountS);
        amountMon = amountMon*100;


        if(fromText.equals("Checking Account")&&toText.equals("Saving Account"))
        {
            checkingAccountMon = checkingAccountMon - amountMon;
            if(checkingAccountMon > 0) {
                savingAccountMon = savingAccountMon + amountMon;
                String savingAccountString = loadInfo.readFromFile(this, "Saving1.txt");
                String splitSaving[] = savingAccountString.split(";");
                String savingAccountS = Double.toString(savingAccountMon/100);
                splitSaving[0] = "money=" + savingAccountS + ";";
                StringBuilder sbSaving = new StringBuilder();
                for (int i = 0; i < splitSaving.length; i++) {
                    sbSaving.append(splitSaving[i] + ";").append('\n');
                }
                loadInfo.writeToFile(sbSaving.toString(), this, "Saving1.txt");

                //checkingAccountMon = checkingAccountMon - amountMon;
                String checkingAccountString = loadInfo.readFromFile(this, "Checking1.txt");
                String splitChecking[] = checkingAccountString.split(";");
                String checkingAccountS = Double.toString(checkingAccountMon/100);
                splitChecking[0] = "money=" + checkingAccountS + ";";
                StringBuilder sbChecking = new StringBuilder();
                for (int i = 0; i < splitChecking.length; i++) {
                    sbChecking.append(splitChecking[i] + ";").append('\n');
                }
                loadInfo.writeToFile(sbChecking.toString(), this, "Checking1.txt");
                textbox.setText("Money has been transferred successfully");
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
                String savingAccountString = loadInfo.readFromFile(this, "Saving1.txt");
                String splitSaving[] = savingAccountString.split(";");
                String savingAccountS = Double.toString(savingAccountMon/100);
                splitSaving[0] = "money=" + savingAccountS + ";";
                StringBuilder sbSaving = new StringBuilder();
                for (int i = 0; i < splitSaving.length; i++) {
                    sbSaving.append(splitSaving[i] + ";").append('\n');
                }
                loadInfo.writeToFile(sbSaving.toString(), this, "Saving1.txt");

                checkingAccountMon = checkingAccountMon + amountMon;
                String checkingAccountString = loadInfo.readFromFile(this, "Checking1.txt");
                String splitChecking[] = checkingAccountString.split(";");
                String checkingAccountS = Double.toString(checkingAccountMon/100);
                splitChecking[0] = "money=" + checkingAccountS + ";";
                StringBuilder sbChecking = new StringBuilder();
                for (int i = 0; i < splitChecking.length; i++) {
                    sbChecking.append(splitChecking[i] + ";").append('\n');
                }
                loadInfo.writeToFile(sbChecking.toString(), this, "Checking1.txt");
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

        final Account user = new Account(load.getAccount(context),load.getPassword(context),load.getFingerprint(context)
                ,load.getSaving(context),load.getChecking(context));

        Button btn = (Button) findViewById(R.id.btm_comfirm);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trans(user);
            }
        });
    }

}
