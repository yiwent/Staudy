package com.yiwen.broasdcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private IntentFilter intentFilter;
    private NetwordChangerReceviver networdchangerReceviver;
    private LocalBroadcastManager localBroadcastManager;
    private LocalReceive localReceive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        localBroadcastManager=LocalBroadcastManager.getInstance(this);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent("com.yiwen.broasdcasttest.MY_BROADCAST");
//                sendOrderedBroadcast(intent, null);//有序广播
////                sendBroadcast(intent);//标准广播
                Intent intent=new Intent("com.yiwen.broasdcasttest.LOCAL_BROADCAST");
                localBroadcastManager.sendBroadcast(intent);

            }
        });
//        //动态注册全局广播
//        intentFilter = new IntentFilter();
//        intentFilter.addAction("android.net.conn.CONNECTVITY_CHANGE");
//        networdchangerReceviver = new NetwordChangerReceviver();
//        registerReceiver(networdchangerReceviver, intentFilter);
        //本地广播
        intentFilter =new IntentFilter();
        intentFilter.addAction("com.yiwen.broasdcasttest.LOCAL_BROADCAST");
        localReceive=new LocalReceive();
        localBroadcastManager.registerReceiver(localReceive,intentFilter);

    }

    /*
    接收网络改变
     */
    class NetwordChangerReceviver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                Toast.makeText(context, "network is ok", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "network is unavilable", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*
    本地广播
     */
    class LocalReceive extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "Reveive Local broadcast", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networdchangerReceviver);
    }
}
