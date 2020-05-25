package com.ramgom.traffic;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TrafficReportInfo {

    private final int droneId;
    private final String station;
    private final long timeInSecond;
    private final double speedMetersSeconds;
    private final TrafficStatus status;

}
