package com.example.android.bakingapp.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Ingredient;
import com.example.android.bakingapp.models.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    private static final String TAG = "RecipeWidgetProvider";

    public static final String EXTRA_RECIPE = "recipe";

    private static Recipe mRecipe;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list_view);

        if(mRecipe != null)
        views.setTextViewText(R.id.recipe_title, mRecipe.getName() + " ingredients");

        Intent intent = new Intent(context, ListViewWidgetService.class);
        Log.d(TAG, "updateAppWidget: mRecipe : " + mRecipe.toString());

        /**
         * 여기서 에러 발생했다.
         * intent.putExtra() 를 통해 recipe 데이터를 ListViewWidgetService 로 전달하려 했지만
         * 이렇게 작성시 problem loading widget 에러가 발생했다.
         */

        views.setRemoteAdapter(R.id.list_view, intent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        DisplayIngredientsService.startDisplayIngredients(context, mRecipe);

    }


    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, Recipe recipe) {

        mRecipe = recipe;

        Log.d(TAG, "updateAppWidgets: recipe : " + recipe.toString());
        for(int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

