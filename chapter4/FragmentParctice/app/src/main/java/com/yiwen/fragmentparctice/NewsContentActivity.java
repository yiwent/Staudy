package com.yiwen.fragmentparctice;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NewsContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);
        String newTitle=getIntent().getStringExtra("new_title");
        String newContent=getIntent().getStringExtra("nes_content");
        NewContentFragment newContentFragment= (NewContentFragment)
                getSupportFragmentManager().findFragmentById(R.id.news_content_fragment);
        newContentFragment.refresh(newTitle,newContent);
    }
    public static void actionStar(Context context,String newTitle,String newContent){
        Intent intent=new Intent(context,NewsContentActivity.class);
        intent.putExtra("new_title",newTitle);
        intent.putExtra("nes_content",newContent);
        context.startActivity(intent);
        }
}
