package com.jamp.elastic.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class Event {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("eventType")
    @Expose
    private EventType eventType;

    @SerializedName("date")
    @Expose
    private Date date;

    @SerializedName("place")
    @Expose
    private String place;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("subTopics")
    @Expose
    private List<String> subTopics;

}
