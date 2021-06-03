package com.jamp.elastic;

import com.github.javafaker.Faker;
import com.jamp.elastic.api.high.HighLevelRestApiClient;
import com.jamp.elastic.api.low.LowLevelRestApiClient;
import com.jamp.elastic.model.Event;
import com.jamp.elastic.model.EventType;

import java.util.Arrays;

public class Application {

    private static final Faker faker = new Faker();


    public static void main(String[] args) {

//        new LowLevelRestApiClient().createIndex("qwertyuiop");
//        new LowLevelRestApiClient().createEvent(createEvent());
//        new LowLevelRestApiClient().searchAllEvents();
//        new LowLevelRestApiClient().searchEventsWithTitle("Title 1");
//        new LowLevelRestApiClient().searchWorkshopEvents();
//        new LowLevelRestApiClient().searchRangeAndMatch();
//
//        new HighLevelRestApiClient().createIndex("zxcvbnm");
//        new HighLevelRestApiClient().searchAllEvents();
//        new HighLevelRestApiClient().searchEventsWithTitle("Title 1");
//        new HighLevelRestApiClient().searchWorkshopEvents();
//        new HighLevelRestApiClient().searchRangeAndMatch();
//        new HighLevelRestApiClient().bulkDelete();

    }

    private static Event createEvent() {
        return Event.builder()
                .title(faker.job().title())
                .eventType(EventType.random())
                .description(faker.food().ingredient())
                .place(faker.address().city())
                .date(faker.date().birthday())
                .subTopics(Arrays.asList(faker.job().title(), faker.job().title())).build();
    }
}
