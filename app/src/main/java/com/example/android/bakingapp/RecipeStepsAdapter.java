package com.example.android.bakingapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.android.bakingapp.models.Step;

import java.util.List;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.StepsViewHolder> {

    private List<Step> mSteps;
    private OnStepsItemClickListener onStepsItemClickListener;

    public interface OnStepsItemClickListener{
        void onStepsItemClicked(int position);
    }

    public RecipeStepsAdapter(OnStepsItemClickListener listener) {
        onStepsItemClickListener = listener;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_step_item_row, viewGroup, false);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int i) {

        Step step = mSteps.get(i);

        holder.currentStep.setText("STEP" + (i + 1));
        holder.shortDesc.setText(step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if(mSteps == null) return 0;
        return mSteps.size();
    }

    public void setSteps(List<Step> steps) {
        mSteps = steps;
        notifyDataSetChanged();
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView currentStep, shortDesc;

        public StepsViewHolder(@NonNull View itemView) {
            super(itemView);

            currentStep = itemView.findViewById(R.id.recipe_step);
            shortDesc = itemView.findViewById(R.id.recipe_short_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onStepsItemClickListener.onStepsItemClicked(getAdapterPosition());
        }
    }
}
