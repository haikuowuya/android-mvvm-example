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
public class DetailsViewModel {

    // Subjects

    /**
     * Emits the title of the item.
     */
    public final BehaviorSubject<String> titleSubject = BehaviorSubject.create();

    /**
     * Emits the plot of the item.
     */
    public final BehaviorSubject<String> plotSubject = BehaviorSubject.create("");

    /**
     * Emits the poster url of the item.
     */
    public final BehaviorSubject<String> posterUrlSubject = BehaviorSubject.create();

    // Commands

    /**
     * Send an item to this command to update the text etc.
     */
    public final PublishSubject<SearchResult> setItemCommand = PublishSubject.create();

    /**
     * Send a value to this command to load the required details.
     */
    public final PublishSubject<Void> loadDetailsCommand = PublishSubject.create();

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
                .subscribe(titleSubject::onNext);

        // Loading
        Observable<DetailsResult> loadingObservable = cachedSample(setItemCommand, loadDetailsCommand)
                .concatMap(controller::getDetails)
                .retry();

        // Plot
        loadingObservable
                .map(result -> result.plot)
                .subscribe(plotSubject::onNext);

        // Poster URL
        loadingObservable
                .map(result -> result.posterUrl)
                .subscribe(posterUrlSubject::onNext);
    }
}
