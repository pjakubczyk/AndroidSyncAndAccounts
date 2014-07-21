package org.jakubczyk.syncandaccoutns.foreignreader;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import org.jakubczyk.syncandaccoutns.lib.AccountHelper;


public class ForeignActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreign);

        AccountHelper.displayAccount(this, R.id.account);

//        Uri uri = Uri.parse("org.jakubczyk.provider");
//
//        getContentResolver().query(uri, null, null, null, null,null);

        String android_id = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Log.d("DDD", android_id);

    }


}
