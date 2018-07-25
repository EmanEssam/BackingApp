package com.peacefulwarrior.eman.backingapp;

import android.os.SystemClock;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import com.peacefulwarrior.eman.backingapp.activity.ItemListActivity;
import com.peacefulwarrior.eman.backingapp.activity.MainActivity;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    public static final String RECIPE_NAME = "";
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);


    @Test
    public void clickGridViewItem_I_openDetailsActivity() throws InterruptedException {
//        onView(withId(R.id.recipesList))
//                .perform(click());
//        onView(withId(R.id.recipesList)).check(ViewAssertions.matches(isDisplayed()));
        Thread.sleep(2000);
        onView(withId(R.id.recipesList))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
    }


}
