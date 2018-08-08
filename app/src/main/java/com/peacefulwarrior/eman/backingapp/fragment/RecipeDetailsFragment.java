package com.peacefulwarrior.eman.backingapp.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
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
    Recipe recipe;
    RecyclerView recipesList;
    LinearLayoutManager layoutManager;
    private OnStepClicked onStepClicked;
    Bundle bundle;

    public RecipeDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        initViews(rootView);
        if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable("list");
            int position = savedInstanceState.getInt("position");
            setupDetailsList(recipe, 15);

        } else {
            if (getArguments() != null) {
                recipe = getArguments().getParcelable("food");
                setupDetailsList(recipe);
            }

        }

        return rootView;
    }

    private void initViews(View rootView) {
        layoutManager = new LinearLayoutManager(getContext());
        recipesList = rootView.findViewById(R.id.ingredientRV);
    }

    private void setupDetailsList(Recipe recipe) {
        sections.addAll(recipe.getIngredients());
        sections.addAll(recipe.getSteps());
        steps = recipe.getSteps();
        RecipeDetailsAdapter mAdapter = new RecipeDetailsAdapter(sections, getContext(), steps, getArguments().getBoolean("tablet"), this);
        recipesList.setLayoutManager(layoutManager);
        recipesList.setAdapter(mAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (bundle != null) {
            Parcelable listState = bundle.getParcelable("state");
            recipesList.getLayoutManager().onRestoreInstanceState(listState);
//            recipesList.smoothScrollToPosition(5);

        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
//            Parcelable listState = savedInstanceState.getParcelable("state");
//            recipesList.getLayoutManager().onRestoreInstanceState(listState);
            recipesList.scrollToPosition(savedInstanceState.getInt("pos")+10);


        }
    }


    @Override
    public void onPause() {
        super.onPause();
        bundle = new Bundle();
        Parcelable listState = recipesList.getLayoutManager().onSaveInstanceState();
        bundle.putParcelable("state", listState);
        bundle.putInt("pos", layoutManager.findFirstCompletelyVisibleItemPosition());

    }

    private void setupDetailsList(Recipe recipe, int position) {
        sections.addAll(recipe.getIngredients());
        sections.addAll(recipe.getSteps());
        steps = recipe.getSteps();
        RecipeDetailsAdapter mAdapter = new RecipeDetailsAdapter(sections, getContext(), steps, getArguments().getBoolean("tablet"), this);
        recipesList.setLayoutManager(layoutManager);
        recipesList.setAdapter(mAdapter);
//        recipesList.smoothScrollToPosition(position);
        recipesList.scrollToPosition(position);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            onStepClicked = (OnStepClicked) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }


    @Override
    public void onStepSelected(int position) {
        onStepClicked.onStepClicked(position);


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (recipe != null) {
            outState.putParcelable("list", recipe);
            outState.putInt("position", layoutManager.findFirstVisibleItemPosition());
        }

        Parcelable listState = recipesList.getLayoutManager().onSaveInstanceState();
        outState.putParcelable("state", listState);
        outState.putInt("pos", layoutManager.findFirstVisibleItemPosition());

    }

    public interface OnStepClicked {
        void onStepClicked(int position);

    }
}
