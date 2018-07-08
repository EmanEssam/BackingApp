package com.peacefulwarrior.eman.backingapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.peacefulwarrior.eman.backingapp.model.Step;

import java.util.ArrayList;

public class StepsPlayerActivity extends AppCompatActivity {

    static ArrayList<Step> steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_player);
        getIntent().getExtras().getParcelableArrayList("step");
        steps = getIntent().getExtras().getParcelableArrayList("step");
        Log.v("steps", steps.size() + "");
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(new SampleFragmentPageAdapter(getSupportFragmentManager(),
                StepsPlayerActivity.this, steps));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_steps_player, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
