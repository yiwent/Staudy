package com.yiwen.servicetest;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * User: Yiwen(https://github.com/yiwent)
 * Date: 2017-03-31
 * Time: 22:44
 * FIXME
 */
public class MyIntentService extends IntentService {
   public MyIntentService(){
       super("MyIntentService");
   }
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("MyIntentService", "Thread id is: "+Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyIntentService", "onDestroy: ");
    }
}
