package com.peacefulwarrior.eman.backingapp.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.peacefulwarrior.eman.backingapp.R;
import com.peacefulwarrior.eman.backingapp.StepFragment;
import com.peacefulwarrior.eman.backingapp.adapter.RecipeDetailsAdapter;
import com.peacefulwarrior.eman.backingapp.fragment.RecipeDetailsFragment;
import com.peacefulwarrior.eman.backingapp.model.Recipe;
import com.peacefulwarrior.eman.backingapp.model.Step;
import com.peacefulwarrior.eman.backingapp.utils.Section;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeDetailsFragment.OnStepClicked {

    private boolean mTWoPane;
    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        Bundle b = getIntent().getBundleExtra("food");
        recipe = b.getParcelable("food");
        if (findViewById(R.id.fragment_container) != null) {
            mTWoPane = true;
            Bundle args = new Bundle();
            args.putParcelableArrayList("step", (ArrayList<? extends Parcelable>) recipe.getSteps());
            StepFragment stepFragment = new StepFragment();
            stepFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, stepFragment).commit();

        } else {
            mTWoPane = false;
        }

        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("food", b.getParcelable("food"));
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
        StepFragment stepFragment = new StepFragment();
        stepFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, stepFragment).commit();
    }
}
