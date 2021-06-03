package com.jamp.elastic.model;

import java.util.Random;

public enum EventType {
    WORKSHOP, TECH_TALK;

    public static EventType random() {
        int index = new Random().nextInt(2);
        return values()[index];
    }
}
