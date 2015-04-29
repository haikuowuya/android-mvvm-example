package de.rheinfabrik.mvvm_example.viewmodels;

import java.util.List;
import java.util.concurrent.TimeUnit;

import de.rheinfabrik.mvvm_example.controller.SearchResultController;
import de.rheinfabrik.mvvm_example.network.models.SearchResult;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

import static de.rheinfabrik.mvvm_example.utils.rx.RxCachedSample.cachedSample;

/**
 * ViewModel responsible for the search screen.
 */
public final class SearchViewModel {

    // Observables

    /**
     * Emits the received search results.
     */
    public final Observable<List<SearchResult>> searchResults() {
        return mSearchResultsSubject.asObservable();
    }

    /**
     * Emits a value if the search should be shown or hidden.
     */
    public final Observable<Boolean> showSearch() {
        return mShowSearchSubject.asObservable();
    }

    // Commands

    /**
     * Emit a string to this command to start a search.
     */
    public final PublishSubject<String> searchCommand = PublishSubject.create();

    /**
     * Toggle the visibility of the search.
     */
    public final PublishSubject<Void> toggleSearchVisibilityCommand = PublishSubject.create();

    // Members

    private final BehaviorSubject<List<SearchResult>> mSearchResultsSubject = BehaviorSubject.create();
    private final BehaviorSubject<Boolean> mShowSearchSubject = BehaviorSubject.create(false);

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
                .subscribe(mSearchResultsSubject::onNext);

        // Search visibility
        cachedSample(mShowSearchSubject, toggleSearchVisibilityCommand)

                /* Invert value */
                .map(shown -> !shown)
                .subscribe(mShowSearchSubject::onNext);
    }

}
