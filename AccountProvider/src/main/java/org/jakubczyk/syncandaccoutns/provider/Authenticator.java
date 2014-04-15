package org.jakubczyk.syncandaccoutns.provider;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import org.jakubczyk.syncandaccoutns.lib.AccountHelper;

/*
 * Implement AbstractAccountAuthenticator and stub out all
 * of its methods
 */
public class Authenticator extends AbstractAccountAuthenticator {
    private Context context;

    // Simple constructor
    public Authenticator(Context context) {
        super(context);
        this.context = context;
    }

    // Editing properties is not supported
    @Override
    public Bundle editProperties(
            AccountAuthenticatorResponse r, String s) {
        throw new UnsupportedOperationException();
    }

    // Don't add additional accounts
    @Override
    public Bundle addAccount(
            AccountAuthenticatorResponse r,
            String s,
            String s2,
            String[] strings,
            Bundle bundle) throws NetworkErrorException {
        return null;
    }

    // Ignore attempts to confirm credentials
    @Override
    public Bundle confirmCredentials(
            AccountAuthenticatorResponse r,
            Account account,
            Bundle bundle) throws NetworkErrorException {
        return null;
    }

    // Getting an authentication token is not supported
    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse r, Account account, String authTokenType, Bundle bundle) throws NetworkErrorException {
        Bundle bundle1 = new Bundle();
        bundle1.putString("token", "nekot");

        String log = String.format("AccountAuthenticatorResponse: [[%s]]\nAccount: [[%s]]\nauthTokenType: [[%s]]\nBundle: [[%s]]",
                r.toString(),
                account.toString(),
                authTokenType,
                bundle2string(bundle));

        Log.d("DDD", log);


        String packageName = bundle.getString("androidPackageName");

        Bundle tokenBundle = new Bundle();

        if ("org.jakubczyk.syncandaccoutns.reader".equals(packageName)) {
            tokenBundle.putString("token", "nekot " + AccountHelper.getManager(context).getPassword(account));
        } else {
            tokenBundle.putString("not a token", "tralala " + AccountHelper.getManager(context).getPassword(account));
        }

        return tokenBundle;

    }

    public static String bundle2string(Bundle bundle) {
        String string = "Bundle{";
        for (String key : bundle.keySet()) {
            string += " " + key + " => " + bundle.get(key) + ";";
        }
        string += " }Bundle";
        return string;
    }

    // Getting a label for the auth token is not supported
    @Override
    public String getAuthTokenLabel(String s) {
        return "WTF";
    }

    // Updating user credentials is not supported
    @Override
    public Bundle updateCredentials(
            AccountAuthenticatorResponse r,
            Account account,
            String s, Bundle bundle) throws NetworkErrorException {
        throw new UnsupportedOperationException();
    }

    // Checking features for the account is not supported
    @Override
    public Bundle hasFeatures(
            AccountAuthenticatorResponse r,
            Account account, String[] strings) throws NetworkErrorException {
        throw new UnsupportedOperationException();
    }
}