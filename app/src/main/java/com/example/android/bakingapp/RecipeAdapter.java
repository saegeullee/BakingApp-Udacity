package com.example.android.bakingapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.models.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> mRecipes;
    private OnRecipeItemClickListener onRecipeItemClickListener;

    public interface OnRecipeItemClickListener {
        void onRecipeItemClicked(int position);
    }

    public RecipeAdapter(OnRecipeItemClickListener listener) {
        onRecipeItemClickListener = listener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_item_row, viewGroup, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int i) {

        Recipe recipe = mRecipes.get(i);

        holder.mTitle.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        if(mRecipes == null) return 0;
        return mRecipes.size();
    }

    public void setRecipes(List<Recipe> recipes) {
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitle;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.recipe_title);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onRecipeItemClickListener.onRecipeItemClicked(getAdapterPosition());
        }
    }
}
