package de.rheinfabrik.mvvm_example.utils;

import android.content.Context;
import android.content.Intent;

import de.rheinfabrik.mvvm_example.activities.DetailsActivity;
import de.rheinfabrik.mvvm_example.activities.extras.DetailsActivityExtras;
import de.rheinfabrik.mvvm_example.network.models.SearchResult;

/**
 * Class used to create intents.
 */
public class IntentFactory {

    // Public Api

    /**
     * Builds an intent to open the details activity.
     */
    public static Intent newDetailsActivityIntent(Context context, SearchResult searchResult) {

        // Build extras
        DetailsActivityExtras extras = new DetailsActivityExtras();
        extras.searchResult = searchResult;

        // Build intent
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtras(extras.buildBundle());

        return intent;
    }
}
