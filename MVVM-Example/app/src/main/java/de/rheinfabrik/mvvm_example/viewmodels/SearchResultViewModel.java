package de.rheinfabrik.mvvm_example.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;

import de.rheinfabrik.mvvm_example.network.models.SearchResult;
import de.rheinfabrik.mvvm_example.utils.IntentFactory;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

import static de.rheinfabrik.mvvm_example.utils.rx.RxCachedSample.cachedSample;

/**
 * ViewModel for a single search result item.
 */
public final class SearchResultViewModel {

    // Observable

    /**
     * Emits the text that should be displayed
     */
    public final Observable<Spanned> text() {
        return mTextSubject.asObservable();
    }

    /**
     * Emits the correct intent to open the details activity.
     */
    public final Observable<Intent> onOpenDetails() {
        return mOnOpenDetailsSubject.asObservable();
    }

    // Commands

    /**
     * Send item to this command to trigger changes to the text subject.
     */
    public final PublishSubject<SearchResult> setSearchResultCommand = PublishSubject.create();

    /**
     * Send null to this command to request a details intent.
     */
    public final PublishSubject<Void> openDetailsCommand = PublishSubject.create();

    // Members

    private final BehaviorSubject<Spanned> mTextSubject = BehaviorSubject.create();
    private final PublishSubject<Intent> mOnOpenDetailsSubject = PublishSubject.create();

    // Constructor

    /**
     * The default constructor.
     */
    public SearchResultViewModel(Context context) {

        // Text
        setSearchResultCommand

                /* e.g. Frozen (2012) */
                .map(searchResult -> {
                    if (searchResult.title == null) {
                        return new SpannableString("/");
                    }

                    StringBuilder builder = new StringBuilder("<b>" + searchResult.title + "</b>");
                    if (searchResult.year != null) {
                        builder.append(" ").append("(").append(searchResult.year).append(")");
                    }

                    return Html.fromHtml(builder.toString());
                })

                /* Resubscribe on error */
                .retry()
                .subscribe(mTextSubject::onNext);

        // Details
        cachedSample(setSearchResultCommand, openDetailsCommand)
                .map(item -> IntentFactory.newDetailsActivityIntent(context, item))
                .subscribe(mOnOpenDetailsSubject::onNext);
    }
}
