package com.yiwen.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intViews();
    }

    private void intViews() {
        Button send = (Button) findViewById(R.id.bt_send_notice);
        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_send_notice:
                Intent intent=new Intent(this,NotifitionActivity.class);
                PendingIntent pi=PendingIntent.getActivity(this,0,intent,0);
                NotificationManager manager= (NotificationManager)
                        getSystemService(NOTIFICATION_SERVICE);
                Notification notifition=new NotificationCompat.Builder(this)
                        .setContentTitle("This is content Title")
                        .setContentText("This is content text setWifiIndicators:")
//                                " mCurrentState={connected=true,enabled=true,level=2,")
//                        .setStyle(new NotificationCompat.BigTextStyle().bigText("This is content text setWifiIndicators " +
//                                "This is content text setWifiIndicators"))
                        .setStyle(new NotificationCompat.BigPictureStyle().
                                bigPicture(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher)))
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                        .setContentIntent(pi)
////                        .setAutoCancel(true)//自动取消
//                        .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Luna.ogg")))
//                        .setVibrate(new long[]{0,1000,1000,1000})
//                        .setLights(Color.GREEN,1000,2000)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .build();
                manager.notify(1,notifition);
                break;
            default:
        }
    }
}
