package com.ramgom.drone;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.ramgom.StationService;
import com.ramgom.TrafficReportable;
import com.ramgom.location.TubeLocation;
import com.ramgom.configuration.AppConfiguration;
import com.ramgom.configuration.DroneConfiguration;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@Component
@Slf4j
public class DroneController {
    private final StationService stationService;
    private final TrafficReportable trafficReporter;
    private boolean isRealTime;

    public DroneController(StationService stationService, TrafficReportable trafficReporter, AppConfiguration appConfiguration) {
        this.stationService = stationService;
        this.trafficReporter = trafficReporter;
        this.isRealTime = appConfiguration.isShowRealTime();

    }

    public LocalTime move(Drone drone, List<DroneConfiguration> configurations, LocalTime time) {

        LocalTime currentTime = time;

        for (DroneConfiguration config: configurations) {
            long timeInSeconds = 0;

            if (currentTime != null) {
                timeInSeconds = Duration.between(currentTime, config.getTime()).getSeconds();
            }

            if (isRealTime) {
                try {
                    Thread.sleep(timeInSeconds * 1000);
                } catch (InterruptedException e) {
                    log.info("Fly for drone {} was interrupted {}", drone.getId(), e.getMessage());
                }
            }

            double distance = drone.moveTo(config.getLatitude(), config.getLongitude());

            List<TubeLocation> nearbyStations = stationService.getNearbyStations(config.getLatitude(), config.getLongitude());

            double speed = (timeInSeconds != 0) ? distance/timeInSeconds:0;

            for (TubeLocation station: nearbyStations) {
                trafficReporter.report(drone.getId(), station.getName(), timeInSeconds, speed);
            }

            currentTime = config.getTime();
        }

        return currentTime;
    }
}
