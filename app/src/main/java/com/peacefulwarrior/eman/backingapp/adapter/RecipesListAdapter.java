package com.peacefulwarrior.eman.backingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peacefulwarrior.eman.backingapp.R;
import com.peacefulwarrior.eman.backingapp.activity.RecipeDetailsActivity;
import com.peacefulwarrior.eman.backingapp.model.Recipe;
import com.peacefulwarrior.eman.backingapp.utils.TinyDB;

import java.util.ArrayList;
import java.util.List;

public class RecipesListAdapter extends RecyclerView.Adapter<RecipesListAdapter.RecipeViewHolder> {
    private List<Recipe> recipeList;
    private Context mContext;
    ArrayList<String> ingredientsList =new ArrayList<>();


    public RecipesListAdapter(List<Recipe> recipeList, Context mContext) {
        this.recipeList = recipeList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_row, parent, false);
        return new RecipeViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, final int position) {
        holder.recipeName.setText(recipeList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("food", recipeList.get(position));
                Intent intent = new Intent(mContext, RecipeDetailsActivity.class);
                intent.putExtra("food", bundle);
                ingredientsList.clear();
                TinyDB tinydb = new TinyDB(mContext);
                for (int i=0 ;i<recipeList.get(position).getIngredients().size();i++){
                    ingredientsList.add(recipeList.get(position).getIngredients().get(i).getIngredient());
                }

                tinydb.putListString("list", ingredientsList);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        TextView recipeName;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipe_name_tv);
        }
    }
}
