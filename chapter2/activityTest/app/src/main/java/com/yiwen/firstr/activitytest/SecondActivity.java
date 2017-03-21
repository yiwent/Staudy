package com.yiwen.firstr.activitytest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {
    private Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();
        String data = intent.getStringExtra("put_data");
        Toast.makeText(SecondActivity.this, data, Toast.LENGTH_SHORT).show();
        Button bt = (Button) findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.putExtra("data_return","From ATVT1");
                setResult(RESULT_OK,intent1);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent2 = new Intent();
        intent2.putExtra("data_return","From ATVT1");
        setResult(RESULT_OK,intent2);
        finish();
    }
}
