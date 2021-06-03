package com.jamp.elastic.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class Hits {

    @SerializedName("hits")
    @Expose
    private List<Hit> hits;
}
