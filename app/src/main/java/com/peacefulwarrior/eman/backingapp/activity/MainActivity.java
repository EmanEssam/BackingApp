package com.peacefulwarrior.eman.backingapp.activity;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
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
import com.peacefulwarrior.eman.backingapp.utils.SimpleIdlingResource;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recipeList;
    private List<Recipe> recipes;
    private RecipesListAdapter recipesListAdapter;
    RecyclerView.LayoutManager layoutManager;
    @Nullable
    private SimpleIdlingResource mIdlingResource;
    SimpleIdlingResource idlingResource =
            (SimpleIdlingResource) getIdlingResource();



    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        getRecipes();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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
        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }
        ApiHelper apiHelper = ApiClient.getClient().create(ApiHelper.class);
        retrofit2.Call<List<Recipe>> call = apiHelper.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipes = response.body();
                setupRecipesList(recipes);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                            if (idlingResource != null) {
                                idlingResource.setIdleState(true);
                            }

                    }
                },2000);


                Log.e("test", recipes.toString());


            }

            @Override
            public void onFailure(retrofit2.Call<List<Recipe>> call, Throwable t) {
                Log.e("response", t.getMessage());
            }
        });
    }
}
