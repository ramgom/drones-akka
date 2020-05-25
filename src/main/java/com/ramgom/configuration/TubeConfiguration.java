package com.ramgom.configuration;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TubeConfiguration {

    @CsvBindByPosition(position = 0)
    private String name;

    @CsvBindByPosition(position = 1)
    private double latitude;

    @CsvBindByPosition(position = 2)
    private double longitude;
}
