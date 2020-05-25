package com.ramgom.drone;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.ramgom.location.Location;
import com.ramgom.location.LocationDistance;

@Getter
@Slf4j
@RequiredArgsConstructor
public class Drone {

    private final int id;
    private Location location;
    private LocationDistance distanceCalculator = new LocationDistance();

    public double moveTo(double latitude, double longitude) {
        double distance = 0;
        Location newLocation = new Location(latitude, longitude);
        if (location != null) {
            distance = distanceCalculator.getDistance(location, newLocation);
        }

        location = newLocation;

        return distance;
    }
}
