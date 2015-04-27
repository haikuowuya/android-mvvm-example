package viewmodels

import android.content.Context
import com.andrewreitz.spock.android.AndroidSpecification
import com.google.gson.GsonBuilder
import de.rheinfabrik.mvvm_example.controller.SearchResultController
import de.rheinfabrik.mvvm_example.network.deserializer.SearchResultsDeserializer
import de.rheinfabrik.mvvm_example.network.models.SearchResult
import de.rheinfabrik.mvvm_example.viewmodels.SearchViewModel
import rx.Observable
import spock.lang.Title
import utils.spock.WithTestContext

import static utils.assets.AssetNames.SEARCH_RESULTS_RESPONSE
import static utils.assets.AssetReader.readJSON

@Title("onSearchResultsReceivedSubject")
class SearchViewModelOnSearchResultsReceivedSpecs extends AndroidSpecification {

    // Members

    @WithTestContext
    private Context mContext

    // Scenarios

    def "it should emit the correct items when sending frozen to the search command"() {

        given: "A valid search results response"
            String json = readJSON(mContext, SEARCH_RESULTS_RESPONSE)
            GsonBuilder gsonBuilder = new GsonBuilder()
            gsonBuilder.registerTypeAdapter(SearchResultsDeserializer.TYPE, new SearchResultsDeserializer())
            List<SearchResult> searchResults = gsonBuilder.create().fromJson(json, SearchResultsDeserializer.TYPE)

        and: "A fake search controller"
            SearchResultController controller = Mock(SearchResultController)
            controller.getSearchResults("frozen") >> Observable.just(searchResults)

        and: "A view model with that controller"
            SearchViewModel viewModel = new SearchViewModel(controller)

        when: "I send frozen to the view model"
            viewModel.searchCommand.onNext("frozen");

        and: "I ask for the search results"
            List<SearchResult> results = viewModel.onSearchResultsReceivedSubject.toBlocking().firstOrDefault(null)

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
}

@Title("onSearchResultsReceivedSubject")
class SearchViewModelToogleSearchVisibilitySpecs extends AndroidSpecification {

    // Scenarios

    def "it should emit false by default when subscribing to onShowSearchSubject"() {

        given: "A search view model"
            SearchViewModel searchViewModel = new SearchViewModel();

        when: "I subscribe to the onShowSearchSubject"
            boolean showSearch = searchViewModel.onShowSearchSubject.toBlocking().first();

        then: "I receive the value false"
            !showSearch
    }

    def "it should emit true if I call toggle the first time"() {

        given: "A search view model"
            SearchViewModel searchViewModel = new SearchViewModel();

        when: "I call toggle"
            searchViewModel.toggleSearchVisibilityCommand.onNext(null)

        and: "I subscribe to the onShowSearchSubject"
            boolean showSearch = searchViewModel.onShowSearchSubject.toBlocking().first();

        then: "I receive the value true"
            showSearch
    }

    def "it should emit false if I call toggle twice"() {

        given: "A search view model"
            SearchViewModel searchViewModel = new SearchViewModel();

        when: "I call toggle"
            searchViewModel.toggleSearchVisibilityCommand.onNext(null)

        and: "I call toggle again"
            searchViewModel.toggleSearchVisibilityCommand.onNext(null)

        and: "I subscribe to the onShowSearchSubject"
            boolean showSearch = searchViewModel.onShowSearchSubject.toBlocking().first();

        then: "I receive the value false"
            !showSearch
    }
}