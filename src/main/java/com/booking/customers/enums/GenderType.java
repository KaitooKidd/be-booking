package com.booking.customers.enums;

public enum GenderType {
    PRIVATE("private"),
    MALE("male"),
    FEMALE("female");

    public final String name;

    GenderType(String name) {
        this.name = name;
    }

}
