package com.ramgom.configuration;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@Getter
public class ConfigurationLoader {

    @Autowired
    private AppConfiguration configuration;

    private List<TubeConfiguration> tubeStations;

    private Map<Integer, List<DroneConfiguration>> droneConfigurations;

    @PostConstruct
    public void init() {
        tubeStations = loadCsvValues(configuration.getTubeFile(), TubeConfiguration.class);

        droneConfigurations = new LinkedMultiValueMap<>();
        for (String file: configuration.getDroneFiles()) {
            List<DroneConfiguration> droneConfig = loadCsvValues(file, DroneConfiguration.class);
            if (!droneConfig.isEmpty()) {
                droneConfigurations.put(droneConfig.get(0).getId(), droneConfig);
            }
        }
    }

    private <T> List<T> loadCsvValues(String fileName, Class<T> type) {
        try {
            return new CsvToBeanBuilder(
                    new InputStreamReader(new ClassPathResource(fileName).getInputStream()))
                    .withType(type).build().parse();
        } catch (IOException e) {
            ConfigurationLoader.log.error("Unable to load rga.asianlogic.configuration file {}", fileName, e);

            return Collections.emptyList();
        }
    }
}
