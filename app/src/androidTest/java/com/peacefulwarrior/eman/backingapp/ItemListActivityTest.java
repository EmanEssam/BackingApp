package com.peacefulwarrior.eman.backingapp;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import android.support.test.rule.ActivityTestRule;

import com.peacefulwarrior.eman.backingapp.activity.ItemListActivity;

@RunWith(AndroidJUnit4.class)
public class ItemListActivityTest {
    @Rule
    public ActivityTestRule<ItemListActivity> mActivityTestRule =
            new ActivityTestRule<>(ItemListActivity.class);


    @Test
    public void clickGridViewItem_I_openDetailsActivity(){
        onData(anything()).inAdapterView
                (withId(R.id.item_list)).atPosition(1).perform(click());
        onView(withId(R.id.recipe_image)).check(matches(withText("Cheesecake")));








    }

}
