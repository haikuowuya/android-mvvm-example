package de.rheinfabrik.mvvm_example.network.deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import de.rheinfabrik.mvvm_example.network.models.SearchResult;

/**
 * Deserializer used to deserialize a list of SearchResults.
 */
public class SearchResultsDeserializer implements JsonDeserializer<List<SearchResult>> {

    // Constants

    /**
     * The Type of List<SearchResult>.
     */
    public static final Type TYPE = new TypeToken<List<SearchResult>>() {
    }.getType();

    // JsonDeserializer

    @Override
    public List<SearchResult> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        try {

            // Get the "Search" array
            JsonElement searchElement = json.getAsJsonObject().get("Search");

            // Parse it
            return new Gson().fromJson(searchElement.getAsJsonArray(), TYPE);
        } catch (Exception exception) {

            // On any error - return null
            return null;
        }
    }
}
