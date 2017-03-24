package com.yiwen.broadcastpractice;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;


public class LoginActivity extends AppCompatActivity {

    private EditText account_ed;
    private EditText password_ed;
    Button button_login;

    private CheckBox checkBox;
    private  SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        account_ed = (EditText) findViewById(R.id.account);
        password_ed = (EditText) findViewById(R.id.password);
        button_login = (Button) findViewById(R.id.login);
      pref= PreferenceManager.getDefaultSharedPreferences(this);
        checkBox=(CheckBox) findViewById(R.id.remenber_pass);
        boolean isRemenber=pref.getBoolean("remenber_pass",false);
        if (isRemenber){
            String account=pref.getString("account","");
            String passworld=pref.getString("password","");
            account_ed.setText(account);
            password_ed.setText(passworld);
            checkBox.setChecked(true);
        }
        button_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = account_ed.getText().toString();
                String password = password_ed.getText().toString();
                if (account.equals("admin") && password.equals("123456")) {
                   editor=pref.edit();
                    if (checkBox.isChecked()){
                        editor.putBoolean("remenber_pass",true);
                        editor.putString("account",account);
                        editor.putString("password",password);
                    }else {
                        editor.clear();
                    }
                   editor.apply();
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "account or password is invald", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

