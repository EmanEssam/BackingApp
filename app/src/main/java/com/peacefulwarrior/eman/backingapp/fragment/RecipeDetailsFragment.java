package com.peacefulwarrior.eman.backingapp.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peacefulwarrior.eman.backingapp.R;
import com.peacefulwarrior.eman.backingapp.adapter.RecipeDetailsAdapter;
import com.peacefulwarrior.eman.backingapp.model.Recipe;
import com.peacefulwarrior.eman.backingapp.model.Step;
import com.peacefulwarrior.eman.backingapp.utils.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailsFragment extends Fragment implements RecipeDetailsAdapter.OnStepClickListener {
    List<Section> sections = new ArrayList<>();
    List<Step> steps = new ArrayList<>();
    private OnStepClicked onStepClicked;

    public RecipeDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        Recipe recipe;
        if (getArguments()!=null) {
            recipe = getArguments().getParcelable("food");
            sections.addAll(recipe.getIngredients());
            sections.addAll(recipe.getSteps());
            steps = recipe.getSteps();
            RecyclerView RecipesList = rootView.findViewById(R.id.ingredientRV);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            RecipeDetailsAdapter mAdapter = new RecipeDetailsAdapter(sections, getContext(), steps,getArguments().getBoolean("tablet"),this);
            RecipesList.setLayoutManager(layoutManager);
            RecipesList.setAdapter(mAdapter);
        }
        return rootView;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            onStepClicked= (OnStepClicked) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }


    @Override
    public void onStepSelected(int position) {
        onStepClicked.onStepClicked(position);

    }

    public interface OnStepClicked{
        void onStepClicked(int position);
    }
}
