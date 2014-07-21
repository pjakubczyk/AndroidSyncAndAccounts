package org.jakubczyk.syncandaccoutns.lib;

import android.accounts.*;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;

import java.io.IOException;

public class AccountHelper {


    public static final String ACCOUNT = "uTestAccount";

    public static final String PASSWORD = "The Password";

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

            String android_id = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);

            String password;

            try {
                password = SimpleCrypto.encryptIt(android_id, PASSWORD);

            } catch (Exception e) {
                password = PASSWORD;
            }


            accountManager.setPassword(newAccount, password);
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

        try {
            accountString = myAccount.toString();
        } catch (SecurityException e) {
            accountString = "Unable to get user";
        }

        String passwordString;

        try {
            passwordString = AccountHelper.getManager(activity).getPassword(myAccount);
            String android_id = Settings.Secure.getString(activity.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            passwordString = SimpleCrypto.decryptIt(android_id, passwordString);

        } catch (SecurityException e) {
            passwordString = "Unable to get password";
        } catch (Exception e) {
            passwordString = "Unable to get password";
        }

        Bundle bundle = new Bundle();
        bundle.putString("my account", "dummy bundle");
        bundle.putString("androidPackageName2", "org.jakubczyk.syncandaccoutns.reader");
        bundle.putString("androidPackageName", "org.jakubczyk.syncandaccoutns.reader");

        AccountHelper.getManager(activity).getAuthToken(myAccount, "aaa", bundle, activity, new AccountManagerCallback<Bundle>() {
            @Override
            public void run(AccountManagerFuture<Bundle> bundleAccountManagerFuture) {

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


                String text = String.format("%s\ntoken: %s", currentContent, result);

                accountTv.setText(text);
            }
        }, null);


        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... voids) {
                String tokenString;
                try {
                    tokenString = AccountHelper.getManager(activity).blockingGetAuthToken(myAccount, "aaa", true);
                } catch (OperationCanceledException e) {
                    tokenString = e.getMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                    tokenString = e.getMessage();
                } catch (Exception e) {
                    e.printStackTrace();
                    tokenString = e.getMessage();
                }
                return tokenString;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                CharSequence currentContent = accountTv.getText();
                String text = String.format("%s\nBlockingToken: %s", currentContent, s);

                accountTv.setText(text);
            }
        };//.execute((Void) null);


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
