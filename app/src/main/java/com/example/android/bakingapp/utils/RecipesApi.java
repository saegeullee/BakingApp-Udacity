package com.example.android.bakingapp.utils;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RecipesApi {

    @GET("baking.json")
    Call<Object> getRecipes();
}
