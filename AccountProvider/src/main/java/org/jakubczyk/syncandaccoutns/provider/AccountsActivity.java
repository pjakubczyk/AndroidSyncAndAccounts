package org.jakubczyk.syncandaccoutns.provider;

import android.accounts.Account;
import android.app.Activity;
import android.content.ContentResolver;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import org.jakubczyk.syncandaccoutns.lib.AccountHelper;


public class AccountsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accoutns);

        TextView  textView = (TextView) findViewById(R.id.accounts_tv);

        AccountHelper.createAccount(this);


        final Account myAccount = AccountHelper.getMyAccount(this);

        textView.setText(myAccount.toString());


        final Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        /*
         * Request the sync for the default account, authority, and
         * manual sync settings
         */



        findViewById(R.id.sync_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentResolver.requestSync(myAccount, getString(R.string.authority), settingsBundle);
            }
        });


        ContentResolver.setSyncAutomatically(myAccount, getString(R.string.authority), true);

        Bundle someKindOfBundle = new Bundle();
        someKindOfBundle.putString("forced by", "time");
        ContentResolver.addPeriodicSync(myAccount, getString(R.string.authority), someKindOfBundle, 90);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.accoutns, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
