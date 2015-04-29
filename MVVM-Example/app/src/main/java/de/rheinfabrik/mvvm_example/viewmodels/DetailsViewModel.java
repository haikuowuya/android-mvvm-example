package de.rheinfabrik.mvvm_example.viewmodels;

import de.rheinfabrik.mvvm_example.controller.DetailsResultController;
import de.rheinfabrik.mvvm_example.network.models.DetailsResult;
import de.rheinfabrik.mvvm_example.network.models.SearchResult;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

import static de.rheinfabrik.mvvm_example.utils.rx.RxCachedSample.cachedSample;

/**
 * View model for the details activity.
 */
public final class DetailsViewModel {

    // Observables

    /**
     * Emits the title of the item.
     */
    public final Observable<String> title() {
        return mTitleSubject.asObservable();
    }

    /**
     * Emits the plot of the item.
     */
    public final Observable<String> plot() {
        return mPlotSubject.asObservable();
    }

    /**
     * Emits the poster url of the item.
     */
    public final Observable<String> posterUrl() {
        return mPosterUrlSubject.asObservable();
    }

    // Commands

    /**
     * Send an item to this command to update the text etc.
     */
    public final PublishSubject<SearchResult> setItemCommand = PublishSubject.create();

    /**
     * Send a value to this command to load the required details.
     */
    public final PublishSubject<Void> loadDetailsCommand = PublishSubject.create();

    // Members

    private BehaviorSubject<String> mTitleSubject = BehaviorSubject.create();
    private BehaviorSubject<String> mPlotSubject = BehaviorSubject.create("");
    private BehaviorSubject<String> mPosterUrlSubject = BehaviorSubject.create();

    // Constructor

    /**
     * Default constructor.
     */
    public DetailsViewModel() {
        this(new DetailsResultController());
    }

    /**
     * Custom constructor.
     */
    public DetailsViewModel(DetailsResultController controller) {

        // Title
        setItemCommand
                .map(item -> item.title)
                .subscribe(mTitleSubject::onNext);

        // Loading
        Observable<DetailsResult> loadingObservable = cachedSample(setItemCommand, loadDetailsCommand)
                .concatMap(controller::getDetails)
                .retry();

        // Plot
        loadingObservable
                .map(result -> result.plot)
                .subscribe(mPlotSubject::onNext);

        // Poster URL
        loadingObservable
                .map(result -> result.posterUrl)
                .subscribe(mPosterUrlSubject::onNext);
    }
}
