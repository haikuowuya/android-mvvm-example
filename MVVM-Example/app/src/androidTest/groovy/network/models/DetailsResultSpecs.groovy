package network.models

import android.content.Context
import com.andrewreitz.spock.android.AndroidSpecification
import com.google.gson.Gson
import de.rheinfabrik.mvvm_example.network.models.DetailsResult
import spock.lang.Title
import utils.spock.WithTestContext

import static utils.assets.AssetNames.DETAILS_RESULT
import static utils.assets.AssetReader.readJSON

@Title("Mapping")
class DetailsResultMappingSpecs extends AndroidSpecification {

    // Members

    @WithTestContext
    private Context mContext;

    // Scenarios

    def "it should map the correct poster url"() {

        given: "A json string representing a DetailsResult"
            String json = readJSON(mContext, DETAILS_RESULT)

        when: "I use Gson to create a DetailsResult object from it"
            DetailsResult detailsResult = new Gson().fromJson(json, DetailsResult.class)

        then: "The the identifier should match the one within the json string)"
            with(detailsResult, {
                posterUrl == "http://ia.media-imdb.com/images/M/MV5BMTQyOTg0MjYzMF5BMl5BanBnXkFtZTgwNDgwMzcwMzE@._V1_SX300.jpg"
            })
    }

    def "it should map the correct plot"() {

        given: "A json string representing a DetailsResult"
            String json = readJSON(mContext, DETAILS_RESULT)

        when: "I use Gson to create a DetailsResult object from it"
            DetailsResult detailsResult = new Gson().fromJson(json, DetailsResult.class)

        then: "The year should match the one within the json string"
            with(detailsResult, {
                plot == "Set in the Shinigami technical school for weapon meisters, the series revolves around 3 groups of each a weapon meister and a human weapon. Trying to make the latter a \"Death Scythe\" and thus fit for use by the Shinigami, they must collect the souls of 99 evil humans and 1 witch."
            })
    }

}