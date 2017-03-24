package com.yiwen.broasdcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Receive my broadcast", Toast.LENGTH_SHORT).show();
        abortBroadcast();
    }
}
