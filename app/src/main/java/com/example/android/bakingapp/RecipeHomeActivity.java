package com.example.android.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.bakingapp.models.Recipe;
import com.example.android.bakingapp.utils.NetworkRequestGenerator;
import com.example.android.bakingapp.utils.RecipesApiJsonUtils;
import com.google.gson.Gson;

import org.json.JSONException;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeHomeActivity extends AppCompatActivity
        implements RecipeAdapter.OnRecipeItemClickListener{

    private static final String TAG = "RecipeHomeActivity";

    private RecyclerView mRecipeRecyclerView;
    private RecipeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_home);

        initUI();
        getRecipesFromApi();
    }

    private void initUI() {

        mRecipeRecyclerView = findViewById(R.id.recipe_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(mRecipeRecyclerView.getContext(), layoutManager.getOrientation());
        mRecipeRecyclerView.setLayoutManager(layoutManager);
        mRecipeRecyclerView.addItemDecoration(itemDecoration);
        mAdapter = new RecipeAdapter(this);
        mRecipeRecyclerView.setAdapter(mAdapter);

    }

    private void getRecipesFromApi() {

        getRecipes().enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {


                String result = new Gson().toJson(response.body());

                Log.d(TAG, "onResponse: result1 : " + result);
                try {
                    List<Recipe> recipes = RecipesApiJsonUtils.getRecipesFromJson(result);
                    mAdapter.setRecipes(recipes);

                    Log.d(TAG, "onResponse: recipes : " + recipes.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


    @Override
    public void onRecipeItemClicked(int position) {

    }

    private Call getRecipes() {
        return NetworkRequestGenerator.getRecipesApi().getRecipes();
    }
}
