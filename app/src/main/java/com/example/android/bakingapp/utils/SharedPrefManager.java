package com.example.android.bakingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.android.bakingapp.models.Ingredient;
import com.example.android.bakingapp.models.Recipe;

import java.util.List;

public class SharedPrefManager {

    private static final String TAG = "SharedPrefManager";
    private static final String SHAREDPREF_NAME = "mySharedPref";
    private Context mContext;
    private static SharedPrefManager mInstance;

    private static final String INGREDIENT_STRING = "IngredientString";
    private static final String RECIPE_NAME = "Recipe";

    public SharedPrefManager(Context mContext) {
        this.mContext = mContext;
    }

    public static SharedPrefManager getInstance(Context context) {
        if(mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void setRecipe(Recipe recipe) {

        String ingredientString = SharedPrefConverterUtils.toIngredientString(recipe.getIngredients());

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHAREDPREF_NAME, mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(INGREDIENT_STRING, ingredientString);
        editor.putString(RECIPE_NAME, recipe.getName());
        editor.apply();

    }

    public List<Ingredient> getIngredients() {

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHAREDPREF_NAME, mContext.MODE_PRIVATE);
        String ingredientString = sharedPreferences.getString(INGREDIENT_STRING, "");
        return SharedPrefConverterUtils.toIngredientList(ingredientString);
    }

}
