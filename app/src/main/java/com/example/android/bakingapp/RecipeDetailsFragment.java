package com.example.android.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.models.Ingredient;
import com.example.android.bakingapp.models.Recipe;
import com.example.android.bakingapp.models.Step;
import com.example.android.bakingapp.utils.SharedPrefConverterUtils;
import com.example.android.bakingapp.utils.SharedPrefManager;
import com.example.android.bakingapp.widgets.DisplayIngredientsService;

import java.util.List;

public class RecipeDetailsFragment extends Fragment {

    private static final String TAG = "RecipeDetailsFragment";

    private Recipe mRecipe;

    private RecyclerView mIngredientsRecyclerView;
    private RecipeIngredientsAdapter mIngredientsAdapter;

    private RecyclerView mStepsRecyclerView;
    private RecipeStepsAdapter mStepsAdapter;

    private List<Step> mSteps;

    private RecipeStepsAdapter.OnStepsItemClickListener listener;

    @Override
    public void onAttach(Context context) {
        listener = (RecipeStepsAdapter.OnStepsItemClickListener) context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        mRecipe = new Recipe();

        String recipe_name = getArguments().getString(getString(R.string.recipe_name));
        List<Ingredient> ingredients = getArguments().getParcelableArrayList(getString(R.string.recipe_ingredients));
        mSteps = getArguments().getParcelableArrayList(getString(R.string.recipe_steps));

        mRecipe.setName(recipe_name);
        mRecipe.setIngredients(ingredients);
        mRecipe.setSteps(mSteps);
        
        saveIngredientsForWidgets(mRecipe);

        Log.d(TAG, "onCreateView: mRecipe : " + mRecipe.toString());

        mIngredientsRecyclerView = rootView.findViewById(R.id.recipe_ingredients_recyclerview);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), linearLayout.getOrientation());
        mIngredientsRecyclerView.setLayoutManager(linearLayout);
        mIngredientsRecyclerView.addItemDecoration(dividerItemDecoration);
        mIngredientsRecyclerView.setNestedScrollingEnabled(false);

        mIngredientsAdapter = new RecipeIngredientsAdapter();
        mIngredientsRecyclerView.setAdapter(mIngredientsAdapter);

        mStepsRecyclerView = rootView.findViewById(R.id.recipe_steps_recyclerview);
        LinearLayoutManager stepsLinearLayout = new LinearLayoutManager(getActivity());
        DividerItemDecoration stepsDividerItemDecoration = new DividerItemDecoration(getActivity(), stepsLinearLayout.getOrientation());

        mStepsRecyclerView.setLayoutManager(stepsLinearLayout);
        mStepsRecyclerView.addItemDecoration(stepsDividerItemDecoration);
        mStepsRecyclerView.setNestedScrollingEnabled(false);

        mStepsAdapter = new RecipeStepsAdapter(listener);
        mStepsRecyclerView.setAdapter(mStepsAdapter);

        if(mRecipe != null) {

            mIngredientsAdapter.setIngredients(mRecipe.getIngredients());
            mStepsAdapter.setSteps(mRecipe.getSteps());

        }

        return rootView;
    }

    private void saveIngredientsForWidgets(Recipe recipe) {

        SharedPrefManager.getInstance(getActivity()).setRecipe(recipe);
        DisplayIngredientsService.startDisplayIngredients(getActivity(), recipe);

    }
}
