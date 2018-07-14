package com.peacefulwarrior.eman.backingapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.peacefulwarrior.eman.backingapp.model.Step;

import java.util.ArrayList;

public class SampleFragmentPageAdapter{
    ArrayList<Step> steps;
    private Context context;

//    public SampleFragmentPageAdapter(FragmentManager fm, Context context ,ArrayList<Step> steps) {
//        super(fm);
//        this.context = context;
//        this.steps=steps;
//    }

//    @Override
//    public int getCount() {
//        return 1;
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        Log.e("step",steps.get(position).getVideoURL());
//        return StepFragment.newInstance(position + 1,steps.get(position));
//    }
//
//    @Override
//    public CharSequence getPageTitle(int position) {
//        // Generate title based on item position
//        return "Step"+" "+position;
//    }
}