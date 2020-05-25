package com.ramgom.configuration;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Setter
@Getter
public class DroneConfiguration {

    @CsvBindByPosition(position = 0)
    private Integer id;

    @CsvBindByPosition(position = 1)
    private double latitude;

    @CsvBindByPosition(position = 2)
    private double longitude;

    @CsvCustomBindByPosition(position = 3, converter = LocalDateTimeConverter.class)
    private LocalTime time;
}
