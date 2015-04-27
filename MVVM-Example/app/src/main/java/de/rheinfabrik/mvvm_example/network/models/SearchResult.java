package de.rheinfabrik.mvvm_example.network.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Class representing a single search result within a search API call.
 */
public class SearchResult implements Serializable {

    // Properties

    /**
     * The IMDB identifier of this result.
     */
    @SerializedName("imdbID")
    public String identifier;

    /**
     * The title of this result.
     */
    @SerializedName("Title")
    public String title;

    /**
     * The year of this result.
     */
    @SerializedName("Year")
    public String year;

}
