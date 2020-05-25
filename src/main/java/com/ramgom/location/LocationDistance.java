package com.ramgom.location;

import com.eatthepath.jvptree.DistanceFunction;

public class LocationDistance implements DistanceFunction<Location> {

    private static final double EARTH_RADIUS = 6371e3; // meters

    public double getDistance(final Location firstPoint, final Location secondPoint) {
        final double lat1 = Math.toRadians(firstPoint.getLatitude());
        final double lon1 = Math.toRadians(firstPoint.getLongitude());
        final double lat2 = Math.toRadians(secondPoint.getLatitude());
        final double lon2 = Math.toRadians(secondPoint.getLongitude());

        final double angle = 2 * Math.asin(Math.min(1, Math.sqrt(haversine(lat2 - lat1) + Math.cos(lat1) * Math.cos(lat2) * haversine(lon2 - lon1))));

        return angle * LocationDistance.EARTH_RADIUS;
    }

    private static double haversine(final double theta) {
        final double x = Math.sin(theta / 2);
        return (x * x);
    }
}
