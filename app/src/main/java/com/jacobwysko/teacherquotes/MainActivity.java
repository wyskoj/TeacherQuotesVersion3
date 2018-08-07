package com.jacobwysko.teacherquotes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String version = null;
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        TextView versionText = findViewById(R.id.versionTextView);
        versionText.setText("v" + version + " - DEVELOPMENT VERSION");

    }

    public void startWelcome(View view ){
        Intent myIntent = new Intent(MainActivity.this, WelcomeActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

    public void startQuote(View view ){
        Intent myIntent = new Intent(MainActivity.this, QuoteActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

    public void startTeachers(View view ){
        Intent myIntent = new Intent(MainActivity.this, ImportTeachersActivity.class);
        MainActivity.this.startActivity(myIntent);
    }
}
