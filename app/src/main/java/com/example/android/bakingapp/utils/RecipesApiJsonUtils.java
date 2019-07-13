package com.example.android.bakingapp.utils;

import android.util.Log;

import com.example.android.bakingapp.models.Ingredient;
import com.example.android.bakingapp.models.Recipe;
import com.example.android.bakingapp.models.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecipesApiJsonUtils {

    private static final String TAG = "RecipesApiJsonUtils";

    public static List<Recipe> getRecipesFromJson(String jsonString) throws JSONException {

        JSONArray array = new JSONArray(jsonString);
        List<Recipe> recipes = new ArrayList<>();

        Log.d(TAG, "getRecipesFromJson: " + array.toString());

        for(int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);

            String name = object.getString("name");

            JSONArray ingredientsArray = object.getJSONArray("ingredients");

            List<Ingredient> ingredients = new ArrayList<>();

            for(int j = 0; j < ingredientsArray.length(); j++) {

                JSONObject ingredientObj = ingredientsArray.getJSONObject(j);
                double quantity = ingredientObj.getDouble("quantity");
                String measure = ingredientObj.getString("measure");
                String ingredient_name = ingredientObj.getString("ingredient");

                Ingredient ingredient = new Ingredient();
                ingredient.setIngredient(ingredient_name);
                ingredient.setMeasure(measure);
                ingredient.setQuantity(quantity);

                ingredients.add(ingredient);

            }

            Log.d(TAG, "getRecipesFromJson: ingredients size : " + ingredients.size());

            List<Step> steps = new ArrayList<>();

            JSONArray stepsArray = object.getJSONArray("steps");
            for(int k = 0; k < stepsArray.length(); k++) {

                JSONObject stepObj = stepsArray.getJSONObject(k);
                int id = stepObj.getInt("id");
                String shortDescription = stepObj.getString("shortDescription");
                String description = stepObj.getString("description");
                String videoUrl = stepObj.getString("videoURL");
                String thumbnailUrl = stepObj.getString("thumbnailURL");

                Step step = new Step();
                step.setId(id);
                step.setShortDescription(shortDescription);
                step.setDescription(description);
                step.setVideoUrl(videoUrl);
                step.setThumbnailUrl(thumbnailUrl);

                steps.add(step);
            }

            Log.d(TAG, "getRecipesFromJson: steps size : " + steps.size());

            Recipe recipe = new Recipe();
            recipe.setName(name);
            recipe.setIngredients(ingredients);
            recipe.setSteps(steps);

            recipes.add(recipe);
        }

        Log.d(TAG, "getRecipesFromJson: recipe size : " + recipes.size());

        return recipes;
    }
}
