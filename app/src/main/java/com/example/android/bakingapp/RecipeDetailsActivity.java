package com.example.android.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.android.bakingapp.models.Ingredient;
import com.example.android.bakingapp.models.Recipe;
import com.example.android.bakingapp.models.Step;

import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity {

    private static final String TAG = "RecipeDetailsActivity";

    private Recipe mRecipe;

    private RecyclerView mIngredientsRecyclerView;
    private RecipeIngredientsAdapter mIngredientsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        initUI();
        setDataToViews();
    }

    private void initUI() {

        mRecipe = new Recipe();

        Intent intent = getIntent();
        if(intent.hasExtra(getString(R.string.recipe_name)) &&
                intent.hasExtra(getString(R.string.recipe_ingredients)) &&
                intent.hasExtra(getString(R.string.recipe_steps))) {

            String recipe_name = intent.getStringExtra(getString(R.string.recipe_name));
            List<Ingredient> ingredients = intent.getParcelableArrayListExtra(getString(R.string.recipe_ingredients));
            List<Step> steps = intent.getParcelableArrayListExtra(getString(R.string.recipe_steps));

            Log.d(TAG, "initUI: recipe name : " + recipe_name);
            Log.d(TAG, "initUI: ingredients : " + ingredients.toString());
            Log.d(TAG, "initUI: steps : " + steps.toString());

            mRecipe.setSteps(steps);
            mRecipe.setIngredients(ingredients);
            mRecipe.setName(recipe_name);

        }

        mIngredientsRecyclerView = findViewById(R.id.recipe_ingredients_recyclerview);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, linearLayout.getOrientation());
        mIngredientsRecyclerView.setLayoutManager(linearLayout);
        mIngredientsRecyclerView.addItemDecoration(dividerItemDecoration);
        mIngredientsRecyclerView.setNestedScrollingEnabled(false);

        mIngredientsAdapter = new RecipeIngredientsAdapter();
        mIngredientsRecyclerView.setAdapter(mIngredientsAdapter);
    }

    private void setDataToViews() {

        if(mRecipe != null) {

            mIngredientsAdapter.setIngredients(mRecipe.getIngredients());

        }
    }
}
