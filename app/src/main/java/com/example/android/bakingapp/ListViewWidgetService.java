package com.example.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.example.android.bakingapp.models.Ingredient;
import com.example.android.bakingapp.models.Recipe;
import java.util.ArrayList;
import java.util.List;

public class ListViewWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListViewRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

class ListViewRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = "ListViewRemoteViewsFact";

    private Context mContext;
    private int mAppWidgetId;
    private List<Ingredient> mIngredients;

    public ListViewRemoteViewsFactory(Context context, Intent intent) {
        this.mContext = context;
        Recipe recipe = intent.getParcelableExtra(RecipeWidgetProvider.EXTRA_RECIPE);
        mIngredients = (recipe != null) ? recipe.getIngredients() : new ArrayList<Ingredient>();
        Log.d(TAG, "ListViewRemoteViewsFactory: mIngredients : " + mIngredients.toString());
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: in");
    }

    @Override
    public void onDataSetChanged() {
        Log.d(TAG, "onDataSetChanged: in");
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(mIngredients == null) return 0;
        return mIngredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        Log.d(TAG, "getViewAt: in");

        if(mIngredients == null || mIngredients.size() == 0) return null;

        Ingredient ingredient = mIngredients.get(position);
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_ingredient_item_row);
        views.setTextViewText(R.id.ingredient, ingredient.getIngredient());
        views.setTextViewText(R.id.quantity, String.valueOf(ingredient.getQuantity()));
        views.setTextViewText(R.id.measure, ingredient.getMeasure());

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}