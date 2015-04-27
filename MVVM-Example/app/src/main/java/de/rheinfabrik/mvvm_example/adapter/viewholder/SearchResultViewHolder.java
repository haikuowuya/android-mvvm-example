package de.rheinfabrik.mvvm_example.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.InjectView;
import butterknife.Views;
import de.rheinfabrik.mvvm_example.R;
import de.rheinfabrik.mvvm_example.viewmodels.SearchResultViewModel;

import static de.rheinfabrik.mvvm_example.utils.rx.BindViewImmediate.bindViewImmediate;
import static rx.android.view.ViewObservable.bindView;
import static rx.android.view.ViewObservable.clicks;

/**
 * ViewHolder used in SearchResultsAdapter.
 */
public class SearchResultViewHolder extends RecyclerView.ViewHolder {

    // Members

    @InjectView(R.id.search_result_text)
    protected TextView mTextView;

    @InjectView(R.id.search_result_card)
    protected CardView mCardView;

    private final Context mContext;
    private final SearchResultViewModel mViewModel;

    // Constructor

    /**
     * The default constructor.
     */
    public SearchResultViewHolder(Context context, View itemView) {
        super(itemView);

        mContext = context;

        // ViewModel
        mViewModel = new SearchResultViewModel(mContext);

        // View
        Views.inject(this, itemView);
    }

    // Public Api

    /**
     * Bind all subject to it's views.
     */
    public void bind() {

        // Bind text
        bindViewImmediate(itemView, mViewModel.textSubject)
                .subscribe(mTextView::setText);

        // Bind clicks
        clicks(mCardView)
                .subscribe(x -> mViewModel.openDetailsCommand.onNext(null));

        // Bind open details
        bindView(itemView, mViewModel.onOpenDetailsSubject)
                .subscribe(mContext::startActivity);
    }

    /**
     * The underlying view model.
     */
    public SearchResultViewModel getViewModel() {
        return mViewModel;
    }
}
