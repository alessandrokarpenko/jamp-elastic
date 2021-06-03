package com.jamp.elastic.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class AllSearchResponse {

    @SerializedName("took")
    @Expose
    private int took;

    @SerializedName("timed_out")
    @Expose
    private boolean timeOut;

    @SerializedName("hits")
    @Expose
    private Hits hits;
}