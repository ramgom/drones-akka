package com.ramgom.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "drones", ignoreUnknownFields = false)
@Getter
@Setter
public class AppConfiguration {

    private String tubeFile;

    private String trafficReportFile;

    private List<String> droneFiles;

    private boolean showRealTime;
}
