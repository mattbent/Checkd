package com.example.mattbent.myapplication;

//GUI Imports
import android.app.Activity;
import android.content.Context;
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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class DepositActivity extends AppCompatActivity{
    private TextView mTextMessage;
    Context context = this;                     //represent context in current activity
    fileHandle loadInfo = new fileHandle();     //create a new constructor, helper function fileHandle
    loadUserInfo load = new loadUserInfo();     //helper class that can load all the data that program need

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
    private void trans(Account user, boolean front, boolean back){
        TextView textbox = (TextView) ((Activity)this).findViewById(R.id.bottonMes); //message box at the bot
        double savingAccountMon = user.getSavings()*100;
        double checkingAccountMon = user.getChecking()*100;
        //Get sippner name
        Spinner accountSippner = (Spinner) findViewById(R.id.spinnerAccount);
        String accountOption = accountSippner.getSelectedItem().toString();
        //Get user input
        EditText amount = findViewById(R.id.editMoney);
        String amountS = amount.getText().toString();
        //prevent app crash when user dont enter anything
        if(amountS.matches(""))
        {
            textbox.setText("You did not enter any value!!");
            return;
        }
        double amountMon = Double.parseDouble(amountS);
        amountMon = amountMon*100;

        if(accountOption.equals("Saving Account")&&front==true&&back==true)
        {
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
            textbox.setText("Money Deposited");
        }
        else if(accountOption.equals("Checking Account")&&front==true&&back==true)
        {
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
            textbox.setText("Money Deposited");
        }
        else if(back==false&&front)
        {
            textbox.setText("Missing Back of the Check");
        }
        else if(front==false&&back)
        {
            textbox.setText("Missing Front of the Check");
        }
        else
        {
            textbox.setText("Missing both side of the Check");
        }


    }
    //==============================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(2).setChecked(true);
        //Image bottom
        ImageButton frontPic =(ImageButton)findViewById(R.id.imagebtm_front);
        ImageButton backPic =(ImageButton)findViewById(R.id.imagebtm_back);
        final boolean[] frontPicBtm = {false};
        final boolean[] backPicBtm = {false};

        //botton listener for front
        frontPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frontPicBtm[0] =true; // mean user click it set it true
                TextView textbox = (TextView)findViewById(R.id.bottonMes); //message box at the bot
                textbox.setText("Front Of The Check Has Been Saved");
            }
        });
        //botton listener for back
        backPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backPicBtm[0] =true; // mean user click it set it true
                TextView textbox = (TextView)findViewById(R.id.bottonMes); //message box at the bot
                textbox.setText("Back Of The Check Has Been Saved");
            }
        });

        final Account user = new Account(load.getAccount(context),load.getPassword(context),load.getFingerprint(context)
                ,load.getSaving(context),load.getChecking(context));
        //botton listener for btm_continue
        Button btn = (Button) findViewById(R.id.btm_continue);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean front = frontPicBtm[0];
                boolean back = backPicBtm[0];

                trans(user,front,back);
            }
        });
    }
}
