package network.deserializer

import android.content.Context
import com.andrewreitz.spock.android.AndroidSpecification
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import de.rheinfabrik.mvvm_example.network.deserializer.SearchResultsDeserializer
import de.rheinfabrik.mvvm_example.network.models.SearchResult
import spock.lang.Title
import utils.assets.AssetNames
import utils.assets.AssetReader
import utils.spock.WithTestContext

@Title("SearchResultsDeserializer parsing")
class SearchResultsDeserializerSpecs extends AndroidSpecification {

    // Members

    @WithTestContext
    private Context mContext;

    // Scenarios

    def "it should parses a valid json"() {

        given: "A valid json"
            String json = AssetReader.readJSON(mContext, AssetNames.SEARCH_RESULTS_RESPONSE)

        and: "Gson set up with the deserializer"
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(SearchResultsDeserializer.TYPE, new SearchResultsDeserializer());
            Gson gson = builder.create();

        when: "I parse the json"
            List<SearchResult> results = gson.fromJson(json, SearchResultsDeserializer.TYPE)

        then: "The results contain 2 elements"
            results.size() == 2

        and: "The first one should be Frozen the movie from 2013 and the identifier tt2294629"
            with(results.get(0)) {
                title == "Frozen"
                year == "2013"
                identifier == "tt2294629"
            }

        and: "The second one should be The Frozen Ground the movie from 2013 and the identifier tt2005374"
            with(results.get(1)) {
                title == "The Frozen Ground"
                year == "2013"
                identifier == "tt2005374"
            }
    }

    def "it should return null if it cannot parse the json"() {
        given: "An invalid json"
            String json = "-ASD{190ÖD∆2094PIÖ}Å"

        and: "Gson set up with the deserializer"
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(SearchResultsDeserializer.TYPE, new SearchResultsDeserializer());
            Gson gson = builder.create();

        when: "I parse the json"
            List<SearchResult> results = gson.fromJson(json, SearchResultsDeserializer.TYPE)

        then: "Then I receive null"
            results == null
    }

}
