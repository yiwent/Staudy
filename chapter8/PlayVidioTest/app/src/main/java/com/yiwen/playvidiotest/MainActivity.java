package com.yiwen.playvidiotest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button play = (Button) findViewById(R.id.bt_play);
        play.setOnClickListener(this);
        Button pause = (Button) findViewById(R.id.bt_pause);
        pause.setOnClickListener(this);
        Button replay = (Button) findViewById(R.id.bt_replay);
        replay.setOnClickListener(this);
        videoView= (VideoView) findViewById(R.id.video_view);
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            initVideoPath();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initVideoPath();
                } else {
                    Toast.makeText(MainActivity.this, "拒绝权限，无法使用", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            default:
        }
    }

    private void initVideoPath() {
        File file = new File(Environment.getExternalStorageDirectory(),"movie.mp4");
        videoView.setVideoPath(file.getPath());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_play:
                if (!videoView.isPlaying()) {
                    videoView.start();
                }
                break;
            case R.id.bt_pause:
                if (videoView.isPlaying()) {
                    videoView.pause();
                }
                break;
            case R.id.bt_replay:
                if (videoView.isPlaying()) {
                    videoView.resume();

                }
                break;
            default:
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView.suspend();

        }
    }
}
