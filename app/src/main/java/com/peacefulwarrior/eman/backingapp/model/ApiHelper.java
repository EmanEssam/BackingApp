package com.peacefulwarrior.eman.backingapp.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiHelper {
    @GET("baking.json")
    Call<List<Recipe>> getRecipes();
}
