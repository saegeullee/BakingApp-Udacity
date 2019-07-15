package com.example.android.bakingapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.models.Ingredient;

import java.util.List;

public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsAdapter.IngredientViewHolder> {

    private List<Ingredient> mIngredients;

    public RecipeIngredientsAdapter() {
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_ingredient_item_row, viewGroup, false);

        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int i) {

        Ingredient ingredient = mIngredients.get(i);

        holder.mIngredient.setText(ingredient.getIngredient());
        holder.mMeasure.setText(ingredient.getMeasure());
        holder.mQuantity.setText(String.valueOf(ingredient.getQuantity()));

    }

    public void setIngredients(List<Ingredient> ingredients) {
        mIngredients = ingredients;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mIngredients == null) return 0;
        return mIngredients.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        private TextView mIngredient, mQuantity, mMeasure;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);

            mIngredient = itemView.findViewById(R.id.ingredient);
            mQuantity = itemView.findViewById(R.id.quantity);
            mMeasure = itemView.findViewById(R.id.measure);

        }
    }
}
