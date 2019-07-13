package com.example.android.bakingapp.utils;

import com.example.android.bakingapp.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkRequestGenerator {

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();
    private static RecipesApi recipesApi = retrofit.create(RecipesApi.class);

    public static RecipesApi getRecipesApi() {
        return recipesApi;
    }

}
