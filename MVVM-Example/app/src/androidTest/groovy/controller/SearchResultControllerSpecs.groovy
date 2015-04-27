package controller

import android.content.Context
import com.google.gson.GsonBuilder
import de.rheinfabrik.mvvm_example.controller.SearchResultController
import de.rheinfabrik.mvvm_example.network.OMDBApiService
import de.rheinfabrik.mvvm_example.network.deserializer.SearchResultsDeserializer
import de.rheinfabrik.mvvm_example.network.models.SearchResult
import rx.Observable
import spock.lang.Specification
import spock.lang.Title
import utils.spock.WithTestContext

import static utils.assets.AssetNames.SEARCH_RESULTS_RESPONSE
import static utils.assets.AssetReader.readJSON

@Title("getSearchResults()")
class SearchResultControllerGetSearchResultsSpecs extends Specification {

    // Members

    @WithTestContext
    private Context mContext;

    // Scenarios

    def "it should emit an empty array if no results can be found"() {

        given: "A fake api service emitting null"
            OMDBApiService service = Mock(OMDBApiService)
            service.getSearchResults("frozen") >> Observable.just(null)

        and: "A search result controller with that service"
            SearchResultController controller = new SearchResultController(service)

        when: "I ask for the search results"
            List<SearchResult> results = controller.getSearchResults("frozen").toBlocking().firstOrDefault(null)

        then: "The results should be an empty list"
            results != null
            results.size() == 0
    }

    def "it should emit the fetched results"() {

        given: "A valid search results response"
            String json = readJSON(mContext, SEARCH_RESULTS_RESPONSE)
            GsonBuilder gsonBuilder = new GsonBuilder()
            gsonBuilder.registerTypeAdapter(SearchResultsDeserializer.TYPE, new SearchResultsDeserializer())
            List<SearchResult> searchResults = gsonBuilder.create().fromJson(json, SearchResultsDeserializer.TYPE)

        and: "A fake api service emitting the results"
            OMDBApiService service = Mock(OMDBApiService)
            service.getSearchResults("frozen") >> Observable.just(searchResults)

        and: "A search result controller with that service"
            SearchResultController controller = new SearchResultController(service)

        when: "I ask for the search results"
            List<SearchResult> results = controller.getSearchResults("frozen").toBlocking().firstOrDefault(null)

        then: "The results should contain 2 items"
            results != null
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

    def "it should emit the error if there was one"() {

        given: "A mocked api"
            OMDBApiService api = Mock(OMDBApiService)
            api.getSearchResults("frozen") >> Observable.error(new Throwable())

        and: "A search result controller with that service"
            SearchResultController controller = new SearchResultController(api)

        when: "I ask for the search results"
            controller.getSearchResults("frozen").toBlocking().firstOrDefault(null)

        then: "An exception is thrown"
            thrown(Throwable)
    }

}