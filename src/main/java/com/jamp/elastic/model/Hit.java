package com.jamp.elastic.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Hit {

    @SerializedName("_index")
    @Expose
    private String index;

    @SerializedName("_source")
    @Expose
    private Event event;
}
