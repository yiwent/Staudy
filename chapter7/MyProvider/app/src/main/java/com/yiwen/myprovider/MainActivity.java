package com.yiwen.myprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String newid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intviews();
    }

    private void intviews() {
        Button add = (Button) findViewById(R.id.bt_add);
        Button query = (Button) findViewById(R.id.bt_query);
        Button update = (Button) findViewById(R.id.bt_updata);
        Button delete = (Button) findViewById(R.id.bt_delete);
        add.setOnClickListener(this);
        query.setOnClickListener(this);
        update.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add:
                adddata();
                break;
            case R.id.bt_query:
                querydata();
                break;
            case R.id.bt_updata:
                updatadata();
                break;
            case R.id.bt_delete:
                deletedata();
                break;
            default:
        }
    }

    private void querydata() {
        Uri uri = Uri.parse("content://com.yiwen.databasetest.provider/book");
        Cursor cursor=getContentResolver().query(uri,null,null,null,null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                String name=cursor.getString(cursor.getColumnIndex("name"));
                String author=cursor.getString(cursor.getColumnIndex("author"));
                int pages=cursor.getInt(cursor.getColumnIndex("pages"));
                double price=cursor.getDouble(cursor.getColumnIndex("price"));
                Log.d("MainActivity", "querydata:name "+name);
                Log.d("MainActivity", "querydata:author "+author);
                Log.d("MainActivity", "querydata:pages "+pages);
                Log.d("MainActivity", "querydata:price "+price);
            }
        }
    }

    private void adddata() {
//        Uri uri = Uri.parse("content://com.yiwen.databasetest.provider/book");
//        ContentValues values = new ContentValues();
//        values.put("name", "A Class of Kinds");
//        values.put("author", "George Martin");
//        values.put("pages", 1040);
//        values.put("price", 22.85);
//        Uri newUrl = getContentResolver().insert(uri, values);
//        newid = newUrl.getPathSegments().get(1);
        // 添加数据com.yiwen.databasetest.provider
        Uri uri = Uri.parse("content://com.yiwen.databasetest.provider/book");
        ContentValues values = new ContentValues();
        values.put("name", "A Clash of Kings");
        values.put("author", "George Martin");
        values.put("pages", 1040);
        values.put("price", 55.55);
        Uri newUri = getContentResolver().insert(uri, values);
        newid = newUri.getPathSegments().get(1);
        Log.d("MainActivity", " adddata:newid "+newid);
    }

    private void updatadata() {
        Uri uri = Uri.parse("content://com.yiwen.databasetest.provider/book/"+newid);
        ContentValues value=new ContentValues();
        value.put("name","A Storm of Swords");
        value.put("pages",1216);
        value.put("price",24.05);
        getContentResolver().update(uri,value,null,null);
        Log.d("MainActivity", " updatadata:newid "+newid);
    }

    private void deletedata() {
        Log.d("MainActivity", " updatadata:deletedata "+newid);
        Uri uri = Uri.parse("content://com.yiwen.databasetest.provider/book/"+newid);
        getContentResolver().delete(uri,null,null);
    }
}
