package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.bakingapp.models.Ingredient;
import com.example.android.bakingapp.models.Recipe;
import com.example.android.bakingapp.models.Step;
import com.example.android.bakingapp.utils.NetworkRequestGenerator;
import com.example.android.bakingapp.utils.RecipesApiJsonUtils;
import com.google.gson.Gson;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeHomeActivity extends AppCompatActivity
        implements RecipeAdapter.OnRecipeItemClickListener{

    private static final String TAG = "RecipeHomeActivity";

    private RecyclerView mRecipeRecyclerView;
    private RecipeAdapter mAdapter;

    private List<Recipe> mRecipes;

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
                    mRecipes = RecipesApiJsonUtils.getRecipesFromJson(result);
                    mAdapter.setRecipes(mRecipes);

                    Log.d(TAG, "onResponse: recipes : " + mRecipes.toString());
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

        Recipe recipe = mRecipes.get(position);
        Log.d(TAG, "onRecipeItemClicked: recipe : " + recipe.toString());

        Intent intent = new Intent(RecipeHomeActivity.this, RecipeDetailsActivity.class);
        intent.putExtra(getString(R.string.recipe_name), recipe.getName());
        intent.putParcelableArrayListExtra(getString(R.string.recipe_ingredients), (ArrayList<Ingredient>) mRecipes.get(position).getIngredients());
        intent.putParcelableArrayListExtra(getString(R.string.recipe_steps), (ArrayList<Step>) mRecipes.get(position).getSteps());
        startActivity(intent);

    }

    private Call getRecipes() {
        return NetworkRequestGenerator.getRecipesApi().getRecipes();
    }
}
