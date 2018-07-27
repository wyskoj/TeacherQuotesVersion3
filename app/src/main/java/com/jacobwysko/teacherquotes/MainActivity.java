package com.jacobwysko.teacherquotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
