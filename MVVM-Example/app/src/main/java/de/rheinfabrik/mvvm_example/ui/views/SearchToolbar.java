package de.rheinfabrik.mvvm_example.ui.views;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.EditText;

import butterknife.InjectView;
import butterknife.Views;
import de.rheinfabrik.mvvm_example.R;

/**
 * Toolbar subclass containing a text field for search input.
 */
public class SearchToolbar extends Toolbar {

    // Members

    @InjectView(R.id.search_edit_text)
    protected EditText mSearchEditTextView;

    // Constructor

    public SearchToolbar(Context context) {
        super(context);

        setUp();
    }

    public SearchToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);

        setUp();
    }

    public SearchToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setUp();
    }

    // Public Api

    /**
     * Call this to hide or show the search UI.
     */
    public void setSearchUIVisible(boolean showSearchUI) {
        if (showSearchUI) {
            mSearchEditTextView.setVisibility(VISIBLE);
        } else {
            mSearchEditTextView.setVisibility(GONE);
        }
    }

    /**
     * The underlying edit text used for search input.
     */
    public EditText getSearchEditTextView() {
        return mSearchEditTextView;
    }

    // Private Api

    // Do NOT refactor this to be inside the constructors!
    private void setUp() {

        // View
        inflate(getContext(), R.layout.toolbar_search, this);
        Views.inject(this);

        // Style
        setTitleTextColor(getContext().getResources().getColor(R.color.white));
        setBackgroundColor(getContext().getResources().getColor(R.color.dark_orchid));
        mSearchEditTextView.getBackground().setColorFilter(getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
    }
}
