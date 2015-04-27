package controller

import android.content.Context
import com.google.gson.Gson
import de.rheinfabrik.mvvm_example.controller.DetailsResultController
import de.rheinfabrik.mvvm_example.network.OMDBApiService
import de.rheinfabrik.mvvm_example.network.models.DetailsResult
import de.rheinfabrik.mvvm_example.network.models.SearchResult
import rx.Observable
import spock.lang.Specification
import spock.lang.Title
import utils.spock.WithTestContext

import static utils.assets.AssetNames.DETAILS_RESULT
import static utils.assets.AssetReader.readJSON

@Title("getDetails()")
class DetailsResultControllerGetDetailsSpecs extends Specification {

    // Members

    @WithTestContext
    private Context mContext;

    // Scenarios

    def "it should emit the fetched result"() {

        given: "A valid details results json"
            String json = readJSON(mContext, DETAILS_RESULT)

        and: "A fake api service emitting the detail result"
            OMDBApiService service = Mock(OMDBApiService)
            service.getDetails(_) >> Observable.just(new Gson().fromJson(json, DetailsResult.class))

        and: "A details result controller with that service"
            DetailsResultController controller = new DetailsResultController(service)

        when: "I ask for the details"
            DetailsResult detailsResult = controller.getDetails(new SearchResult()).toBlocking().firstOrDefault(null)

        then: "The result should have the correct poster url"
            detailsResult.posterUrl == "http://ia.media-imdb.com/images/M/MV5BMTQyOTg0MjYzMF5BMl5BanBnXkFtZTgwNDgwMzcwMzE@._V1_SX300.jpg"

        and: "The result should have the correct plot"
            detailsResult.plot == "Set in the Shinigami technical school for weapon meisters, the series revolves around 3 groups of each a weapon meister and a human weapon. Trying to make the latter a \"Death Scythe\" and thus fit for use by the Shinigami, they must collect the souls of 99 evil humans and 1 witch."
    }

    def "it should emit the error if there was one"() {

        given: "A mocked api"
            OMDBApiService api = Mock(OMDBApiService)
            api.getSearchResults(_) >> Observable.error(new Throwable())

        and: "A search result controller with that service"
            DetailsResultController controller = new DetailsResultController(api)

        when: "I ask for the search results"
            controller.getDetails(new SearchResult()).toBlocking().firstOrDefault(null)

        then: "An exception is thrown"
            thrown(Throwable)
    }

}