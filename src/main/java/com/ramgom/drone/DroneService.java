package com.ramgom.drone;

import akka.actor.AbstractActor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.ramgom.configuration.DroneConfiguration;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DroneService extends AbstractActor {

    private static final LocalTime MAX_TIME = LocalTime.of(8, 10);

    private final DroneController droneController;

    @Builder
    static public class DroneServiceMessage {
        private final int droneId;
        private final List<DroneConfiguration> configurations;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(DroneServiceMessage.class, message -> this.fly(message)).build();
    }

    private void fly(DroneServiceMessage message) {
        List<DroneConfiguration> limitedConfigurations = new ArrayList<>(10);
        int capacity = 0;
        Drone drone = new Drone(message.droneId);
        LocalTime initialTime = null;

        for (DroneConfiguration config: message.configurations) {
            if (config.getTime().isAfter(MAX_TIME)) {
                break;
            }

            if (capacity++ == 10) {
                initialTime = droneController.move(drone, limitedConfigurations, initialTime);
                limitedConfigurations.clear();
                capacity = 0;
            }

            limitedConfigurations.add(config);
        }

        droneController.move(drone, limitedConfigurations, initialTime);
    }
}
