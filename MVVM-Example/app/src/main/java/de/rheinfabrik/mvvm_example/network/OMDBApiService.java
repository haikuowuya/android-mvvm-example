package de.rheinfabrik.mvvm_example.network;

import java.util.List;

import de.rheinfabrik.mvvm_example.network.models.DetailsResult;
import de.rheinfabrik.mvvm_example.network.models.SearchResult;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Interface that defines how to communicate with http://www.omdbapi.com/ .
 */
public interface OMDBApiService {

    // GET

    /**
     * Returns a cold observable emitting the results for a given search term.
     */
    @GET("/")
    public Observable<List<SearchResult>> getSearchResults(@Query("s") String searchTerm);

    /**
     * Returns a cold observable emitting the details for a given id (containing the full plot)
     */
    @GET("/?plot=full")
    public Observable<DetailsResult> getDetails(@Query("i") String identifier);

}
