package com.yiwen.databasetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static String TAG = "MainActivity";
    private MydatabaseHelper mydatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydatabaseHelper = new MydatabaseHelper(this, "BookStore.db", null, 2);
        Button button = (Button) findViewById(R.id.button_creat);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydatabaseHelper.getWritableDatabase();
            }
        });
        Button bt_add = (Button) findViewById(R.id.add_data);
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = mydatabaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name", "The Da Vinci");
                values.put("author", "Dan Brown");
                values.put("pages", 454);
                values.put("price", 16.96);
                db.insert("Book", null, values);
                values.clear();

                values.put("name", "The last Symbol");
                values.put("author", "Dan Brown");
                values.put("pages", 510);
                values.put("price", 19.95);
                db.insert("Book", null, values);
               // values.clear();

            }
        });

        Button bt_update = (Button) findViewById(R.id.updapte_data);
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = mydatabaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price", 10.99);
                db.update("Book", values, "name= ?", new String[]{"The Da Vinci"});
            }
        });

        Button bt_delete = (Button) findViewById(R.id.delete_data);
        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = mydatabaseHelper.getWritableDatabase();
                db.delete("Book", "pages >?", new String[]{"500"});
            }
        });

        Button bt_query = (Button) findViewById(R.id.query_data);
        bt_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"123",Toast.LENGTH_SHORT).show();
                SQLiteDatabase db = mydatabaseHelper.getWritableDatabase();
                Cursor cursor = db.query("Book", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d(TAG, "name " + name);
                        Log.d(TAG, "author " + author);
                        Log.d(TAG, "pages " + pages);
                        Log.d(TAG, "price " + price);
                        Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
                    } while (cursor.moveToNext());
                }
              cursor.close();
            }

        });
    }
}
