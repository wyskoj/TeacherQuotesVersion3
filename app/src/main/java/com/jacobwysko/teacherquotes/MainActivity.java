package com.jacobwysko.teacherquotes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardView basicQuote = findViewById(R.id.basicQuote);
        CardView bulkQuote = findViewById(R.id.bulkQuote);
        CardView teacherImport = findViewById(R.id.teacherImport);

        basicQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, QuoteActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });
        bulkQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(context, "Coming soon...", duration).show();

                /*Intent myIntent = new Intent(MainActivity.this, BulkQuoteActivity.class);
                MainActivity.this.startActivity(myIntent);*/
            }
        });
        teacherImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ImportTeachersActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });


    }

    public void onClick(View view){

        switch (view.getId()){
            case R.id.basicQuote :
                new Intent(this, QuoteActivity.class);
                break;
            case R.id.bulkQuote :
                new Intent(this, BulkQuoteActivity.class);
                break;
            case R.id.teacherImport :
                new Intent(this, ImportTeachersActivity.class);
                break;
        }
    }
}
