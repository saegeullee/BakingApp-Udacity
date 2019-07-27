package com.example.android.bakingapp.widgets;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Ingredient;
import com.example.android.bakingapp.utils.SharedPrefManager;
import java.util.List;


public class ListViewWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListViewRemoteViewsFactory(this.getApplicationContext());
    }
}

class ListViewRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = "ListViewRemoteViewsFact";

    private Context mContext;
    private List<Ingredient> mIngredients;

    public ListViewRemoteViewsFactory(Context context) {
        this.mContext = context;
        Log.d(TAG, "ListViewRemoteViewsFactory: in");
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: in");
    }

    @Override
    public void onDataSetChanged() {
        Log.d(TAG, "onDataSetChanged: in");

        mIngredients = SharedPrefManager.getInstance(mContext).getIngredients();
        Log.d(TAG, "onDataSetChanged: mIngredients : " + mIngredients.toString());

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

        if(mIngredients == null || mIngredients.size() == 0) {
            Log.d(TAG, "getViewAt: null");
            return null;
        }

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