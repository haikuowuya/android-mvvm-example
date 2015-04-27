package viewmodels

import android.content.Context
import android.content.Intent
import android.test.UiThreadTest
import android.text.Spanned
import com.andrewreitz.spock.android.WithContext
import de.rheinfabrik.mvvm_example.activities.DetailsActivity
import de.rheinfabrik.mvvm_example.activities.extras.DetailsActivityExtras
import de.rheinfabrik.mvvm_example.network.models.SearchResult
import de.rheinfabrik.mvvm_example.viewmodels.SearchResultViewModel
import icepick.Icepick
import spock.lang.Specification
import spock.lang.Timeout
import spock.lang.Title

@Title("openDetailsSubject")
class SearchResultViewModelOpenDetailsSubjectSpecs extends Specification {

    // Members

    @WithContext
    private Context mContext

    // Scenarios

    @Timeout(1)
    @UiThreadTest
    def "it should send me the correct intent when I ask fot it"() {

        given: "A search results item"
            SearchResult item = new SearchResult()
            item.identifier = "identifier"

        and: "A view model"
            SearchResultViewModel viewModel = new SearchResultViewModel(mContext)

        and: "I send the item to view model"
            viewModel.setSearchResultCommand.onNext(item)

        and: "I am listening for details intents"
            Intent intent = null
            viewModel.onOpenDetailsSubject
                    .subscribe({ i -> intent = i })

        when: "I want to open the details"
            viewModel.openDetailsCommand.onNext(null)

        then: "I receive an intent"
            intent != null

        and: "It has the correct component class"
            intent.getComponent().getClassName() == DetailsActivity.class.getName()

        and: "it has the correct identifier"
            DetailsActivityExtras extras = new DetailsActivityExtras();
            Icepick.restoreInstanceState(extras, intent.getExtras());
            extras.searchResult == item
    }
}

@Title("textSubject")
class SearchResultViewModelTextSubjectSpecs extends Specification {

    // Members

    @WithContext
    private Context mContext

    // Scenarios

    @UiThreadTest
    def "it should build the correct string"() {

        given: "A search results item"
            SearchResult searchResult = new SearchResult()
            searchResult.title = "Title"
            searchResult.year = "2015"

        and: "A view model"
            SearchResultViewModel viewModel = new SearchResultViewModel(mContext)

        when: "I send the item to view model"
            viewModel.setSearchResultCommand.onNext(searchResult)

        and: "I ask for the text"
            Spanned text = viewModel.textSubject.toBlocking().first()

        then: "It should match my expected text"
            text.toString() == "Title (2015)"
    }

    @UiThreadTest
    def "it should emit / if there is no title"() {

        given: "A results without a title"
            SearchResult searchResult = new SearchResult()
            searchResult.year = "2015"

        and: "A view model"
            SearchResultViewModel viewModel = new SearchResultViewModel()

        when: "I send the item to view model"
            viewModel.setSearchResultCommand.onNext(searchResult)

        and: "I ask for the text"
            Spanned text = viewModel.textSubject.toBlocking().first()

        then: "It should be a placeholder text"
            text.toString() == "/"
    }

    @UiThreadTest
    def "it should emit only the title if there is no year"() {

        given: "A results without a title"
            SearchResult searchResult = new SearchResult()
            searchResult.title = "Title"

        and: "A view model"
            SearchResultViewModel viewModel = new SearchResultViewModel(mContext)

        when: "I send the item to view model"
            viewModel.setSearchResultCommand.onNext(searchResult)

        and: "I ask for the text"
            Spanned text = viewModel.textSubject.toBlocking().first()

        then: "It should be the title"
            text.toString() == "Title"
    }

}