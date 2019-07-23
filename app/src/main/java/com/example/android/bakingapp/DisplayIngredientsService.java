package com.example.android.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.bakingapp.models.Ingredient;
import com.example.android.bakingapp.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class DisplayIngredientsService extends IntentService {

    private static final String TAG = "DisplayIngredientsServi";

    public static final String ACTION_DISPLAY_INGREDIENTS = "com.example.android.bakingapp.action.display_ingredients";
    public static final String EXTRAS_INGREDIENTS = "ingredients";

    public DisplayIngredientsService() {
        super(DisplayIngredientsService.class.getSimpleName());
    }

    public static void startDisplayIngredients(Context context, Recipe recipe) {
        Log.d(TAG, "startDisplayIngredients: in");
        Log.d(TAG, "startDisplayIngredients: recipe : " + recipe.toString());
        Intent intent = new Intent(context, DisplayIngredientsService.class);
        intent.setAction(ACTION_DISPLAY_INGREDIENTS);
        intent.putExtra(EXTRAS_INGREDIENTS, recipe);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null) {
            Log.d(TAG, "onHandleIntent: in");
            String action = intent.getAction();
            if(ACTION_DISPLAY_INGREDIENTS.equals(action)) {
                Log.d(TAG, "onHandleIntent: in2");
                Recipe recipe = intent.getParcelableExtra(EXTRAS_INGREDIENTS);
                Log.d(TAG, "onHandleIntent: recipe : " + recipe.toString());
                handleDisplayIngredients(recipe);
            }
        }
    }

    private void handleDisplayIngredients(Recipe recipe) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
        RecipeWidgetProvider.updateAppWidgets(this, appWidgetManager, appWidgetIds, recipe);

    }
}
