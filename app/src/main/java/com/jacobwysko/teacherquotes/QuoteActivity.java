package com.jacobwysko.teacherquotes;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class QuoteActivity extends AppCompatActivity {

    String dateFormat = "MM/dd/yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
    final Calendar myCalendar = Calendar.getInstance();
    Boolean showShareButton;
    Boolean quoteIsGood;
    Boolean teacherIsGood;
    Boolean showQuote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);


        EditText dateEditText = findViewById(R.id.dateEditText);

        final Context context = this;

        final DatePickerDialog.OnDateSetListener date;

        long currentDate = System.currentTimeMillis(); // Get the current date
        String dateString = sdf.format(currentDate); // Format it
        dateEditText.setText(dateString); // Set the editText to that formatted date

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }
        };

        dateEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Spinner teacherSpinner = null;
        try {
            BufferedReader input;
            File file;
            String teacherList = null;
            teacherSpinner = findViewById(R.id.teacherSpinner);
            try {
                file = new File(getFilesDir(), "teacherList");
                input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                teacherList = input.readLine();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (teacherList == null){
                Intent myIntent = new Intent(QuoteActivity.this, ImportTeachersActivity.class);
                QuoteActivity.this.startActivity(myIntent);
                finish();
            }
            String[] teachers;

            assert teacherList != null;
            String[] splitTeachers = teacherList.split(",");
            String[] temp1 = Arrays.copyOf(splitTeachers, splitTeachers.length);
            String[] temp2 = new String[temp1.length + 1];
            temp2[0] = "Choose a teacher...";
            System.arraycopy(temp1, 0, temp2, 1, temp1.length);
            teachers = Arrays.copyOf(temp2, temp2.length);


            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, teachers);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            teacherSpinner.setAdapter(adapter);

        } catch (Exception ignored) {
        }


        showShareButton = false;
        quoteIsGood = false;
        teacherIsGood = false;
        showQuote = false;
        updateButtons();

        final EditText quoteEditText = findViewById(R.id.quoteEditText);
        quoteEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // If does not contain quote mark or isn't blank...
                quoteIsGood = (quoteEditText.getText().toString().indexOf('"') == -1) && (!quoteEditText.getText().toString().trim().equals(""));
                somethingChangedSoDisableShareButtonAndHideQuote();
                updateButtons();

            }
        });

        assert teacherSpinner != null;
        teacherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                teacherIsGood = (position != 0);
                somethingChangedSoDisableShareButtonAndHideQuote();
                updateButtons();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void updateDate() {
        EditText dateEditText = findViewById(R.id.dateEditText);
        dateEditText.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateButtons(){
        Button preview = findViewById(R.id.previewButton);
        if (teacherIsGood && quoteIsGood){
            preview.setVisibility(View.VISIBLE);
        } else {
            preview.setVisibility(View.GONE);
            /*
               You see, taking shits and wiping your ass
               on the couch is all fun and games, but once
               you're in there and trying to hide the remains,
               it's View.GONE, it's fucking in the springs and shit.
            */
        }
        Button share = findViewById(R.id.shareButton);
        if (showShareButton) {
            share.setVisibility(View.VISIBLE);
        } else {
            share.setVisibility(View.GONE);
        }

    }

    public void previewPressed(View view) {
        showShareButton = true;
        updateButtons();
        TextView quoteView = findViewById(R.id.quotePreviewTextView);
        quoteView.setVisibility(View.VISIBLE);

        // OH YEAH BABY HERE WE GO

        quoteView.setText(makeQuote());
    }

    private void somethingChangedSoDisableShareButtonAndHideQuote() {
        showShareButton = false;
        updateButtons();
        Button share = findViewById(R.id.shareButton);
        share.setVisibility(View.GONE);
        TextView quote = findViewById(R.id.quotePreviewTextView);
        quote.setVisibility(View.GONE);

    }

    public void share(View view){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, makeQuote());
        startActivity(Intent.createChooser(sharingIntent, "Select Discord, then #quotes."));
    }

    private String makeQuote(){
        EditText quoteET = findViewById(R.id.quoteEditText);
        Spinner teacherSPIN = findViewById(R.id.teacherSpinner);
        EditText dateET = findViewById(R.id.dateEditText);

        String quote, date, teacher;

        quote = quoteET.getText().toString();
        date = dateET.getText().toString();
        teacher = teacherSPIN.getSelectedItem().toString();


        return '"' + quote + "\" - " + teacher + ", " + date;
    }

}