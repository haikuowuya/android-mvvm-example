package network.models

import android.content.Context
import com.andrewreitz.spock.android.AndroidSpecification
import com.google.gson.Gson
import de.rheinfabrik.mvvm_example.network.models.SearchResult
import spock.lang.Title
import utils.spock.WithTestContext

import static utils.assets.AssetNames.SEARCH_RESULT
import static utils.assets.AssetReader.readJSON

@Title("JSON mapping")
class SearchResultJSONMappingSpec extends AndroidSpecification {

    // Members

    @WithTestContext
    private Context mContext;

    // Scenarios

    def "it should map the correct id"() {

        given: "A json string representing a SearchResult"
            String json = readJSON(mContext, SEARCH_RESULT)

        when: "I use Gson to create a SearchResult object from it"
            SearchResult searchResult = new Gson().fromJson(json, SearchResult.class)

        then: "The the identifier should match the one within the json string)"
            with(searchResult, {
                identifier == "tt0259974"
            })
    }

    def "it should map the correct year"() {

        given: "A json string representing a SearchResult"
            String json = readJSON(mContext, SEARCH_RESULT)

        when: "I use Gson to create a SearchResult object from it"
            SearchResult searchResult = new Gson().fromJson(json, SearchResult.class)

        then: "The year should match the one within the json string"
            with(searchResult, {
                year == "2000"
            })
    }

    def "it should map the correct title"() {

        given: "A json string representing a SearchResult"
            String json = readJSON(mContext, SEARCH_RESULT)

        when: "I use Gson to create a SearchResult object from it"
            SearchResult searchResult = new Gson().fromJson(json, SearchResult.class)

        then: "The title should match the one within the json string"
            with(searchResult, {
                title == "Digimon: The Movie"
            })
    }
}
