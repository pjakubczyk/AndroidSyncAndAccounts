package org.jakubczyk.syncandaccoutns.lib;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

public class AccountHelper {


    public static final String ACCOUNT = "uTestAccount";

    public static final String PASSWORD = "Very secret word";

    public static void createAccount(Context context) {
        // Create the account type and default account
        Account newAccount = new Account(ACCOUNT, getType(context));
        // Get an instance of the Android account manager
        AccountManager accountManager = getManager(context);


        if (accountManager.addAccountExplicitly(newAccount, PASSWORD, null)) {
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call context.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
        } else {
            accountManager.setPassword(newAccount, PASSWORD);
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.             */

        }
    }

    public static AccountManager getManager(Context context) {
        return (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
    }

    public static Account getMyAccount(Context context) {
        AccountManager accountManager = getManager(context);

        return accountManager.getAccountsByType(getType(context))[0];
    }

    public static void displayAccount(final Activity activity, int id) {
        final TextView accountTv = (TextView) activity.findViewById(id);

        final Account myAccount = AccountHelper.getMyAccount(activity);

        String accountString;

        accountString = myAccount.toString();

        String passwordString;

        try {
            passwordString = AccountHelper.getManager(activity).getPassword(myAccount);

        } catch (Exception e) {
            passwordString = "Unable to get password";
        }

        // Just check if providing custom package name can hack Android API
        Bundle bundle = new Bundle();
        // Add two dummy pieces of information
        bundle.putString("my account", "dummy bundle");
        bundle.putString("androidPackageName2", "org.jakubczyk.syncandaccoutns.friendly");

        // the value of androidPackageName is later replaced by Operating System
        bundle.putString("androidPackageName", "org.jakubczyk.syncandaccoutns.friendly");

        AccountHelper.getManager(activity).getAuthToken(myAccount, "aaa", bundle, activity, new AccountManagerCallback<Bundle>() {
            @Override
            public void run(AccountManagerFuture<Bundle> bundleAccountManagerFuture) {
                // This is the response from org.jakubczyk.syncandaccoutns.provider.Authenticator.getAuthToken()

                CharSequence currentContent = accountTv.getText();

                String result;
                try {
                    result = bundle2string(bundleAccountManagerFuture.getResult());
                } catch (OperationCanceledException e) {
                    e.printStackTrace();
                    result = e.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                    result = e.toString();
                } catch (AuthenticatorException e) {
                    e.printStackTrace();
                    result = e.toString();
                }

                // Print the token got from Authenticator
                String text = String.format("%s\ntoken: %s", currentContent, result);

                accountTv.setText(text);
            }
        }, null);

        // Print the password got from Account Manager
        accountTv.setText(String.format("%s\npassword: %s\n", accountString, passwordString));
    }


    public static String bundle2string(Bundle bundle) {
        String string = "{";
        for (String key : bundle.keySet()) {
            string += " " + key + " => " + bundle.get(key) + ";";
        }
        string += " }";
        return string;
    }

    public static String getType(Context context) {
        return context.getString(R.string.account_type);
    }
}
