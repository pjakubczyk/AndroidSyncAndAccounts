package org.jakubczyk.syncandaccoutns.friendly;

import android.app.Activity;
import android.os.Bundle;

import org.jakubczyk.syncandaccoutns.lib.AccountHelper;

public class FriendlyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AccountHelper.displayAccount(this, R.id.account);
    }
}
