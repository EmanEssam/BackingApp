package com.peacefulwarrior.eman.backingapp.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.peacefulwarrior.eman.backingapp.R;
import com.peacefulwarrior.eman.backingapp.StepFragment;
import com.peacefulwarrior.eman.backingapp.fragment.RecipeDetailsFragment;
import com.peacefulwarrior.eman.backingapp.model.Recipe;

import java.util.ArrayList;

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeDetailsFragment.OnStepClicked {

    Recipe recipe;
    Bundle b;
    private boolean mTWoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable("recipe");
        } else {
            b = getIntent().getBundleExtra("food");
            recipe = b.getParcelable("food");
        }
        if (findViewById(R.id.fragment_container) != null) {
            mTWoPane = true;
            Bundle args = new Bundle();
            args.putParcelableArrayList("step", (ArrayList<? extends Parcelable>) recipe.getSteps());
            args.putBoolean("tablet", true);
            StepFragment stepFragment = new StepFragment();
            stepFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, stepFragment).commit();

        } else {
            mTWoPane = false;
        }

        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("food", recipe);
        args.putBoolean("tablet", mTWoPane);
        recipeDetailsFragment.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.master_list_fragment, recipeDetailsFragment).commit();
        setTitle(recipe.getName());
    }




    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (recipe != null) {
            outState.putParcelable("recipe", recipe);

        }

    }

    @Override
    public void onStepClicked(int position) {
        Bundle args = new Bundle();
        args.putParcelableArrayList("step", (ArrayList<? extends Parcelable>) recipe.getSteps());
        args.putInt("position", position);
        args.putBoolean("tablet", true);
        StepFragment stepFragment = new StepFragment();
        stepFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, stepFragment).commit();
    }
}
