package de.rheinfabrik.mvvm_example.controller;

import java.util.ArrayList;
import java.util.List;

import de.rheinfabrik.mvvm_example.network.OMDBApiFactory;
import de.rheinfabrik.mvvm_example.network.OMDBApiService;
import de.rheinfabrik.mvvm_example.network.models.SearchResult;
import rx.Observable;

/**
 * Controller for interacting with search results.
 */
public class SearchResultController {

    // Members

    private OMDBApiService mOMDBApiService;

    // Constructor

    /**
     * The default constructor.
     */
    public SearchResultController() {
        this(OMDBApiFactory.newApi());
    }

    /**
     * The custom constructor. This one may be used within tests to inject a mocked service instance.
     */
    public SearchResultController(OMDBApiService apiService) {
        super();

        mOMDBApiService = apiService;
    }

    // Public Api

    /**
     * Fetches the proper search results for the given search term. Returns an empty list if no search results could be found.
     */
    public Observable<List<SearchResult>> getSearchResults(String searchTerm) {
        return mOMDBApiService
                .getSearchResults(searchTerm)
                .map(searchResults -> searchResults == null ? new ArrayList<>() : searchResults);
    }
}
