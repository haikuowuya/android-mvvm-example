package de.rheinfabrik.mvvm_example.viewmodels;

import java.util.List;
import java.util.concurrent.TimeUnit;

import de.rheinfabrik.mvvm_example.controller.SearchResultController;
import de.rheinfabrik.mvvm_example.network.models.SearchResult;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

import static de.rheinfabrik.mvvm_example.utils.rx.RxCachedSample.cachedSample;

/**
 * ViewModel responsible for the search screen.
 */
public class SearchViewModel {

    // Subjects

    /**
     * Emits the received search results.
     */
    public final BehaviorSubject<List<SearchResult>> onSearchResultsReceivedSubject = BehaviorSubject.create();

    /**
     * Emits a value if the search should be shown or hidden.
     */
    public final BehaviorSubject<Boolean> onShowSearchSubject = BehaviorSubject.create(false);

    // Commands

    /**
     * Emit a string to this command to start a search.
     */
    public final PublishSubject<String> searchCommand = PublishSubject.create();

    /**
     * Toggle the visibility of the search.
     */
    public final PublishSubject<Void> toggleSearchVisibilityCommand = PublishSubject.create();

    // Constructor

    /**
     * Default constructor.
     */
    public SearchViewModel() {
        this(new SearchResultController());
    }

    /**
     * Custom constructor.
     */
    public SearchViewModel(SearchResultController controller) {
        super();

        // Bind searching
        searchCommand

                /* Discard the old signal if a new one comes in within 500 ms. */
                .debounce(500, TimeUnit.MILLISECONDS)

                /* Start the search */
                .concatMap(controller::getSearchResults)

                /* Resubscribe on error */
                .retry()
                .subscribe(onSearchResultsReceivedSubject::onNext);

        // Search visibility
        cachedSample(onShowSearchSubject, toggleSearchVisibilityCommand)

                /* Invert value */
                .map(shown -> !shown)
                .subscribe(onShowSearchSubject::onNext);
    }

}
