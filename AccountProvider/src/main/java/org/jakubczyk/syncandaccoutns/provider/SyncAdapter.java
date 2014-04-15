package org.jakubczyk.syncandaccoutns.provider;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SyncAdapter extends AbstractThreadedSyncAdapter {


    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        Log.d("DDD", String.format("Context: %s\t autoInitialize: %s", context, autoInitialize));
    }

    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        Log.d("DDD", String.format("Context: %s\t autoInitialize: %s\tallowParallelSyncs: %s", context, autoInitialize, allowParallelSyncs));
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String authority, ContentProviderClient contentProviderClient, SyncResult syncResult) {
        String format = "Account: %s\nBundle: %s\nAuthority: %s\nContentProviderClient: %s\nSyncResult: %s";

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS");
        Date dt = new Date();
        String S = sdf.format(dt); // formats to 09/23/2009 13:53:28.238

        Log.d("DDD", "WOKEN UP!\t " + S );
        Log.d("DDD", String.format(format, account,bundle,account,contentProviderClient.toString(), syncResult));
        Log.d("DDD","------------------------");


    }
}
