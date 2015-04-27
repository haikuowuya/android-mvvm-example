package de.rheinfabrik.mvvm_example.network.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Class representing a single search result within a search API call.
 */
public class DetailsResult implements Serializable {

    // Properties

    /**
     * The poster of this result.
     */
    @SerializedName("Poster")
    public String posterUrl;

    /**
     * The plot of this result.
     */
    @SerializedName("Plot")
    public String plot;

}
