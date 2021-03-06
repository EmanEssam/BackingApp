package com.peacefulwarrior.eman.backingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
    List<Step> steps;
    int step_position;
    int ingredients;
    OnStepClickListener mCallback;
    private List<Section> sections;
    private Context context;
    private Boolean mTwoPane;

    private int selected;

    public RecipeDetailsAdapter(List<Section> sections, Context context, List<Step> steps, Boolean mTwoPane, OnStepClickListener mCallback) {
        this.sections = sections;
        this.context = context;
        this.steps = steps;
        this.mTwoPane = mTwoPane;
        this.mCallback = mCallback;
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

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Section item = sections.get(position);

        if (item instanceof Ingredient) {
            Ingredient ingredient = (Ingredient) item;
            RowViewHolder holder1 = (RowViewHolder) holder;
            holder1.ingredientTv.setText(ingredient.getIngredient() + " (" + Math.round(ingredient.getQuantity()) + "" + ingredient.getMeasure() + ")");
        } else {
            final Step step = (Step) item;
            final SectionViewHolder holder1 = (SectionViewHolder) holder;
            holder1.stepTv.setText(step.getShortDescription());
            if (selected == position) {
                holder1.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            } else {
                holder1.itemView.setBackgroundColor(Color.TRANSPARENT);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    mCallback.onStepSelected(position);
                    if (!mTwoPane) {
                        Intent intent = new Intent(context, StepsPlayerActivity.class);
                        intent.putParcelableArrayListExtra("step", (ArrayList<? extends Parcelable>) steps);
                        ingredients = sections.size() - steps.size();
                        step_position = position - ingredients;
                        intent.putExtra("position", step_position);
                        context.startActivity(intent);
                    } else {
                        selected = position;
                        notifyDataSetChanged();
                        ingredients = sections.size() - steps.size();
                        step_position = position - ingredients;
                        mCallback.onStepSelected(step_position);
                    }

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
}
