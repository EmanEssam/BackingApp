package com.peacefulwarrior.eman.backingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.peacefulwarrior.eman.backingapp.model.ApiHelper;
import com.peacefulwarrior.eman.backingapp.model.Recipe;
import com.peacefulwarrior.eman.backingapp.service.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private View recyclerView;
    private List<Recipe> recipes;
    private Recipe recipe;
    SimpleItemRecyclerViewAdapter simpleItemRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        recyclerView = findViewById(R.id.item_list);
        getRecipes();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        assert recyclerView != null;
        recipes = new ArrayList<>();


    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        getRecipes();
//        simpleItemRecyclerViewAdapter.notifyDataSetChanged();
//    }

    private void getRecipes() {
        ApiHelper apiHelper = ApiClient.getClient().create(ApiHelper.class);
        retrofit2.Call<List<Recipe>> call = apiHelper.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Recipe>> call, Response<List<Recipe>> response) {
//                recipes.addAll(response.body());
                setupRecyclerView((RecyclerView) recyclerView, response.body());
                recipes = response.body();
//                simpleItemRecyclerViewAdapter.notifyDataSetChanged();
//                Log.e("response", response.message());
//                Log.e("response", response.body().get(0).toString());
            }

            @Override
            public void onFailure(retrofit2.Call<List<Recipe>> call, Throwable t) {
                Log.e("response", t.getMessage());
            }
        });
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, List<Recipe> recipes) {
        simpleItemRecyclerViewAdapter = new SimpleItemRecyclerViewAdapter(this, recipes, mTwoPane, this);
        recyclerView.setAdapter(simpleItemRecyclerViewAdapter);
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private Context context;
        private final ItemListActivity mParentActivity;
        private final List<Recipe> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
//                    arguments.putString(ItemDetailFragment.ARG_ITEM_ID, item.id);
                    ItemDetailFragment fragment = new ItemDetailFragment();
//                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("food", recipe);
                    Intent intent = new Intent(context, RecipeDetailsActivity.class);
                    intent.putExtra("food", bundle);
//                    intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(ItemListActivity parent,
                                      List<Recipe> items,
                                      boolean twoPane, Context mContext) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
            context = mContext;


        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            if (!mValues.get(position).getImage().isEmpty() && !mValues.get(position).getImage().equals(null)) {
                Glide.with(context)
                        .load(mValues.get(position).getImage())
                        .into(holder.mRecipe);
            }
//            holder.itemView.setTag(mValues.get(position));
            recipe = mValues.get(position);
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {

            return mValues.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            final ImageView mRecipe;

            ViewHolder(View view) {
                super(view);
                mRecipe = (ImageView) view.findViewById(R.id.recipe_image);
            }
        }
    }
}
