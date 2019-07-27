package com.example.android.bakingapp.utils;

import com.example.android.bakingapp.models.Ingredient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class SharedPrefConverterUtils {

        public static List<Ingredient> toIngredientList(String ingredientString) {
            if (ingredientString == null) {
                return Collections.emptyList();
            }
            Gson gson = new Gson();
            Type stepListType = new TypeToken<List<Ingredient>>() {}.getType();
            return gson.fromJson(ingredientString, stepListType);
        }

        public static String toIngredientString (List<Ingredient> ingredients) {
            if (ingredients == null) {
                return null;
            }
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Ingredient>>() {}.getType();
            return gson.toJson(ingredients, listType);
        }
}
