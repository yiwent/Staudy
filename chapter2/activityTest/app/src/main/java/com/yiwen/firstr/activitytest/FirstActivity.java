package com.yiwen.firstr.activitytest;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class FirstActivity extends AppCompatActivity {
    private Button Bt1;
    private Button Bt2;
    private Button Bt3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Button Bt1 = (Button) findViewById(R.id.button1);
        Button Bt2 = (Button) findViewById(R.id.button2);
        Button Bt3 = (Button) findViewById(R.id.button3);
        Bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FirstActivity.this, "click", Toast.LENGTH_SHORT).show();

            }
        });
        Bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.显式 传数据
//                String data="FirstActivity come";
//                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
//                intent.putExtra("put_data",data);
//                startActivity(intent);
                //1.显式  后一个传给前一个
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                startActivityForResult(intent,1);
                //2.隐式
//                Intent intent = new Intent("com.yiwen.firstr.Action_star");
//                intent.addCategory("com.yiwen.firstr.My_Category");
//                startActivity(intent);
                //3.更多
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("http://www.baidu.com"));
//                startActivity(intent);
                //3.call
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:10086"));
//                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                Toast.makeText(this, "click add", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(this, "click remove", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode==RESULT_OK){
                    String retrun_data=data.getStringExtra("data_return");
                    Log.d("data_return", "onActivityResult: "+retrun_data);
                }
        }
    }
}
