package com.peacefulwarrior.eman.backingapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.peacefulwarrior.eman.backingapp.R;
import com.peacefulwarrior.eman.backingapp.adapter.RecipesListAdapter;
import com.peacefulwarrior.eman.backingapp.model.ApiHelper;
import com.peacefulwarrior.eman.backingapp.model.Recipe;
import com.peacefulwarrior.eman.backingapp.service.ApiClient;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recipeList;
    private List<Recipe> recipes;
    private Recipe recipe;
    private RecipesListAdapter recipesListAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getRecipes();
        initViews();
    }

    private void setupRecipesList(List<Recipe> recipes) {
        recipesListAdapter = new RecipesListAdapter(recipes, this);
        recipeList.setLayoutManager(layoutManager);
        recipeList.setAdapter(recipesListAdapter);
        recipeList.setHasFixedSize(true);
    }

    private void initViews() {
        recipeList = findViewById(R.id.recipesList);
        layoutManager = new LinearLayoutManager(this);
    }

    private void getRecipes() {
        ApiHelper apiHelper = ApiClient.getClient().create(ApiHelper.class);
        retrofit2.Call<List<Recipe>> call = apiHelper.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Recipe>> call, Response<List<Recipe>> response) {
//                recipes.addAll(response.body());
                recipes = response.body();
                setupRecipesList(recipes);
                Log.e("test", recipes.toString());
//                simpleItemRecyclerViewAdapter.notifyDataSetChanged();
//                Log.e("response", response.message());
//                Log.e("response", response.body().get(0).toString());
            }

            @Override
            public void onFailure(retrofit2.Call<List<Recipe>> call, Throwable t) {
                Log.e("response", t.getMessage());
            }
        });
    }
}
