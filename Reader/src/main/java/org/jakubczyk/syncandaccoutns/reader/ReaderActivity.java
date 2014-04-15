package org.jakubczyk.syncandaccoutns.reader;

import android.app.Activity;
import android.os.Bundle;
import org.jakubczyk.syncandaccoutns.lib.AccountHelper;

public class ReaderActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        AccountHelper.displayAccount(this, R.id.account);
    }


}
