package com.ramgom;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.ramgom.drone.DroneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.ramgom.configuration.ConfigurationLoader;
import com.ramgom.configuration.DroneConfiguration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class Dispatcher {

    private final ConfigurationLoader configuration;

    private final ActorSystem system;

    private final SpringExtension ext;

    private ActorRef droneController;

    @PostConstruct
    protected void init() {
        droneController = system.actorOf(ext.props("droneService"));
    }

    public void start() {
        log.info("Starting Dispatcher");

        for (Map.Entry<Integer, List<DroneConfiguration>> entry: configuration.getDroneConfigurations().entrySet()) {
            droneController.tell(DroneService.DroneServiceMessage.builder()
                    .droneId(entry.getKey())
                    .configurations(entry.getValue())
                    .build(), ActorRef.noSender()
            );
        }
    }
}
