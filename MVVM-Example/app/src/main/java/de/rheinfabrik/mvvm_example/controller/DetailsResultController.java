package de.rheinfabrik.mvvm_example.controller;

import de.rheinfabrik.mvvm_example.network.OMDBApiFactory;
import de.rheinfabrik.mvvm_example.network.OMDBApiService;
import de.rheinfabrik.mvvm_example.network.models.DetailsResult;
import de.rheinfabrik.mvvm_example.network.models.SearchResult;
import rx.Observable;

/**
 * Controller responsible for detail results.
 */
public class DetailsResultController {

    // Members

    private OMDBApiService mOMDBApiService;

    // Constructor

    /**
     * The default constructor.
     */
    public DetailsResultController() {
        this(OMDBApiFactory.newApi());
    }

    /**
     * The custom constructor. This one may be used within tests to inject a mocked service instance.
     */
    public DetailsResultController(OMDBApiService apiService) {
        super();

        mOMDBApiService = apiService;
    }

    /**
     * Fetches the details for a given search result.
     */
    public Observable<DetailsResult> getDetails(SearchResult searchResult) {
        return mOMDBApiService.getDetails(searchResult.identifier);
    }
}
