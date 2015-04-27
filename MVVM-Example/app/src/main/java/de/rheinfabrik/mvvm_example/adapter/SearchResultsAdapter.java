package de.rheinfabrik.mvvm_example.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import de.rheinfabrik.mvvm_example.R;
import de.rheinfabrik.mvvm_example.adapter.viewholder.SearchResultViewHolder;
import de.rheinfabrik.mvvm_example.network.models.SearchResult;

/**
 * Adapter for displaying search results.
 */
public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultViewHolder> {

    // Members

    private final List<SearchResult> mResults;
    private final Context mContext;

    // Public Api

    /**
     * Default constructor.
     */
    public SearchResultsAdapter(Context context, List<SearchResult> results) {
        super();

        mContext = context;
        mResults = results;
    }

    // RecyclerView.Adapter

    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view_search_result, viewGroup, false);
        return new SearchResultViewHolder(mContext, itemView);
    }

    @Override
    public int getItemCount() {
        return mResults == null ? 0 : mResults.size();
    }

    @Override
    public void onBindViewHolder(SearchResultViewHolder searchResultViewHolder, int position) {

        // Rebind
        searchResultViewHolder.bind();

        // Send current data to view model
        SearchResult searchResult = mResults.get(position);
        searchResultViewHolder.getViewModel().setSearchResultCommand.onNext(searchResult);
    }
}
