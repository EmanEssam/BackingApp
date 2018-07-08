package com.peacefulwarrior.eman.backingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.peacefulwarrior.eman.backingapp.adapter.RecipeDetailsAdapter;
import com.peacefulwarrior.eman.backingapp.model.Recipe;
import com.peacefulwarrior.eman.backingapp.model.Step;
import com.peacefulwarrior.eman.backingapp.utils.Section;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity {
    List<Section> sections = new ArrayList<>();
    List<Step> steps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details2);
        Bundle b = getIntent().getBundleExtra("food");
        Recipe recipe = b.getParcelable("food");
        sections.addAll(recipe.getIngredients());
        sections.addAll(recipe.getSteps());
        steps = recipe.getSteps();
        initViews();
//        if (savedInstanceState == null) {
//            ItemDetailFragment fragment = new ItemDetailFragment();
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.item_detail_container, fragment)
//                    .commit();
//        }
    }

    private void initViews() {
        RecyclerView RecipesList = findViewById(R.id.ingredientRV);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecipeDetailsAdapter mAdapter = new RecipeDetailsAdapter(sections, this,steps);
        RecipesList.setLayoutManager(layoutManager);
        RecipesList.setAdapter(mAdapter);
    }
}
