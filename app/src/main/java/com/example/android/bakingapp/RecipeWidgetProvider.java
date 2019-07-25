package com.example.android.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.android.bakingapp.models.Recipe;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    private static final String TAG = "RecipeWidgetProvider";

    public static final String EXTRA_RECIPE = "recipe";

    private static Recipe mRecipe;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, Recipe recipe) {

        // Construct the RemoteViews object
        RemoteViews views;

        views = new RemoteViews(context.getPackageName(), R.layout.widget_list_view);

        Intent intent = new Intent(context, ListViewWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        mRecipe = (recipe != null) ? recipe : new Recipe();
        intent.putExtra(EXTRA_RECIPE, mRecipe);
        Log.d(TAG, "updateAppWidget: mRecipe : " + mRecipe.toString());
        views.setRemoteAdapter(R.id.list_view, intent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, Recipe recipe) {

        Log.d(TAG, "updateAppWidgets: recipe : " + recipe.toString());
        for(int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipe);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        Log.d(TAG, "onUpdate: in");

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

