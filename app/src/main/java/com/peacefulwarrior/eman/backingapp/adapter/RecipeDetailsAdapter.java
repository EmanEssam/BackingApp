package com.peacefulwarrior.eman.backingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peacefulwarrior.eman.backingapp.R;
import com.peacefulwarrior.eman.backingapp.activity.StepsPlayerActivity;
import com.peacefulwarrior.eman.backingapp.model.Ingredient;
import com.peacefulwarrior.eman.backingapp.model.Step;
import com.peacefulwarrior.eman.backingapp.utils.Section;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Section> sections;
    List<Step> steps;
    private Context context;
    OnStepClickListener mCallback;



    public RecipeDetailsAdapter(List<Section> sections, Context context, List<Step> steps) {
        this.sections = sections;
        this.context = context;
        this.steps = steps;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ingredient_item_row, parent, false);
            return new RowViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.step_item_row, parent, false);
            return new SectionViewHolder(view);
        }
    }


    public class RowViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientTv;

        public RowViewHolder(View itemView) {
            super(itemView);
            ingredientTv = itemView.findViewById(R.id.ingredientTv);
        }
    }

    public class SectionViewHolder extends RecyclerView.ViewHolder {
        TextView stepTv;

        public SectionViewHolder(View itemView) {
            super(itemView);
            stepTv = itemView.findViewById(R.id.stepTv);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Section item = sections.get(position);

        if (item instanceof Ingredient) {
            Ingredient ingredient = (Ingredient) item;
            RowViewHolder holder1 = (RowViewHolder) holder;
            holder1.ingredientTv.setText(ingredient.getIngredient() + " (" + ingredient.getQuantity() + ingredient.getMeasure() + ")");
        } else {
            Step step = (Step) item;
            SectionViewHolder holder1 = (SectionViewHolder) holder;
            holder1.stepTv.setText(step.getShortDescription());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallback.onStepSelected(position);
                    Intent intent = new Intent(context, StepsPlayerActivity.class);
                    intent.putParcelableArrayListExtra("step", (ArrayList<? extends Parcelable>) steps);
                    context.startActivity(intent);

                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return sections.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (sections.get(position) instanceof Ingredient) {
            return 0;
        } else {
            return 1;
        }
    }
    public interface OnStepClickListener {
        void onStepSelected(int position);
    }
}
