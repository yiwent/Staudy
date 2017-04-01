package com.yiwen.servicetest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private MyService.DownloadBinder mDownloadBinder;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mDownloadBinder = (MyService.DownloadBinder) service;
            mDownloadBinder.starDownlod();
            mDownloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button star = (Button) findViewById(R.id.star_service);
        star.setOnClickListener(this);
        Button stop = (Button) findViewById(R.id.stop_service);
        stop.setOnClickListener(this);
        Button bind = (Button) findViewById(R.id.bind_service);
        bind.setOnClickListener(this);
        Button unbind = (Button) findViewById(R.id.unbind_service);
        unbind.setOnClickListener(this);
        Button intentService = (Button) findViewById(R.id.intent_service);
        intentService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.star_service:
                Intent starIntent = new Intent(this, MyService.class);
                startService(starIntent);
                break;
            case R.id.stop_service:
                Intent stopIntent = new Intent(this, MyService.class);
                stopService(stopIntent);
                break;
            case R.id.bind_service:
                Intent bindIntent = new Intent(this, MyService.class);
                bindService(bindIntent, mConnection, BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service:
                unbindService(mConnection);
                break;
            case R.id.intent_service:
                Log.d("MainActivity", "Thread id is: " + Thread.currentThread().getId());
                Intent intentService = new Intent(MainActivity.this, MyIntentService.class);
                startService(intentService);
                break;
            default:
                break;
        }
    }
}
