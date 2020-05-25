package com.ramgom;

import com.eatthepath.jvptree.VPTree;
import org.springframework.stereotype.Service;
import com.ramgom.configuration.ConfigurationLoader;
import com.ramgom.configuration.TubeConfiguration;
import com.ramgom.location.Location;
import com.ramgom.location.LocationDistance;
import com.ramgom.location.TubeLocation;

import java.util.List;

@Service
public class StationService {

    private static final double NEARBY_DISTANCE = 350;

    private final VPTree<Location, TubeLocation> stations;

    public StationService(ConfigurationLoader configurationLoader) {

        stations = new VPTree<>(new LocationDistance());

        configurationLoader.getTubeStations()
                .stream()
                .map(this::toTubeLocation)
                .forEach(stations::add);
    }

    private TubeLocation toTubeLocation(TubeConfiguration configuration) {
        return new TubeLocation(configuration.getName(), configuration.getLatitude(), configuration.getLongitude());
    }

    public List<TubeLocation> getNearbyStations(double latitude, double longitude) {
        return stations.getAllWithinDistance(new Location(latitude, longitude), NEARBY_DISTANCE);
    }
}
