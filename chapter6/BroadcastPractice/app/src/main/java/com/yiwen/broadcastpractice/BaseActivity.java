package com.yiwen.broadcastpractice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BaseActivity extends AppCompatActivity {
    private ForceofflineReceiver forceofflineReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivitityCollector.removeActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.yiwen.broadcastpractice.FORCE_OFFLINE");
        forceofflineReceiver = new ForceofflineReceiver();
        registerReceiver(forceofflineReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (forceofflineReceiver!=null)
        {
            unregisterReceiver(forceofflineReceiver);
            forceofflineReceiver=null;
        }
    }

    class ForceofflineReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, Intent intent) {
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setTitle("warning")
                    .setMessage("you are forcd to be offline ,please try to login again")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivitityCollector.finishAll();
                            Intent intent=new Intent(context,LoginActivity.class);
                            startActivity(intent);
                        }
                    });
            builder.show();
        }
    }
}
