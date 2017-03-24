package com.yiwen.filepersisitencetest;

import android.content.Context;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.R.id.input;

public class MainActivity extends AppCompatActivity {
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.ed_text);
        String inputtext=load();
        if (!TextUtils.isEmpty(inputtext)){
            editText.setText(inputtext);
            editText.setSelection(inputtext.length());
            Toast.makeText(MainActivity.this,"restoring succeeded",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String input = editText.getText().toString();
        save(input);
        Log.d("input", "onDestroy: "+input.toString());
    }

    public void save(String input) {
        FileOutputStream out = null;
        BufferedWriter write = null;

        try {
            out = openFileOutput("data", Context.MODE_PRIVATE);
            write = new BufferedWriter(new OutputStreamWriter(out));
            write.write(input);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (write != null) {
                    write.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
    public String load(){
        FileInputStream in=null;
        BufferedReader read=null;
        StringBuilder content=new StringBuilder();
        try {
            in=openFileInput("data");
            read=new BufferedReader(new InputStreamReader(in));
            String line="";
            while ((line=read.readLine())!=null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (read!=null){
                    read.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d("content", "load: "+content.toString());
        return content.toString();
    }

}
