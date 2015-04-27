package de.rheinfabrik.mvvm_example.network;

import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import de.rheinfabrik.mvvm_example.network.deserializer.SearchResultsDeserializer;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Class used to generate OMDBApiService instances.
 */
public class OMDBApiFactory {

    // Constants

    private static final String API_ENDPOINT = "http://www.omdbapi.com";

    // Public API

    /**
     * Creates a fresh OMDBApiService instance.
     */
    public static OMDBApiService newApi() {
        return newApi(new OkClient(new OkHttpClient()));
    }

    /**
     * Creates a fresh OMDBApiService instance with the given client.
     */
    public static OMDBApiService newApi(Client client) {

        // Gson
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(SearchResultsDeserializer.TYPE, new SearchResultsDeserializer());

        // Setup rest adapter.
        RestAdapter.Builder restAdapterBuilder = new RestAdapter
                .Builder()
                .setClient(client)
                .setConverter(new GsonConverter(gsonBuilder.create()))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API_ENDPOINT);

        // Build API service
        return restAdapterBuilder.build().create(OMDBApiService.class);
    }

}
