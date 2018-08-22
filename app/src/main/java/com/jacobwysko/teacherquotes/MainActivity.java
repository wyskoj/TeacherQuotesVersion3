package com.jacobwysko.teacherquotes;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            File file = new File(getFilesDir(), "firstTime");
            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            if (!"done".equals(input.readLine())){
                String fileName = "firstTime";
                FileOutputStream outputStream;
                try {
                    outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                    outputStream.write("done".getBytes());
                    outputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                Intent myIntent = new Intent(MainActivity.this, WelcomeActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        } catch (FileNotFoundException e) {
            String fileName = "firstTime";
            FileOutputStream outputStream;
            try {
                outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                outputStream.write("done".getBytes());
                outputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            Intent myIntent = new Intent(MainActivity.this, WelcomeActivity.class);
            MainActivity.this.startActivity(myIntent);
        } catch (IOException e) {
            String fileName = "firstTime";
            FileOutputStream outputStream;
            try {
                outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                outputStream.write("done".getBytes());
                outputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            Intent myIntent = new Intent(MainActivity.this, WelcomeActivity.class);
            MainActivity.this.startActivity(myIntent);
        }

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

        BufferedReader input;
        File file;
        Integer numberOfSharedQuotes = null;
        Boolean askedBefore = null;
        try {
            file = new File(getFilesDir(), "numberOfSharedQuotes");
            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            numberOfSharedQuotes = Integer.parseInt(input.readLine());

            file = new File(getFilesDir(), "askedBefore");
            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            askedBefore = Integer.parseInt(input.readLine()) != 0;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (!askedBefore && (numberOfSharedQuotes >= 3)) { // If I've never asked before, and the user has shared 3+ quotes

                final Context context = this;

                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Enjoying Teacher Quotes?");
                // dialog.setMessage("Quotations are not required; they are added automatically.");
                dialog.setPositiveButton("Yes!", new DialogInterface.OnClickListener() { // YES I AM ENJOYING
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                        dialog.setTitle("How about a rating on the Play Store, then?");
                        // dialog.setMessage("Quotations are not required; they are added automatically.");
                        dialog.setPositiveButton("Ok, sure", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                openReview();
                            }
                        });
                        dialog.setNegativeButton("No, thanks", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alert = dialog.create();
                        alert.show();


                    }
                });
                dialog.setNegativeButton("Not really", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
                AlertDialog alert = dialog.create();
                alert.show();

            }
        } catch (Exception ignored){}



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

    private void openReview(){
        Context context = this;
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        }
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }

    }
}
