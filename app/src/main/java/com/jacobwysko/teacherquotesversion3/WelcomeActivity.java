package com.jacobwysko.teacherquotesversion3;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class WelcomeActivity extends AppIntro2 {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setZoomAnimation();
        showSkipButton(false);

        addSlide(AppIntroFragment.newInstance("Teacher Quotes", "Quote your favorite teachers on what they say", R.drawable.noback, getResources().getColor(R.color.primaryDarkColor)));
        addSlide(AppIntroFragment.newInstance("Advanced Tools", "Advanced tools for bulk quoting and complex quotations", R.drawable.baseline_tune_white_48, getResources().getColor(R.color.primaryAccent)));
        addSlide(AppIntroFragment.newInstance("Analytics","Detailed quotation database with extensive statistical analyses", R.drawable.baseline_bar_chart_white_48, getResources().getColor(R.color.primaryColor)));
        addSlide(AppIntroFragment.newInstance("Community", "Discord server with a community of quoters", R.drawable.baseline_people_white_48,getResources().getColor(R.color.primaryDarkColor)));
        addSlide(AppIntroFragment.newInstance("Bot","Utilizes analytics from the database to provide members of the Discord Server with statistics", R.drawable.baseline_memory_white_48, getResources().getColor(R.color.primaryColor)));
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent myIntent = new Intent(WelcomeActivity.this, ImportTeachersActivity.class);
        WelcomeActivity.this.startActivity(myIntent);


    }
}
