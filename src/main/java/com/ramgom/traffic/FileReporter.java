package com.ramgom.traffic;

import akka.actor.AbstractActor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Scope("prototype")
public class FileReporter extends AbstractActor {

    private static final Logger trafficReportLogger = LoggerFactory.getLogger("TrafficReportLogger");

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(TrafficReportInfo.class, report -> {
                    log.info("DroneId={} Station={} Time(s)={} Speed(m/s)={} Traffic={}",
                            report.getDroneId(),
                            report.getStation(),
                            report.getTimeInSecond(),
                            report.getSpeedMetersSeconds(),
                            report.getStatus()
                    );

                    trafficReportLogger.info("\"{}\", \"{}\", \"{}\", \"{}\", \"{}\"",
                            report.getDroneId(),
                            report.getStation(),
                            report.getTimeInSecond(),
                            report.getSpeedMetersSeconds(),
                            report.getStatus()
                    );

                })
                .build();
    }
}
