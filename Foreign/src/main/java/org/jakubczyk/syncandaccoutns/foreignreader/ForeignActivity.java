package org.jakubczyk.syncandaccoutns.foreignreader;

import android.app.Activity;
import android.os.Bundle;

import org.jakubczyk.syncandaccoutns.lib.AccountHelper;


public class ForeignActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreign);
    }


    @Override
    protected void onResume() {
        super.onResume();
        AccountHelper.displayAccount(this, R.id.account);
    }
}
