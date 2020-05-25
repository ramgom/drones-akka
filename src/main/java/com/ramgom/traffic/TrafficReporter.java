package com.ramgom.traffic;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import org.springframework.stereotype.Component;
import com.ramgom.SpringExtension;
import com.ramgom.TrafficReportable;

import javax.annotation.PostConstruct;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class TrafficReporter implements TrafficReportable {

    private final SpringExtension ext;

    private final ActorSystem system;

    private ActorRef reporter;

    public TrafficReporter(ActorSystem system, SpringExtension ext) {
        this.system = system;
        this.ext = ext;
    }

    @PostConstruct
    protected void init() {
        reporter = system.actorOf(ext.props("fileReporter"));
    }

    @Override
    public void report(int droneId, String station, long timeInSecond, double speedMetersSeconds) {
        reporter.tell(TrafficReportInfo.builder()
                .droneId(droneId)
                .station(station)
                .timeInSecond(timeInSecond)
                .speedMetersSeconds(speedMetersSeconds)
                .status(generateTrafficStatus())
                .build(),
                ActorRef.noSender()
        );
    }

    private TrafficStatus generateTrafficStatus() {
        return TrafficStatus.values()[ThreadLocalRandom.current().nextInt(0, 3)];
    }
}
