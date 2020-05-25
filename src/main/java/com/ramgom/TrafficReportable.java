package com.ramgom;

public interface TrafficReportable {

    void report(int droneId, String station, long timeInSecond, double speedMetersSeconds);
}
