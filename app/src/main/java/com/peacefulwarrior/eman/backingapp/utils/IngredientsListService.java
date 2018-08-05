package com.peacefulwarrior.eman.backingapp.utils;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.widget.RemoteViewsService;

import com.peacefulwarrior.eman.backingapp.BakingWidgetProvider;

public class IngredientsListService extends IntentService {

    public static final String ACTION_ADD_INGREDIENTS =
            "com.peacefulwarrior.eman.backingapp.action.add_ingredients";

    public static final String ACTION_UPDATE_LIST = "com.peacefulwarrior.eman.backingapp.action.update_ingredients";


    public IngredientsListService() {
        super("IngredientsListService");
    }

    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_ADD_INGREDIENTS.equals(action)) {
                handleActionAddIngredients();
            } else if (ACTION_UPDATE_LIST.equals(action)) {
                handleActionUpdateIngredients();
            }
        }
    }


    public static void startActionUpdateWidget(Context context) {
        Intent intent = new Intent(context, IngredientsListService.class);
        intent.setAction(ACTION_UPDATE_LIST);
        context.startService(intent);

    }


    private void handleActionUpdateIngredients() {
        TinyDB tinyDB = new TinyDB(getApplicationContext());
        tinyDB.getListString("list");
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingWidgetProvider.class));
        //Now update all widgets
        BakingWidgetProvider.updateWidgets(this, appWidgetManager, appWidgetIds);

    }

    private void handleActionAddIngredients() {
//        startActionWaterPlants(getApplicationContext());
//        Uri PLANTS_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PLANTS).build();
//        ContentValues contentValues = new ContentValues();
//        long timeNow = System.currentTimeMillis();
//        contentValues.put(PlantContract.PlantEntry.COLUMN_LAST_WATERED_TIME, timeNow);
//        // Update only plants that are still alive
//        getContentResolver().update(
//                PLANTS_URI,
//                contentValues,
//                PlantContract.PlantEntry.COLUMN_LAST_WATERED_TIME+">?",
//                new String[]{String.valueOf(timeNow - PlantUtils.MAX_AGE_WITHOUT_WATER)});
    }

    public static void startActionWaterPlants(Context context) {
        Intent intent = new Intent(context, IngredientsListService.class);
        intent.setAction(ACTION_ADD_INGREDIENTS);
        context.startService(intent);
    }
}


