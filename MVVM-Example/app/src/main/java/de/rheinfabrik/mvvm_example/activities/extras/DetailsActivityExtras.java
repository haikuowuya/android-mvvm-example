package de.rheinfabrik.mvvm_example.activities.extras;

import de.rheinfabrik.mvvm_example.network.models.SearchResult;
import de.rheinfabrik.mvvm_example.utils.IcepickIntentExtras;
import icepick.Icicle;

/**
 * Extras for the details activity.
 */
public class DetailsActivityExtras extends IcepickIntentExtras {

    // Properties

    @Icicle
    public SearchResult searchResult;

}
