package network

import android.content.Context
import com.andrewreitz.spock.android.AndroidSpecification
import de.rheinfabrik.mvvm_example.network.OMDBApiFactory
import de.rheinfabrik.mvvm_example.network.OMDBApiService
import de.rheinfabrik.mvvm_example.network.models.DetailsResult
import de.rheinfabrik.mvvm_example.network.models.SearchResult
import retrofit.client.Response
import retrofit.mime.TypedString
import spock.lang.Title
import utils.assets.AssetNames
import utils.assets.AssetReader
import utils.retrofit.RetrofitTestClient
import utils.spock.WithTestContext

import static utils.assets.AssetNames.SEARCH_RESULTS_RESPONSE

@Title("getSearchResults()")
class ApiFactoryGetSearchResultsSpec extends AndroidSpecification {

    // Members

    @WithTestContext
    private Context mContext;

    // Scenarios

    def "it should build the correct request url"() {

        given: "A mocked api"
            RetrofitTestClient client = new RetrofitTestClient()
            OMDBApiService api = OMDBApiFactory.newApi(client)

        when: "I search for frozen"
            api.getSearchResults("frozen").toBlocking().first()

        then: "The url of the request is correct"
            with(client.tapedRequest) {
                url == "http://www.omdbapi.com/?s=frozen"
            }
    }

    def "it should emit the list of the received search results"() {

        given: "A faked valid response"
            String json = AssetReader.readJSON(mContext, SEARCH_RESULTS_RESPONSE)
            Response response = new Response("", 200, "", new ArrayList<>(), new TypedString(json))

        and: "A mocked api"
            OMDBApiService api = OMDBApiFactory.newApi(new RetrofitTestClient(response))

        when: "I search for frozen"
            List<SearchResult> results = api.getSearchResults("frozen").toBlocking().first()

        then: "The results contain 2 elements"
            results.size() == 2

        and: "The first one should be Frozen the movie from 2013 and the identifier tt2294629"
            with(results.get(0)) {
                title == "Frozen"
                year == "2013"
                identifier == "tt2294629"
            }

        and: "The second one should be The Frozen Ground the movie from 2013 and the identifier tt2005374"
            with(results.get(1)) {
                title == "The Frozen Ground"
                year == "2013"
                identifier == "tt2005374"
            }
    }
}

@Title("getDetails()")
class ApiFactoryGetDetailsResultsSpec extends AndroidSpecification {

    // Members

    @WithTestContext
    private Context mContext;

    // Scenarios

    def "it should build the correct request url"() {

        given: "A mocked api"
            RetrofitTestClient client = new RetrofitTestClient()
            OMDBApiService api = OMDBApiFactory.newApi(client)

        when: "I request the details of 1234"
            api.getDetails("1234").toBlocking().first()

        then: "The url of the request is correct"
            with(client.tapedRequest) {
                url == "http://www.omdbapi.com/?plot=full&i=1234"
            }
    }

    def "it should emit the result item"() {

        given: "A faked valid response"
            String json = AssetReader.readJSON(mContext, AssetNames.DETAILS_RESULT)
            Response response = new Response("", 200, "", new ArrayList<>(), new TypedString(json))

        and: "A mocked api"
            OMDBApiService api = OMDBApiFactory.newApi(new RetrofitTestClient(response))

        when: "I request the details of 1234"
            DetailsResult result = api.getDetails("1234").toBlocking().first()

        then: "The result should have the correct plot"
            result.plot == "Set in the Shinigami technical school for weapon meisters, the series revolves around 3 groups of each a weapon meister and a human weapon. Trying to make the latter a \"Death Scythe\" and thus fit for use by the Shinigami, they must collect the souls of 99 evil humans and 1 witch."

        and: "The result should have the correct poster url"
            result.posterUrl == "http://ia.media-imdb.com/images/M/MV5BMTQyOTg0MjYzMF5BMl5BanBnXkFtZTgwNDgwMzcwMzE@._V1_SX300.jpg"
    }
}
