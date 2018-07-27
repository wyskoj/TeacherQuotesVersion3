package com.jacobwysko.teacherquotesversion3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.FileOutputStream;
import java.util.Arrays;

public class ImportTeachersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_teachers);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Inflate the menu; this adds items to the action bar if it is present. */
        getMenuInflater().inflate(R.menu.menu_example, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                /* OKAY THEY CLICKED IT */
                EditText teacherEditText = findViewById(R.id.teacherListEditText);
                String content = teacherEditText.getText().toString();
                CheckBox sortCheckBox = findViewById(R.id.sortCheckBox);
                sortCheckBox.isChecked();

                String[] temp = content.split("\n");
                if (sortCheckBox.isChecked()) Arrays.sort(temp);
                content = joinWithCommas(temp);

                String filename = "teacherList";
                FileOutputStream outputStream;
                try {
                    outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                    outputStream.write(content.getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finish();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        /* Nothing. */
    }

    @SuppressLint("Assert")
    private String joinWithCommas(String[] list){
        String builder = "";
        for (int i = 0; i < list.length; i++){
            if (i != list.length - 1){
                builder = builder + (list[i]);
                builder = builder + (",");
            } else {
                assert false;
                builder = builder + (list[i]);
            }

        }
        return builder;
    }
}
