package com.example.mattbent.myapplication;

/**
 * This is a class for the account object.
 */

public class Account
{
    // fields
    private String  username;
    private String  password;
    private String  fingerprint;
    private double  savings;
    private double  checking;

    // full constructor
    public Account (String initUsername, String initPassword, String initFingerprint,
            double initSavings, double initChecking)
    {
        username = initUsername;
        password = initPassword;
        fingerprint = initFingerprint;
        savings = initSavings;
        checking = initChecking;
    }

    /**
     * Getter method for this account's username.
     * 
     * @return the username
     */
    public String getUsername()
    {
        return this.username;
    }

    /**
     * Getter method for this account's password.
     * 
     * @return the password
     */
    public String getPassword()
    {
        return this.password;
    }

    /**
     * Getter method for this account's fingerprint.
     * 
     * @return the fingerprint
     */
    public String getFingerprint()
    {
        return this.fingerprint;
    }

    /**
     * Getter method for this account's savings balance
     * 
     * @return the account's savings
     */
    public double getSavings()
    {
        return this.savings;
    }

    /**
     * Getter method for this account's checking balance
     * 
     * @return the account's checking balance.
     */
    public double getChecking()
    {
        return this.checking;
    }

    /**
     * Setter method for this account's password
     */
    public void setPassword(String newPassword)
    {
        this.password = newPassword;
    }

    /**
     * Setter method for this account's fingerprint
     */
    public void setFingerprint(String newFingerprint)
    {
        this.fingerprint = newFingerprint;
    }

    /**
     * Setter method for this account's savings
     * (this probably shouldn't ever be used.)
     */
    public void setSavings(double newSavings)
    {
        this.savings = newSavings;
    }

    /**
     * Setter method for this account's checking balance.
     * (This probably shouldn't ever be used.)
     */
    public void setChecking(double newChecking)
    {
        this.checking = newChecking;   
    }

    /**
     * Method for making a deposit.
     * This method can theoretically be used to make a withdrawal.
     */
     public void makeDeposit(String which, double amount)
     {
         if(which.toLowerCase().equals("savings"))
         {
             this.savings += amount;
         }
         else if(which.toLowerCase().equals("checking"))
         {
             this.checking += amount;
         }
         else
         {
             // YO DAWG
             // LOOK
             // THIS NEEDS TO BE FIXED.
             // IT NEEDS TO THROW A PROPER EXCEPTION.
             System.out.println("You done fucked up");
         }
     }
}