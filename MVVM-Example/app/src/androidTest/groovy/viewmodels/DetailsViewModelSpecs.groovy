package viewmodels

import android.content.Context
import com.andrewreitz.spock.android.AndroidSpecification
import com.google.gson.Gson
import de.rheinfabrik.mvvm_example.controller.DetailsResultController
import de.rheinfabrik.mvvm_example.network.models.DetailsResult
import de.rheinfabrik.mvvm_example.network.models.SearchResult
import de.rheinfabrik.mvvm_example.viewmodels.DetailsViewModel
import rx.Observable
import spock.lang.Title
import utils.spock.WithTestContext

import static utils.assets.AssetNames.DETAILS_RESULT
import static utils.assets.AssetReader.readJSON

@Title("titleSubject")
class DetailsViewModelTitleSubjectSpecs extends AndroidSpecification {

    // Scenarios

    def "it should emit the correct title"() {

        given: "An item"
            SearchResult searchResult = new SearchResult()
            searchResult.title = "Testio"

        and: "A DetailsViewModel"
            DetailsViewModel viewModel = new DetailsViewModel()

        and: "I send the item to the view model"
            viewModel.setItemCommand.onNext(searchResult)

        when: "I ask for the title"
            String title = viewModel.title().toBlocking().first()

        then: "I receive the correct title"
            title == "Testio"
    }
}

@Title("plotSubject")
class DetailsViewModelPlotSubjectSpecs extends AndroidSpecification {

    // Members

    @WithTestContext
    private Context mContext

    // Scenarios

    def "it should emit the correct plot"() {

        given: "A valid details results response"
            String json = readJSON(mContext, DETAILS_RESULT)
            DetailsResult detailsResult = new Gson().fromJson(json, DetailsResult.class)

        and: "A search result object"
            SearchResult searchResult = new SearchResult()

        and: "A fake details controller"
            DetailsResultController controller = Mock(DetailsResultController)
            controller.getDetails(searchResult) >> Observable.just(detailsResult)

        and: "A view model with that controller"
            DetailsViewModel viewModel = new DetailsViewModel(controller)

        when: "I send the search result item to it"
            viewModel.setItemCommand.onNext(searchResult)

        and: "I request the details"
            viewModel.loadDetailsCommand.onNext(null)

        and: "I ask for the plot"
            String plot = viewModel.plot().toBlocking().first()

        then: "I should receive the correct plot"
            plot == "Set in the Shinigami technical school for weapon meisters, the series revolves around 3 groups of each a weapon meister and a human weapon. Trying to make the latter a \"Death Scythe\" and thus fit for use by the Shinigami, they must collect the souls of 99 evil humans and 1 witch."
    }
}

@Title("posterUrlSubject")
class DetailsViewModelPosterUrlSubjectSpecs extends AndroidSpecification {

    // Members

    @WithTestContext
    private Context mContext

    // Scenarios

    def "it should emit the correct poster url"() {

        given: "A valid details results response"
            String json = readJSON(mContext, DETAILS_RESULT)
            DetailsResult detailsResult = new Gson().fromJson(json, DetailsResult.class)

        and: "A search result object"
            SearchResult searchResult = new SearchResult()

        and: "A fake details controller"
            DetailsResultController controller = Mock(DetailsResultController)
            controller.getDetails(searchResult) >> Observable.just(detailsResult)

        and: "A view model with that controller"
            DetailsViewModel viewModel = new DetailsViewModel(controller)

        when: "I send the search result item to it"
            viewModel.setItemCommand.onNext(searchResult)

        and: "I request the details"
            viewModel.loadDetailsCommand.onNext(null)

        and: "I ask for the poster url"
            String posterUrl = viewModel.posterUrl().toBlocking().first()

        then: "I should receive the correct poster url"
            posterUrl == "http://ia.media-imdb.com/images/M/MV5BMTQyOTg0MjYzMF5BMl5BanBnXkFtZTgwNDgwMzcwMzE@._V1_SX300.jpg"
    }
}
