package com.yiwen.litepaltest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button creat = (Button) findViewById(R.id.create);
        creat.setOnClickListener(this);
        Button add = (Button) findViewById(R.id.bt_add);
        add.setOnClickListener(this);
        Button updata = (Button) findViewById(R.id.bt_updata);
        updata.setOnClickListener(this);
        Button delete = (Button) findViewById(R.id.bt_delete);
        delete.setOnClickListener(this);
        Button query = (Button) findViewById(R.id.bt_query);
        query.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create:
                createDatabase();
                break;
            case R.id.bt_add:
                addDatabase();
                break;
            case R.id.bt_updata:
                updataDatabase();
                break;
            case R.id.bt_delete:
                deleDatabase();
                break;
            case R.id.bt_query:
                queryDatabase();
                break;
        }
    }

    private void queryDatabase() {
        List<Book> books=DataSupport.findAll(Book.class);
        for (Book book:books){
            Log.d(TAG, "queryDatabase: name"+book.getName());
            Log.d(TAG, "queryDatabase: author"+book.getAuthor());
            Log.d(TAG, "queryDatabase: pages"+book.getPages());
            Log.d(TAG, "queryDatabase: price"+book.getPrice());
            Log.d(TAG, "queryDatabase: press"+book.getPress());
        }
    }

    private void deleDatabase() {
        DataSupport.deleteAll(Book.class,"price<?","15");
    }

    private void updataDatabase() {
//        Book book = new Book();
//        book.setName("The Last Symbol");
//        book.setAuthor("Dan Brown");
//        book.setPages(510);
//        book.setPrice(19.95);
//        book.setPress("unknown");
//        book.save();
//        book.setPrice(19.08);
//        book.save();
        Book book = new Book();
        book.setPrice(16.96);
        book.setPress("Anchor");
        book.updateAll("name= ? and author= ?","The Last Symbol","Dan Brown");

    }

    private void addDatabase() {

        Book book = new Book();
        book.setName("The Da Vinci Code");
        book.setAuthor("Dan Brown");
        book.setPages(454);
        book.setPrice(16.96);
        book.setPress("unknown");
        book.save();


    }

    private void createDatabase() {
        LitePal.getDatabase();

    }
}
