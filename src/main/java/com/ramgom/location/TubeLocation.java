package com.ramgom.location;

import lombok.Getter;

@Getter
public class TubeLocation extends Location {
    private final String name;

    public TubeLocation(String name, double latitude, double longitude) {
        super(latitude, longitude);

        this.name = name;
    }
}
