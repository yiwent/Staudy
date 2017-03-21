package com.yiwen.uiwidgetest;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editText;
    private ImageView imageView;
    private ProgressBar mprogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt1 = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.et_text);
        imageView = (ImageView) findViewById(R.id.imageView);
        mprogress=(ProgressBar) findViewById(R.id.progressBar);
        bt1.setOnClickListener(this);

    }

    //接口的方法
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                //1.read et input
//                String inputText = editText.getText().toString();
//                Toast.makeText(MainActivity.this, inputText, Toast.LENGTH_SHORT).show();
                //2.set imageview
//                imageView.setImageResource(R.drawable.mypic);
                //3.set Progress
//                if(mprogress.getVisibility()==View.GONE){
//                    mprogress.setVisibility(View.VISIBLE);
//                }else{
//                    mprogress.setVisibility(View.GONE);
//                }
                //4.set Progress add++
//                int progress=mprogress.getProgress();
//                if(progress>=100)
//                {  progress=0;}
//                else{
//                progress=progress+10;
//                }
//                Log.d("progress", "onClick: "+progress);
//                mprogress.setProgress(progress);
                //4.showAlerDia
//                showAlerDia();
                //5.showProgressDia
              //  showProgressDia();
                Intent intent=new Intent(MainActivity.this,PercentActivity.class);
                startActivity(intent);
                break;
            default:
        }
    }

    private void showProgressDia() {
        ProgressDialog progressDia=new ProgressDialog(MainActivity.this);
        progressDia.setTitle("This is ProgressDialog");
        progressDia.setMessage("Loading...");
        progressDia.setCancelable(true);//fase不能取消
        progressDia.show();
    }

    private void showAlerDia() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("This is Title")
                .setMessage("Something need show")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }


}
