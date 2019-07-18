package com.example.android.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.bakingapp.models.Ingredient;
import com.example.android.bakingapp.models.Recipe;
import com.example.android.bakingapp.models.Step;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeStepsAdapter.OnStepsItemClickListener {

    private static final String TAG = "RecipeDetailsActivity";

    private Recipe mRecipe;
    private List<Step> mSteps;
    private FragmentManager mFragmentManager;
    private RecipeDetailsFragment mDetailsFragment;

    private boolean mIsUserCurrentLocationStepFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        initUI();

    }

    private void initUI() {

        mIsUserCurrentLocationStepFragment = false;

        mRecipe = new Recipe();

        Intent intent = getIntent();
        if(intent.hasExtra(getString(R.string.recipe_name)) &&
                intent.hasExtra(getString(R.string.recipe_ingredients)) &&
                intent.hasExtra(getString(R.string.recipe_steps))) {

            String recipe_name = intent.getStringExtra(getString(R.string.recipe_name));
            List<Ingredient> ingredients = intent.getParcelableArrayListExtra(getString(R.string.recipe_ingredients));
            mSteps = intent.getParcelableArrayListExtra(getString(R.string.recipe_steps));

            Log.d(TAG, "initUI: recipe name : " + recipe_name);
            Log.d(TAG, "initUI: ingredients : " + ingredients.toString());
            Log.d(TAG, "initUI: steps : " + mSteps.toString());

            mRecipe.setSteps(mSteps);
            mRecipe.setIngredients(ingredients);
            mRecipe.setName(recipe_name);

            setTitle(recipe_name);

        }

        // Fragment 열어라

        mDetailsFragment = new RecipeDetailsFragment();
        mFragmentManager = getSupportFragmentManager();

        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.recipe_name), mRecipe.getName());
        bundle.putParcelableArrayList(getString(R.string.recipe_ingredients), (ArrayList<Ingredient>) mRecipe.getIngredients());
        bundle.putParcelableArrayList(getString(R.string.recipe_steps), (ArrayList<Step>) mRecipe.getSteps());

        mDetailsFragment.setArguments(bundle);
        mFragmentManager.beginTransaction()
                .replace(R.id.container, mDetailsFragment)
                .commit();

    }

    @Override
    public void onStepsItemClicked(int position) {

        mIsUserCurrentLocationStepFragment = true;

        Log.d(TAG, "onStepsItemClicked: step : " + position + 1);
        RecipeStepFragment stepFragment = new RecipeStepFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.step_number), position);
        bundle.putParcelableArrayList(getString(R.string.recipe_steps), (ArrayList<Step>) mRecipe.getSteps());

        stepFragment.setArguments(bundle);

        mFragmentManager.beginTransaction()
                .replace(R.id.container, stepFragment)
                .commit();

    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: in");

        if(mIsUserCurrentLocationStepFragment) {
            mFragmentManager.beginTransaction()
                    .replace(R.id.container, mDetailsFragment)
                    .commit();
            
            mIsUserCurrentLocationStepFragment = false;
        } else {
            Log.d(TAG, "onBackPressed: super.onBackPressed");
            super.onBackPressed();
        }
        
    }
}
