package com.yiwen.sharepreferenttest;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button save= (Button) findViewById(R.id.button_save);
        Button retore= (Button) findViewById(R.id.button_restore);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();
                editor.putString("name","Tom");
                editor.putInt("age",28);
                editor.putBoolean("married",false);
                editor.apply();
            }
        });
        retore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
                String name=sharedPreferences.getString("name","");
                int age =sharedPreferences.getInt("age",0);
                boolean married=sharedPreferences.getBoolean("married",false);
                Log.d("MainActivity", "onClick: "+name);
            }
        });
    }
}
