package utils

import android.content.Context
import android.content.Intent
import com.andrewreitz.spock.android.AndroidSpecification
import com.andrewreitz.spock.android.WithContext
import de.rheinfabrik.mvvm_example.activities.DetailsActivity
import de.rheinfabrik.mvvm_example.activities.extras.DetailsActivityExtras
import de.rheinfabrik.mvvm_example.network.models.SearchResult
import de.rheinfabrik.mvvm_example.utils.IntentFactory
import icepick.Icepick
import spock.lang.Title

@Title("newDetailsActivityIntent()")
class IntentFactoryNewDetailsActivityIntentSpecs extends AndroidSpecification {

    // Members

    @WithContext
    private Context mContext;

    // Scenarios

    def "it should create an intent with the correct class"() {

        given: "/"

        when: "I create a details intent"
            Intent intent = IntentFactory.newDetailsActivityIntent(mContext, null);

        then: "The intent should have the correct component class"
            intent.getComponent().getClassName() == DetailsActivity.class.getName()
    }

    def "it should set the item as extra"() {

        given: "An item"
            SearchResult item = new SearchResult()
            item.identifier = "1234"

        when: "I create a details intent"
            Intent intent = IntentFactory.newDetailsActivityIntent(mContext, item);

        and: "I grab the extras"
            DetailsActivityExtras extras = new DetailsActivityExtras();
            Icepick.restoreInstanceState(extras, intent.getExtras());

        then: "The extras should hold the correct item"
            extras.searchResult == item
    }
}